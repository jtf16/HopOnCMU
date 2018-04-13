package pt.ulisboa.tecnico.cmov.hoponcmu.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.CommunicationTask;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.MonumentCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.MonumentResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.repositories.MonumentRepository;

public class SplashActivity extends ManagerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent activityIntent;

        new CommunicationTask(this, new MonumentCommand()).execute();

        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = pref.getString(LoginActivity.USER, "");
        User user = gson.fromJson(json, User.class);
        if (user != null) {
            activityIntent = new Intent(this, MainActivity.class);
        } else {
            activityIntent = new Intent(this, LoginActivity.class);
        }

        startActivity(activityIntent);
        finish();
    }

    @Override
    public void updateInterface(Response response) {
        MonumentResponse monumentResponse = (MonumentResponse) response;
        MonumentRepository monumentRepository = new MonumentRepository(this);
        monumentRepository.insertMonument(monumentResponse.getMonuments());
    }
}
