package pt.ulisboa.tecnico.cmov.hoponcmu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent activityIntent;

        // TODO: verify if login is done
        if (true) {
            activityIntent = new Intent(this, LoginActivity.class);
        } else {
            activityIntent = new Intent(this, RankingActivity.class);
        }

        startActivity(activityIntent);
        finish();
    }
}
