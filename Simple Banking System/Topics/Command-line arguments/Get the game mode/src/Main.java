import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Problem {
    public static void main(String[] args) throws FileNotFoundException {
        try (Stream<String> stream = Files.lines(Paths.get("C://Users//BAlaktaa//OneDrive - Cofinpro AG//Desktop//JavaEE//dataset_91007.txt"))) {



            List<String> list = stream.collect(Collectors.toList());
            String word = list.get(0);
            int counter = 0;
            String[] arr = word.split(" ");

            System.out.println(Collections.max(Arrays.asList(arr)));

            System.out.println(counter);




            /**
            List<String> list = stream.collect(Collectors.toList());
            String word = list.get(0);
            int counter = 0;
            String[] arr = word.split(" ");


            for (String s : arr) {
                if (Integer.parseInt(s) >= 9999){
                    counter++;
                }
            }

            System.out.println(counter);

                List<String> list =  stream.filter(line -> Integer.parseInt(line) >= 9999).collect(Collectors.toList());
                          for (String str: list){
                              System.out.println(str);
                          }
             */
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}