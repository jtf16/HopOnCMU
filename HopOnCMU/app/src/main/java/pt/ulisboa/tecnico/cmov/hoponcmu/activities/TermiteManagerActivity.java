package pt.ulisboa.tecnico.cmov.hoponcmu.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import pt.inesc.termite.wifidirect.SimWifiP2pBroadcast;
import pt.inesc.termite.wifidirect.SimWifiP2pDevice;
import pt.inesc.termite.wifidirect.SimWifiP2pDeviceList;
import pt.inesc.termite.wifidirect.SimWifiP2pInfo;
import pt.inesc.termite.wifidirect.SimWifiP2pManager;
import pt.inesc.termite.wifidirect.SimWifiP2pManager.GroupInfoListener;
import pt.inesc.termite.wifidirect.SimWifiP2pManager.PeerListListener;
import pt.inesc.termite.wifidirect.service.SimWifiP2pService;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocket;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocketManager;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocketServer;
import pt.ulisboa.tecnico.cmov.hoponcmu.termite.SimWifiP2pBroadcastReceiver;

public abstract class TermiteManagerActivity extends ManagerActivity
        implements PeerListListener, GroupInfoListener {

    public static boolean mBound = false;
    static List<SimWifiP2pDevice> peersList = new ArrayList<>();
    static List<SimWifiP2pDevice> groupList = new ArrayList<>();
    private static SimWifiP2pManager mManager = null;
    private static SimWifiP2pManager.Channel mChannel = null;
    private static Messenger mService = null;
    SimWifiP2pBroadcastReceiver mReceiver;
    ServiceConnection mConnection = new ServiceConnection() {
        // callbacks for service binding, passed to bindService()

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = new Messenger(service);
            mManager = new SimWifiP2pManager(mService);
            mChannel = mManager.initialize(getApplication(), getMainLooper(), null);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mService = null;
            mManager = null;
            mChannel = null;
            mBound = false;
        }
    };
    private boolean mReceiverBound = false;
    private SimWifiP2pSocketServer mSrvSocket = null;
    private static SimWifiP2pSocket mCliSocket = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!mReceiverBound) {
            registerReceiver();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
        mReceiverBound = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mReceiverBound) {
            registerReceiver();
        }
    }

    public boolean isPeer(String peer) {
        for (SimWifiP2pDevice device : peersList) {
            //String devstr = "" + device.deviceName + " (" + device.getVirtIp() + ")\n";
            if (device.deviceName.equals(peer)) {
                return true;
            }
        }
        return false;
    }

    public List<SimWifiP2pDevice> getPeers() {
        return peersList;
    }

    public List<SimWifiP2pDevice> getGroup() {
        return groupList;
    }

    public void updatePeers(TermiteManagerActivity activity) {
        if (mBound) {
            mManager.requestPeers(mChannel, activity);
        } else {
            Toast.makeText(this, "Service not bound",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void updateGroupInfo(TermiteManagerActivity activity) {
        if (mBound) {
            mManager.requestGroupInfo(mChannel, activity);
        } else {
            Toast.makeText(this, "Service not bound",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPeersAvailable(SimWifiP2pDeviceList peers) {
        peersList = new ArrayList<>(peers.getDeviceList());
    }

    @Override
    public void onGroupInfoAvailable(SimWifiP2pDeviceList devices,
                                     SimWifiP2pInfo groupInfo) {
        groupList = new ArrayList<>();
        // compile list of network members
        StringBuilder peersStr = new StringBuilder();
        for (String deviceName : groupInfo.getDevicesInNetwork()) {
            SimWifiP2pDevice device = devices.getByName(deviceName);
            groupList.add(device);
        }
    }

    public void registerReceiver() {
        mReceiverBound = true;
        // initialize the Termite API
        SimWifiP2pSocketManager.Init(getApplicationContext());
        // register broadcast receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_STATE_CHANGED_ACTION);
        filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_PEERS_CHANGED_ACTION);
        filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_NETWORK_MEMBERSHIP_CHANGED_ACTION);
        filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_GROUP_OWNERSHIP_CHANGED_ACTION);
        mReceiver = new SimWifiP2pBroadcastReceiver(this);
        registerReceiver(mReceiver, filter);
    }

    public void bindService() {
        Intent intent = new Intent(getApplicationContext(), SimWifiP2pService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        mBound = true;

        // spawn the chat server background task
        new IncommingCommTask().executeOnExecutor(
                AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /*
     * Asynctasks implementing message exchange
     */

    public class IncommingCommTask extends AsyncTask<Void, String, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try {
                mSrvSocket = new SimWifiP2pSocketServer(
                        Integer.parseInt("10001"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    SimWifiP2pSocket sock = mSrvSocket.accept();
                    try {
                        BufferedReader sockIn = new BufferedReader(
                                new InputStreamReader(sock.getInputStream()));
                        String st = sockIn.readLine();
                        publishProgress(st);
                        sock.getOutputStream().write(("\n").getBytes());
                    } catch (IOException e) {
                        Log.d("Error reading socket:", e.getMessage());
                    } finally {
                        sock.close();
                    }
                } catch (IOException e) {
                    Log.d("Error socket:", e.getMessage());
                    break;
                    //e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Toast.makeText(getApplicationContext(), values[0], Toast.LENGTH_SHORT).show();
        }
    }

    public static class SendCommTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... msg) {
            try {
                for (SimWifiP2pDevice device : groupList) {
                    mCliSocket = new SimWifiP2pSocket(device.getVirtIp(),
                            device.getVirtPort());
                    mCliSocket.getOutputStream().write((msg[0] + "\n").getBytes());
                    BufferedReader sockIn = new BufferedReader(
                            new InputStreamReader(mCliSocket.getInputStream()));
                    sockIn.readLine();
                    mCliSocket.close();
                }
            } catch (UnknownHostException e) {
                return "Unknown Host:" + e.getMessage();
            } catch (IOException e) {
                return "IO error:" + e.getMessage();
            }
            mCliSocket = null;
            return null;
        }
    }
}
