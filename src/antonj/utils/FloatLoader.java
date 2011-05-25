package antonj.utils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.Arrays;

/**
 * User: Anton
 * Date: 06.11.2009
 * Time: 20:34:59
 */
public class FloatLoader extends BufferLoader<FloatBuffer> {
    @Override
    public void copyData(FloatBuffer dest, FloatBuffer source, int size) {
        float[] buf = new float[size];
        source.get(buf);
        dest.put(buf);
    }

    @Override
    public int getBytes() {
        return Primitives.FLOAT_SIZE;
    }

    @Override
    public String toString(ByteBuffer source) {
        FloatBuffer buf = source.asFloatBuffer();
        float[] ar = new float[buf.capacity()];
        buf.get(ar);
        return Arrays.toString(ar);
    }

    @Override
    public FloatBuffer getTypedBuffer(ByteBuffer buffer) {
        return buffer.asFloatBuffer();
    }
}
