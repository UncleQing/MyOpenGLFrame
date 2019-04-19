package com.zidian.myopenglframe;

import android.app.Activity;
import android.os.Bundle;

import com.zidian.myopenglframe.renderer.BaseRenderer;
import com.zidian.myopenglframe.renderer.PostiveTriangleRenderer;
import com.zidian.myopenglframe.renderer.TriangleRenderer;
import com.zidian.myopenglframe.view.BaseGLSurfaceView;

public class GraphActivity extends Activity {
    public static final String TAG = "graph_activtiy";
    public static final int TYPE_SIMPLE = 0;
    public static final int TYPE_HIGH = 1;
    private int type;

    private BaseGLSurfaceView glView_up;
    private BaseGLSurfaceView glView_down;

    private BaseRenderer mTriangleRenderer;
    private BaseRenderer mRectangleRender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        type = getIntent().getIntExtra(TAG, TYPE_SIMPLE);

        glView_up = findViewById(R.id.bgv_graph_1);
        glView_down = findViewById(R.id.bgv_graph_2);

        if (type == TYPE_HIGH) {
            mTriangleRenderer = new PostiveTriangleRenderer();
            mRectangleRender = new PostiveTriangleRenderer();
        }else {
            mTriangleRenderer = new TriangleRenderer();
            mRectangleRender = new TriangleRenderer();
        }
        mTriangleRenderer.setType(TriangleRenderer.TYPE_TRIANGLE);
        mRectangleRender.setType(TriangleRenderer.TYPE_RECTANGLE);

        glView_up.setRenderer(mTriangleRenderer);
        glView_down.setRenderer(mRectangleRender);
    }

    @Override
    protected void onPause() {
        super.onPause();
        glView_up.onPause();
        glView_down.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        glView_up.onResume();
        glView_down.onResume();
    }
}
