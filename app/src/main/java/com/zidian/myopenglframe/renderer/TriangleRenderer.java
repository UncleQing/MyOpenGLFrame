package com.zidian.myopenglframe.renderer;

import android.content.Context;
import android.opengl.GLES20;

import com.zidian.myopenglframe.tools.ShaderHelper;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 简单三角形和矩形
 */
public class TriangleRenderer extends BaseRenderer {
    private Context mContext;
    private int pragram;
    //确认路径无误，可以点进去查看
    private static final String VERT_PATH = "triangle/triangle.vert";
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
    //片元着色器成员
    private static final String V_COLOR = "vColor";
    private int vColor;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //清屏颜色:灰
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);

        vertBuffer = ShaderHelper.initBuffer(pos);
        pragram = ShaderHelper.makeProgram(VERT_PATH, FRAG_PATH);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //设置窗口大小
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        /**
         * 清除屏幕
         * GL_COLOR_BUFFER_BIT    表明颜色缓冲区
         * GL_DEPTH_BUFFER_BIT    表明深度缓冲
         * GL_STENCIL_BUFFER_BIT  表明模型缓冲区
         */
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        //将程序加入OpenGL2.0环境
        GLES20.glUseProgram(pragram);
        //获取顶点着色器位置索引vPosition
        vPosition = GLES20.glGetAttribLocation(pragram, V_POSITION);
        //顶点数组使能
        GLES20.glEnableVertexAttribArray(vPosition);
        /**
         * vPosition关联buffer
         *
         * 位置索引
         * 每个顶点关联的分量：1、2、3、4
         * 数据类型
         * 当被访问时固定的数值是否被归一化（true）或直接转换为固定点值（false）,只有使用整型数据才有意义，暂且忽略
         * 顶点属性之间的跨距，若为0则代表顶点紧密排列在一起，一般顶点之后还带颜色，设置跨距即跳过颜色
         * 哪里读数据，数据缓冲区
         */
        GLES20.glVertexAttribPointer(vPosition, PER_COMENT_CONUT, GLES20.GL_FLOAT, false, 0, vertBuffer);


        //获取片元着色器vColor
        vColor = GLES20.glGetUniformLocation(pragram, V_COLOR);
        //绘制颜色
        GLES20.glUniform4fv(vColor, 1, colors, 0);
        if (type == TYPE_RECTANGLE){
            //绘制三角
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, pos.length / PER_COMENT_CONUT);
        }else {
            //绘制矩形
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, pos.length / PER_COMENT_CONUT);
        }

        //顶点数组逆使能
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
