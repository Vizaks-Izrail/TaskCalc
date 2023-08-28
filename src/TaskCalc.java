import java.util.Scanner;

public class TaskCalc {
    static String FirstString, SecondString;
    static char CharOperation;
    static int FirstValue, SecondValue;
    static boolean Roman;
    enum RomanNumerals{
        I(1),II(2),III(3),IV(4),V(5),
        VI(6),VII(7),VIII(8),IX(9),X(10),
        XL(40),L(50),XC(90),C(100);
        private final int value;
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
        if (!Roman){
            //Проверка на целостность чисел
            numberIntegrity();
            FirstValue = Integer.parseInt(FirstString);
            SecondValue = Integer.parseInt(SecondString);
            //Проверка на допуск введеных значений
            checkValue();
        }
        Result = operationResult();

       return Result;
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
            if (!(FirstCheck %1 == 0) || !(SecondCheck %1 == 0)){
                System.out.println("ВНИМАНИЕ! Запрещено использовать дробные числа!");
                System.exit(1);
            }
        }catch (NumberFormatException e){
            System.out.println("ВНИМАНИЕ! Запрещено использовать дробные числа!");
            System.exit(1);
        }
    }

    public static void checkValue() {
        if (FirstValue < 0 || SecondValue < 0 || FirstValue > 10 || SecondValue > 10){
            System.out.println("ВНИМАНИЕ! Разрешаеться использовать цифры от 1 до 10");
            System.exit(1);
        }
    }

    public static String operationResult() {
        String Result;
        int ResultCalc = 0;
        switch (CharOperation) {
            case '+' -> ResultCalc = FirstValue + SecondValue;
            case '-' -> {
                ResultCalc = FirstValue - SecondValue;
                if (Roman && ResultCalc <= 0) {
                    System.out.println("ВНИМАНИЕ! В римском цифроисчислении нет отрицательных значений и нуля!");
                    System.exit(1);
                }
            }
            case '*' -> ResultCalc = FirstValue * SecondValue;
            case '/' -> {
                try {
                    ResultCalc = FirstValue / SecondValue;
                } catch (ArithmeticException e) {
                    System.out.println("ВНИМАНИЕ! На 0 делить нельзя!");
                    System.exit(1);
                }
            }
        }
        if (Roman && ResultCalc != 0) Result = constructorRoman(ResultCalc);
        else Result = String.valueOf(ResultCalc);
        return Result;
    }

    public static String constructorRoman(int result) {
        String StringResult = "";
        int value;
        if (result < 11) StringResult = constructorElement(result, 1);
        else if (result < 100) {
            String StringElements = String.valueOf(result);
            for (int i = 0; i < StringElements.length(); i++){
                value = Character.getNumericValue(StringElements.charAt(i));
                if (i == 0) {
                    if (result < 40) StringResult += constructorElement(10, value);
                    else if (result > 40 && result < 50) StringResult += constructorElement(40, 1);
                    else if (result > 50 && result < 90) {
                        StringResult += constructorElement(50, 1);
                        StringResult += constructorElement(10, value - 5);
                    } else StringResult += constructorElement(value * 10, 1);
                }else StringResult += constructorElement(value, 1);
            }
        }else StringResult += constructorElement(result, 1);
        return StringResult;
    }
    public static String constructorElement(int invalue, int repeat) {
        String element = "";
        for (int i = 0; i < repeat; i++){
            for (RomanNumerals value : RomanNumerals.values()) {
                if (invalue == value.value) {
                    element += value.name();
                    break;
                }
            }
        }
        return element;
    }
}