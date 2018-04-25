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
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.LoginResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.exceptions.PasswordExceptionResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.exceptions.UsernameExceptionResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;

public class LoginActivity extends ManagerActivity {

    public static final String USER = "user";
    public static final String SESSION_ID = "session_id";

    SharedPreferences pref;

    String strUsername;
    String strPassword;

    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.usernameLogin);
        password = findViewById(R.id.passLogin);
        password.setTypeface(Typeface.DEFAULT);

        pref = PreferenceManager.getDefaultSharedPreferences(this);

        Intent intent = getIntent();
        strUsername = intent.getStringExtra(CreateAccountActivity.USERNAME);
        strPassword = intent.getStringExtra(CreateAccountActivity.PASSWORD);

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
            User user = new User(strUsername);
            user.setPassword(strPassword);
            new CommunicationTask(this, new LoginCommand(user)).execute();
        }
    }

    @Override
    public void updateInterface(Response response) {
        if (response instanceof LoginResponse) {
            LoginResponse loginResponse = (LoginResponse) response;

            addObjectToSharedPrefs(USER, loginResponse.getUser());
            addLongToSharedPrefs(SESSION_ID, loginResponse.getSessionId());

            Intent loginIntent = new Intent(this, MainActivity.class);
            startActivity(loginIntent);
            finish();
        } else if (response instanceof PasswordExceptionResponse) {
            password.setError(((PasswordExceptionResponse) response).getMessage());
        } else if (response instanceof UsernameExceptionResponse) {
            username.setError(((UsernameExceptionResponse) response).getMessage());
        }
    }

    private void addObjectToSharedPrefs(String tag, Object o) {
        SharedPreferences.Editor edit = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(o);
        edit.putString(tag, json);
        edit.apply();
    }

    private void addLongToSharedPrefs(String tag, Long l) {
        SharedPreferences.Editor edit = pref.edit();
        edit.putLong(tag, l);
        edit.apply();
    }
}
