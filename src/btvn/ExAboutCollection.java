package btvn;

import java.util.*;

public class ExAboutCollection {
    public static void main(String[] args) {
        List<String> lstString = new ArrayList<>();
        lstString.add("nguyen");
        lstString.add("duy");
        lstString.add("linh");

        for (int i = 0; i < lstString.size(); i++) {
            System.out.print(lstString.get(i) + " ");
        }

        System.out.println();

        Deque<String> queString = new LinkedList<>();
        queString.add("nguyen");
        queString.add("duy");
        queString.add("linh");
        for (String item : queString
        ) {
            System.out.println(item);
        }
        String str = "homquatoidihocmeduatoidenloplalala";
        char[] chars = str.toCharArray();
        Set<Character> charS = new HashSet<>();
        for (int i = 0; i < chars.length; i++) {
            charS.add(chars[i]);
        }
        System.out.println(charS.toString());

    }
}
