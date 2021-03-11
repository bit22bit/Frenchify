package com.bits.frenchify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Signup extends AppCompatActivity {

    LinearLayout rootLayout;
    Toolbar toolbar;

    TextView signUptv;
    TextView usernameTv;
    TextView emailTv;
    TextView passTv;
    TextView verifypassTv;
    TextView haveAnAccountTv;

    EditText usernameEt;
    EditText emailEt;
    EditText passEt;
    EditText verifypassEt;

    Button signUp;
    Button gotoSignin;

    CheckBox condition;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    private void initViews(){

        rootLayout = findViewById(R.id.rootLayout);
        toolbar =  findViewById(R.id.toolbar);
        toolbar.setTitle("Welcome to Frenchify");
        signUptv = findViewById(R.id.userName_tv);
        usernameTv = findViewById(R.id.userName_tv);
        emailTv = findViewById(R.id.email_tv);
        passTv=findViewById(R.id.pass_tv);
        verifypassTv=findViewById(R.id.verifypass_tv);
        haveAnAccountTv =findViewById(R.id.haveAnAccount_tv);

        usernameEt = findViewById(R.id.userName_et);
        emailEt =  findViewById(R.id.email_et);
        passEt=findViewById(R.id.pass_et);
        verifypassEt=findViewById(R.id.verifypass_et);

        signUp=findViewById(R.id.signUp_button);
        gotoSignin=findViewById(R.id.gotoSignIn_button);

        condition=findViewById(R.id.condition_cb);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);


    }



    public void gotoSignInPage(View view){

        startActivity(new Intent(Signup.this, Login.class));

    }

    public void authProcess(View view){

                register();


    }

    private void register() {

        String username = usernameEt.getText().toString();
        String email= emailEt.getText().toString();
        String password= passEt.getText().toString();
        String verifyPassword= verifypassEt.getText().toString();

        if(TextUtils.isEmpty(username)){

            usernameEt.setError("Please! Enter Your Username");
            return;
        }
        else if(TextUtils.isEmpty(email)){

            emailEt.setError("Please! Enter Your Email");
            return;
        }
        else if(TextUtils.isEmpty(password)){

            passEt.setError("Please! Enter Your Password");
            return;
        }
        else if(TextUtils.isEmpty(verifyPassword)){

            verifypassEt.setError("Please! Reenter Your Password");
            return;
        }
        else if(password.length()<=5){
             passEt.setError("Please! Enter Your Password > 5 words or numbers");
            return;
        }
        else if(!password.equals(verifyPassword)){
            verifypassEt.setError("Password does not match");
            return;
        }

        else if(!isValidEmail(email)){
            emailEt.setError("Invalid email");
            return;
        }
        else if(condition.isChecked()==false){
            condition.setError("Please accept the terms");
            return;
        }

        progressDialog.setMessage("Please wait....");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Signup.this, "Sucessfully registered", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Signup.this,LearningOrAssessment.class);
                    startActivity(intent);
                    finish();

                }
                else{

                    Toast.makeText(Signup.this, "Sucessfully not registered", Toast.LENGTH_SHORT).show();

                }
                progressDialog.dismiss();
            }
        });

    }

    private boolean isValidEmail(CharSequence target) {

        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        initViews();


    }


}