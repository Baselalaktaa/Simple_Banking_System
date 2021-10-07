import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        int rows = scanner.nextInt();
        int cols = scanner.nextInt();
        int[][] arr = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                arr[i][j] = scanner.nextInt();
            }
        }
        int resRow = 0;
        int resCol =0;
        int max = arr[0][0];
        for (int i = 0; i <arr.length ; i++) {
            for (int j = 0; j <arr[i].length ; j++) {
                if (arr[i][j] > max){
                    max = arr[i][j];
                    resRow = i;
                    resCol = j;
                }
            }
        }
        System.out.println(resRow+" "+resCol);

    }
}