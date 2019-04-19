package com.zidian.myopenglframe.renderer;

import android.content.Context;
import android.opengl.GLSurfaceView;

public abstract class BaseRenderer implements GLSurfaceView.Renderer {
    protected Context mContext;
    protected int pragram;
    protected int type;

    protected abstract void with(Context context);

    public void setType(int type) {
        this.type = type;
    }
}
