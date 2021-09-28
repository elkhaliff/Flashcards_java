import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

class ConcatPositiveNumbersProblem {
    public static ArrayList<Integer> concatArray(ArrayList<Integer> l1) {
        ArrayList<Integer> outList = new ArrayList<>();
        for (Integer l: l1) {
            if (l > 0) {
                outList.add(l);
            }
        }
        return outList;
    }

    public static ArrayList<Integer> concatPositiveNumbers(ArrayList<Integer> l1, ArrayList<Integer> l2) {
        ArrayList<Integer> outList = concatArray(l1);
        outList.addAll(concatArray(l2));
        return outList; // write your code here
    }

    /* Do not modify this method */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Integer> list1 = readArrayList(scanner);
        ArrayList<Integer> list2 = readArrayList(scanner);

        ArrayList<Integer> result = concatPositiveNumbers(list1, list2);

        result.forEach(n -> System.out.print(n + " "));
    }

    /* Do not modify this method */
    private static ArrayList<Integer> readArrayList(Scanner scanner) {
        return Arrays
                .stream(scanner.nextLine().split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}