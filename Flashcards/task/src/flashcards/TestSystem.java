package flashcards;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class TestSystem {
    private String exportFile;

    final private String separator = ":";
    final private Map<String, String> memoryCards = new LinkedHashMap<>();
    final private Map<String, Integer> errorCards = new HashMap<>();
    final private List<String> logList = new ArrayList<>();
    Scanner scanner;

    public TestSystem() {
        scanner = new Scanner(System.in);
        logList.add("\n");
        exportFile = "";
    }

    public void setExportFile(String exportFile) {
        this.exportFile = exportFile;
    }

    private String logAny(String str) {
        logList.add(str);
        return str;
    }

    private void loadCard() {
        String question =  getString("The card:");

        if (memoryCards.containsKey(question)) {
            println("The card \"" + question + "\" already exists.:");
            return;
        }

        var answer = getString("The definition of the card:");
        if (memoryCards.containsValue(answer)) {
            println("The definition \"" + answer + "\" already exists.");
            return;
        }
        memoryCards.put(question, answer);
        errorCards.put(question, 0);
        println("The pair (\"" + question + "\":\"" + answer + "\") has been added.");
    }

    private void removeCard() {
        String question = getString("Which card?");
        if (!memoryCards.containsKey(question)) {
            println("Can't remove \"" + question + "\": there is no such card.");
        } else {
            memoryCards.remove(question);
            errorCards.remove(question);
            println("The card has been removed.");
        }
    }

    private String getQuestionByAnswer(String answer) {
        if (memoryCards.isEmpty()) return null;
        for (var entry : memoryCards.entrySet()) {
            if (answer.equals(entry.getValue())) return entry.getKey();
        }
        return null;
    }

    private void testOne(String question, String answer) {
        String userAnswer = getString("Print the definition of \"" + question + "\":");
        String wrong = "Wrong. The right answer is \"" + answer + "\"";
        if (answer.equals(userAnswer))
            println("Correct!");
        else {
            Integer errCnt = errorCards.get(question);
            errorCards.remove(question);
            errorCards.put(question, errCnt + 1);
            String alreadyQuestion = getQuestionByAnswer(userAnswer);
            println((alreadyQuestion != null ) ? wrong + ", but your definition is correct for \"" + alreadyQuestion + "\"." : wrong + ".");
        }
    }

    private void exportFile() {
        String fileName = exportFile != "" ? exportFile : getString("File name:");
        try (FileWriter writer = new FileWriter(fileName)) {
            for (var entry : memoryCards.entrySet()) { // var - Map.Entry<String, String>
                writer.write(entry.getKey() + separator + entry.getValue() + separator + errorCards.get(entry.getKey()) +"\n");
            }
            println(memoryCards.size() + " cards have been saved.");
        } catch (Exception e) {
            println("File not found.");
        }
    }

    public void importFile(String importFile) {
        String fileName = importFile != "" ? importFile : getString("File name:");
        File file = new File(fileName);
        int cnt = 0;

        try (Scanner scanFile = new Scanner(file)) {
            while (scanFile.hasNext()) {
                String[] row = scanFile.nextLine().split(separator);
                String question = row[0];
                String answer = row[1];
                Integer errCnt = Integer.parseInt(row[2]);
                memoryCards.remove(question);
                memoryCards.put(question, answer);
                errorCards.remove(question);
                errorCards.put(question, errCnt);
                cnt++;
            }
            println(cnt + " cards have been loaded.");
        } catch (Exception e) {
            println("File not found.");
        }
    }

    private void testing(int cnt) {
        Set<String> keySet = memoryCards.keySet();
        int realSize = keySet.size();
        realSize = Math.min(cnt, realSize);
        for (String key : keySet) {
            if (realSize < 0) return;
            testOne(key, memoryCards.get(key));
            realSize--;
        }
    }

    private void ask() {
        testing(Integer.parseInt(getString("How many times to ask?")));
    }

    private void log() {
        String fileName = getString("File name:");
        try (FileWriter writer = new FileWriter(fileName)) {
            for (String logStr : logList) {
                writer.write(logStr +"\n");
            }
            println("The log has been saved.");
        } catch (Exception e) {
            println("File not found.");
        }
    }

    private void hardestCard() {
        int error = 0;
        List<String> errorLog = new ArrayList<>();

        for (var entry : errorCards.entrySet()) {
            int currError = entry.getValue();
            if (currError > error ) {
                errorLog.clear();
                errorLog.add(entry.getKey());
                error = currError;
            } else if (currError != 0 && currError == error) {
                errorLog.add(entry.getKey());
            }
        }

        if (errorLog.size() == 0)
            println("There are no cards with errors.");
        else if (errorLog.size() > 1) {
            StringBuilder out = new StringBuilder();
            for (String err : errorLog) {
                out.append("\""); out.append(err); out.append("\" ");
            }
            println("The hardest cards are " + out + ". You have " + error + " errors answering them.");
        } else
            println("The hardest card is \"" + errorLog.get(0) + "\". You have " + error +" errors answering it.");
    }

    private void resetStats() {
        Set<String> keySet = memoryCards.keySet();
        errorCards.clear();
        for (String key : keySet) {
            errorCards.put(key, 0);
        }
        println("Card statistics have been reset.");
    }

    public void getMenu() {
        boolean doIt = true;
        while (doIt) {
            switch (getString("Input the action (add, remove, import, export, ask, exit):")) {
                case "add": loadCard(); break;
                case "remove": removeCard(); break;
                case "import": importFile(""); break;
                case "export": exportFile(); break;
                case "ask": ask(); break;
                case "log": log(); break;
                case "hardest card": hardestCard(); break;
                case "reset stats": resetStats(); break;
                case "exit": {
                    if (exportFile != "") exportFile();
                    doIt = false;
                    break;
                }
            }
        }
        println("Bye bye!");
    }

    private String getString(String input) {
        println(input);
        return logAny(scanner.nextLine());
    }

    private void println(String string) {
        System.out.println(logAny(string));
    }
}