package org.example.courier;

import org.apache.commons.lang3.RandomStringUtils;


public class CourierGenerator {
    public static Courier random() {
        return new Courier(
                "RandomName_" + randomString(),
                "Qwerty_" + randomString(),
                "Adam_" + randomString());
    }

    private static String randomString() {
        return RandomStringUtils.randomAlphabetic(8, 10);
    }
}