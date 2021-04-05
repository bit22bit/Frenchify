package com.bits.frenchify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    Button signup;
    Button loginButton;

    Toolbar toolbar;
    LinearLayout mainContent;
    DrawerLayout root;
    LinearLayout toolbarLl;

    TextView signInMainTv;
    TextView nicetomeetTv;
    TextView emailMainTV;
    TextView passMainTv;
    TextView loginAsGuest;
    TextView forgetpassTv;

    EditText emailmainEt;
    EditText passmainEt;

    ProgressDialog progressDialog1;
    FirebaseAuth firebaseAuth1;
    FirebaseUser currentUser;

    Boolean guestBro=false;



    private boolean isValidEmail(CharSequence target) {

        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void initView(){

        toolbar =  findViewById(R.id.toolbar);
        mainContent = findViewById(R.id.mainContent_ll);
        //pb=findViewById(R.id.loadingProgressBar);
        toolbarLl=findViewById(R.id.toolbar_ll);
        toolbar.setTitle("Welcome to Frenchify!!");
        signup = findViewById(R.id.gotosignup_button);

        root=findViewById(R.id.drawer_layout);
        root.setBackgroundColor(ContextCompat.getColor(Login.this, R.color.white));
        signInMainTv=findViewById(R.id.signin_main_tv);
        nicetomeetTv=findViewById(R.id.nicetomeet_tv);
        emailMainTV=findViewById(R.id.email_main_tv);
        passMainTv=findViewById(R.id.pass_main_tv);
        forgetpassTv=findViewById(R.id.pass_main_tv);
        loginAsGuest = findViewById(R.id.asAGuest);

        emailmainEt=findViewById(R.id.email_main_et);
        passmainEt=findViewById(R.id.pass_main_et);
        loginButton = findViewById(R.id.login_main_button);
        firebaseAuth1= FirebaseAuth.getInstance();
        progressDialog1=new ProgressDialog(this);

        currentUser = firebaseAuth1.getCurrentUser();


    }
    public void updateUI(FirebaseUser user)
    {
        Intent intent = new Intent(Login.this,LearningOrAssessment.class);
        intent.putExtra("guestBro",guestBro);
        intent.putExtra("user",user);
        startActivity(intent);
        finish();
    }

    public void gotoForgetPassPage(View view){

        if(TextUtils.isEmpty(emailmainEt.getText().toString())){

            emailmainEt.setError("Please! Enter Your Email here to reset the password");
            return;
        }

        else if(!isValidEmail(emailmainEt.getText().toString())){
            emailmainEt.setError("Invalid email, Please write correct email to get reset link");
            return;
        }

        progressDialog1.setMessage("Please wait....");
        progressDialog1.show();
        progressDialog1.setCanceledOnTouchOutside(false);

        firebaseAuth1.sendPasswordResetEmail(emailmainEt.getText().toString())
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "password link has been sent to above email address", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Login.this, "Sorry! Something went Wrong..", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog1.dismiss();
                    }
                });
    }

    public void gotoSignUpPage(View view){
        startActivity(new Intent(Login.this, Signup.class));


    }

    public void continueAsAGuest(View view) {

        guestBro=true;
        Intent intent = new Intent(Login.this,LearningOrAssessment.class);
        intent.putExtra("guestBro",guestBro);
        startActivity(intent);
        finish();

    }

    public void login(View view){

        String email1= emailmainEt.getText().toString();
        String password1= passmainEt.getText().toString();

        if(TextUtils.isEmpty(email1)){

            emailmainEt.setError("Please! Enter Your Email");
            return;
        }
        else if(TextUtils.isEmpty(password1)){

            passmainEt.setError("Please! Enter Your Password");
            return;
        }

        else if(!isValidEmail(email1)){
            emailmainEt.setError("Invalid email");
            return;
        }

        progressDialog1.setMessage("Please wait....");
        progressDialog1.show();
        progressDialog1.setCanceledOnTouchOutside(false);


        firebaseAuth1.signInWithEmailAndPassword(email1,password1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Toast.makeText(Login.this, "Loged In", Toast.LENGTH_SHORT).show();
                    currentUser = firebaseAuth1.getCurrentUser();
                    updateUI(currentUser);


                }
                else{
                    Toast.makeText(Login.this, "Not Logged In", Toast.LENGTH_SHORT).show();
                }

                progressDialog1.dismiss();
            }
        });

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (currentUser != null)
        {
            updateUI(currentUser);
            Toast.makeText(getApplicationContext(),"User Already Signing",Toast.LENGTH_LONG).show();
        }


    }

}

