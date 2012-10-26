import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;


public final class View {

    Shell shell;
    Display display;
    Table table;
    private Button extractAllButton;
    private Button createNewArchiveButton;
    private Button extractSelectedButton;
    private Menu fileMenu;
    private Menu openMenu;
    private Menu registerMenu;
    TableListener tableListener;
    private MenuItem fileMenuItem;
    private MenuItem helpMenuItem;
    ExtractAllButtonListener extractAllButtonListener;
    private MenuItem exitMenuItem;
    private MenuItem openMenuItem;
    private MenuItem registerMenuItem;
    private CreateNewArchiveButtonListener createNewArchiveButtonListener;
    ExtractSelectedButtonListener extractSelectedButtonListener;

    View(App app) {
        display = new Display();
        shell = new Shell(display);
        shell.setSize(600, 400);
        fileMenu = new Menu(shell, 2);
        fileMenuItem = new MenuItem(fileMenu, 64);
        fileMenuItem.setText("&File");
        openMenu = new Menu(shell, 4);
        fileMenuItem.setMenu(openMenu);
        openMenuItem = new MenuItem(openMenu, 8);
        openMenuItem.setText("&Open");
        exitMenuItem = new MenuItem(openMenu, 8);
        exitMenuItem.setText("E&xit");
        helpMenuItem = new MenuItem(fileMenu, 64);
        helpMenuItem.setText("&Help");
        registerMenu = new Menu(shell, 4);
        helpMenuItem.setMenu(registerMenu);
        registerMenuItem = new MenuItem(registerMenu, 8);
        registerMenuItem.setText("&Register");
        exitMenuItem.addSelectionListener(new ExitMenuItemSelectionListener(shell, display));
        openMenuItem.addSelectionListener(new OpenMenuItemSelectionListener(app));
        registerMenuItem.addSelectionListener(new RegisterMenuItemSelectionListener());
        shell.setMenuBar(fileMenu);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 5;
        shell.setLayout(gridLayout);
        extractAllButton = new Button(shell, 8);
        extractAllButton.setText("Extract all");
        extractAllButtonListener = new ExtractAllButtonListener(app, table);
        extractAllButton.addListener(13, extractAllButtonListener);
        extractSelectedButton = new Button(shell, 8);
        extractSelectedButton.setText("Extract selected");
        extractSelectedButtonListener = new ExtractSelectedButtonListener(app, table);
        extractSelectedButton.addListener(13, extractSelectedButtonListener);
        createNewArchiveButton = new Button(shell, 8);
        createNewArchiveButton.setText("Create new archive");
        createNewArchiveButtonListener = new CreateNewArchiveButtonListener(app);
        createNewArchiveButton.addListener(13, createNewArchiveButtonListener);
        table = new Table(shell, 0x10010802);
        table.setHeaderVisible(true);
        String[] s = new String[] {
            "File name", "File size"
        };
        for (int i = 0; i < s.length; i++)
            new TableColumn(table, 0).setText(s[i]);

        GridData gridData = new GridData();
        gridData.horizontalAlignment = 4;
        gridData.verticalAlignment = 4;
        gridData.grabExcessHorizontalSpace = true;
        gridData.grabExcessVerticalSpace = true;
        gridData.horizontalSpan = 5;
        table.setLayoutData(gridData);
        tableListener = new TableListener(table);
        table.addListener(36, tableListener);
        shell.pack();
        shell.open();
    }

    public final boolean showFileChooser() {
        Shell shell = new Shell(this.shell, 2144);
        Label label = new Label(shell, 0);
        label.setText("Choose an archive to extract");
        Button okButton = new Button(shell, 8);
        okButton.setText("&OK");
        Button cancelButton = new Button(shell, 8);
        cancelButton.setText("&Cancel");
        FormLayout formLayout = new FormLayout();
        formLayout.marginWidth = formLayout.marginHeight = 8;
        shell.setLayout(formLayout);
        FormData formData = new FormData();
        formData.top = new FormAttachment(label, 8);
        okButton.setLayoutData(formLayout);
        formData = new FormData();
        formData.left = new FormAttachment(okButton, 8);
        formData.top = new FormAttachment(okButton, 0, 128);
        cancelButton.setLayoutData(formData);
        shell.setDefaultButton(okButton);
        boolean[] result = new boolean[1];
        DialogListener listener = new DialogListener(okButton, result, shell);
        okButton.addListener(13, listener);
        cancelButton.addListener(13, listener);
        shell.pack();
        shell.open();
        while (true) {
            if (shell.isDisposed())
                break;
            if (!display.readAndDispatch())
                display.sleep();
        }
        return result[0];
    }
}
