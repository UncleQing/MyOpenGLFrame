package com.zidian.myopenglframe.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.zidian.myopenglframe.renderer.TriangleRenderer;

public class BaseGLSurfaceView extends GLSurfaceView {

    public BaseGLSurfaceView(Context context) {
        super(context);
        init();
    }

    public BaseGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //版本2
        setEGLContextClientVersion(2);
    }

    @Override
    public void setRenderer(Renderer renderer) {
        super.setRenderer(renderer);
        //懒惰渲染
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
