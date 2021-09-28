import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner =  new Scanner(System.in);
        int sum = 0;
        int input;
        do {
            input = scanner.nextInt();
            sum += input;
        } while (input != 0);
        System.out.println(sum);
    }
}