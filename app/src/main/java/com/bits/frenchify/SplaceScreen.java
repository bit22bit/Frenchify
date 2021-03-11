package com.bits.frenchify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;

public class SplaceScreen extends AppCompatActivity {

    ImageView imageView;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splace_screen);
        imageView=findViewById(R.id.splaceScreen1);
        constraintLayout=findViewById(R.id.rootlayoutOfsplace);
        time();







    }
    public void time(){
        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFnished) {
                constraintLayout.setBackgroundColor(ContextCompat.getColor(SplaceScreen.this, R.color.black));
                startAnimation();

            }
            @Override
            public void onFinish() {

                Intent intent= new Intent(SplaceScreen.this, Login.class);
                startActivity(intent);
                finish();

            }
        }.start();
    }

    private void startAnimation() {


        ViewPropertyAnimator viewPropertyAnimator = imageView.animate();
        viewPropertyAnimator.setDuration(11000);

        viewPropertyAnimator.yBy(-1000f).alpha(-7f);





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