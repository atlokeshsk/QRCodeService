import java.util.HashMap;
import java.util.Scanner;

class Main {
    private static void printMaxKey(HashMap<Integer, Integer> map) {
        // implement me
        var count = map.keySet().stream().reduce((integer, integer2) -> integer > integer2 ? integer : integer2);
        System.out.println(map.get(count.get()));
    }

    public static void main(String[] args) {
        HashMap<Integer, Integer> map = new HashMap<>();

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        for (int i = 0; i < n; ++i) {
            map.put(scanner.nextInt(), scanner.nextInt());
        }

        printMaxKey(map);
    }
}