import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;


public class RealFile {

    private File file;

    public RealFile(String filename) {
        file = new File(filename);
    }

    /**
     * @param salt1
     * @param salt2 DES だから 64bit ?
     */
    public void generate(byte[] salt1, byte[] salt2, InputStream is) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream baos = new BufferedOutputStream(fos);
            int i = 0;
            boolean flag = false;
            byte[] salt1len = ByteBuffer.allocate(4).putInt(salt1.length).array();
            int j = 0;
            int k = 0;
int d = 0;
            boolean done = false;
            while (!done) {
                byte[] one = new byte[1];
                if (flag && i < salt1.length) {
                    one[0] = salt1[i];
System.err.print("A");
                    i++;
                } else if (j < 4) {
                    one[0] = salt1len[j];
System.err.print("B");
                    j++;
                } else if (k < salt2.length) {
                    one[0] = salt2[k];
System.err.print("C");
                    k++;
                } else {
if (d < 100) {
System.err.print("D");
d++;
}
                    done = is.read(one) == -1;
                }
                baos.write(one);
                flag = !flag;
            }
System.err.println();
            baos.close();
            fos.flush();
            fos.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getFile() {
        return file;
    }
}
