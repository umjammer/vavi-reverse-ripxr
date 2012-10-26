import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;


public final class TableListener implements Listener {

    private String[] items;
    private Table table;

    public TableListener(Table table) {
        this.table = table;
    }

    public final void setItems(String[] items) {
        this.items = items;
    }

    public final void handleEvent(Event event) {
        TableItem item = (TableItem) event.item;
        int p = table.indexOf(item);
        String[] strings = items[p].split("####");
        for (int i = 0; i < strings.length; i++)
            item.setText(i, strings[i].trim());
    }
}
