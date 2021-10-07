import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String inputAsString = scanner.next();
        List<String> inputs = new ArrayList<>();
        while (!inputAsString.equals("0")){
            inputs.add(inputAsString);
            inputAsString = scanner.next();
        }
        try {
            for (String str: inputs){
                try {
                    int inputAsNumber = Integer.parseInt(str);
                    System.out.println(inputAsNumber * 10);
                }
                catch (NumberFormatException e){
                    System.out.println("Invalid user input: "+str);
                }

            }

        }
        catch (NumberFormatException e){
            System.out.println("Invalid user input: "+inputAsString);
        }
    }
}