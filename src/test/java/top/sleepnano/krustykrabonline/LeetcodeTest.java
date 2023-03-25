package top.sleepnano.krustykrabonline;

public class LeetcodeTest {
    public static void main(String[] args) {
//        String a = "ulacfddd", b = "ddjizalu";
//        String a = "ulacfd", b = "jizalu";
//        String a = "abdef", b = "fecab";
//        String a = "pvhmupgqeltozftlmfjjde", b = "yjgpzbezspnnpszebzmcvp";
        String a = "pvhmupgqeltozftlmfjjde" ,b ="yjgpzbezspnnpszebzmhvp";
//        String a = "abxyzjw", b = "wjxyzba";
//
        if (a.length() == 1 || b.length() == 1) {
            return;
        }
//            return true;
        if (a.charAt(0) == b.charAt(b.length() - 1)) {
            int tar = 0;
            for (int i = 0; i < a.length(); i++) {
//            System.out.println("a.charAt(i) = " + a.charAt(i));
                for (int i1 = b.length() - i - 1; i1 > 0; i1--) {
//                System.out.println("b.charAt(i1) = " + b.charAt(i1));
                    if (b.charAt(i1) == a.charAt(i)) {
                        tar += 1;
                        break;
                    }else{
                        if (a.charAt(i-1) != b.charAt(i)){
                            System.out.println("false = " + false);
                        }else {
                            System.out.println("true = " + true);

                        }
//                        int index = 0;
//                        index = a.length()-i*2;
//                        if (b.length()-i1+(i-i1) + 1==i){
//                            System.out.println("false");
//                        }else {
//                            System.out.println("true");
//                        }

                    }
                }
            }
            if (tar > 1) {
                System.out.println("true = " + true);
            }
//            return false;
        } else {
            int tar = 0;
            for (int i = 0; i < b.length(); i++) {
//            System.out.println("a.charAt(i) = " + a.charAt(i));
                for (int i1 = a.length() - i - 1; i1 > 0; i1--) {
//                System.out.println("b.charAt(i1) = " + b.charAt(i1));
                    if (a.charAt(i1) == b.charAt(i)) {
                        tar += 1;
                        break;
                    }else{
                        if (a.length()-i1>i+1){
                            System.out.println("false");

                        }
                        System.out.println("i = " + i);
                        System.out.println("a.charAt(i) = " + a.charAt(i));
                        System.out.println("i1 = " + i1);
                        System.out.println("b.charAt(i1) = " + b.charAt(i1));
                    }
                }
            }
            if (tar > 1) {
//                return true;
            }
//            return false;
        }
    }
}
