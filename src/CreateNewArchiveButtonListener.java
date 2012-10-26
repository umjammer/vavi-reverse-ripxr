import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;


public final class CreateNewArchiveButtonListener implements Listener {

    private App d_;

    CreateNewArchiveButtonListener(App d1) {
        d_ = d1;
    }

    public final void handleEvent(Event event) {
        d_.createArchive();
    }
}
