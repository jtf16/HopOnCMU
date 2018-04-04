package pt.ulisboa.tecnico.cmov.hoponcmu.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.CommunicationTask;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.HelloCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;

public class LoginActivity extends ManagerActivity {

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

        // TODO: Verify if login is valid (username and password == DB)
        if (StringUtils.isBlank(strUsername)) {
            username.setError("You must enter your username");
        } else if (StringUtils.isBlank(strPassword)) {
            password.setError("You must enter your password");
        } else {
            // TODO: Change the command
            HelloCommand suc = new HelloCommand(strPassword);
            new CommunicationTask(this, suc).execute();
        }
    }

    @Override
    public void updateInterface(Response response) {
        Intent loginIntent = new Intent(this, MainActivity.class);
        startActivity(loginIntent);
        finish();
    }
}
