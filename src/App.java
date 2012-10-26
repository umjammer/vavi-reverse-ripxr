import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.SequenceInputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.zip.CRC32;

import javax.crypto.spec.SecretKeySpec;

import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;


public final class App {

    private View view;
    private ArchiveFile archive;
    private RemoteClassInterface remoteClassInterface;

    public final void showMessageBox(String message) {
        MessageBox messageBox = new MessageBox(view.shell, 32);
        messageBox.setMessage(message);
        messageBox.open();
        System.out.println(message);
    }

    public final boolean isFileSelected() {
        boolean loop = true;
        boolean flag = false;
        if (archive != null)
            view.table.removeAll();
        while (loop) {
            FileDialog dialog = new FileDialog(view.shell, 4096);
            dialog.setFilterNames(new String[] {
                "ripxr", "All Files (*.*)"
            });
            dialog.setFilterExtensions(new String[] {
                "*.ripxr", "*.*"
            });
            String fileName = dialog.open();
            if (fileName != null) {
                archive = new ArchiveFile(fileName);
                loop = false;
                flag = true;
            } else {
                loop = view.showFileChooser();
            }
        }
        return flag;
    }

    public final void initTable() {
        try {
            byte[] header = archive.getHeader();
            SecretKeySpec secretKeySpec = new SecretKeySpec(archive.secretKey, "DES");
            CryptKeeper cryptKeeper = new CryptKeeper();
            String[] files = new String(cryptKeeper.exhume(header, secretKeySpec)).split("#,#");
            CRC32 crc32 = new CRC32();
            crc32.update(header);
            initRemoteClassInterface(crc32.getValue());
            view.table.setItemCount(files.length);
            view.tableListener.setItems(files);
            view.table.getColumn(0).pack();
            view.table.getColumn(1).setAlignment(0x20000);
            view.table.getColumn(1).pack();
            view.shell.pack();
            view.extractAllButtonListener.setTable(view.table);
            view.extractSelectedButtonListener.setTable(view.table);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void createArchive() {
        FileDialog dialog = new FileDialog(view.shell, 2);
        dialog.setFilterNames(new String[] {
            "All Files (*.*)"
        });
        dialog.setFilterExtensions(new String[] {
            "*.*"
        });
        dialog.open();
        String[] fileNames = dialog.getFileNames();
        String dir = dialog.getFilterPath();
        if (dir.charAt(dir.length() - 1) != File.separatorChar)
            dir = dir + File.separatorChar;
        for (int i = 0; i < fileNames.length; i++)
            fileNames[i] = dir + fileNames[i];

        dialog = new FileDialog(view.shell, 8192);
        dialog.setFilterNames(new String[] {
            "ripxr", "All Files (*.*)"
        });
        dialog.setFilterExtensions(new String[] {
            "*.ripxr", "*.*"
        });
        remoteClassInterface.createArchive(fileNames, dialog.open(), getUserId());
    }

    public final void extract(String[] fileNames, int[] ai, long[] al) {
        try {
System.err.println(isRegistered());
/*
            label0: {
                if (isRegistered()) {
                    boolean violation = false;
                    if (remoteClassInterface != null)
                        violation = remoteClassInterface.copyrightViolation();
                    if (!violation)
                        break label0;
                }
                if (remoteClassInterface == null) {
                    showMessageBox("Unable to contact registration server!\n check your firewall settings.");
                    return;
                }
                if (!isRegistered()) {
//                a;
                    fileNames = null;
                    Program.launch(remoteClassInterface.getErrorMessage());
                }
                showMessageBox(remoteClassInterface.getErrorMessage());
                return;
            }
*/
            if (fileNames.length == 0) {
                showMessageBox("No files to extract");
                return;
            }
            ByteArrayInputStream bais = new ByteArrayInputStream(archive.buffer.array());
            remoteClassInterface.extractProtectedMedia(fileNames, al, ai, new SequenceInputStream(bais, archive.bis), archive.file.getName());
            archive.fis.close();
            archive.bis.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                view.table.removeAll();
                showMessageBox("Files extracted to new directory");
            } catch (Exception f) {
                f.printStackTrace();
            }
        }
    }

    App() {
        view = new View(this);
        archive = null;
        remoteClassInterface = null;
    }

    App(String filename) {
        view = new View(this);
        archive = new ArchiveFile(filename);
        remoteClassInterface = null;
    }

    public final void start() {
        if (archive != null || isFileSelected())
            initTable();
        if (remoteClassInterface == null)
            initRemoteClassInterface(0);
        while (true) {
            if (view.shell.isDisposed())
                break;
            if (!view.display.readAndDispatch())
                view.display.sleep();
        }
        view.display.dispose();
        if (remoteClassInterface != null)
            remoteClassInterface.finish();
    }

    private boolean isRegistered() {
        boolean registered = false;
        if (remoteClassInterface != null)
            registered = remoteClassInterface.isRegistered();
        return registered;
    }

    private void initRemoteClassInterface(long fileId) {
        try {
//            String url = "http://tools.ripxr.com/FALSEFILE/" + getUserId() + "/" + fileId + "/";
//            System.err.println(url);
//            Class<?> clazz = new URLClassLoader(new URL[] { new URL(url) }).loadClass("RemoteExtractMedia");
//            remoteClassInterface = (RemoteClassInterface) clazz.newInstance();
            remoteClassInterface = new RemoteExtractMedia();
            remoteClassInterface.setCurrentFile(fileId);
            remoteClassInterface.setCurrentUser(getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long getUserId() {
        CRC32 crc32 = null;
        try {
            byte[] bytes = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
            crc32 = new CRC32();
            crc32.update(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return crc32.getValue();
    }
}
