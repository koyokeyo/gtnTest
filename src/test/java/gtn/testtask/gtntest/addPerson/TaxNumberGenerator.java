package gtn.testtask.gtntest.addPerson;

public class TaxNumberGenerator {
    public static String innfl() {
        String region = zeros(String.valueOf((int) (Math.random() * 92) + 1), 2);
        String inspection = zeros(String.valueOf((int) (Math.random() * 99) + 1), 2);
        String numba = zeros(String.valueOf((int) (Math.random() * 999999) + 1), 6);
        String rezult = region + inspection + numba;

        int kontr = (
                7 * Character.getNumericValue(rezult.charAt(0)) +
                        2 * Character.getNumericValue(rezult.charAt(1)) +
                        4 * Character.getNumericValue(rezult.charAt(2)) +
                        10 * Character.getNumericValue(rezult.charAt(3)) +
                        3 * Character.getNumericValue(rezult.charAt(4)) +
                        5 * Character.getNumericValue(rezult.charAt(5)) +
                        9 * Character.getNumericValue(rezult.charAt(6)) +
                        4 * Character.getNumericValue(rezult.charAt(7)) +
                        6 * Character.getNumericValue(rezult.charAt(8)) +
                        8 * Character.getNumericValue(rezult.charAt(9))
        ) % 11 % 10;

        kontr = kontr == 10 ? 0 : kontr;
        rezult += kontr;

        kontr = (
                3 * Character.getNumericValue(rezult.charAt(0)) +
                        7 * Character.getNumericValue(rezult.charAt(1)) +
                        2 * Character.getNumericValue(rezult.charAt(2)) +
                        4 * Character.getNumericValue(rezult.charAt(3)) +
                        10 * Character.getNumericValue(rezult.charAt(4)) +
                        3 * Character.getNumericValue(rezult.charAt(5)) +
                        5 * Character.getNumericValue(rezult.charAt(6)) +
                        9 * Character.getNumericValue(rezult.charAt(7)) +
                        4 * Character.getNumericValue(rezult.charAt(8)) +
                        6 * Character.getNumericValue(rezult.charAt(9)) +
                        8 * Character.getNumericValue(rezult.charAt(10))
        ) % 11 % 10;

        kontr = kontr == 10 ? 0 : kontr;
        rezult += kontr;

        return rezult;
    }

    private static String zeros(String str, int lng) {
        int factlength = str.length();
        if (factlength < lng) {
            for (int i = 0; i < lng - factlength; i++) {
                str = "0" + str;
            }
        }
        return str;
    }


}
