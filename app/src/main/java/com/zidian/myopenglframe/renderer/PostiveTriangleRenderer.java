package com.zidian.myopenglframe.renderer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.zidian.myopenglframe.tools.ShaderHelper;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class PostiveTriangleRenderer extends BaseRenderer {

    //确认路径无误，可以点进去查看
    private static final String VERT_PATH = "triangle/postive_triangle.vert";
    private static final String FRAG_PATH = "triangle/triangle.frag";
    //每个顶点关联分量数，1、2、3、4,x\y\z\w
    private static final int PER_COMENT_CONUT = 2;
    //形状，三角，矩形
    private int type;
    public static final int TYPE_TRIANGLE = 0;
    public static final int TYPE_RECTANGLE = 1;
    //顶点坐标
    private float[] pos = {
            -0.8f, -0.8f,
            0.8f, -0.8f,
            -0.8f, 0.8f,
            0.8f, 0.8f
    };
    //颜色:white
    private float[] colors = {1f, 1f, 1f, 1f};
    //顶点buffer
    private FloatBuffer vertBuffer;
    //顶点着色器成员
    private static final String V_POSITION = "vPosition";
    private int vPosition;
    private static final String V_MATRIX = "vMatrix";
    private int vMatrix;
    private float[] projectMatrix = new float[16];
    //片元着色器成员
    private static final String V_COLOR = "vColor";
    private int vColor;


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        vertBuffer = ShaderHelper.initBuffer(pos);
        program = ShaderHelper.makeProgram(VERT_PATH, FRAG_PATH);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        //宽高比ratio
        float ratio = width > height ? (float)width / height : (float)height / width;
        if (width > height) {
            Matrix.orthoM(projectMatrix, 0, -ratio, ratio, -1f, 1f, -1f, 1f);
        }else {
            Matrix.orthoM(projectMatrix, 0, -1f, 1f, -ratio, ratio, -1f, 1f);
        }

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUseProgram(program);

        vMatrix = GLES20.glGetUniformLocation(program, V_MATRIX);
        GLES20.glUniformMatrix4fv(vMatrix, 1, false, projectMatrix, 0);

        vPosition = GLES20.glGetAttribLocation(program, V_POSITION);
        GLES20.glEnableVertexAttribArray(vPosition);
        GLES20.glVertexAttribPointer(vPosition, PER_COMENT_CONUT, GLES20.GL_FLOAT, false, 0 , vertBuffer);

        vColor = GLES20.glGetUniformLocation(program, V_COLOR);
        GLES20.glUniform4fv(vColor, 1, colors, 0);

        if (type == TYPE_RECTANGLE) {
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, pos.length / PER_COMENT_CONUT);
        }else {
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, pos.length / PER_COMENT_CONUT);
        }

        GLES20.glDisableVertexAttribArray(vPosition);

    }

    @Override
    protected void with(Context context) {
        mContext = context;
    }

    public void setType(int type) {
        this.type = type;
    }
}
