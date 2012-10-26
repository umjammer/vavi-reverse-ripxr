
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public final class ExtractSelectedButtonListener
    implements Listener {

    private Table table;
    private App app;

    ExtractSelectedButtonListener(App app, Table table) {
        this.table = table;
        this.app = app;
    }

    public final void setTable(Table table) {
        this.table = table;
    }

    public final void handleEvent(Event event) {
        if (table != null) {
            TableItem[] items = table.getSelection();
            String[] filenames = new String[items.length];
            int[] ai = new int[items.length];
            for (int i = 0; i < items.length; i++) {
                filenames[i] = items[i].getText(0);
                ai[i] = table.indexOf(items[i]);
                System.out.println(filenames[i]);
            }

            long[] al = new long[(items = table.getItems()).length];
            for (int i = 0; i < items.length; i++)
                al[i] = Long.valueOf(items[i].getText(1));

            app.extract(filenames, ai, al);
            return;
        } else {
            app.showMessageBox("No files to extract");
            return;
        }
    }
}
