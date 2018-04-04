package pt.ulisboa.tecnico.cmov.hoponcmu.communication;

import android.os.AsyncTask;
import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import pt.ulisboa.tecnico.cmov.hoponcmu.activities.ManagerActivity;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.Command;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;

public class CommunicationTask extends AsyncTask<Void, Void, Response> {

    private ManagerActivity activity;
    private Command command;

    public CommunicationTask(ManagerActivity activity, Command command) {
        this.activity = activity;
        this.command = command;
    }

    @Override
    protected Response doInBackground(Void[] voids) {
        Socket server = null;
        Response response = null;
        try {
            server = new Socket("10.0.2.2", 9090);

            ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
            oos.writeObject(command);

            ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
            response = (Response) ois.readObject();

            oos.close();
            ois.close();
        } catch (Exception e) {
            Log.d(CommunicationTask.class.getName(), "CommunicationTask failed..." + e.getMessage());
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (Exception e) {
                }
            }
        }
        return response;
    }

    @Override
    protected void onPostExecute(Response o) {
        if (o != null) {
            activity.updateInterface(o);
        }
    }
}
