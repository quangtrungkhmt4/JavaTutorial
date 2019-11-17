package btvn;

import java.util.ArrayList;
import java.util.List;

public class ExAboutString{
    public static void main(String[] args) {
        String string = "hanoioi.hanoioi.hanoidepqua.hanoioi";

        char[] chars = string.toCharArray();

        List<Character> character = new ArrayList<>();
        List<Integer> count = new ArrayList<>();

        for (int i=0; i< chars.length; i++){
            if (!character.contains(chars[i])){
                character.add(chars[i]);
                count.add(1);
            }else {
                int index = character.indexOf(chars[i]);
                count.set(index, count.get(index) + 1);
            }
        }

        for (int i=0; i<character.size(); i++){
            System.out.print(character.get(i));
            System.out.print(" -> ");
            System.out.println(count.get(i) + " lan");
        }




        // cái này như dictionary trong c#
//        Map<Character, Integer> chars = new HashMap<>();
//
//        char[] cs = string.toCharArray();
//
//        for (int i=0; i< cs.length; i++){
//            if (chars.containsKey(cs[i])){
//                int count = chars.get(cs[i]);
//                chars.put(cs[i], count + 1);
//            }else {
//                chars.put(cs[i], 1);
//            }
//        }
//
//        System.out.println(chars);

//        string = string.toLowerCase();
//        char[] charAr = string.toCharArray();
//        Arrays.sort(charAr);
//        for (int i = 1; i < charAr.length; i++) {
//            int count = recursiveMethod(charAr, i, 1);
//            if (count > 1) {
//                System.out.println("'" + charAr[i] + "' comes " + count + " times");
//                i = i + count;
//            }
//        }
    }

    public static int recursiveMethod(char[] charAr, int i, int count) {
        if (charAr[i-1] == charAr[i]) {
            count = count + recursiveMethod(charAr, ++i, count);
        }
        return count;
    }

}
