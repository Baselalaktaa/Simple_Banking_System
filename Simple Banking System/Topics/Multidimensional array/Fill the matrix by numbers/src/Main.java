import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        int length = scanner.nextInt();
        int[][] res = new int[length][length];
        if (length <= 100){
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < length; j++) {
                    res[i][j] = Math.abs(j - i);
                }
            }
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < length; j++) {
                    System.out.print(res[i][j] + " ");
                }
                System.out.println();
            }
        }
    }
}