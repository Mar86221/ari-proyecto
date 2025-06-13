package edu.uca.ari;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "cliente")
@XmlAccessorType(XmlAccessType.FIELD)
public class Cliente {
    @XmlElement
    private String documento;
    @XmlElement
    private String nombres;
    @XmlElement
    private String apellidos;
    @XmlElement
    private String tarjeta;
    @XmlElement
    private String tipo;
    @XmlElement
    private String telefono;
    @XmlElement
    private Poligono poligono;

    public Cliente() {}

    public Cliente(String[] campos, VigenereCipher cipher) {
        this.documento = campos[0];
        this.nombres = campos[1];
        this.apellidos = campos[2];
        this.tarjeta = cipher.encrypt(campos[3]);
        this.tipo = campos[4];
        this.telefono = campos[5];
        this.poligono = new Poligono(Double.parseDouble(campos[6]), Double.parseDouble(campos[7]));
    }

    public String[] toArray(VigenereCipher cipher) {
        return new String[] {
            documento,
            nombres,
            apellidos,
            cipher.decrypt(tarjeta),
            tipo,
            telefono,
            String.valueOf(poligono.getLatitud()),
            String.valueOf(poligono.getLongitud())
        };
    }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getTarjeta() { return tarjeta; }
    public void setTarjeta(String tarjeta) { this.tarjeta = tarjeta; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public Poligono getPoligono() { return poligono; }
    public void setPoligono(Poligono poligono) { this.poligono = poligono; }
} 