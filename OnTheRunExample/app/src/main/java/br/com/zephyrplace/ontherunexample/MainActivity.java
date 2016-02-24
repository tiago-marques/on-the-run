package br.com.zephyrplace.ontherunexample;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nineoldandroids.animation.ObjectAnimator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ObjectAnimator.ofFloat(findViewById(R.id.background), "alpha", 0.05f).start();
    }
}
