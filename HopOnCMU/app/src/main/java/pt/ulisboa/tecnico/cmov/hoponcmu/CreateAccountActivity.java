package pt.ulisboa.tecnico.cmov.hoponcmu;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;

import static android.content.ContentValues.TAG;

public class CreateAccountActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public void accountCreated(View view){

        EditText editTextPassword1 = (EditText) findViewById(R.id.password1);
        EditText editTextPassword2 = (EditText) findViewById(R.id.password2);
        EditText editTextFirstName = (EditText) findViewById(R.id.firstName);
        EditText editTextLastName = (EditText) findViewById(R.id.lastName);
        EditText editTextEmail = (EditText) findViewById(R.id.email);
        EditText editTextUsername = (EditText) findViewById(R.id.username);

        String password1 = editTextPassword1.getText().toString();
        String password2 = editTextPassword1.getText().toString();
        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String email = editTextEmail.getText().toString();
        String username = editTextUsername.getText().toString();

        if (password1 == null) {}
        else if (password2 == null) {}
        else if (firstName == null) {}
        else if (lastName == null) {}
        else if (email == null) {}
        else if (username == null) {}

        if (password1.equals(password2)) {
            // Algumas cenas
            // Enviar o Username e Password para a activity anterior
            finish();
        }
        else {
            // Alert que as passwords não estão correctar
        }
    }
}
