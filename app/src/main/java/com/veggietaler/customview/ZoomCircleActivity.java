package com.veggietaler.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.veggietaler.customview.customview.ZoomCircleView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZoomCircleActivity extends AppCompatActivity {

    @BindView(R.id.circle)
    ZoomCircleView circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_circle);
        ButterKnife.bind(this);

//        new Thread(circle).start();
    }
}
