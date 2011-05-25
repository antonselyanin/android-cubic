package antonj.utils;

import android.util.FloatMath;

/**
 * User: Anton
 * Date: 24.11.2009
 * Time: 0:23:23
 */
public class Arrays2 {
    public static void reverse(int[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            int buf = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = buf;
        }
    }

    public static void sub3(float[] dest, float[] v1, float[] v2) {
        dest[0] = v1[0] - v2[0];
        dest[1] = v1[1] - v2[1];
        dest[2] = v1[2] - v2[2];
    }

    public static void cross3(float[] dest, float[] v1, float[] v2) {
        dest[0] = v1[1] * v2[2] - v1[2] * v2[1];
        dest[1] = v1[2] * v2[0] - v1[0] * v2[2];
        dest[2] = v1[0] * v2[1] - v1[1] * v2[0];
    }

    public static void add3(float[] dest, float[] v1, float[] v2) {
        dest[0] = v1[0] + v2[0];
        dest[1] = v1[1] + v2[1];
        dest[2] = v1[2] + v2[2];
    }
    public static void add3(float[] v1, float[] v2) {
        v1[0] += v2[0];
        v1[1] += v2[1];
        v1[2] += v2[2];
    }

    public static float dot3(float[] v1, float[] v2) {
        return v1[0]*v2[0] + v1[1]*v2[1] + v1[2]*v2[2];
    }

//////////////////////////////////////////

    public static void sub3(float[] dest, float[] vertices, int offset, int index1, int index2) {
        int offset1 = offset + index1 * 3;
        int offset2 = offset + index2 * 3;
        dest[0] = vertices[offset1] - vertices[offset2];
        dest[1] = vertices[offset1 + 1] - vertices[offset2 + 1];
        dest[2] = vertices[offset1 + 2] - vertices[offset2 + 2];
    }

    public static void cross3(float[] dest, float[] vertices, int offset, int index1, int index2) {
        int offset1 = offset + index1 * 3;
        int offset2 = offset + index2 * 3;
        dest[0] = vertices[offset1 + 1] * vertices[offset2 + 2] - vertices[offset1 + 2] * vertices[offset2 + 1];
        dest[1] = vertices[offset1 + 2] * vertices[offset2] - vertices[offset1] * vertices[offset2 + 2];
        dest[2] = vertices[offset1] * vertices[offset2 + 1] - vertices[offset1 + 1] * vertices[offset2];
    }

    public static void add3(float[] dest, float[] vertices, int offset, int index1, int index2) {
        int offset1 = offset + index1 * 3;
        int offset2 = offset + index2 * 3;
        dest[0] = vertices[offset1] + vertices[offset2];
        dest[1] = vertices[offset1 + 1] + vertices[offset2 + 1];
        dest[2] = vertices[offset1 + 2] + vertices[offset2 + 2];
    }

    public static float dot3(float[] vertices, int offset, int index1, int index2) {
        int offset1 = offset + index1 * 3;
        int offset2 = offset + index2 * 3;

        return vertices[offset1] * vertices[offset2]
                + vertices[offset1 + 1] * vertices[offset2 + 1]
                + vertices[offset1 + 2] * vertices[offset2 + 2]; 
    }

//////////////////////////////////////////

    public static void sub3(float[] dest, float[] v1, int offset1, float[] v2, int offset2) {
        dest[0] = v1[offset1] - v2[offset2];
        dest[1] = v1[offset1 + 1] - v2[offset2 + 1];
        dest[2] = v1[offset1 + 2] - v2[offset2 + 2];
    }

    public static void cross3(float[] dest, float[] v1, int offset1, float[] v2, int offset2) {
        dest[0] = v1[offset1 + 1] * v2[offset2 + 2] - v1[offset1 + 2] * v2[offset2 + 1];
        dest[1] = v1[offset1 + 2] * v2[offset2 + 0] - v1[offset1 + 0] * v2[offset2 + 2];
        dest[2] = v1[offset1 + 0] * v2[offset2 + 1] - v1[offset1 + 1] * v2[offset2 + 0];
    }

    public static void add3(float[] dest, float[] v1, int offset1, float[] v2, int offset2) {
        dest[0] = v1[offset1 + 0] + v2[offset2 + 0];
        dest[1] = v1[offset1 + 1] + v2[offset2 + 1];
        dest[2] = v1[offset1 + 2] + v2[offset2 + 2];
    }

    public static float dot3(float[] v1, int offset1, float[] v2, int offset2) {
        return v1[offset1 + 0]*v2[offset2 + 0]
                + v1[offset1 + 1]*v2[offset2 + 1]
                + v1[offset1 + 2]*v2[offset2 + 2];
    }

    public static void normalize3(float[] vector) {
        float length = length3(vector);
        vector[0] /= length;
        vector[1] /= length;
        vector[2] /= length;        
    }

    public static float length3(float[] vector) {
        return FloatMath.sqrt(dot3(vector, vector));
    }

    public static void multiply(float[] vector, float scalar) {
        vector[0] *= scalar;
        vector[1] *= scalar;
        vector[2] *= scalar;
    }
}
