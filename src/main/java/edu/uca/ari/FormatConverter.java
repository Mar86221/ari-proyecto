package edu.uca.ari;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.*;
import java.io.*;
import java.util.*;

@XmlRootElement(name = "clientes")
@XmlAccessorType(XmlAccessType.FIELD)
public class FormatConverter {
    @XmlElement(name = "cliente")
    private List<Cliente> clientes = new ArrayList<>();

    public FormatConverter() {}

    public void addCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public static FormatConverter fromText(String text, String delimiter, VigenereCipher cipher) {
        FormatConverter converter = new FormatConverter();
        String[] lineas = text.split("\n");
        for (String linea : lineas) {
            String[] campos = linea.split(delimiter);
            if (campos.length >= 8) {
                converter.addCliente(new Cliente(campos, cipher));
            }
        }
        return converter;
    }

    public static FormatConverter fromXml(String xml) {
        try {
            JAXBContext context = JAXBContext.newInstance(FormatConverter.class, Cliente.class, Poligono.class);
            return (FormatConverter) context.createUnmarshaller().unmarshal(new StringReader(xml));
        } catch (Exception e) {
            throw new RuntimeException("Error al parsear XML: " + e.getMessage());
        }
    }

    public static FormatConverter fromJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, FormatConverter.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al parsear JSON: " + e.getMessage());
        }
    }

    public String toXml() {
        try {
            JAXBContext context = JAXBContext.newInstance(FormatConverter.class, Cliente.class, Poligono.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter writer = new StringWriter();
            marshaller.marshal(this, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar XML: " + e.getMessage());
        }
    }

    public String toJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Error al generar JSON: " + e.getMessage());
        }
    }

    public String toText(String delimiter, VigenereCipher cipher) {
        StringBuilder result = new StringBuilder();
        for (Cliente cliente : clientes) {
            String[] campos = cliente.toArray(cipher);
            result.append(String.join(delimiter, campos)).append("\n");
        }
        return result.toString();
    }
} 