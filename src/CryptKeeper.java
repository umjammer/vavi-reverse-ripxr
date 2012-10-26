import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;


public class CryptKeeper {

    public InputStream entomb(InputStream is, SecretKey secretKey) {
        return encrypt(compress(is), secretKey);
    }

    public byte[] entomb(byte[] bytes, SecretKey secretKey) {
        return encrypt(compress(bytes), secretKey);
    }

    public byte[] entombed(String string, SecretKey secretKey) {
        try {
            byte[] bytes = string.getBytes("UTF8");
            return entomb(bytes, secretKey);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public InputStream exhume(InputStream is, SecretKey secretKey) {
        return decompress(decrypt(is, secretKey));
    }

    public byte[] exhume(byte[] bytes, SecretKey secretkey) {
        return decompress(decrypt(bytes, secretkey));
    }

    public InputStream compress(InputStream is) {
        DeflaterInputStream dis = null;
        try {
            Deflater deflater = new Deflater(9, false);
            dis = new DeflaterInputStream(is, deflater);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dis;
    }

    public InputStream decompress(InputStream is) {
        InflaterInputStream iis = null;
        try {
            Inflater inflater = new Inflater(false);
            iis = new InflaterInputStream(is, inflater);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iis;
    }

    public byte[] compress(byte[] bytes) {
        try {
            Deflater deflater = new Deflater(9, false);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DeflaterOutputStream dos = new DeflaterOutputStream(baos, deflater);
            dos.write(bytes);
            dos.close();
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] decompress(byte[] bytes) {
        try {
            Inflater inflater = new Inflater(false);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            InflaterInputStream iis = new InflaterInputStream(bais, inflater);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int b = 0;
            while ((b = iis.read()) != -1)
                baos.write(b);

            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public InputStream decrypt(InputStream is, SecretKey secretKey) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CipherInputStream(is, cipher);
    }

    public InputStream encrypt(InputStream is, SecretKey secretkey) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, secretkey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CipherInputStream(is, cipher);
    }

    public byte[] encrypt(byte[] bytes, SecretKey secretKey) {
        byte[] result = null;
        try {
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            result = cipher.doFinal(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public byte[] decrypt(byte[] bytes, SecretKey secretKey) {
        byte[] result = null;
        try {
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            result = cipher.doFinal(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
