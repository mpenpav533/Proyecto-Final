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
import com.example.songolee.databinding.ActivityRegisterBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    public ActivityRegisterBinding bindingR;// Vincular la vista con la actividad
    public SharedPreferences bbdd;//Para obtener la informacion del hilo

    public String rol = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindingR = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(bindingR.getRoot());
        this.bbdd = this.getSharedPreferences("bbdd", Context.MODE_PRIVATE);

        bindingR.btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GUARDAR EL ROL DEL USUARIO
                if(bindingR.checkRol.isActivated()){
                    rol = "Admin";
                }else{
                    rol = "normal";
                }

                BaseDatos.insertUsuario(RegisterActivity.this,
                        bindingR.etusername.getText().toString(),
                        bindingR.etnombre.getText().toString(),
                        bindingR.etedad.getText().toString(),
                        rol,
                        new Runnable() {
                            @Override
                            public void run() {
                                //success

                                try {
                                    JSONObject u = new JSONObject(bbdd.getString("result", ""));
                                   //Snackbar.make(bindingR.layoutR, "bienvenido " + username, Snackbar.LENGTH_LONG).show();

                                    Bundle bundle = new Bundle();
                                    Usuario usuarioA = new Usuario();
                                    usuarioA = usuarioA.converJson(u);
                                    if (usuarioA.getRol().equalsIgnoreCase("normal")) {
                                        Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                                        bundle.putString("usuario",u.toString());
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }else{
                                        Intent intent = new Intent(getApplicationContext(), GameActivityAdmin.class);
                                        bundle.putString("usuario", u.toString());
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    Toast.makeText(RegisterActivity.this, "Error al extraer el usuario.", Toast.LENGTH_LONG).show();
                                }

                            }
                        },
                        new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegisterActivity.this,"El usuario ya existe.", Toast.LENGTH_LONG).show();


                            }
                        });
            }
        });

        bindingR.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent);
            }
        });

    }
}