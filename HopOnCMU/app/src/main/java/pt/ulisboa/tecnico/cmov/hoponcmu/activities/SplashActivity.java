package pt.ulisboa.tecnico.cmov.hoponcmu.activities;

import android.content.Intent;
import android.os.Bundle;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;

public class SplashActivity extends ManagerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent activityIntent;

        // TODO: verify if login is done
        if (true) {
            activityIntent = new Intent(this, LoginActivity.class);
        } else {
            activityIntent = new Intent(this, MainActivity.class);
        }

        startActivity(activityIntent);
        finish();
    }

    @Override
    public void updateInterface(Response response) {

    }
}
