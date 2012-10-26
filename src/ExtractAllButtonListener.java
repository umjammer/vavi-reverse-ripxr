
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public final class ExtractAllButtonListener
    implements Listener {

    private Table table;
    private App app;

    ExtractAllButtonListener(App app, Table table) {
        this.table = table;
        this.app = app;
    }

    public final void setTable(Table table) {
        this.table = table;
    }

    public final void handleEvent(Event event) {
        if (table != null) {
            TableItem[] items = table.getItems();
            String[] filenames = new String[items.length];
            int[] ai = new int[items.length];
            long[] fileIds = new long[items.length];
            for (int i = 0; i < items.length; i++) {
                filenames[i] = items[i].getText(0);
                fileIds[i] = Long.valueOf(items[i].getText(1));
                ai[i] = i;
                System.out.println(filenames[i]);
            }

            app.extract(filenames, ai, fileIds);
        } else {
            app.showMessageBox("No files to extract");
        }
    }
}
