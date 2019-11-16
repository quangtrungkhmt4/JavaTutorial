package btvn;

public class ExAboutPrime {
    public static void main(String[] args) {
        if (isPrime(11))
            System.out.println("la so nguyen to");
        else
            System.out.println("khong phai so nguyen to");
    }

    private static boolean isPrime(int number) {
        if (number <= 1)
            return false;
        else if (number == 2)
            return true;
        else {
            for (int i = 2; i < Math.sqrt(number); i++) {
                if (number % i == 0)
                    return false;
                else
                    return true;
            }
        }
        return true;
    }
}
