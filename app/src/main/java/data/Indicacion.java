package data;

import android.content.ContentValues;

import java.util.UUID;

public class Indicacion {

    private int orden;
    private String id;
    private String ruta;
    private String imagen;
    private String textoIndicacion;
    private int pasos;

    public Indicacion(String ruta, String imagen, String textoIndicacion, int pasos, int orden) {
        this.id = UUID.randomUUID().toString();
        this.ruta = ruta;
        this.imagen = imagen;
        this.textoIndicacion = textoIndicacion;
        this.pasos = pasos;
        this.orden = orden;
    }

    public String getId() { return id; }

    public String getRuta() { return ruta; }

    public String getTextoIndicacion() {
        return textoIndicacion;
    }

    public String getImagen() {
        return imagen;
    }


    public int getOrden() { return orden; }

    public int getPasos() {
        return pasos;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(IndicacionesContract.IndicacionEntry.ID, id);
        values.put(IndicacionesContract.IndicacionEntry.RUTA, ruta);
        values.put(IndicacionesContract.IndicacionEntry.ORDEN, orden);
        values.put(IndicacionesContract.IndicacionEntry.TEXTO_INDICACION, textoIndicacion);
        values.put(IndicacionesContract.IndicacionEntry.PASOS, pasos);
        values.put(IndicacionesContract.IndicacionEntry.IMAGEN, imagen);
        return values;
    }

    public String toString(){
        return "ID: "+this.id
                +"\n Ruta: "+this.ruta
                +"\n Imagen: "+this.imagen
                +"\n Pasos: "+this.pasos
                +"\n Orden: "+this.orden;
    }
}
