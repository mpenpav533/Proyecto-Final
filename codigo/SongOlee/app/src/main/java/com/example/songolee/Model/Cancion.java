package com.example.songolee.Model;

import com.example.songolee.Connector.BaseDatos;

import org.json.JSONException;
import org.json.JSONObject;

public class Cancion {
    private int id_cancion;
    private String nombre,url;

    public Cancion() {
    }

    public Cancion(int id_cancion, String nombre, String url) {
        this.id_cancion = id_cancion;
        this.nombre = nombre;
        this.url = url;
    }

    public int getId_cancion() {
        return id_cancion;
    }

    public void setId_cancion(int id_cancion) {
        this.id_cancion = id_cancion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Cancion converJson(JSONObject jsonObject) throws JSONException {


        this.setId_cancion(jsonObject.getInt("id_cancion"));
        this.setNombre(jsonObject.getString("nombre"));
        String url = (jsonObject.getString("url"));

        String resultado = BaseDatos.posturl + url.replace("\'","/");

        this.setUrl(resultado);

        return this;
    }
    public Cancion conver() throws JSONException {


        String resultado = url.replace("\'","/");

        this.setUrl(resultado);

        return this;
    }

    @Override
    public String toString() {
        return "Id cancion: "+ id_cancion
        +"\nCancion:" + nombre
                + "\nUrl:" + url;
    }
}
