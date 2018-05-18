package pt.ulisboa.tecnico.cmov.hoponcmu.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;

import java.security.InvalidKeyException;
import java.security.KeyPair;

import javax.crypto.SecretKey;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.CommunicationTask;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.RankingCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.keys.PubKeySignUpExchangeCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.sealed.SignUpSealedCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.PubKeyExchangeResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.RankingResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.SignUpResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.exceptions.PasswordExceptionResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.exceptions.UsernameExceptionResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.repositories.UserRepository;
import pt.ulisboa.tecnico.cmov.hoponcmu.security.SecurityManager;

public class CreateAccountActivity extends ManagerActivity {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    private User user;

    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText username;
    private EditText password;
    private EditText confirmPassword;

    private KeyPair keyPair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password1);
        confirmPassword = findViewById(R.id.password2);

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
        } else if (findInitialSpace(strLastName)) {
            lastName.setError("Remove space in beginning");
        } else if (findInitialSpace(strEmail)) {
            email.setError("Remove space in beginning");
        } else if (StringUtils.isBlank(strUsername)) {
            username.setError("You must enter your username");
        } else if (findInitialSpace(strUsername)) {
            username.setError("Remove space in beginning");
        } else if (StringUtils.isBlank(strPassword)) {
            password.setError("You must enter your password");
        } else if (!strPassword.equals(strConfirmPassword)) {
            confirmPassword.setError("Your passwords don't match");
        } else {
            user = new User(strUsername, strFirstName, strLastName, strEmail, strPassword);

            publicKeyToServer(strUsername);
        }
    }

    private boolean findInitialSpace(String string) {
        return !string.equals(string.trim());
    }

    private void publicKeyToServer(String username) {
        keyPair = SecurityManager.getNewKeyPair();
        new CommunicationTask(this, new PubKeySignUpExchangeCommand(
                username, keyPair.getPublic().getEncoded())).execute();
    }

    @Override
    public void updateInterface(Response response) {
        if (response instanceof SignUpResponse) {
            SignUpResponse signUpResponse = (SignUpResponse) response;
            new CommunicationTask(this, new RankingCommand()).execute();

            Intent loginIntent = new Intent(this, LoginActivity.class);
            loginIntent.putExtra(USERNAME, user.getUsername());
            loginIntent.putExtra(PASSWORD, user.getPassword());
            startActivity(loginIntent);
            finish();
        } else if (response instanceof PubKeyExchangeResponse) {
            try {
                SecretKey sharedSecret = SecurityManager.generateSharedSecret(keyPair.getPrivate(),
                        ((PubKeyExchangeResponse) response).getPublicKey());
                new CommunicationTask(this, new SignUpSealedCommand(user.getUsername(),
                        sharedSecret, user)).execute();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
        } else if (response instanceof PasswordExceptionResponse) {
            password.setError(((PasswordExceptionResponse) response).getMessage());
        } else if (response instanceof UsernameExceptionResponse) {
            username.setError(((UsernameExceptionResponse) response).getMessage());
        } else if (response instanceof RankingResponse) {
            UserRepository userRepository = new UserRepository(this);
            userRepository.insertUser(((RankingResponse) response).getUsers());
        }
    }
}
