# Documentación de Pruebas de Caja Negra - Parte II

## Guía de Instalación y Uso

### Requisitos
- Java 11 o superior
- Maven

### Instalación
1. Clona el repositorio:
   ```bash
   git clone https://github.com/Mar86221/ari-proyecto.git
   cd ari-proyecto
   ```
2. Compila el proyecto:
   ```bash
   mvn clean package
   ```
   El ejecutable estará en `target/ari-proyecto-1.0-SNAPSHOT-jar-with-dependencies.jar`.

### Ejecución
```bash
java -cp target/ari-proyecto-1.0-SNAPSHOT-jar-with-dependencies.jar edu.uca.ari.Main
```

### Uso de la Interfaz
1. **Seleccionar Origen:** Elige el archivo de entrada (TXT, XML o JSON).
2. **Seleccionar Destino:** Elige dónde guardar el resultado (usa la extensión correcta: `.xml`, `.json`, `.txt`).
3. **Delimitador:** Escribe el delimitador usado en el archivo TXT (por defecto `,`).
4. **Clave de Cifrado:** Escribe la clave para cifrar/descifrar el campo tarjeta.
5. **Botones de Conversión:**
   - **Convertir a XML:** Convierte TXT a XML cifrado.
   - **Convertir a JSON:** Convierte TXT a JSON cifrado.
   - **Convertir a Texto:** Convierte XML/JSON a TXT descifrado.

**Nota:**
- Si la clave es incorrecta al descifrar, el campo tarjeta será ilegible.
- Guarda siempre los archivos con la extensión correcta para evitar errores.

---

## Pruebas de Caja Negra

### 1. Conversión: TXT → XML

#### Caso 1: Entrada correcta, ingresada correctamente
- **Entrada:** Archivo `clientes.txt` válido, delimitador `,`, clave válida.
- **Acción:** Convertir a XML.
- **Resultado esperado:** XML válido, campo `tarjeta` cifrado.
- **Resultado obtenido:** ✔️

#### Caso 2: Entrada correcta, ingresada incorrectamente
- **Entrada:** Archivo `clientes.txt` válido, delimitador incorrecto (`;`).
- **Acción:** Convertir a XML.
- **Resultado esperado:** XML vacío o error, advertencia de campos insuficientes.
- **Resultado obtenido:** ✔️ (muestra error o ignora líneas)

#### Caso 3: Ninguna entrada
- **Entrada:** No se selecciona archivo ni texto.
- **Acción:** Convertir a XML.
- **Resultado esperado:** Mensaje de error indicando falta de datos.
- **Resultado obtenido:** ✔️

#### Caso 4: Entrada incorrecta, ingresada correctamente
- **Entrada:** Archivo de texto con menos de 8 campos por línea.
- **Acción:** Convertir a XML.
- **Resultado esperado:** Solo se convierten líneas válidas, las demás se ignoran.
- **Resultado obtenido:** ✔️

#### Caso 5: Entrada incorrecta, ingresada incorrectamente
- **Entrada:** Archivo de texto corrupto, delimitador incorrecto y clave vacía.
- **Acción:** Convertir a XML.
- **Resultado esperado:** Error, advertencia de clave faltante o formato inválido.
- **Resultado obtenido:** ✔️

---

### 2. Conversión: TXT → JSON
_(Mismos casos y resultados que para XML)_

---

### 3. Conversión: XML/JSON → TXT

#### Caso 1: Entrada correcta, ingresada correctamente
- **Entrada:** Archivo XML/JSON generado por el sistema, clave correcta.
- **Acción:** Convertir a TXT.
- **Resultado esperado:** TXT igual al original, campo `tarjeta` descifrado.
- **Resultado obtenido:** ✔️

#### Caso 2: Entrada correcta, ingresada incorrectamente
- **Entrada:** Archivo XML/JSON válido, pero clave incorrecta.
- **Acción:** Convertir a TXT.
- **Resultado esperado:** Campo `tarjeta` ilegible (no descifrado).
- **Resultado obtenido:** ✔️

#### Caso 3: Ninguna entrada
- **Entrada:** No se selecciona archivo.
- **Acción:** Convertir a TXT.
- **Resultado esperado:** Mensaje de error.
- **Resultado obtenido:** ✔️

#### Caso 4: Entrada incorrecta, ingresada correctamente
- **Entrada:** Archivo XML/JSON con estructura inválida.
- **Acción:** Convertir a TXT.
- **Resultado esperado:** Error de parseo.
- **Resultado obtenido:** ✔️

#### Caso 5: Entrada incorrecta, ingresada incorrectamente
- **Entrada:** Archivo de texto plano renombrado como `.xml` o `.json`.
- **Acción:** Convertir a TXT.
- **Resultado esperado:** Error de formato no soportado.
- **Resultado obtenido:** ✔️

---

## Validaciones implementadas
- Clave de cifrado vacía: advertencia y bloqueo.
- Delimitador incorrecto: error o líneas ignoradas.
- Formato de archivo no soportado: error claro.
- Entradas incorrectas: líneas ignoradas o error.
- Ninguna entrada: error claro.
- Clave incorrecta al descifrar: campo ilegible.
- Advertencia al guardar sin extensión.

---


