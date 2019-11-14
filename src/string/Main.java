package string;

public class Main {
    public static void main(String[] args) {
        String str1 = "le";
        String str2 = "quangle";
        String str3 = "trung";
        String str4 = "Quang";


        //so sanh chuoi
        int result = str1.compareTo(str2);
        boolean checkOne = str2.equals(str4);
        boolean checkTwo = str2.equalsIgnoreCase(str4);

        //noi chuoi
        String newStr = str1 + str2;
        String newStr2 = str1.concat(str2);
        String newStr3 = String.format("%s     %s", str1, str2);

        //kiem tra chuoi a co chua trong chuoi b
        boolean checkContain = str2.contains(str1);

        //kiem tra xem chuoi co rong hay ko
        String str5 = "1111";
        boolean checkEmpty = str5.isEmpty();

        //thay doi chuoi cu bang 1 chuoi moi
        str2 = str2.replace("le", "trung");

        //chuyen tat ca thanh chu thuong
//        System.out.println(str1.toLowerCase());
//        System.out.println(str1.toUpperCase());

        //cat chuoi
        String[] strArr = str3.split("u");
//        System.out.println(String.join(", ", strArr));

        System.out.println(str2);

        System.out.println(checkContain);

        System.out.println(checkOne + " - " + checkTwo);


        String demo = "hanoioiHanoioidepquaHanoihanoi";
        String hanoi = "hanoi";

        String newDemo = demo.toLowerCase();
//        String[] arr = newDemo.split(hanoi);
//        System.out.println(arr.length - 1);


        char[] chars = newDemo.toCharArray();

        int count = 0;
        for (int i=0; i<=chars.length - 5; i++){
            String strNew = String.valueOf(chars[i]) + String.valueOf(chars[i+1])
                    + String.valueOf(chars[i+2]) + String.valueOf(chars[i+3]) + String.valueOf(chars[i+4]);
            if (strNew.equals(hanoi)){
                count++;
            }
        }

        System.out.println(count);


    }
}
