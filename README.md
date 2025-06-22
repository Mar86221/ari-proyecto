# Proyecto ARI - Administración de Riesgos Informáticos
## Conversor de Formatos con Cifrado

### 📋 Descripción
Este proyecto implementa un conversor de formatos de datos que permite:
- Convertir archivos de texto delimitados (CSV/TXT) a formatos XML y JSON
- Convertir archivos XML/JSON de vuelta a formato de texto delimitado
- Cifrar/descifrar automáticamente el campo "tarjeta" usando el algoritmo Vigenère
- Manejar entidades geográficas (coordenadas de latitud y longitud)
- Interfaz gráfica intuitiva para todas las operaciones

### 🚀 Instalación y Ejecución

#### Requisitos
- Java 11 o superior
- Maven 3.6 o superior

#### Instalación
1. Clona el repositorio:
   ```bash
   git clone https://github.com/Mar86221/ari-proyecto.git
   cd ari-proyecto
   ```

2. Compila el proyecto:
   ```bash
   mvn clean package
   ```

3. Ejecuta la aplicación:
   ```bash
   java -jar target/ari-proyecto-1.0-SNAPSHOT-jar-with-dependencies.jar
   ```

### 🖥️ Uso de la Interfaz Gráfica

#### Pasos para Convertir TXT → XML/JSON:
1. **Seleccionar Origen**: Haz clic en "Seleccionar Origen" y elige tu archivo TXT
2. **Configurar Delimitador**: Especifica el símbolo que separa los campos (por defecto: `,`)
3. **Ingresar Clave**: Escribe la clave de cifrado para el campo tarjeta
4. **Seleccionar Destino**: Haz clic en "Seleccionar Destino" y elige dónde guardar el resultado
5. **Convertir**: Haz clic en "Convertir a XML" o "Convertir a JSON"

#### Pasos para Convertir XML/JSON → TXT:
1. **Seleccionar Destino**: Primero selecciona donde guardar el archivo TXT resultante
2. **Configurar Delimitador**: Especifica el símbolo delimitador para el archivo de salida
3. **Ingresar Clave**: Escribe la clave de descifrado (debe ser la misma usada para cifrar)
4. **Convertir**: Haz clic en "Convertir a Texto" y selecciona el archivo XML/JSON

### 📄 Formato de Datos

#### Archivo TXT de Entrada
Cada línea debe contener 8 campos separados por el delimitador especificado:
```
documento,nombres,apellidos,tarjeta,tipo,telefono,latitud,longitud
```

**Ejemplo:**
```
12345678,Juan,Pérez,1234567890123456,VIP,555-1234,13.6923,-89.2184
87654321,María,García,6543210987654321,Regular,555-5678,13.6945,-89.2199
```

#### Consideraciones Importantes:
- **Campo tarjeta**: Se cifra automáticamente al convertir a XML/JSON
- **Coordenadas**: Latitud y longitud deben ser números decimales válidos
- **Líneas inválidas**: Se ignoran automáticamente (menos de 8 campos)
- **Delimitadores**: Puedes usar cualquier símbolo: `,`, `;`, `|`, `\t`, etc.

### 🔐 Sistema de Cifrado

#### Algoritmo Vigenère
- Solo se cifra el campo "tarjeta" por seguridad
- Otros campos permanecen en texto claro para consulta
- La clave de cifrado es requerida tanto para cifrar como descifrar
- **Importante**: Guarda la clave de cifrado, sin ella no podrás recuperar los números de tarjeta

#### Ejemplo de Cifrado:
```
Original: 1234567890123456
Clave: MICLAVE
Resultado: [texto cifrado ilegible]
```

### 🗂️ Estructura del Proyecto

```
ARI/
├── src/
│   ├── main/java/edu/uca/ari/
│   │   ├── Main.java              # Interfaz gráfica principal
│   │   ├── Cliente.java           # Modelo de datos del cliente
│   │   ├── FormatConverter.java   # Motor de conversión de formatos
│   │   ├── VigenereCipher.java    # Implementación del cifrado
│   │   └── Poligono.java          # Manejo de coordenadas geográficas
│   └── test/java/edu/uca/ari/
│       ├── VigenereCipherTest.java
│       └── FormatConverterTest.java
├── docs/                          # Documentación del proyecto
├── test/                          # Archivos de prueba
└── target/                        # Archivos compilados
```

### 🧪 Ejecutar Pruebas

```bash
mvn test
```

Las pruebas cubren:
- ✅ Funcionamiento del cifrado Vigenère
- ✅ Conversiones entre todos los formatos (TXT ↔ XML ↔ JSON)
- ✅ Manejo de delimitadores personalizados
- ✅ Validación de datos inválidos
- ✅ Procesos completos de ida y vuelta

### ⚠️ Resolución de Problemas

#### Error: "No se encontraron registros válidos"
- Verifica que cada línea tenga exactamente 8 campos
- Revisa que el delimitador especificado sea correcto
- Asegúrate de que las coordenadas sean números válidos

#### Error: "Formato de archivo no soportado"
- El archivo debe ser XML válido (empezar con `<?xml` o `<`)
- El archivo debe ser JSON válido (empezar con `{` o `[`)
- Verifica que el archivo no esté corrupto

#### Campo tarjeta ilegible
- Verifica que estés usando la misma clave usada para cifrar
- Las claves son sensibles a mayúsculas/minúsculas
- Si perdiste la clave, no es posible recuperar los datos

### 👥 Equipo de Desarrollo

**Miembros del Grupo:**
- [Nombre] - [Carnet] - [Email]
- [Nombre] - [Carnet] - [Email]

**Repositorio:** https://github.com/Mar86221/ari-proyecto

### 📝 Licencia

Este proyecto es parte del curso de Administración de Riesgos Informáticos - Universidad Centroamericana (UCA)

---

🔧 **Versión:** 1.0-SNAPSHOT  
📅 **Última actualización:** Junio 2024
