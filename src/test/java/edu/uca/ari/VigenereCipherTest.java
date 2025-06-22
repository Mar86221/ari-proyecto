package edu.uca.ari;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VigenereCipherTest {

    @Test
    public void testEncryptDecrypt() {
        VigenereCipher cipher = new VigenereCipher("CLAVE");
        String original = "1234567890123456";
        
        String encrypted = cipher.encrypt(original);
        String decrypted = cipher.decrypt(encrypted);
        
        assertEquals(original, decrypted, "El texto descifrado debe ser igual al original");
        assertNotEquals(original, encrypted, "El texto cifrado debe ser diferente al original");
    }

    @Test
    public void testEncryptWithDifferentKeys() {
        String text = "1234567890123456";
        VigenereCipher cipher1 = new VigenereCipher("CLAVE1");
        VigenereCipher cipher2 = new VigenereCipher("CLAVE2");
        
        String encrypted1 = cipher1.encrypt(text);
        String encrypted2 = cipher2.encrypt(text);
        
        assertNotEquals(encrypted1, encrypted2, "Diferentes claves deben producir diferentes cifrados");
    }

    @Test
    public void testEmptyString() {
        VigenereCipher cipher = new VigenereCipher("CLAVE");
        String empty = "";
        
        assertEquals(empty, cipher.encrypt(empty), "Cadena vacía cifrada debe seguir siendo vacía");
        assertEquals(empty, cipher.decrypt(empty), "Cadena vacía descifrada debe seguir siendo vacía");
    }

    @Test
    public void testDifferentTextLengths() {
        VigenereCipher cipher = new VigenereCipher("CLAVE");
        
        String short1 = "123";
        String medium = "1234567890";
        String long1 = "12345678901234567890123456789012345";
        
        assertEquals(short1, cipher.decrypt(cipher.encrypt(short1)));
        assertEquals(medium, cipher.decrypt(cipher.encrypt(medium)));
        assertEquals(long1, cipher.decrypt(cipher.encrypt(long1)));
    }
} 