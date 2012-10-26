
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

public final class OpenMenuItemSelectionListener
    implements SelectionListener {

    private App d_;

    OpenMenuItemSelectionListener(App d1) {
        d_ = d1;
    }

    public final void widgetSelected(SelectionEvent event) {
        d_.isFileSelected();
        d_.initTable();
    }

    public final void widgetDefaultSelected(SelectionEvent event) {
        d_.isFileSelected();
        d_.initTable();
    }
}
