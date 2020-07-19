package com.company;
import java.util.Scanner;
import java.util.Arrays;
import java.util.TreeMap;


public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите строку с параметрами: ");
        String inputString = in.nextLine();

        String[] possibleOperators = new String[]{"+", "-", "/", "*"};
        String[] possibleDecimals = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        String[] possibleRomeNumbers = new String[]{"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        try {

            String delimeter = " "; // Разделитель

            String[] arrOfParams = inputString.split(delimeter);

            if (arrOfParams.length != 3) throw new Exception("Не верное количество аргументов");

            String leftParam = arrOfParams[0];
            String thisOperator = arrOfParams[1];
            String rightParam = arrOfParams[2];


            if (!Arrays.asList(possibleOperators).contains(thisOperator)) {
                throw new Exception("Не допустимый оператор");
            }

            if(Arrays.asList(possibleDecimals).contains(leftParam) && Arrays.asList(possibleDecimals).contains(rightParam)){
                Calculator calc = new Calculator();
                int llP = Integer.parseInt(leftParam);
                int rrP = Integer.parseInt(rightParam);

                int newResult =  calc.calculate(llP,thisOperator, rrP )   ;
                System.out.printf("Ваш результат в : %s \n", newResult);

            }else if (Arrays.asList(possibleRomeNumbers).contains(leftParam.toUpperCase()) && Arrays.asList(possibleRomeNumbers).contains(rightParam.toUpperCase())) {
                ConvertDigitsRoman convAR1 = new ConvertDigitsRoman();
                ConvertDigitsRoman convAR2 = new ConvertDigitsRoman();


                int leftDigit =  convAR1.to_arab(leftParam);
                int rightDigit =  convAR2.to_arab(rightParam);



                Calculator calcAr = new Calculator();
                int newResultArab =  calcAr.calculate(leftDigit, thisOperator, rightDigit )   ;

                if(newResultArab<1){
                    System.out.printf("Римские цифры не могут быть меньше единицы \n");
                } else {
                    ConvertDigitsRoman convRome = new ConvertDigitsRoman();
                    String transormToRome = convRome.to_roman(newResultArab);
                    System.out.printf("Результат римскими: %s \n", transormToRome);

                }

            } else {
                throw new Exception("Не допустиме операнды");
            }


        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }




        in.close();
    }




    ////////////////////////////////////////////////////////////////////

    public static class Calculator {


        static int calculate(int lP, String operator, int rP) throws Exception {
            int calcResult;

            switch (operator) {
                case "+":calcResult = lP + rP;break;

                case "*":calcResult = lP * rP;break;

                case "/":
                    if (rP == 0) {
                        throw new Exception("Деление на ноль не допустимо");
                    } else {
                        calcResult = lP / rP;break;
                    }
                case "-":calcResult = lP - rP;break;

                default:
                    throw new Exception("Не допустимый оператор");

            }
            return calcResult;
        }
    }
///////////////////////////////////////////////////////////////////////

    public static class ConvertDigitsRoman {

        private final static TreeMap<Integer, String> map = new TreeMap<Integer, String>();

        static {

            map.put(1000, "M");
            map.put(900, "CM");
            map.put(500, "D");
            map.put(400, "CD");
            map.put(100, "C");
            map.put(90, "XC");
            map.put(50, "L");
            map.put(40, "XL");
            map.put(10, "X");
            map.put(9, "IX");
            map.put(5, "V");
            map.put(4, "IV");
            map.put(1, "I");

        }

        public final static String to_roman(int number) {
            int l =  map.floorKey(number);
            if ( number == l ) {
                return map.get(number);
            }
            return map.get(l) + to_roman(number-l);
        }


        //преобразуем десятичные в римские

        private static int decodeSingle(char letter) {
            switch(letter) {
                case 'M': return 1000;
                case 'D': return 500;
                case 'C': return 100;
                case 'L': return 50;
                case 'X': return 10;
                case 'V': return 5;
                case 'I': return 1;
                default: return 0;
            }
        }

        public static int to_arab(String roman) {
            int result = 0;
            String uRoman = roman.toUpperCase(); //case-insensitive
            for(int i = 0;i < uRoman.length() - 1;i++) {//loop over all but the last character
                //if this character has a lower value than the next character
                if (decodeSingle(uRoman.charAt(i)) < decodeSingle(uRoman.charAt(i+1))) {
                    //subtract it
                    result -= decodeSingle(uRoman.charAt(i));
                } else {
                    //add it
                    result += decodeSingle(uRoman.charAt(i));
                }
            }
            //decode the last character, which is always added
            result += decodeSingle(uRoman.charAt(uRoman.length()-1));
            return result;
        }

    }

}




