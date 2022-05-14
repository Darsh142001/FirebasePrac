package za.ac.iie.opsc.firebaseprac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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

    EditText name;
    EditText surname;
    EditText email;
    EditText password;

    Button register;
    TextView goLoginPg;

    //This will Register users to the realtime database in firebase.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        name = findViewById(R.id.nameET);
        surname = findViewById(R.id.surnameET);
        email = (EditText) findViewById(R.id.emailET);
        password = findViewById(R.id.passwordET);

        register = findViewById(R.id.registerBtn);

        goLoginPg = (TextView) findViewById(R.id.loginPg);
        goLoginPg.setOnClickListener(this);



        //setUpUI();
        //setUpListeners();

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
        RegisterUser();
    }
    /*
        public void setUpUI()
        {

        }


        public void setUpListeners()
        {

        }
    */
    private void RegisterUser()
    {
        //Converted every value to a string and stored our variables.

        String username = name.getText().toString().trim();
        String userSurname = surname.getText().toString().trim();
        String userEmail = email.getText().toString().trim();;
        String userPassword = password.getText().toString().trim();

        if(username.isEmpty()){
            name.setError("Full name is required");
            name.requestFocus();
            return;
        }
        if(userSurname.isEmpty()){
            surname.setError("Surname is required");
            surname.requestFocus();
            return;
        }
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

        createAccount(username, userSurname, userEmail, userPassword);

    }

    private void createAccount(String username, String userSurname, String userEmail, String userPassword)
    {
        mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(username, userSurname, userEmail);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(MainActivity.this, "User has been successfully registered", Toast.LENGTH_LONG).show();
                                            }else{
                                                Toast.makeText(MainActivity.this, "Failed to register! Try again", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(MainActivity.this, "Failed to register", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

/*
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
*/


    /*
    public void updateUI()
    {

    }

     */

}