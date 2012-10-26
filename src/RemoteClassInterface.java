
import java.io.InputStream;

public interface RemoteClassInterface {

    String getErrorMessage();

    boolean isRegistered();

    boolean copyrightViolation();

    boolean createArchive(String[] fileNames, String archiveName, long userId);

    void setCurrentFile(long id);

    void setCurrentUser(long id);

    void extractProtectedMedia(String[] as, long[] al, int[] ai, InputStream is, String s);

    void finish();
}
