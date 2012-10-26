
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public final class ExitMenuItemSelectionListener
    implements SelectionListener {

    private Shell shell_;
    private Display display_;

    ExitMenuItemSelectionListener(Shell shell, Display display) {
        shell_ = shell;
        display_ = display;
    }

    public final void widgetSelected(SelectionEvent event) {
        shell_.close();
        display_.dispose();
    }

    public final void widgetDefaultSelected(SelectionEvent event) {
        shell_.close();
        display_.dispose();
    }
}
