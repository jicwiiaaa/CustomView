package com.veggietaler.customview;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.veggietaler.customview.customview.LineChartView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LineChartActivity extends AppCompatActivity {

    @BindView(R.id.line_chart_view)
    LineChartView lineChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        ButterKnife.bind(this);

        List<PointF> pointFs = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 11; i++) {
            float x = i;
            float y = random.nextInt(100);
            PointF pointF = new PointF(x, y);
            pointFs.add(pointF);
        }
        lineChartView.setData(pointFs, "哈哈", "嘿嘿");


    }
}
