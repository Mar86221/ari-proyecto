package edu.uca.ari;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FormatConverterTest {

    @Test
    public void testTextToXmlConversion() {
        String testData = "12345678,Juan,Pérez,1234567890123456,VIP,555-1234,13.6923,-89.2184\n" +
                         "87654321,María,García,6543210987654321,Regular,555-5678,13.6945,-89.2199";
        
        VigenereCipher cipher = new VigenereCipher("TESTKEY");
        FormatConverter converter = FormatConverter.fromText(testData, ",", cipher);
        
        assertEquals(2, converter.getClientes().size(), "Debe crear 2 clientes");
        
        String xml = converter.toXml();
        assertNotNull(xml, "XML no debe ser nulo");
        assertTrue(xml.contains("<clientes>"), "XML debe contener elemento raíz clientes");
        assertTrue(xml.contains("<cliente>"), "XML debe contener elementos cliente");
    }

    @Test
    public void testTextToJsonConversion() {
        String testData = "12345678,Juan,Pérez,1234567890123456,VIP,555-1234,13.6923,-89.2184";
        
        VigenereCipher cipher = new VigenereCipher("TESTKEY");
        FormatConverter converter = FormatConverter.fromText(testData, ",", cipher);
        
        assertEquals(1, converter.getClientes().size(), "Debe crear 1 cliente");
        
        String json = converter.toJson();
        assertNotNull(json, "JSON no debe ser nulo");
        assertTrue(json.contains("\"clientes\""), "JSON debe contener array clientes");
        assertTrue(json.contains("\"documento\""), "JSON debe contener campo documento");
    }

    @Test
    public void testXmlRoundTrip() {
        String testData = "12345678,Juan,Pérez,1234567890123456,VIP,555-1234,13.6923,-89.2184";
        
        VigenereCipher cipher = new VigenereCipher("TESTKEY");
        
        // TXT -> XML
        FormatConverter converter1 = FormatConverter.fromText(testData, ",", cipher);
        String xml = converter1.toXml();
        
        // XML -> TXT
        FormatConverter converter2 = FormatConverter.fromXml(xml);
        String txtResult = converter2.toText(",", cipher);
        
        assertEquals(testData + "\n", txtResult, "El resultado debe ser igual al original (con salto de línea)");
    }

    @Test
    public void testJsonRoundTrip() {
        String testData = "12345678,Juan,Pérez,1234567890123456,VIP,555-1234,13.6923,-89.2184";
        
        VigenereCipher cipher = new VigenereCipher("TESTKEY");
        
        // TXT -> JSON
        FormatConverter converter1 = FormatConverter.fromText(testData, ",", cipher);
        String json = converter1.toJson();
        
        // JSON -> TXT
        FormatConverter converter2 = FormatConverter.fromJson(json);
        String txtResult = converter2.toText(",", cipher);
        
        assertEquals(testData + "\n", txtResult, "El resultado debe ser igual al original (con salto de línea)");
    }

    @Test
    public void testInvalidLineHandling() {
        String testData = "12345678,Juan,Pérez,1234567890123456,VIP,555-1234,13.6923,-89.2184\n" +
                         "linea_invalida_con_pocos_campos\n" +
                         "87654321,María,García,6543210987654321,Regular,555-5678,13.6945,-89.2199";
        
        VigenereCipher cipher = new VigenereCipher("TESTKEY");
        FormatConverter converter = FormatConverter.fromText(testData, ",", cipher);
        
        assertEquals(2, converter.getClientes().size(), "Debe crear solo 2 clientes válidos, ignorando la línea inválida");
    }

    @Test
    public void testDifferentDelimiter() {
        String testData = "12345678;Juan;Pérez;1234567890123456;VIP;555-1234;13.6923;-89.2184";
        
        VigenereCipher cipher = new VigenereCipher("TESTKEY");
        FormatConverter converter = FormatConverter.fromText(testData, ";", cipher);
        
        assertEquals(1, converter.getClientes().size(), "Debe crear 1 cliente con delimitador punto y coma");
        
        String txtResult = converter.toText(";", cipher);
        assertEquals(testData + "\n", txtResult, "El resultado con el mismo delimitador debe ser igual");
    }
} 