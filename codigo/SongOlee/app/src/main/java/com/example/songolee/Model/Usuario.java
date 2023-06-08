package com.example.songolee.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class Usuario {
    private int id;
    private String username;
    private String nombre, rol;
    private int edad;


    public Usuario() {
    }

    public Usuario usuarioInsert(String username,String nombre, int edad,String rol) {

        this.username = username;
        this.nombre = nombre;
        this.edad = edad;
        this.rol = rol;
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Usuario converJson(JSONObject jsonObject) throws JSONException {

        this.setId(jsonObject.getInt("id_usuario"));
        this.setUsername(jsonObject.getString("username"));
        this.setNombre(jsonObject.getString("nombre"));
        this.setEdad(jsonObject.getInt("edad"));
        this.setRol((jsonObject.getString("rol")));


        return this;
    }
}
