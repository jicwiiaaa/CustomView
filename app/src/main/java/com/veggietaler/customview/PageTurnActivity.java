package com.veggietaler.customview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.veggietaler.customview.customview.PageTurnView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PageTurnActivity extends AppCompatActivity {

    @BindView(R.id.page_turn_view)
    PageTurnView pageTurnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_turn);
        ButterKnife.bind(this);

        int[] resIds = new int[]{R.mipmap.pic1, R.mipmap.pic2, R.mipmap.pic3, R.mipmap.pic4};
        List<Bitmap> bitmaps = new ArrayList<>();
        for (int i = 0; i < resIds.length; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resIds[i]);
            bitmaps.add(bitmap);
        }
        pageTurnView.setBitmaps(bitmaps);
    }
}
