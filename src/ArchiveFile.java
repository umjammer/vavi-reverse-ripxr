import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;


public final class ArchiveFile {

    File file;
    byte[] secretKey;
    FileInputStream fis;
    BufferedInputStream bis;
    ByteBuffer buffer;

    ArchiveFile(String filename) {
        file = new File(filename);
        secretKey = new byte[8];
    }

    /** @return header */
    public byte[] getHeader() {
        fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteBuffer salt2Buffer;
        ByteBuffer salt1buffer;
        boolean flag;
        bis = new BufferedInputStream(fis);
        salt2Buffer = ByteBuffer.allocate(4);
        salt1buffer = ByteBuffer.allocate(4);
        try {
            flag = false;
            for (int i = 0; i < 8; i++) {
                if (flag) {
                    // A salt1 (*1)
                    salt1buffer.put((byte) bis.read());
                } else {
                    // B salt1 length
                    salt2Buffer.put((byte) bis.read());
                }
                flag = !flag;
            }

            int salt1len = salt2Buffer.getInt(0);
            byte[] buf1 = salt1buffer.array(); // A
            salt1buffer = ByteBuffer.allocate(salt1len);
            salt1buffer.put(buf1); // A (*1) 
            System.out.println("data ByteBuffer was: " + (salt1len - 12));
            buffer = ByteBuffer.allocate(salt1len - 12);
            int restlen = (salt1len << 1) - 8;
            int j = 0;
            int k = 0;
            for (int i = 0; i < restlen; i++) {
                if (flag) {
                    // A
                    salt1buffer.put((byte) bis.read());
                } else if (j < secretKey.length) {
                    // C secret key
                    secretKey[j] = (byte) bis.read();
                    j++;
                } else {
                    k++;
                    buffer.put((byte) bis.read());
                }
                flag = !flag;
            }

            System.out.println("filled: " + k);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return salt1buffer.array();
    }
}
