
public class RunArchiveTool {

    public static void main(String[] args) {
        try {
            App app;
            if (args.length == 1)
                app = new App(args[0]);
            else
                app = new App();
            app.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
