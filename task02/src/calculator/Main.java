package calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Float number1;
    static Float number2;
    static Float $last;
    static Float answer = 0.0f;
    static String operator = "";
    static String userInput = "";
    static List<String> userStringList = new ArrayList<>();
    static boolean cont = true;
    public static void main(String[] args) {
        

        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome.");


        while (cont) {
            System.out.print("> ");
            userInput = sc.nextLine();
            try {

                if (userInput.trim().toLowerCase().equalsIgnoreCase("exit")) {
                    cont = false;
                } else {
                    userStringList = Arrays.asList(userInput.split(" "));
                    if (userStringList.get(0).equals("$last")) {
                        number1 = answer;
                    } else {
                        number1 = Float.parseFloat(userStringList.get(0));
                    }
                    if (userStringList.get(2).equals("$last")) {
                        number2 = answer;
                    } else {
                        number2 = Float.parseFloat(userStringList.get(2));
                    }
                    
                    operator = userStringList.get(1);
                    
                    switch(operator) {
                        case "+":
                        answer = number1 + number2;
                        break;
                        
                        case "-":
                        answer = number1 - number2;
                        break;
                        
                        case "/":
                        answer = number1 / number2;
                        
                        break;
                        
                        case "*":
                        answer = number1 * number2;
                        break;
                    }
                    System.out.println(Float.toString(answer).replaceAll("\\.0+$", ""));
                }
            } catch (Exception e) {
                    System.out.println("error, write in this format <number> <space> <operator> <space> <number>");
            }  
        }
        sc.close();
        System.out.println("Bye bye");
    }
}