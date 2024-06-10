package com.task.Utils;

import java.security.SecureRandom;

public class CodeGenerator {
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateCode(String prefix) {
        int randomNumber = 100 + RANDOM.nextInt(4901);
        return prefix + randomNumber;
    }
}