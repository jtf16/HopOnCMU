package pt.ulisboa.tecnico.cmov.hoponcmu.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.CommunicationTask;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.MonumentCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.RankingCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.MonumentResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.RankingResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.repositories.MonumentRepository;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.repositories.UserRepository;

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
            new CommunicationTask(this, new RankingCommand()).execute();
            activityIntent = new Intent(this, MainActivity.class);
        } else {
            activityIntent = new Intent(this, LoginActivity.class);
        }

        startActivity(activityIntent);
        finish();
    }

    @Override
    public void updateInterface(Response response) {
        if (response instanceof MonumentResponse) {
            MonumentRepository monumentRepository = new MonumentRepository(this);
            monumentRepository.insertMonument(
                    ((MonumentResponse) response).getMonuments());
        } else if (response instanceof RankingResponse) {
            UserRepository userRepository = new UserRepository(this);
            userRepository.insertUser(((RankingResponse) response).getUsers());
        }
    }
}
