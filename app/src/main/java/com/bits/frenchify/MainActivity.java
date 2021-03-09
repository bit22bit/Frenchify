package com.bits.frenchify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.animation.Animator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button signup;

    Toolbar toolbar;
    LinearLayout mainContent;
    DrawerLayout root;
    LinearLayout toolbarLl;


    ImageView imageView;

    TextView signInMainTv;
    TextView nicetomeetTv;
    TextView emailMainTV;
    TextView passMainTv;
    TextView forgetpassTv;

    EditText emailmainEt;
    EditText passmainEt;

     ProgressDialog progressDialog1;
     FirebaseAuth firebaseAuth1;

    Button loginButton;

    Boolean firstTime=true;

    public void gotoForgetPassPage(View view){

        Toast.makeText(this, "Goto Forgot Password Page", Toast.LENGTH_SHORT).show();
    }

    private void initView(){

        toolbar =  findViewById(R.id.toolbar);
        mainContent = findViewById(R.id.mainContent_ll);
        //pb=findViewById(R.id.loadingProgressBar);
        toolbarLl=findViewById(R.id.toolbar_ll);
        toolbar.setTitle("Welcome Back!!");
        signup = findViewById(R.id.gotosignup_button);
        imageView=findViewById(R.id.splaceScreen);
        root=findViewById(R.id.drawer_layout);
        signInMainTv=findViewById(R.id.signin_main_tv);
        nicetomeetTv=findViewById(R.id.nicetomeet_tv);
        emailMainTV=findViewById(R.id.email_main_tv);
        passMainTv=findViewById(R.id.pass_main_tv);
        forgetpassTv=findViewById(R.id.pass_main_tv);

        emailmainEt=findViewById(R.id.email_main_et);
        passmainEt=findViewById(R.id.pass_main_et);
        loginButton = findViewById(R.id.login_main_button);
        firebaseAuth1= FirebaseAuth.getInstance();
        progressDialog1=new ProgressDialog(this);



    }

    public void gotoSignUpPage(View view){
        startActivity(new Intent(MainActivity.this, Signup.class));

        
    }
    private boolean isValidEmail(CharSequence target) {

        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
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

                    Toast.makeText(MainActivity.this, "Loged In", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,LearningOrAssessment.class);
                    startActivity(intent);
                    finish();
                }   
                else{
                    Toast.makeText(MainActivity.this, "Not Logged In", Toast.LENGTH_SHORT).show();
                }

                progressDialog1.dismiss();
            }
        });






    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        initView();


        if(firstTime){
            time();
            firstTime=false;
        }


    }






    public void time(){
        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFnished) {
                root.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.black));
                startAnimation();
            }
            @Override
            public void onFinish() {
                toolbarLl.setVisibility(View.VISIBLE);
                root.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                mainContent.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.INVISIBLE);
            }
        }.start();
    }

    private void startAnimation() {


        ViewPropertyAnimator viewPropertyAnimator = imageView.animate();
        viewPropertyAnimator.setDuration(11000);

        viewPropertyAnimator.yBy(-1000f).alpha(-5f);





        viewPropertyAnimator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }



}