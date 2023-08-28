import java.util.Scanner;

public class TaskCalc {
    static String FirstString, SecondString;
    static char CharOperation;
    static int FirstValue, SecondValue;
    static boolean Roman;
    static enum RomanNumerals{
        I(1),II(2),III(3),IV(4),V(5),
        VI(6),VII(7),VIII(8),IX(9),X(10),
        XL(40),L(50),XC(90),C(100);
        private int value;
        RomanNumerals (int value){
            this.value = value;
        }
    }
    public static void main(String[] args) {
        String inputtext;
        while (true){
            Scanner scan = new Scanner(System.in);
            System.out.print("Введите необходимы значения (для выхода введите: \"exit\"): ");
            inputtext = scan.nextLine();
            if (inputtext.equals("exit")) break;
            String FinalString = calc(inputtext);
            System.out.println("Резултат: " + FinalString);
        }
    }
    public static String calc(String input){
        String[] arrayString = input.split(" ");
        String Result;
        //Разделение строки при отсутствии пробелов
        if (arrayString.length == 1) arrayString = splitString(input);
        //Проверка на ввод только 2 значений
        if (arrayString.length > 3){
            System.out.println("ВНИМАНИЕ! Разрешено использовать арефметические опперации только с 2 эллементами");
            System.exit(1);
        }
        //Разделение массива на переменные
        splitArray(arrayString);
        //Проверка являются ли цифры римскими
        checkRoman();
        //Проверка на целостность чисел
        if (!Roman)  numberIntegrity();
        Result = operationResult();

       return input;
    }
    public static String[] splitString(String intext) {
        String[] ArrayValue = new String[] {"", "", ""};
        boolean FindOperation = false;
        for (int i = 0; i < intext.length(); i++){
            switch (intext.charAt(i)) {
                case '+', '/', '*', '-' -> {
                    constructionElements(ArrayValue, intext, i);
                    FindOperation = true;
                }
            }
        }
        //Проверка на ввод разрешеных символов
        if (!FindOperation){
            System.out.println("ВНИМАНИЕ! Разрешено использование только операций: \"+\", \"-\", \"*\", \"/\"");
            System.exit(1);
        }
        return ArrayValue;
    }
    public static void constructionElements(String[] array, String intext, int index) {
        for (int j = 0; j < index; j++)array[0] += intext.charAt(j);
        for (int j = index+1; j < intext.length(); j++)array[2] += intext.charAt(j);
        if(array[1].equals(String.valueOf('+')) || array[1].equals(String.valueOf('-')) ||
                array[1].equals(String.valueOf('*')) || array[1].equals(String.valueOf('/'))){
            System.out.println("ВНИМАНИЕ! Разрешено использовать арефметические опперации только с 2 эллементами");
            System.exit(1);
        }
        array[1] = String.valueOf(intext.charAt(index));
    }

    public static void splitArray(String[] inarray) {
        FirstString    = inarray[0];
        SecondString   = inarray[2];
        CharOperation  = inarray[1].charAt(0);
    }
    public static void checkRoman() {
        boolean FirstCheck = false;
        boolean SecondCheck = false;
        for(RomanNumerals value: RomanNumerals.values()){
            if (FirstString.equals(value.name())) {
                FirstCheck = true;
                FirstValue = value.value;
            }
            if (SecondString.equals(value.name())) {
                SecondCheck = true;
                SecondValue = value.value;
            }
        }
        //Проверка на одинаковое цифроисчисление
        if ((!FirstCheck && SecondCheck) || (FirstCheck && !SecondCheck)){
            System.out.println("ВНИМАНИЕ! Разрешено использовать только одинаковое цифроисчисление!");
            System.exit(1);
        }else if (FirstCheck && SecondCheck) Roman = true;
    }

    public static void numberIntegrity() {
        try{
            float FirstCheck  = Float.parseFloat(FirstString);
            float SecondCheck = Float.parseFloat(SecondString);
            if (!(FirstCheck %2 == 0) || !(SecondCheck %2 == 0)){
                System.out.println("ВНИМАНИЕ! Запрещено использовать дробные числа!");
                System.exit(1);
            }
        }catch (NumberFormatException e){
            System.out.println("ВНИМАНИЕ! Запрещено использовать дробные числа!");
            System.exit(1);
        }
    }

    public static String operationResult() {
        String Result = null;
        int ResultCalc = 0;
        switch (CharOperation){
            case '+': ResultCalc = FirstValue + SecondValue;
            case '-': ResultCalc = FirstValue - SecondValue;
            case '*': ResultCalc = FirstValue * SecondValue;
            case '/': ResultCalc = FirstValue / SecondValue;
        }
//        if (Roman) Result = constructorRoman(ResultCalc);
        return Result;
    }

//    public static String constructorRoman(int result) {
//
//    }
//    public static String constructorElement(int invalue, int repeat) {
//
//    }
}