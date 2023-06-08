package com.example.songolee.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import com.example.songolee.Connector.BaseDatos;
import com.example.songolee.Model.Cancion;
import com.example.songolee.Model.Usuario;
import com.example.songolee.databinding.ActivityGameAdminBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class GameActivityAdmin extends AppCompatActivity {

    public ActivityGameAdminBinding binding;
    public SharedPreferences bbdd;//Para obtener la informacion del hilo

    public Bundle bundle;
    public Usuario usuario; //guarda la informacion del usuario actual
    public List<Cancion> listaCanciones = new ArrayList<>();// lista para guardar todas las canciones;
    public ArrayAdapter<Cancion> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameAdminBinding.inflate(getLayoutInflater());
        this.bbdd = this.getSharedPreferences("bbdd", Context.MODE_PRIVATE);
        //metodo para recuperar el usuario

        recuperarUsuario();
        setContentView(binding.getRoot());
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCanciones);
        recuperarCanciones();



    }

    private void recuperarCanciones() {
        BaseDatos.buscarTodasCanciones(this, new Runnable() {
            @Override
            public void run() {
                //success
                try {

                    JSONArray u = new JSONArray(bbdd.getString("result",""));
                    Gson gson = new Gson();
                    Cancion[] personasArray = gson.fromJson(String.valueOf(u), Cancion[].class);

                    // Convertir el array en una lista
                    Cancion[] personasList = personasArray;
                    for(Cancion cancion : personasList){

                        cancion.conver();
                        listaCanciones.add(cancion);

                    }
                    adapter.addAll(listaCanciones);
                    binding.songListView.setAdapter(adapter);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Runnable() {
            @Override
            public void run() {
                //error
                Toast.makeText(GameActivityAdmin.this, "Error al extraer las canciones.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void recuperarUsuario() {
        bundle = this.getIntent().getExtras();
        if(bundle!= null){
            try {
                JSONObject convers = new JSONObject(bundle.getString("usuario"));
                usuario = new Usuario();
                usuario.converJson(convers);
                //binding.username.setText(usuario.getUsername());

                binding.adminNameTextView.setText("Nombre del administrador: "+ usuario.getNombre());
            }
            catch (JSONException e) {
                Toast.makeText(GameActivityAdmin.this, "Se ha perdido la información del usuario.", Toast.LENGTH_LONG).show();
            }


        }
    }


}