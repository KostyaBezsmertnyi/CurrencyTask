package consts;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

public class StringUtils {

    public static final String EMPTY = "";

    public static String generateRandomText(int count) {
        return new Faker().lorem().paragraph(count);
    }

    public static String generateRandomString(int count) {
        return RandomStringUtils.randomAlphabetic(count);
    }

}
