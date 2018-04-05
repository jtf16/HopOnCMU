package pt.ulisboa.tecnico.cmov.hoponcmu.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.CommunicationTask;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.SignUpCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.SignUpResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;

public class CreateAccountActivity extends ManagerActivity {

    EditText firstName;
    EditText lastName;
    EditText email;
    EditText username;
    EditText password;
    EditText confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password1);
        confirmPassword = (EditText) findViewById(R.id.password2);

        password.setTypeface(Typeface.DEFAULT);
        confirmPassword.setTypeface(Typeface.DEFAULT);
    }

    public void createAccount(View view) {

        String strFirstName = firstName.getText().toString();
        String strLastName = lastName.getText().toString();
        String strEmail = email.getText().toString();
        String strUsername = username.getText().toString();
        String strPassword = password.getText().toString();
        String strConfirmPassword = confirmPassword.getText().toString();

        if (StringUtils.isBlank(strFirstName)) {
            firstName.setError("You must enter your first name");
        } else if (findInitialSpace(strFirstName)) {
            firstName.setError("Remove space in beginning");
        }

        //else if (StringUtils.isBlank(strLastName)) {lastName.setError("You must enter your last name");}
        else if (findInitialSpace(strLastName)) {
            lastName.setError("Remove space in beginning");
        }

        //else if (StringUtils.isBlank(strEmail)) {email.setError("You must enter your email");}
        else if (findInitialSpace(strEmail)) {
            email.setError("Remove space in beginning");
        } else if (StringUtils.isBlank(strUsername)) {
            username.setError("You must enter your username");
        } else if (findInitialSpace(strUsername)) {
            username.setError("Remove space in beginning");
        } else if (StringUtils.isBlank(strPassword)) {
            password.setError("You must enter your password");
        } else if (!strPassword.equals(strConfirmPassword)) {
            confirmPassword.setError("Your passwords doesn't match");
        } else {
            User user = new User();
            user.setFirstName(strFirstName);
            user.setLastName(strLastName);
            user.setEmail(strEmail);
            user.setUsername(strUsername);
            user.setPassword(strPassword);
            user.setScore(0);

            SignUpCommand suc = new SignUpCommand(user);
            new CommunicationTask(this, suc).execute();
        }
    }

    private boolean findInitialSpace(String string) {
        return !string.equals(string.trim());
    }

    @Override
    public void updateInterface(Response response) {
        SignUpResponse signUpResponse = (SignUpResponse) response;
        if (!signUpResponse.getErrors()[1]) {
            username.setError("Name already in use");
        }
        if (!signUpResponse.getErrors()[2]) {
            password.setError("Not a valid code");
        }
        if (signUpResponse.getErrors()[0]) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            loginIntent.putExtra("Username", signUpResponse.getUser().getUsername());
            loginIntent.putExtra("Password", signUpResponse.getUser().getPassword());
            startActivity(loginIntent);
            finish();
        }
    }
}
