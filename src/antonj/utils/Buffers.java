package antonj.utils;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * User: Anton
 * Date: 08.11.2009
 * Time: 20:15:43
 */
public class Buffers {
    public static <B extends Buffer> ByteBuffer read(ByteBuffer source, BufferLoader<B> loader, int size) {
        ByteBuffer dest = createDirect(loader.getBytes() * size);
        B typedDest = loader.getTypedBuffer(dest);
        byte[] buf = new byte[loader.getBytes() * size];
        source.get(buf);
        ByteBuffer buffer = ByteBuffer.wrap(buf);
        buffer.order(source.order());
        B typedSource = loader.getTypedBuffer(buffer);
        loader.copyData(typedDest, typedSource, size);
        return dest;
    }

    public static ByteBuffer readShorts(ByteBuffer source, int size) {
        return read(source, BufferLoaders.SHORT, size);
    }

    public static ByteBuffer readFloats(ByteBuffer source, int size) {
        return read(source, BufferLoaders.FLOAT, size);
    }

    public static ByteBuffer createDirect(int capacity) {
        ByteBuffer dest = ByteBuffer.allocateDirect(capacity);
        dest.order(ByteOrder.nativeOrder());
        return dest;
    }

    //TODO: try direct buffer?
    public static FloatBuffer create(float[] floats) {
//        FloatBuffer buffer = createDirect(floats.length * Primitives.FLOAT_SIZE).asFloatBuffer();
        FloatBuffer buffer = FloatBuffer.allocate(floats.length);
        buffer.put(floats);
        return buffer;
    }
}
