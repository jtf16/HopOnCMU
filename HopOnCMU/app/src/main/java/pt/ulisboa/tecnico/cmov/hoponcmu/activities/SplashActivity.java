package pt.ulisboa.tecnico.cmov.hoponcmu.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.CommunicationTask;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.SetUpCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.SetUpResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.repositories.TransactionRepository;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.repositories.UserRepository;

public class SplashActivity extends ManagerActivity {

    private TransactionRepository transactionRepository;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        transactionRepository = new TransactionRepository(this);
        userRepository = new UserRepository(this);

        Intent activityIntent;

        new CommunicationTask(this, new SetUpCommand()).execute();

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
        if (response instanceof SetUpResponse) {
            SetUpResponse setUpResponse = (SetUpResponse) response;
            transactionRepository.insertMonumentsAndQuizzes(
                    setUpResponse.getMonuments(), setUpResponse.getQuizzes());
            userRepository.insertUser(setUpResponse.getUsers());
        }
    }
}
