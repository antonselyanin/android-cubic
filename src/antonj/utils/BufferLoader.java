package antonj.utils;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * User: Anton
 * Date: 06.11.2009
 * Time: 20:25:18
 */
public abstract class BufferLoader<B extends Buffer> {
    public abstract void copyData(B dest, B source, int size);

    public abstract B getTypedBuffer(ByteBuffer buffer);
    
    public abstract int getBytes();

    public abstract String toString(ByteBuffer buffer);
}

