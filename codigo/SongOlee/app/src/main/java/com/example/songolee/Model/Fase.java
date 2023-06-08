package com.example.songolee.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class Fase {
    private  int idFase,idCancion,idPartida,puntos,num_fase;

    public Fase() {
    }

    public Fase(int idFase, int idCancion, int idPartida, int puntos,int num_fase) {
        this.idFase = idFase;
        this.idCancion = idCancion;
        this.idPartida = idPartida;
        this.puntos = puntos;
        this.num_fase = num_fase;
    }

    public int getIdFase() {
        return idFase;
    }

    public void setIdFase(int idFase) {
        this.idFase = idFase;
    }

    public int getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(int idCancion) {
        this.idCancion = idCancion;
    }

    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getNum_fase() {
        return num_fase;
    }

    public void setNum_fase(int num_fase) {
        this.num_fase = num_fase;
    }

    public Fase converJson(JSONObject jsonObject) throws JSONException {

        this.setIdFase(jsonObject.getInt("id_fase"));
        this.setIdCancion(jsonObject.getInt("id_cancion"));
        this.setIdPartida(jsonObject.getInt("id_partida"));
        this.setPuntos(jsonObject.getInt("puntos"));
        this.setNum_fase(jsonObject.getInt("num_fase"));


        return this;
    }

    @Override
    public String toString() {
        return "Fase: " + num_fase+
        "\nPuntos: " + puntos ;
    }
}
