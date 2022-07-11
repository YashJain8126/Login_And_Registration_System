package com.example.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupScreen extends AppCompatActivity {
    EditText email;
    EditText password;
    EditText confirmpassword;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        confirmpassword=findViewById(R.id.confirmpassword);
        submit=findViewById(R.id.submit);

        FirebaseAuth auth=FirebaseAuth.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emails=email.getText().toString();
                String passwords=password.getText().toString();
                String confirmpasswords=confirmpassword.getText().toString();
                //Regex can also to validation
                if(emails.isEmpty() || passwords.isEmpty() || confirmpasswords.isEmpty()){
                    Toast.makeText(SignupScreen.this, "Credentials cannot be empty ", Toast.LENGTH_LONG).show();
                    return;
                }
                if(passwords.length()<6){
                    Toast.makeText(SignupScreen.this, "Password length should be more than 6", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!passwords.equals(confirmpasswords)){
                    Toast.makeText(SignupScreen.this, "confirmPassword should be same as Password", Toast.LENGTH_LONG).show();
                    return;
                }
                auth.createUserWithEmailAndPassword(emails, passwords).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(SignupScreen.this, "Register Successfully", Toast.LENGTH_LONG).show();
                            auth.getCurrentUser().sendEmailVerification();
                            finish();
                        }
                        else{
                            Toast.makeText(SignupScreen.this, ""+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }
}