/*
 * Copyright (c) 2012 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

import java.io.File;

import javax.crypto.spec.SecretKeySpec;

import org.junit.Test;



/**
 * RemoteExtractMediaTest. 
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2012/10/25 umjammer initial version <br>
 */
public class RemoteExtractMediaTest {

    @Test
    public void test() throws Exception {
        RemoteExtractMedia extractMedia = new RemoteExtractMedia();
        String[] files = new File("/Applications/Local/Experimental/RipXR.app/Contents/Resources/src").list();
        for (int i = 0; i < files.length; i++) {
            files[i] = "/Applications/Local/Experimental/RipXR.app/Contents/Resources/src/" + files[i];
        }
        extractMedia.createArchive(files, "/Applications/Local/Experimental/RipXR.app/Contents/Resources/tmp/tmp.ripxr", 0);
    }

//    @Test
    public void test1() throws Exception {
        ArchiveFile archive = new ArchiveFile("/Users/nsano/Downloads/JDownloder/album-435472.ripxr/album-435472.ripxr");
        byte[] header = archive.getHeader();
        SecretKeySpec secretKeySpec = new SecretKeySpec(archive.secretKey, "DES");
        CryptKeeper cryptKeeper = new CryptKeeper();
        String[] files = new String(cryptKeeper.exhume(header, secretKeySpec)).split("#,#");
        for (String file : files) {
            String[] data = file.split("####");
            System.err.println(data[0].trim() + ", " + data[1].trim());
        }
        
    }
}

/* */
