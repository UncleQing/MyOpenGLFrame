package com.zidian.myopenglframe.renderer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.zidian.myopenglframe.tools.ShaderHelper;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class ColorTriangleRenderer extends BaseRenderer {
    //确认路径无误，可以点进去查看
    private static final String VERT_PATH = "triangle/color_triangle.vert";
    private static final String FRAG_PATH = "triangle/color_triangle.frag";
    //每个顶点关联分量数，1、2、3、4,x\y\z\w
    private static final int PER_COMENT_CONUT = 2;
    //R、G、B
    private static final int COLOR_COMENT_CONUT = 3;
    //形状，三角，矩形
    private int type;
    public static final int TYPE_TRIANGLE = 0;
    public static final int TYPE_RECTANGLE = 1;
    //顶点坐标
    private float[] pos = {
            //x, y, R, G, B
            -0.8f, -0.8f, 1f, 0, 0,
            0.8f, -0.8f, 0, 1f, 0,
            -0.8f, 0.8f, 0, 0, 1f,
            0.8f, 0.8f, 1f, 0, 0
    };
    //跨距
    private final static int STRIDE = (PER_COMENT_CONUT + COLOR_COMENT_CONUT) * 4;
    //顶点buffer
    private FloatBuffer vertBuffer;
    //顶点着色器成员
    private static final String V_POSITION = "vPosition";
    private int vPosition;
    private static final String V_MATRIX = "vMatrix";
    private int vMatrix;
    private float[] projectMatrix = new float[16];
    private static final String V_COLOR = "vColor";
    private int vColor;
    //片元着色器成员
    private int aColor;
    private static final String A_COLOR = "aColor";
    @Override
    protected void with(Context context) {
        mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.5f, 0.5f, 0.5f , 1);
        vertBuffer = ShaderHelper.initBuffer(pos);
        program = ShaderHelper.makeProgram(VERT_PATH, FRAG_PATH);

        vPosition = GLES20.glGetAttribLocation(program, V_POSITION);
        vColor = GLES20.glGetAttribLocation(program, V_COLOR);
        vMatrix = GLES20.glGetUniformLocation(program, V_MATRIX);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = width > height ? (float)width / height : (float) height / width;
        if (width > height){
            Matrix.orthoM(projectMatrix, 0, -ratio, ratio, -1f, 1f, -1f, 1f);
        }else {
            Matrix.orthoM(projectMatrix, 0, -1f, 1f, -ratio, ratio, -1f, 1f);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glUseProgram(program);

        GLES20.glUniformMatrix4fv(vMatrix, 1, false, projectMatrix, 0);

        GLES20.glEnableVertexAttribArray(vPosition);
        vertBuffer.position(0);
        GLES20.glVertexAttribPointer(vPosition, PER_COMENT_CONUT, GLES20.GL_FLOAT, false, STRIDE, vertBuffer);

        GLES20.glEnableVertexAttribArray(vColor);
        vertBuffer.position(PER_COMENT_CONUT);
        GLES20.glVertexAttribPointer(vColor, COLOR_COMENT_CONUT, GLES20.GL_FLOAT, false, STRIDE, vertBuffer);

        if (type == TYPE_RECTANGLE) {
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, pos.length / PER_COMENT_CONUT);
        }else {
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, pos.length / PER_COMENT_CONUT);
        }

        GLES20.glDisableVertexAttribArray(vPosition);
        GLES20.glDisableVertexAttribArray(vColor);
    }

    @Override
    public void setType(int type) {
        this.type = type;
    }
}
