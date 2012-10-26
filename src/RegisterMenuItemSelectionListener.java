
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.program.Program;

public final class RegisterMenuItemSelectionListener
    implements SelectionListener {

    public final void widgetSelected(SelectionEvent event) {
        Program.launch("http://tools.ripxr.com/");
    }

    public final void widgetDefaultSelected(SelectionEvent nevent) {
        Program.launch("http://tools.ripxr.com/");
    }
}
