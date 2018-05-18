package pt.ulisboa.tecnico.cmov.hoponcmu.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pt.inesc.termite.wifidirect.SimWifiP2pBroadcast;
import pt.inesc.termite.wifidirect.SimWifiP2pDevice;
import pt.inesc.termite.wifidirect.SimWifiP2pDeviceList;
import pt.inesc.termite.wifidirect.SimWifiP2pManager;
import pt.inesc.termite.wifidirect.service.SimWifiP2pService;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocketManager;
import pt.ulisboa.tecnico.cmov.hoponcmu.termite.SimWifiP2pBroadcastReceiver;

public abstract class TermiteManagerActivity extends ManagerActivity
        implements SimWifiP2pManager.PeerListListener {

    static List<SimWifiP2pDevice> peersList = new ArrayList<>();

    private SimWifiP2pManager mManager = null;
    private SimWifiP2pManager.Channel mChannel = null;
    private Messenger mService = null;
    SimWifiP2pBroadcastReceiver mReceiver;

    ServiceConnection mConnection = new ServiceConnection() {
        // callbacks for service binding, passed to bindService()

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = new Messenger(service);
            mManager = new SimWifiP2pManager(mService);
            mChannel = mManager.initialize(getApplication(), getMainLooper(), null);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mManager = null;
            mChannel = null;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        termiteReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregisterReceiver(mReceiver);
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

    public void updatePeers(TermiteManagerActivity activity) {
        mManager.requestPeers(mChannel, activity);
    }

    @Override
    public void onPeersAvailable(SimWifiP2pDeviceList peers) {
        peersList = new ArrayList<>(peers.getDeviceList());
    }

    public void termiteReceiver() {
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

        Intent intent = new Intent(getApplicationContext(), SimWifiP2pService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }
}
