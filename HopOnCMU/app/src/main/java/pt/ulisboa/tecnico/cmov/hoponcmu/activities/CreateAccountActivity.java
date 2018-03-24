package pt.ulisboa.tecnico.cmov.hoponcmu.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;

public class CreateAccountActivity extends Activity {

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

        if (StringUtils.isBlank(strFirstName)) {firstName.setError("You must enter your first name");}
        else if (findInitialSpace(strFirstName)) {firstName.setError("Remove space in beginning");}

        //else if (StringUtils.isBlank(strLastName)) {lastName.setError("You must enter your last name");}
        else if (findInitialSpace(strLastName)) {lastName.setError("Remove space in beginning");}

        //else if (StringUtils.isBlank(strEmail)) {email.setError("You must enter your email");}
        else if (findInitialSpace(strEmail)) {email.setError("Remove space in beginning");}

        else if (StringUtils.isBlank(strUsername)) {username.setError("You must enter your username");}
        else if (findInitialSpace(strUsername)) {username.setError("Remove space in beginning");}

        else if (StringUtils.isBlank(strPassword)) {password.setError("You must enter your password");}
        else if (!strPassword.equals(strConfirmPassword)) {confirmPassword.setError("Your passwords doesn't match");}

        // TODO: Insert all fields in DB
        else {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            loginIntent.putExtra("Username", strUsername);
            loginIntent.putExtra("Password", strPassword);
            startActivity(loginIntent);
            finish();
        }
    }

    private boolean findInitialSpace(String string) { return !string.equals(string.trim()); }
}
