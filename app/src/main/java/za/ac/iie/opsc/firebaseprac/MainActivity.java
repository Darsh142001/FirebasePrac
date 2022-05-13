package za.ac.iie.opsc.firebaseprac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    EditText email;
    EditText password;
    Button register;
    TextView goLoginPg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.emailET);
        password = findViewById(R.id.passwordET);
        register = findViewById(R.id.registerBtn);

        goLoginPg = (TextView) findViewById(R.id.loginPg);
        goLoginPg.setOnClickListener(this);

        setUpUI();
        setUpListeners();

        //Need to push the realtime_database_connection branch

    }


    @Override
    public void onClick(View v) //https://www.youtube.com/watch?v=Z-RE1QuUWPg&list=PL65Ccv9j4eZJ_bg0TlmxA7ZNbS8IMyl5i&index=4
    {
        switch(v.getId())
        {
            case R.id.loginPg:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    public void onClickRegister(View view)
    {
        createAccount(email.getText().toString(),password.getText().toString());
    }

    public void setUpUI()
    {

    }

    public void setUpListeners()
    {

    }

    private void createAccount(String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            firebaseDatabase = FirebaseDatabase.getInstance();
                            databaseReference = firebaseDatabase.getReference("users");
                            databaseReference.setValue(email);

                            Toast.makeText(MainActivity.this, "Registered successfully", Toast.LENGTH_LONG).show();
                           //updateUI(user);
                        } else {
                            Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                           // updateUI(null);
                        }
                    }

                });
    }



    /*
    public void updateUI()
    {

    }

     */

}