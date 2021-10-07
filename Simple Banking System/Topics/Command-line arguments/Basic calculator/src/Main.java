

class Problem {

    public static void main(String[] args) {





            int leftSide = Integer.parseInt(args[1]);
            int rightSide = Integer.parseInt(args[2]);
            if (args[0].equals("*")){
                leftSide = Integer.parseInt(args[1]);
                rightSide = Integer.parseInt(args[2]);
                System.out.println(leftSide * rightSide);
            }
            else if (args[0].equals("+")){
                System.out.println(leftSide + rightSide);
            }
            else if (args[0].equals("-")){
                System.out.println(rightSide - leftSide);
            }
            else {
                System.out.println("Unknown operator");
            }
    }
}