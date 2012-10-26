import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.net.URL;
import java.util.Formatter;
import java.util.Vector;
import java.util.zip.CRC32;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


public class RemoteExtractMedia implements RemoteClassInterface {

    private long currentFileChecksum;
    private String errorMessage;
    private long macChecksum;

    public void setCurrentFile(long fileId) {
        currentFileChecksum = fileId;
    }

    public void setCurrentUser(long userId) {
        macChecksum = userId;
    }

    @SuppressWarnings("unused")
    private SecretKey getFileKey() {
        return null;
    }

    public boolean createArchive(String[] filenames, String archive, long userId) {
        try {
            RealFile realfile = new RealFile(archive);
            SecretKey secretKey = KeyGenerator.getInstance("DES").generateKey();
            CryptKeeper cryptKeeper = new CryptKeeper();
            Vector<FileInputStream> vector = new Vector<FileInputStream>(filenames.length);
            String str = "";
            for (int i = 0; i < filenames.length; i++) {
                System.out.println(filenames[i]);
                File file = new File(filenames[i]);
                str = str + file.getName() + "####" + file.length();
                if (i != filenames.length - 1)
                    str = str + "#,#";
                vector.add(new FileInputStream(file));
            }
            System.out.println(str);
            byte[] salt1 = cryptKeeper.entombed(str, secretKey);
            byte[] salt2 = secretKey.getEncoded();
            SequenceInputStream sis = new SequenceInputStream(vector.elements());
            secretKey = KeyGenerator.getInstance("DESede").generateKey();
            realfile.generate(salt1, salt2, cryptKeeper.entomb(sis, secretKey));
            registerArchive(checksum(salt1), userId, secretKey.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean registerArchive(long checkSum, long userId, byte[] secretKey) {
        System.out.println(bytesToHexString(secretKey));
        try {
            URL url = new URL("http://tools.ripxr.com/FALSEFILE/" + userId + "/" + checkSum + "/" + bytesToHexString(secretKey) + "/1");
            InputStream is = url.openStream();
            while (is.read() != -1)
                ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isRegistered() {
        errorMessage = "http://tools.ripxr.com/REGISTERME?u=" + macChecksum + "&f=" + currentFileChecksum;
        System.err.println(errorMessage);
        return false;
    }

    public boolean copyrightViolation() {
        return false;
    }

    public void finish() {
        try {
            System.out.println("We are not finished yet...");
            Thread.sleep(10000);
            System.out.println("Now we are done.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void extractProtectedMedia(String[] filenames, long[] al, int[] ai, InputStream is, String archive) {
    }

    private long checksum(byte[] bytes) {
        CRC32 crc32 = new CRC32();
        try {
            crc32.update(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return crc32.getValue();
    }

    @SuppressWarnings("unused")
    private byte[] hexStringToByteArray(String s) {
        int l = s.length();
        byte[] bytes = new byte[l / 2];
        for (int i = 0; i < l; i += 2)
            bytes[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));

        return bytes;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        Formatter formatter = new Formatter(sb);
        int l = bytes.length;
        for (int i = 0; i < l; i++) {
            formatter.format("%02x", bytes[i]);
        }

        return sb.toString();
    }
}
