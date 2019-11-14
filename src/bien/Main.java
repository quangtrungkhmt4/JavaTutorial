package bien;

public class Main {

    public static void main(String[] args) {
        // cac kieu du lieu nguyen thuy
        int number = 5;
        float numberOne = 1.2f;
        double numberTwo = 3.4;
        boolean check = true;
        char c = 't';


        // cac kieu dl doi tuong
        String str = "trung";
        String str1 = new String("linh");
        Integer numberObject = 2;
        Float numberTwoObject = 2.4f;
        Double numberThreeObject = 4.5;
        Boolean checkObject = true;
        Character cObject = 'C';

        // convert kieu dl
        //// chuyen tu String
        String a = "45";
        int aInt = Integer.parseInt(a);
        float aFloat = Float.parseFloat(a);

        System.out.println(aInt);
        System.out.println(aFloat);



    }

}
