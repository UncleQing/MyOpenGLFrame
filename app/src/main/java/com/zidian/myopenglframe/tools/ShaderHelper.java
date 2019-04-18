package com.zidian.myopenglframe.tools;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.content.res.Resources;
import android.opengl.GLES20;
import android.util.Log;

import com.zidian.myopenglframe.App;

import java.io.InputStream;

public class ShaderHelper {
    private static final String TAG = "ShaderHelper";

    /**
     * 对外提供入口
     * 创建OpenGL程序并链接返回程序对象
     *
     * @param vertexPath
     * @param fragmentPath
     * @return
     */
    public int makeProgram(String vertexPath, String fragmentPath) {
        //编译顶点着色器
        int vertex = compileVertexShader(vertexPath);
        if (vertex == 0) {
            return 0;
        }
        //编译片元着色器
        int fragment = compileFragmentShader(fragmentPath);
        if (fragment == 0) {
            return 0;
        }
        //链接程序
        return linkPragram(vertex, fragment);
    }

    /**
     * 编译顶点着色器
     *
     * @param vertexPath
     */
    public static int compileVertexShader(String vertexPath) {
        return compileShader(GLES20.GL_VERTEX_SHADER, loadRes(vertexPath));
    }

    /**
     * 编译片元着色器
     *
     * @param fragmentPath
     */
    public static int compileFragmentShader(String fragmentPath) {
        return compileShader(GLES20.GL_FRAGMENT_SHADER, loadRes(fragmentPath));
    }

    /**
     * 编译着色器
     *
     * @param shaderType
     * @param source
     * @return
     */
    public static int compileShader(int shaderType, String source) {
        //创建着色器对象
        int shader = GLES20.glCreateShader(shaderType);
        if (0 != shader) {
            //将着色器代码上传至着色器对象
            GLES20.glShaderSource(shader, source);
            //编译着色器
            GLES20.glCompileShader(shader);
            //获取编译状态，OpenGL中将要获取的值放入长度为1的数组首位
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {
                glError(1, "Could not compile shader:" + shaderType);
                glError(1, "GLES20 Error:" + GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }
        return shader;
    }

    /**
     * 加载asset下的着色器源码
     *
     * @param path
     * @return
     */
    private static String loadRes(String path) {
        Resources mRes = App.getInstance().getResources();
        StringBuilder result = new StringBuilder();
        try {
            InputStream is = mRes.getAssets().open(path);
            int ch;
            byte[] buffer = new byte[1024];
            while (-1 != (ch = is.read(buffer))) {
                result.append(new String(buffer, 0, ch));
            }
        } catch (Exception e) {
            return null;
        }
        return result.toString().replaceAll("\\r\\n", "\n");
    }


    /**
     * 新建程序对象并附上着色器
     *
     * @param vertexSharder
     * @param fragmentShader
     * @return
     */
    private static int linkPragram(int vertexSharder, int fragmentShader) {
        //创建一个OpenGL对象
        int program = GLES20.glCreateProgram();
        if (program != 0) {
            //将顶点着色器依附到OpenGL程序对象
            GLES20.glAttachShader(program, vertexSharder);
            //将片段着色器依附到OpenGL程序对象
            GLES20.glAttachShader(program, fragmentShader);
            //将两个着色器链接到OpenGL程序对象
            GLES20.glLinkProgram(program);
            //获取链接状态
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] != GLES20.GL_TRUE) {
                glError(2, "Could not link program:" + GLES20.glGetProgramInfoLog(program));
                GLES20.glDeleteProgram(program);
                program = 0;
            }
        } else {
            glError(1, "Could not create program");
        }
        return program;
    }


    /**
     * 判断设备是否支持OpenGL2.0
     *
     * @param context
     * @return
     */
    public boolean isSupported(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        return configurationInfo.reqGlEsVersion >= 0x2000;
    }

    private static void glError(int code, Object index) {
        if (code != 0) {
            Log.e(TAG, "glError:" + code + "---" + index);
        }
    }
}
