package algo.password;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Secure Random Password Generator
 * Requirements:
 * - At least 11 characters total
 * - At least 1 uppercase letter
 * - At least 1 non-word character (special symbol)
 * - Alphanumeric and special characters
 */
public class SecurePasswordGenerator {

    // Character sets
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*_+-=;:,.?";
    private static final String ALL_CHARACTERS = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARS;

    // Secure random generator
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    /**
     * Generates a secure random password with specified minimum length
     * Requirements: at least 1 uppercase + 1 special character + minimum length
     * @param minLength minimum length of password (must be at least 2)
     * @return secure random password
     * @throws IllegalArgumentException if minLength < 2
     */
    public static String generatePassword(int minLength) {
        if (minLength < 2) {
            throw new IllegalArgumentException("Minimum length must be at least 2 to satisfy requirements");
        }

        List<Character> passwordChars = new ArrayList<>();

        // Ensure at least one character from each required category
        passwordChars.add(getRandomCharacter(UPPERCASE));     // At least 1 uppercase
        passwordChars.add(getRandomCharacter(SPECIAL_CHARS)); // At least 1 special character

        // Fill the rest with random characters from all available sets
        for (int i = 2; i < minLength; i++) {
            passwordChars.add(getRandomCharacter(ALL_CHARACTERS));
        }

        // Shuffle the password to avoid predictable patterns
        Collections.shuffle(passwordChars, SECURE_RANDOM);

        // Convert to string
        StringBuilder password = new StringBuilder();
        for (Character c : passwordChars) {
            password.append(c);
        }

        return password.toString();
    }


    public static char getRandomCharacter(String characterSet) {
        int randomIndex = SECURE_RANDOM.nextInt(characterSet.length());
        return characterSet.charAt(randomIndex);
    }







}