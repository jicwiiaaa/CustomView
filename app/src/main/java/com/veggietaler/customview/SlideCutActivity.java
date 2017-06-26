package com.veggietaler.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.veggietaler.customview.customview.SlideCutListView;

import java.util.ArrayList;
import java.util.List;

public class SlideCutActivity extends AppCompatActivity implements SlideCutListView.RemoveListener {

    private SlideCutListView listContent;

    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_cut);

        initViews();

        initData();
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        listContent = (SlideCutListView) findViewById(R.id.list_content);

        listContent.setRemoveListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add("滑动" + i);
        }
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.item_slide_cut, R.id.tv_item, datas);
        listContent.setAdapter(arrayAdapter);
    }

    @Override
    public void removeItem(SlideCutListView.RemoveDirection direction, int position) {
        switch (direction) {
            case RIGHT:
                Toast.makeText(this, "右滑删除" + position, Toast.LENGTH_SHORT).show();
                break;
            case LEFT:
                Toast.makeText(this, "左滑删除" + position, Toast.LENGTH_SHORT).show();
                break;
        }
        arrayAdapter.remove(arrayAdapter.getItem(position));

    }
}
