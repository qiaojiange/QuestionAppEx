package com.example.questionappex;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.questionappex.ui.BaseActivity;

public class StartActivity extends BaseActivity {

    private ImageView  imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
//欢迎界面
        imageView = (ImageView)this.findViewById(R.id.iv_welcome);
        Animation animation = new AlphaAnimation(0.0f,1.0f);
        animation.setDuration(3000);
        imageView.startAnimation(animation);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartActivity.this,SequenceActivity.class);
                startActivity(intent);
                StartActivity.this.finish();
            }
        },5000);


    }
}
