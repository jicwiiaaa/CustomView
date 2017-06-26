package com.veggietaler.customview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.tv_slide_cut)
    TextView tvSlideCut;
    @BindView(R.id.tv_banner)
    TextView tvBanner;
    @BindView(R.id.tv_zoom_circle)
    TextView tvZoomCircle;
    @BindView(R.id.tv_paint_test)
    TextView tvPaintTest;
    @BindView(R.id.tv_mask_filter)
    TextView tvMaskFilter;
    @BindView(R.id.tv_image_mask_filter)
    TextView tvImageMaskFilter;
    @BindView(R.id.tv_path_effect)
    TextView tvPathEffect;
    @BindView(R.id.tv_ecgview)
    TextView tvEcgview;
    @BindView(R.id.tv_bitmap_shader)
    TextView tvBitmapShader;
    @BindView(R.id.tv_brick_view)
    TextView tvBrickView;
    @BindView(R.id.tv_gradient_view)
    TextView tvGradientView;
    @BindView(R.id.tv_reflect_view)
    TextView tvReflectView;
    @BindView(R.id.tv_dream_effect)
    TextView tvDreamEffect;
    @BindView(R.id.tv_matrix_image)
    TextView tvMatrixImage;
    @BindView(R.id.tv_multi_circle)
    TextView tvMultiCircle;
    @BindView(R.id.tv_bitmap_mesh)
    TextView tvBitmapMesh;
    @BindView(R.id.tv_bitmap_mesh2)
    TextView tvBitmapMesh2;
    @BindView(R.id.tv_canvas)
    TextView tvCanvas;
    @BindView(R.id.tv_path)
    TextView tvPath;
    @BindView(R.id.tv_wave)
    TextView tvWave;
    @BindView(R.id.tv_line_chart)
    TextView tvLineChart;
    @BindView(R.id.tv_layer_view)
    TextView tvLayerView;
    @BindView(R.id.tv_page_turn)
    TextView tvPageTurn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.tv_slide_cut, R.id.tv_banner, R.id.tv_zoom_circle, R.id.tv_paint_test, R.id.tv_mask_filter, R.id.tv_image_mask_filter, R.id.tv_path_effect, R.id.tv_ecgview, R.id.tv_bitmap_shader, R.id.tv_brick_view, R.id.tv_gradient_view, R.id.tv_reflect_view, R.id.tv_dream_effect, R.id.tv_matrix_image, R.id.tv_multi_circle, R.id.tv_bitmap_mesh, R.id.tv_bitmap_mesh2, R.id.tv_canvas, R.id.tv_path, R.id.tv_wave, R.id.tv_line_chart, R.id.tv_layer_view, R.id.tv_page_turn})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_slide_cut:
                intent = new Intent(MainActivity.this, SlideCutActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_banner:
                intent = new Intent(MainActivity.this, BannerActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_zoom_circle:
                intent = new Intent(MainActivity.this, ZoomCircleActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_paint_test:
                intent = new Intent(MainActivity.this, PaintTextActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_mask_filter:
                intent = new Intent(MainActivity.this, MaskFilterActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_image_mask_filter:
                intent = new Intent(MainActivity.this, ImageMaskFilterActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_path_effect:
                intent = new Intent(MainActivity.this, PathEffectActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_ecgview:
                intent = new Intent(MainActivity.this, ECGViewActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_bitmap_shader:
                intent = new Intent(MainActivity.this, BitmapShaderActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_brick_view:
                intent = new Intent(MainActivity.this, BrickViewActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_gradient_view:
                intent = new Intent(MainActivity.this, GradientViewActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_reflect_view:
                intent = new Intent(MainActivity.this, ReflectViewActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_dream_effect:
                intent = new Intent(MainActivity.this, DreamEffectActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_matrix_image:
                intent = new Intent(MainActivity.this, MatrixImageActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_multi_circle:
                intent = new Intent(MainActivity.this, MultiCircleActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_bitmap_mesh:
                intent = new Intent(MainActivity.this, BitmapMeshActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_bitmap_mesh2:
                intent = new Intent(MainActivity.this, BitmapMesh2Activity.class);
                startActivity(intent);
                break;
            case R.id.tv_canvas:
                intent = new Intent(MainActivity.this, CanvasViewActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_path:
                intent = new Intent(MainActivity.this, PathViewActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_wave:
                intent = new Intent(MainActivity.this, WaveViewActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_line_chart:
                intent = new Intent(MainActivity.this, LineChartActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_layer_view:
                intent = new Intent(MainActivity.this, LayerViewActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_page_turn:
                intent = new Intent(MainActivity.this, PageTurnActivity.class);
                startActivity(intent);
                break;


        }
    }

}
