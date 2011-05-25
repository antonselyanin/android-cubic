package antonj.utils;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;

/**
 * User: Anton
 * Date: 06.11.2009
 * Time: 20:34:59
 */
public class ShortLoader extends BufferLoader<ShortBuffer> {
    @Override
    public void copyData(ShortBuffer dest, ShortBuffer source, int size) {
        short[] buf = new short[size];
        source.get(buf);
        dest.put(buf);
    }

    @Override
    public int getBytes() {
        return Primitives.SHORT_SIZE;
    }

    @Override
    public String toString(ByteBuffer source) {
        ShortBuffer buf = source.asShortBuffer();
        short[] ar = new short[buf.capacity()];
        buf.get(ar);
        return Arrays.toString(ar);
    }

    @Override
    public ShortBuffer getTypedBuffer(ByteBuffer buffer) {
        return buffer.asShortBuffer();
    }
}