package pt.ulisboa.tecnico.cmov.hoponcmu.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.CommunicationTask;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.LoginCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.RankingCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.LoginResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.RankingResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.repositories.UserRepository;

public class LoginActivity extends ManagerActivity {

    public static final String USER = "user";
    EditText username;
    EditText password;
    String strUsername;
    String strPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.usernameLogin);
        password = (EditText) findViewById(R.id.passLogin);
        password.setTypeface(Typeface.DEFAULT);

        Intent intent = getIntent();
        strUsername = intent.getStringExtra("Username");
        strPassword = intent.getStringExtra("Password");

        if (strUsername != null && strPassword != null) {
            username.setText(strUsername);
            password.setText(strPassword);
        }
    }

    public void createAccount(View view) {

        Intent createAccountIntent = new Intent(this, CreateAccountActivity.class);
        startActivity(createAccountIntent);
    }

    public void login(View view) {

        strUsername = username.getText().toString();
        strPassword = password.getText().toString();

        if (StringUtils.isBlank(strUsername)) {
            username.setError("You must enter your username");
        } else if (StringUtils.isBlank(strPassword)) {
            password.setError("You must enter your password");
        } else {
            User user = new User();
            user.setUsername(strUsername);
            user.setPassword(strPassword);
            new CommunicationTask(this, new LoginCommand(user)).execute();
        }
    }

    @Override
    public void updateInterface(Response response) {
        if (response instanceof LoginResponse) {
            LoginResponse loginResponse = (LoginResponse) response;
            if (!loginResponse.getErrors()[1]) {
                username.setError("No such username!");
            }
            if (!loginResponse.getErrors()[2]) {
                password.setError("Incorrect password");
            }
            if (loginResponse.getErrors()[0]) {
                new CommunicationTask(this, new RankingCommand()).execute();
                SharedPreferences pref =
                        PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor edit = pref.edit();
                Gson gson = new Gson();
                String json = gson.toJson(loginResponse.getUser());
                edit.putString(USER, json);
                edit.apply();

                Intent loginIntent = new Intent(this, MainActivity.class);
                startActivity(loginIntent);
                finish();
            }
        } else if (response instanceof RankingResponse) {
            UserRepository userRepository = new UserRepository(this);
            userRepository.insertUser(((RankingResponse) response).getUsers());
        }

    }
}
