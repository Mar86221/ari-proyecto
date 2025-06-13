package edu.uca.ari;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main extends JFrame {
    private JTextArea sourceTextArea;
    private JTextArea resultTextArea;
    private JTextField delimiterField;
    private JTextField encryptionKeyField;
    private JButton convertToXmlButton;
    private JButton convertToJsonButton;
    private JButton convertToTextButton;
    private JButton selectSourceButton;
    private JButton selectDestinationButton;
    private File sourceFile;
    private File destinationFile;

    public Main() {
        setTitle("Conversor de Formatos - ARI Proyecto");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Crear componentes
        sourceTextArea = new JTextArea();
        resultTextArea = new JTextArea();
        delimiterField = new JTextField(",");
        encryptionKeyField = new JTextField();
        
        // Configurar áreas de texto
        sourceTextArea.setEditable(false);
        resultTextArea.setEditable(false);
        
        // Crear botones
        convertToXmlButton = new JButton("Convertir a XML");
        convertToJsonButton = new JButton("Convertir a JSON");
        convertToTextButton = new JButton("Convertir a Texto");
        selectSourceButton = new JButton("Seleccionar Origen");
        selectDestinationButton = new JButton("Seleccionar Destino");

        // Configurar layout
        setLayout(new BorderLayout());
        
        // Panel superior para controles
        JPanel controlPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        controlPanel.add(new JLabel("Delimitador:"));
        controlPanel.add(delimiterField);
        controlPanel.add(selectSourceButton);
        controlPanel.add(new JLabel("Clave de Cifrado:"));
        controlPanel.add(encryptionKeyField);
        controlPanel.add(selectDestinationButton);
        
        // Panel central para áreas de texto
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
            new JScrollPane(sourceTextArea),
            new JScrollPane(resultTextArea));
        splitPane.setDividerLocation(300);
        
        // Panel inferior para botones de conversión
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(convertToXmlButton);
        buttonPanel.add(convertToJsonButton);
        buttonPanel.add(convertToTextButton);
        
        // Agregar componentes al frame
        add(controlPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Configurar listeners
        setupListeners();
    }

    private void setupListeners() {
        selectSourceButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(new java.io.File("test"));
            fileChooser.setFileFilter(new FileNameExtensionFilter(
                "Archivos de texto", "txt"));
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                sourceFile = fileChooser.getSelectedFile();
                try {
                    String content = new String(java.nio.file.Files.readAllBytes(sourceFile.toPath()));
                    sourceTextArea.setText(content);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error al leer el archivo: " + ex.getMessage());
                }
            }
        });

        selectDestinationButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(new java.io.File("test"));
            fileChooser.setFileFilter(new FileNameExtensionFilter(
                "Archivos de texto/XML/JSON", "txt", "xml", "json"));
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                destinationFile = fileChooser.getSelectedFile();
            }
        });

        convertToXmlButton.addActionListener(e -> {
            try {
                String sourceText = sourceTextArea.getText();
                String delimiter = delimiterField.getText();
                String key = encryptionKeyField.getText();
                
                if (key.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor ingrese una clave de cifrado");
                    return;
                }

                VigenereCipher cipher = new VigenereCipher(key);
                FormatConverter converter = FormatConverter.fromText(sourceText, delimiter, cipher);
                String xmlResult = converter.toXml();
                
                resultTextArea.setText(xmlResult);
                if (destinationFile != null) {
                    java.nio.file.Files.write(destinationFile.toPath(), xmlResult.getBytes());
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en la conversión: " + ex.getMessage());
            }
        });

        convertToJsonButton.addActionListener(e -> {
            try {
                String sourceText = sourceTextArea.getText();
                String delimiter = delimiterField.getText();
                String key = encryptionKeyField.getText();
                
                if (key.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor ingrese una clave de cifrado");
                    return;
                }

                VigenereCipher cipher = new VigenereCipher(key);
                FormatConverter converter = FormatConverter.fromText(sourceText, delimiter, cipher);
                String jsonResult = converter.toJson();
                
                resultTextArea.setText(jsonResult);
                if (destinationFile != null) {
                    java.nio.file.Files.write(destinationFile.toPath(), jsonResult.getBytes());
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en la conversión: " + ex.getMessage());
            }
        });

        convertToTextButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(new java.io.File("test"));
            fileChooser.setFileFilter(new FileNameExtensionFilter(
                "Archivos XML/JSON", "xml", "json"));
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                sourceFile = fileChooser.getSelectedFile();
                try {
                    String content = new String(java.nio.file.Files.readAllBytes(sourceFile.toPath()));
                    sourceTextArea.setText(content);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error al leer el archivo: " + ex.getMessage());
                }
            }
            try {
                String sourceText = sourceTextArea.getText();
                String delimiter = delimiterField.getText();
                String key = encryptionKeyField.getText();
                
                if (key.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor ingrese una clave de cifrado");
                    return;
                }

                VigenereCipher cipher = new VigenereCipher(key);
                FormatConverter converter;
                
                if (sourceFile.getName().endsWith(".xml")) {
                    converter = FormatConverter.fromXml(sourceText);
                } else if (sourceFile.getName().endsWith(".json")) {
                    converter = FormatConverter.fromJson(sourceText);
                } else {
                    throw new IllegalArgumentException("Formato de archivo no soportado");
                }
                
                String textResult = converter.toText(delimiter, cipher);
                resultTextArea.setText(textResult);
                
                if (destinationFile != null) {
                    java.nio.file.Files.write(destinationFile.toPath(), textResult.getBytes());
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en la conversión: " + ex.getMessage());
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
} 