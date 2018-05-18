package pt.ulisboa.tecnico.cmov.hoponcmu.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import java.security.InvalidKeyException;
import java.security.KeyPair;

import javax.crypto.SecretKey;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.CommunicationTask;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.keys.PubKeyExchangeCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.sealed.LoginSealedCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.LoginResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.PubKeyExchangeResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.exceptions.PasswordExceptionResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.exceptions.UsernameExceptionResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.sealed.SealedResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;
import pt.ulisboa.tecnico.cmov.hoponcmu.security.SecurityManager;

public class LoginActivity extends ManagerActivity {

    public static final String USER = "user";
    public static final String SESSION_ID = "session_id";
    public static final String SHARED_SECRET = "secret_key";
    SharedPreferences pref;
    String strUsername;
    String strPassword;
    private User user;
    private EditText username;
    private EditText password;

    private KeyPair keyPair;
    private SecretKey sharedSecret;

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
            user = new User(strUsername);
            user.setPassword(strPassword);
            publicKeyToServer(strUsername);
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

    private void addStringToSharedPrefs(String tag, String s) {
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(tag, s);
        edit.apply();
    }

    private void addByteArrayToSharedPrefs(String tag, byte[] array) {
        SharedPreferences.Editor edit = pref.edit();
        String stringArray = Base64.encodeToString(array, Base64.DEFAULT);
        edit.putString(tag, stringArray);
        edit.apply();
    }

    private void publicKeyToServer(String username) {
        keyPair = SecurityManager.getNewKeyPair();
        new CommunicationTask(this, new PubKeyExchangeCommand(
                username, keyPair.getPublic().getEncoded())).execute();
    }

    @Override
    public void updateInterface(Response response) {

        if (response instanceof PubKeyExchangeResponse) {
            try {
                sharedSecret = SecurityManager.generateSharedSecret(keyPair.getPrivate(),
                        ((PubKeyExchangeResponse) response).getPublicKey());

                // save secret key on shared preferences
                addByteArrayToSharedPrefs(SHARED_SECRET, sharedSecret.getEncoded());
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
            SecretKey key = SecurityManager.getSecretKey(pref);

            new CommunicationTask(this, new LoginSealedCommand(strUsername,
                    key, user)).execute();
        } else if (response instanceof SealedResponse) {

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SecretKey secretKey = SecurityManager.getSecretKey(sharedPreferences);

            SealedResponse sr = (SealedResponse) response;

            Response response1 = (Response) SecurityManager.getObject(sr.getSealedContent(), sr.getDigest(), secretKey);

            if (response1 instanceof LoginResponse) {
                LoginResponse loginResponse = (LoginResponse) response1;

                addObjectToSharedPrefs(USER, loginResponse.getUser());
                addLongToSharedPrefs(SESSION_ID, loginResponse.getSessionId());

                Intent loginIntent = new Intent(this, MainActivity.class);
                startActivity(loginIntent);
                finish();
            } else if (response1 instanceof PasswordExceptionResponse) {
                password.setError(((PasswordExceptionResponse) response1).getMessage());
            } else if (response1 instanceof UsernameExceptionResponse) {
                username.setError(((UsernameExceptionResponse) response1).getMessage());
            }
        }

    }
}
