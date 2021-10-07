import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        int lengthAsNum = scanner.nextInt();
        if (lengthAsNum <= 10){
            int[][] arr = new int[lengthAsNum][lengthAsNum];

            for (int i = 0; i < lengthAsNum ; i++) {
                for (int j = 0; j < lengthAsNum; j++) {
                    int tempAsNr = scanner.nextInt();
                    arr[i][j] = tempAsNr;
                }
            }
            boolean isMatch = true;
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr.length -1; j++) {
                    if (arr[i][j] != arr[j][i]) {
                        isMatch = false;

                    }
                }
            }
            if (isMatch ){
                System.out.println("YES");
            }
            else {
                System.out.println("NO");
            }
        }
    }
}