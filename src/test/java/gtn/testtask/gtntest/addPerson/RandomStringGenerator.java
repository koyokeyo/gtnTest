package gtn.testtask.gtntest.addPerson;

import java.util.Random;

public class RandomStringGenerator {

    private static final String CYRILLIC_CHARACTERS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";

    public static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CYRILLIC_CHARACTERS.charAt(random.nextInt(CYRILLIC_CHARACTERS.length())));
        }
        return sb.toString();
    }
}