package edu.uca.ari;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "poligono")
@XmlAccessorType(XmlAccessType.FIELD)
public class Poligono {
    @XmlAttribute
    private String type = "Point";
    @XmlElement
    private double latitud;
    @XmlElement
    private double longitud;
    @XmlAttribute
    private String ubicacion;

    public Poligono() {}

    public Poligono(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.ubicacion = String.format("%.6f,%.6f", latitud, longitud);
    }

    public double getLatitud() { return latitud; }
    public void setLatitud(double latitud) { this.latitud = latitud; }
    public double getLongitud() { return longitud; }
    public void setLongitud(double longitud) { this.longitud = longitud; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
} 