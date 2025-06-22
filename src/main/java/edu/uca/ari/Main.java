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
                String name = destinationFile.getName().toLowerCase();
                if (!(name.endsWith(".txt") || name.endsWith(".xml") || name.endsWith(".json"))) {
                    JOptionPane.showMessageDialog(this,
                        "Recomendación: Usa la extensión .xml, .json o .txt según el formato para evitar problemas de compatibilidad.",
                        "Sugerencia de extensión", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        convertToXmlButton.addActionListener(e -> {
            try {
                String sourceText = sourceTextArea.getText().trim();
                String delimiter = delimiterField.getText();
                String key = encryptionKeyField.getText();
                
                // Validaciones mejoradas
                if (sourceText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No hay contenido para convertir. Ingrese texto o seleccione un archivo.", 
                        "Sin datos", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                if (key.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor ingrese una clave de cifrado para el campo tarjeta", 
                        "Clave requerida", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                if (delimiter.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor especifique un delimitador para procesar el texto", 
                        "Delimitador requerido", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                VigenereCipher cipher = new VigenereCipher(key);
                FormatConverter converter = FormatConverter.fromText(sourceText, delimiter, cipher);
                
                if (converter.getClientes().isEmpty()) {
                    JOptionPane.showMessageDialog(this, 
                        "No se encontraron registros válidos en el texto.\nVerifique que:\n" +
                        "- Cada línea tenga al menos 8 campos separados por '" + delimiter + "'\n" +
                        "- El formato sea: documento,nombres,apellidos,tarjeta,tipo,telefono,latitud,longitud", 
                        "Sin registros válidos", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                String xmlResult = converter.toXml();
                resultTextArea.setText(xmlResult);
                
                if (destinationFile != null) {
                    java.nio.file.Files.write(destinationFile.toPath(), xmlResult.getBytes());
                    JOptionPane.showMessageDialog(this, "Archivo XML generado y guardado exitosamente en:\n" + 
                        destinationFile.getAbsolutePath(), "Conversión exitosa", JOptionPane.INFORMATION_MESSAGE);
                }
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error al convertir a XML:\n" + ex.getMessage() + 
                    "\n\nVerifique el formato de los datos y que todos los campos numéricos sean válidos.", 
                    "Error de conversión", JOptionPane.ERROR_MESSAGE);
            }
        });

        convertToJsonButton.addActionListener(e -> {
            try {
                String sourceText = sourceTextArea.getText().trim();
                String delimiter = delimiterField.getText();
                String key = encryptionKeyField.getText();
                
                // Validaciones mejoradas
                if (sourceText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No hay contenido para convertir. Ingrese texto o seleccione un archivo.", 
                        "Sin datos", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                if (key.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor ingrese una clave de cifrado para el campo tarjeta", 
                        "Clave requerida", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                if (delimiter.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor especifique un delimitador para procesar el texto", 
                        "Delimitador requerido", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                VigenereCipher cipher = new VigenereCipher(key);
                FormatConverter converter = FormatConverter.fromText(sourceText, delimiter, cipher);
                
                if (converter.getClientes().isEmpty()) {
                    JOptionPane.showMessageDialog(this, 
                        "No se encontraron registros válidos en el texto.\nVerifique que:\n" +
                        "- Cada línea tenga al menos 8 campos separados por '" + delimiter + "'\n" +
                        "- El formato sea: documento,nombres,apellidos,tarjeta,tipo,telefono,latitud,longitud", 
                        "Sin registros válidos", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                String jsonResult = converter.toJson();
                resultTextArea.setText(jsonResult);
                
                if (destinationFile != null) {
                    java.nio.file.Files.write(destinationFile.toPath(), jsonResult.getBytes());
                    JOptionPane.showMessageDialog(this, "Archivo JSON generado y guardado exitosamente en:\n" + 
                        destinationFile.getAbsolutePath(), "Conversión exitosa", JOptionPane.INFORMATION_MESSAGE);
                }
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error al convertir a JSON:\n" + ex.getMessage() + 
                    "\n\nVerifique el formato de los datos y que todos los campos numéricos sean válidos.", 
                    "Error de conversión", JOptionPane.ERROR_MESSAGE);
            }
        });

        convertToTextButton.addActionListener(e -> {
            try {
                // Si no hay archivo fuente seleccionado, permitir seleccionar uno
                if (sourceFile == null || sourceTextArea.getText().trim().isEmpty()) {
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
                            return;
                        }
                    } else {
                        return; // Usuario canceló la selección
                    }
                }

                String sourceText = sourceTextArea.getText().trim();
                String delimiter = delimiterField.getText();
                String key = encryptionKeyField.getText();
                
                // Validaciones mejoradas
                if (sourceText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No hay contenido para convertir. Seleccione un archivo primero.", 
                        "Sin datos", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                if (key.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor ingrese una clave de cifrado para descifrar el campo tarjeta", 
                        "Clave requerida", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                if (delimiter.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor especifique un delimitador para el archivo de salida", 
                        "Delimitador requerido", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                VigenereCipher cipher = new VigenereCipher(key);
                FormatConverter converter;
                
                // Detección mejorada de formato
                try {
                    if (sourceFile != null) {
                        String fileName = sourceFile.getName().toLowerCase();
                        if (fileName.endsWith(".xml")) {
                            converter = FormatConverter.fromXml(sourceText);
                        } else if (fileName.endsWith(".json")) {
                            converter = FormatConverter.fromJson(sourceText);
                        } else {
                            // Detección por contenido si no hay extensión clara
                            if (sourceText.startsWith("<?xml") || sourceText.startsWith("<")) {
                                converter = FormatConverter.fromXml(sourceText);
                            } else if (sourceText.startsWith("{") || sourceText.startsWith("[")) {
                                converter = FormatConverter.fromJson(sourceText);
                            } else {
                                throw new IllegalArgumentException("El archivo no parece ser XML ni JSON válido");
                            }
                        }
                    } else {
                        // Solo detección por contenido
                        if (sourceText.startsWith("<?xml") || sourceText.startsWith("<")) {
                            converter = FormatConverter.fromXml(sourceText);
                        } else if (sourceText.startsWith("{") || sourceText.startsWith("[")) {
                            converter = FormatConverter.fromJson(sourceText);
                        } else {
                            throw new IllegalArgumentException("El contenido no parece ser XML ni JSON válido");
                        }
                    }
                    
                    String textResult = converter.toText(delimiter, cipher);
                    resultTextArea.setText(textResult);
                    
                    if (destinationFile != null) {
                        java.nio.file.Files.write(destinationFile.toPath(), textResult.getBytes());
                        JOptionPane.showMessageDialog(this, "Archivo convertido y guardado exitosamente en:\n" + 
                            destinationFile.getAbsolutePath(), "Conversión exitosa", JOptionPane.INFORMATION_MESSAGE);
                    }
                    
                } catch (Exception parseEx) {
                    JOptionPane.showMessageDialog(this, 
                        "Error al procesar el archivo:\n" + parseEx.getMessage() + 
                        "\n\nVerifique que el archivo tenga formato XML o JSON válido y que la clave sea correcta.", 
                        "Error de conversión", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
} 