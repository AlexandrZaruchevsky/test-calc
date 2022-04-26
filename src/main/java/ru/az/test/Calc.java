package ru.az.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calc {

    public final static String PATTERN_ARABIC = "^(\\s*[0-9]+\\s*[\\+\\-\\*\\/]\\s*[0-9]+\\s*)$";
    public final static String PATTERN_ROME = "^(\\s*([NIVX]+|\\s*)+\\s*[\\+\\-\\*\\/]\\s*([NIVX]+|\\s*)+\\s*)$";
    public final static Map<String, Integer> ROME_TO_ARABIC = new HashMap<>() {{
        put("N", 0);
        put("I", 1);
        put("II", 2);
        put("III", 3);
        put("IV", 4);
        put("V", 5);
        put("VI", 6);
        put("VII", 7);
        put("VIII", 8);
        put("IX", 9);
        put("X", 10);
    }};
    public final static Map<Integer, String> ARABIC_TO_ROME = new HashMap<>() {{
        put(0, "N");
        put(1, "I");
        put(2, "II");
        put(3, "III");
        put(4, "IV");
        put(5, "V");
        put(6, "VI");
        put(7, "VII");
        put(8, "VIII");
        put(9, "IX");
        put(10, "X");
        put(11, "XI");
        put(12, "XII");
        put(13, "XIII");
        put(14, "XIV");
        put(15, "XV");
        put(16, "XVI");
        put(17, "XVII");
        put(18, "XVIII");
        put(19, "XIX");
        put(20, "XX");
    }};

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Input example: ");
            String input = scanner.nextLine();
            if ("exit".equalsIgnoreCase(input.trim())) return;
            System.out.println(calc(input));
        }
    }

    public static String calc(String input) throws Exception {
        if (getNumberType(input) == 0)
            return calcArabic(input);
        if (getNumberType(input) == 1)
            return calcRome(input);
        return null;
    }

    private static String calcRome(String input) throws Exception {
        String[] split = input.split("\\s*[\\+\\-\\*\\/]{1}\\s*");
        Matcher matcher = getMatcher(input);
        String action = "";
        while (matcher.find()) {
            action = input.substring(matcher.start(), matcher.end()).trim();
        }
        Integer a = ROME_TO_ARABIC.get(spaceOf(split[0]));
        Integer b = ROME_TO_ARABIC.get(spaceOf(split[1]));
        if (a == null || b == null) {
            throw new Exception("Illegal parameters");
        }
        int result = calculate(action, a, b);
        if (result > 20 || result < 0) throw new Exception("index of bounds");
        return spaceOf(split[0]) + " " + action + " " + spaceOf(split[1]) + " = " + ARABIC_TO_ROME.get(result);
    }

    private static String calcArabic(String input) throws Exception {
        String[] split = input.split("\\s*[\\+\\-\\*\\/]{1}\\s*");
        Matcher matcher = getMatcher(input);
        String action = "";
        while (matcher.find()) {
            action = input.substring(matcher.start(), matcher.end()).trim();
        }
        Integer a = Integer.parseInt(spaceOf(split[0]));
        Integer b = Integer.parseInt(spaceOf(split[1]));
        int result = calculate(action, a, b);
        return spaceOf(split[0]) + " " + action + " " + spaceOf(split[1]) + " = " + result;
    }

    private static Matcher getMatcher(String input) {
        Pattern pattern = Pattern.compile("\\s*[\\+\\-\\*\\/]\\s*");
        return pattern.matcher(input);
    }

    private static int calculate(String action, Integer a, Integer b) throws Exception {
        int result;
        switch (action) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                if (b == 0) throw new Exception("Dived by zero");
                result = a / b;
                break;
            default:
                throw new Exception("Illegal action");
        }
        return result;
    }

    private static short getNumberType(String input) throws Exception {
        Pattern patternArabic = Pattern.compile(PATTERN_ARABIC);
        Pattern patternRome = Pattern.compile(PATTERN_ROME, Pattern.CASE_INSENSITIVE);
        if (patternArabic.matcher(input).find())
            return 0;
        if (patternRome.matcher(input).find())
            return 1;
        throw new Exception("Non valid example");
    }

    private static String spaceOf(String s) {
        return s.replaceAll("\\s+", "").toUpperCase();
    }

}