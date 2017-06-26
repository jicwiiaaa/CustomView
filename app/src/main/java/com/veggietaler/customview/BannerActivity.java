package com.veggietaler.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.veggietaler.customview.customview.BannerLayout;

public class BannerActivity extends AppCompatActivity {

    private BannerLayout bannerLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        initViews();
    }

    private void initViews() {
        bannerLayout = (BannerLayout) findViewById(R.id.banner);

        bannerLayout.setOnItemClickListener(new BannerLayout.OnItemClickListener() {
            @Override
            public void onClick(int index, View childview) {
                Toast.makeText(BannerActivity.this, "点击第" + index + "项", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        bannerLayout.stopScroll();
        super.onPause();
    }

    @Override
    protected void onRestart() {
        if (!bannerLayout.isScrolling()) {
            bannerLayout.startScroll();
        }
        super.onRestart();
    }

    @Override
    protected void onResume() {
        if (!bannerLayout.isScrolling()) {
            bannerLayout.startScroll();
        }
        super.onResume();
    }

    @Override
    protected void onStop() {
        bannerLayout.stopScroll();
        super.onStop();
    }
}
