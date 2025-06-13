package edu.uca.ari;

public class VigenereCipher {
    private String key;

    public VigenereCipher(String key) {
        this.key = key;
    }

    public String encrypt(String text) {
        StringBuilder result = new StringBuilder();
        int keyIndex = 0;
        for (char c : text.toCharArray()) {
            char k = key.charAt(keyIndex);
            char encrypted = (char) ((c + k) % 256);
            result.append(encrypted);
            keyIndex = (keyIndex + 1) % key.length();
        }
        return result.toString();
    }

    public String decrypt(String text) {
        StringBuilder result = new StringBuilder();
        int keyIndex = 0;
        for (char c : text.toCharArray()) {
            char k = key.charAt(keyIndex);
            char decrypted = (char) ((c - k + 256) % 256);
            result.append(decrypted);
            keyIndex = (keyIndex + 1) % key.length();
        }
        return result.toString();
    }
} 