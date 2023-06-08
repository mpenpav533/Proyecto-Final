package com.example.songolee.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.example.songolee.Connector.BaseDatos;
import com.example.songolee.Model.Usuario;
import com.example.songolee.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity  {

    public ActivityMainBinding binding;// Vincular la vista con la actividad
    public SharedPreferences bbdd;//Para obtener la informacion del hilo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //establece la vinculacion
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.bbdd = this.getSharedPreferences("bbdd", Context.MODE_PRIVATE);

        binding.btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseDatos.buscarUsuario(MainActivity.this, binding.username.getText().toString(), new Runnable() {
                    @Override
                    public void run() {
                        //success

                        try {
                            Bundle bundle = new Bundle();
                            JSONObject u = new JSONObject(bbdd.getString("result",""));
                            Usuario usuarioA = new Usuario();
                            usuarioA = usuarioA.converJson(u);
                            if (usuarioA.getRol().equalsIgnoreCase("normal")) {
                                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                                bundle.putString("usuario", u.toString());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent(getApplicationContext(), GameActivityAdmin.class);
                                bundle.putString("usuario", u.toString());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {

                            Toast.makeText(MainActivity.this, "Error al extraer el usuario.", Toast.LENGTH_LONG).show();

                        }


                    }
                }, new Runnable() {
                    @Override
                    public void run() {
                        //error

                        Toast.makeText(MainActivity.this, "Error al buscar el usuario", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
        binding.btnregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);

                startActivity(intent);
            }
        });
    }

}