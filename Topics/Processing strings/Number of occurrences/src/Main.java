import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String strBase = scanner.nextLine();
        String srtSub = scanner.nextLine();
        int cnt = 0;
        do {
            int pos = strBase.indexOf(srtSub);
            if (pos >= 0) {
                cnt++;
            }
            strBase = strBase.substring(pos + srtSub.length());
        } while (strBase.length() >= srtSub.length());
        System.out.println(cnt);
    }
}