package com.example.songolee.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class Partida {

    private int id_partida,id_usuario, num_partida;
    private String resultado;

    private String listaFases;

    public Partida() {
    }

    //Constructor para generar la informacion de la ventana emergente
    public Partida(int id_partida, int id_usuario, int num_partido, String resultado, String listaFases) {
        this.id_partida = id_partida;
        this.id_usuario = id_usuario;
        this.num_partida = num_partido;
        this.resultado = resultado;
        this.listaFases = listaFases;
    }

    public Partida(int id_partida, int id_usuario, String resultado, int num_partido) {
        this.id_partida = id_partida;
        this.id_usuario = id_usuario;
        this.resultado = resultado;
        this.num_partida = num_partido;
    }

    public String getListaFases() {
        return listaFases;
    }

    public void setListaFases(String listaFases) {
        this.listaFases = listaFases;
    }

    public int getId_partida() {
        return id_partida;
    }

    public void setId_partida(int id_partida) {
        this.id_partida = id_partida;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public int getNum_partida() {
        return num_partida;
    }

    public void setNum_partida(int num_partida) {
        this.num_partida = num_partida;
    }

    @Override
    public String toString() {
        return "Partida: " + num_partida +
                "\nResultado: " + resultado +
                "\nFases:\n" + listaFases ;
    }


    public Partida converJson(JSONObject jsonObject) throws JSONException {

        this.setId_partida(jsonObject.getInt("id_partida"));
        this.setId_usuario(jsonObject.getInt("id_usuario"));
        this.setResultado(jsonObject.getString("resultado"));
        this.setNum_partida((jsonObject.getInt("num_partida")));

        return this;
    }
}
