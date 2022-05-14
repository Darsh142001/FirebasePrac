package za.ac.iie.opsc.firebaseprac;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;

    EditText email;
    EditText password;
    TextView goRegisterPg;
    TextView forgotPassword;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.emailLogin);
        password = findViewById(R.id.passwordLogin);

        goRegisterPg = (TextView) findViewById(R.id.registerPg);
        goRegisterPg.setOnClickListener(this);

        forgotPassword = (TextView) findViewById(R.id.forgotPasswordTv);
        forgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) //https://www.youtube.com/watch?v=Z-RE1QuUWPg&list=PL65Ccv9j4eZJ_bg0TlmxA7ZNbS8IMyl5i&index=4
    {
        switch(v.getId())
        {
            case R.id.registerPg:
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.forgotPasswordTv:
                startActivity(new Intent(this, ForgotPasswordActivity.class));
                break;
        }
    }

    public void loginClick(View view)
    {
        LoginUser();
        //signIn(email.getText().toString(), password.getText().toString());
    }

    private void LoginUser()
    {
        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();

        if(userEmail.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
            email.setError("Please provide a valid email!");
            email.requestFocus();
            return;
        }
        if(userPassword.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        if(userPassword.length() < 6){
            password.setError("Min password length should be 6 characters ");
            password.requestFocus();
            return;
        }

        signIn(userEmail, userPassword);

    }

    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if(task.isSuccessful()){
                            //redirect user to Menu activity.
                            startActivity(new Intent(LoginActivity.this, MenuActivity.class)); //check AndroidManifest.

                            //Toast.makeText(LoginActivity.this, "Authentication Successful.", Toast.LENGTH_SHORT).show();
                           // FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        }else{
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

}
