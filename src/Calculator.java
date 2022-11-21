import java.util.Arrays;
import java.util.Scanner;

public class Calculator{

    static char operator;
    static int first, second;
    static boolean first_rom = false;
    static boolean second_rom = false;

    public static void main(String... args) throws Exception {
        Scanner scan = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String input = scan.nextLine();

        convert(input);
        Integer result = calculate();

        if (result!=null) {
            if (first_rom) {
                System.out.println(convertToRoman(result));
            }
            else {
                System.out.println(result);
            }
        }

    }

    public static Integer calculate () throws Exception {
        int result;

        if (first_rom == second_rom){
            if (first < 0 || first > 10){
                throw new Exception("Введите числа от 1 до 10");
            }

            if (second < 0 || second > 10) {
                throw new Exception("Введите числа от 1 до 10");
            }

            result = switch (operator) {
                case '+' -> first + second;
                case '-' -> first - second;
                case '*' -> first * second;
                case '/' -> first / second;

                default -> throw new Exception("Не является операцией");
            };

            return result;
        }
        else {
            throw new Exception("Допустим только один формат данных");
        }
    }

    public static void convert(String input){

        char[] chars = input.toCharArray();

        int charsLength = chars.length;

        String op = "+-*/";
        String des = "IiVvXxLlCcDdMm";

        for (int i=0;i<charsLength;i++) {

            if (op.indexOf(chars[i]) >= 0) {
                operator = chars[i];

                char[] chr_first = Arrays.copyOfRange(chars, 0, i);
                String str_first = String.valueOf(chr_first);
                str_first = str_first.trim();

                char[] chr_second = Arrays.copyOfRange(chars, i+1, charsLength);
                String str_second = String.valueOf(chr_second);
                str_second = str_second.trim();

                if (des.indexOf(str_first.charAt(0)) >= 0) {
                    first = convertToDecimal(str_first);
                    first_rom = true;
                }
                else {
                    try {
                        first = Integer.parseInt(str_first);
                    } catch (NumberFormatException e) {
                        first = -1;
                    }
                }

                if (des.indexOf(str_second.charAt(0)) >= 0) {
                    second = convertToDecimal(str_second);
                    second_rom = true;
                }
                else {
                    try {
                        second = Integer.parseInt(str_second);
                    } catch (NumberFormatException e) {
                        second = -1;
                    }
                }
            }
        }

    }

    public static int convertToDecimal (String romanNumeral) {
        int decimalNum = 0;

        romanNumeral = romanNumeral.toUpperCase();

        int l=  romanNumeral.length();
        int num=0;
        int previous = 0;
        int i;
        for (i = l-1; i>=0; i--)
        {
            char x =  romanNumeral.charAt(i);
            x = Character.toUpperCase(x);
            switch (x) {
                case 'I' -> {
                    previous = num;
                    num = 1;
                }
                case 'V' -> {
                    previous = num;
                    num = 5;
                }
                case 'X' -> {
                    previous = num;
                    num = 10;
                }
            }
            if (num<previous)
            {decimalNum= decimalNum-num;}
            else
                decimalNum= decimalNum+num;
        }

        return decimalNum;
    }

    public static String convertToRoman(int num) {

        int[] values = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String[] romanLiterals = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};

        StringBuilder roman = new StringBuilder();

        for(int i=0;i<values.length;i++) {
            while(num >= values[i]) {
                num -= values[i];
                roman.append(romanLiterals[i]);
            }
        }
        return roman.toString();
    }
}