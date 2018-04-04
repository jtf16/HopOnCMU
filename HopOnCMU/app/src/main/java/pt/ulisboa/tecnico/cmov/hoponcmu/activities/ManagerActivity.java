package pt.ulisboa.tecnico.cmov.hoponcmu.activities;

import android.support.v7.app.AppCompatActivity;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;

public abstract class ManagerActivity extends AppCompatActivity {

    public abstract void updateInterface(Response response);
}
