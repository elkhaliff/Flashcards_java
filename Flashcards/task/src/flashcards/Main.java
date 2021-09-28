package flashcards;

public class Main {
    public static void main(String[] args) {
        TestSystem ts = new TestSystem();
        if (args.length > 1) {
            for (int index = 0; index < args.length; index++)
                if (index == 0 || index == 2) {
                    switch (args[index]) {
                        case "-import": ts.importFile(args[index+1]); break;
                        case "-export": ts.setExportFile(args[index+1]); break;
                    }
                }
            }
        ts.getMenu();
    }
}