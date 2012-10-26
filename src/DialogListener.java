import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;


final class DialogListener implements Listener {

    private Button defaultButton;
    private boolean[] result;
    private Shell shell;

    DialogListener(Button button, boolean[] result, Shell shell) {
        defaultButton = button;
        this.result = result;
        this.shell = shell;
    }

    public final void handleEvent(Event event) {
        if (event.widget == defaultButton)
            result[0] = true;
        else
            result[0] = false;
        shell.close();
    }
}
