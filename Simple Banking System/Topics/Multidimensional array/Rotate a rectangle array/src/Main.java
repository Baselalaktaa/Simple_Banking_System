import java.util.Arrays;

class Main {

    static int[][] rotateArrayCW(int[][] orig , int rows, int cols) {

        final int[][] res = new int[cols][rows];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                res[c][rows - 1 - r] = orig[r][c];
            }
        }

        return res;
    }

    static void printArr (int[][] arr){
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length ; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] arr = {
                {10,11 , 12 ,13 ,14,15,16,17,18,19},
                {20,21 , 22, 23, 24,25,26,27,28,29},
                {30,31,32,33,34,35,36,37,38,39},
                {40,41,42,43,44,45,46,47,48,49},
                {50,51,52,53,54,55,56,57,58,59},
                {60,61,62,63,64,65,66,67,68,39},
                {70,71,72,73,74,75,76,77,78,79},
                {80,81,82,83,84,85,86,87,88,39},
                {90,91,92,93,94,95,96,97,98,99}
        };
        printArr(rotateArrayCW(arr, 9 , 10));
    }
}