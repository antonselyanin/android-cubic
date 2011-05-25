package antonj.utils;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * User: Anton
 * Date: 04.11.2009
 * Time: 17:41:35
 */
public class IO {
    private static final String TAG = IO.class.getSimpleName();

    private static final int IO_BUFFER_SIZE = 4 * 1024;

    public static ByteBuffer streamToBuffer(InputStream in)
            throws IOException {
        BufferedOutputStream out = null;

        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            out = new BufferedOutputStream(os, IO_BUFFER_SIZE);
            copy(in, out);
            out.flush();
            return ByteBuffer.wrap(os.toByteArray());
        } finally {
            silentClose(in);
            silentClose(out);
        }
    }

    public static void silentClose(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                android.util.Log.e(TAG, "Could not close stream", e);
            }
        }
    }

    private static void copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] b = new byte[IO_BUFFER_SIZE];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }

    public static String getStringAndClose(InputStream input)
            throws IOException {
        try {
            return getString(input);
        } finally {
            silentClose(input);
        }
    }

    public static String getString(InputStream input)
        throws IOException {
        return new String(retrieveData(input));
    }

    public static byte[] retrieveData(InputStream input)
            throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        transfer(input, output);
        output.flush();
        return output.toByteArray();
    }

    public static long transfer(InputStream input, OutputStream output)
            throws IOException {
        byte[] buf = new byte[IO_BUFFER_SIZE];
        int count;
        long total = 0;
        do {
            count = input.read(buf);
            if (count > 0) {
                total += count;
                output.write(buf, 0, count);
            }
        } while (count >= 0);
        return total;
    }

}
