package pt.ulisboa.tecnico.cmov.hoponcmu.activities;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
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
        password.setTypeface(Typeface.DEFAULT);
        confirmPassword = (EditText) findViewById(R.id.password2);
        confirmPassword.setTypeface(Typeface.DEFAULT);
    }

    // TODO: All fields must be filled
    public void createAccount(View view) {

        String strFirstName = firstName.getText().toString();
        String strLastName = lastName.getText().toString();
        String strEmail = email.getText().toString();
        String strUsername = username.getText().toString();
        String strPassword = password.getText().toString();
        String strConfirmPassword = confirmPassword.getText().toString();

        if (StringUtils.isBlank(strFirstName) || StringUtils.isBlank(strLastName) ||
                StringUtils.isBlank(strEmail) || StringUtils.isBlank(strUsername) ||
                StringUtils.isBlank(strPassword) || StringUtils.isBlank(strConfirmPassword)) {
            // TODO: Do something
        }

        if (strPassword.equals(strConfirmPassword)) {
            // Algumas cenas
            // Enviar o Username e Password para a activity anterior
            finish();
        } else {
            // Alert que as passwords não estão correctar
        }
    }
}
