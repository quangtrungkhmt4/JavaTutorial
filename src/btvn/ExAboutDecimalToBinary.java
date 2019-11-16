package btvn;

public class ExAboutDecimalToBinary {
    public static void main(String[] args) {
        int decimal = 11;
        String binary = "";
        while (decimal >= 2) {
            int surplus = decimal % 2;
            binary += surplus;
            decimal = decimal / 2;
        }
        System.out.println(binary + (decimal % 2));
    }
}
