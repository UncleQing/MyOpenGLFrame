package com.zidian.myopenglframe;

import android.app.Activity;
import android.os.Bundle;

import java.nio.FloatBuffer;

public class PictureActivity extends Activity {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
    }
}
