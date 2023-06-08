package com.example.songolee.Vista;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import android.media.MediaPlayer;

import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.example.songolee.Connector.BaseDatos;
import com.example.songolee.FasesPartidasCallback;
import com.example.songolee.Model.Cancion;
import com.example.songolee.Model.Fase;
import com.example.songolee.Model.Partida;
import com.example.songolee.Model.Usuario;
import com.example.songolee.R;
import com.example.songolee.databinding.ActivityGameBinding;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameActivity extends AppCompatActivity {

    public ActivityGameBinding binding;
    public SharedPreferences bbdd;//Para obtener la informacion del hilo
    public Bundle bundle;
    public Usuario usuario; //guarda la informacion del usuario actual
    public Partida partida; // guarda la informacion de la partida actual
    public Fase fase; //guarda la informacion de la fase actual
    public Cancion cancion; //guarda la informacion de la cancion que se esta reproduciendo
    public  List<Partida> partidas;// para mostrar todas las partidas de un usuario
    public List<String> fases; // para mostrar todas las fases de todas las partidas de un usuatio
    public boolean resultPartida; // boolean para comprobar si gana o pierde

    private static int duracionReproduccion = 4; // Duración en segundos
    private String urlCancion;// url de cancion para el mediaplayer

    private MediaPlayer mediaPlayer;

    //Ventana emergente de la informacion del usuario
    private TextView nombre;
    private TextView nick;
    private TextView edad;
    private ListView ListPartida;
    private ArrayAdapter<Partida> adapter ;
    private Button btnAtras;
    final Object lock = new Object();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityGameBinding.inflate(getLayoutInflater());
        //metodo para recuperar el usuario
        recuperarUsuario();

        setContentView(binding.getRoot());
        this.bbdd = this.getSharedPreferences("bbdd", Context.MODE_PRIVATE);


        extraerCancion();
        //establecemos las funciones para los botones

        funcionBotones();


    }

    private void funcionBotones() {

        //funcion para el boton reproducir
        binding.btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //creamos partida
                creacionPartida();
                //obtener cancion


                mediaPlayer = new MediaPlayer();

                try {
                    mediaPlayer.setDataSource(urlCancion);
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                    // Reproducir durante x segundos
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(duracionReproduccion* 1000L);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                mediaPlayer.stop();
                                mediaPlayer.release();
                                mediaPlayer = null;
                            }
                        }
                    });
                    thread.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //funcion para validar nombre de cancion
        binding.btnvalidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Comenzamos la fase
                if(binding.textCancion.getText().toString().equalsIgnoreCase(cancion.getNombre())){
                    BaseDatos.insertFase(GameActivity.this, cancion.getId_cancion(), partida.getId_partida(), 10, new Runnable() {
                        @Override
                        public void run() {
                            //success

                            //guardamos la informacion de la fase que se ha insertado en un objeto de Fase
                            try {
                                JSONObject u = new JSONObject(bbdd.getString("result", ""));

                                int num = u.getInt("insertado");
                                //guardamos el intento en el que ha ganado
                                switch (num){
                                    case 1:
                                        binding.intent1.setText("CORRECTO");
                                        binding.intent1.setBackgroundColor(Color.GREEN);

                                        resultPartida = true;
                                        modifPartida();
                                        mostrarDialogAcertado();

                                        break;
                                    case 2:

                                        binding.intent2.setText("CORRECTO");
                                        binding.intent2.setBackgroundColor(Color.GREEN);
                                        resultPartida = true;
                                        modifPartida();
                                        mostrarDialogAcertado();

                                        break;
                                    case 3:

                                        binding.intent3.setText("CORRECTO");
                                        binding.intent3.setBackgroundColor(Color.GREEN);
                                        resultPartida = true;
                                        modifPartida();
                                        mostrarDialogAcertado();

                                        break;
                                    case 4:

                                        binding.intent4.setText("CORRECTO");
                                        binding.intent4.setBackgroundColor(Color.GREEN);
                                        resultPartida = true;
                                        modifPartida();
                                        mostrarDialogAcertado();
                                        break;
                                    case 5:

                                        binding.intent5.setText("CORRECTO");
                                        binding.intent5.setBackgroundColor(Color.GREEN);
                                        resultPartida = true;
                                        modifPartida();
                                        mostrarDialogAcertado();
                                        break;
                                    default:
                                        binding.intent1.setText("INCORRECTO");
                                        binding.intent1.setEnabled(false);
                                        binding.intent2.setText("INCORRECTO");
                                        binding.intent2.setEnabled(false);
                                        binding.intent3.setText("INCORRECTO");
                                        binding.intent3.setEnabled(false);
                                        binding.intent4.setText("INCORRECTO");
                                        binding.intent4.setEnabled(false);
                                        binding.intent5.setText("INCORRECTO");
                                        binding.intent5.setEnabled(false);

                                        resultPartida = false;
                                        modifPartida();
                                        mostrarDialogIntentos();


                                }
                                //guardamos el nombre intorducido por el usuario


                            } catch (JSONException e) {
                                Toast.makeText(GameActivity.this, "Fallo al extraer la fase", Toast.LENGTH_SHORT).show();
                            }


                        }
                    }, new Runnable() {
                        @Override
                        public void run() {
                            //error
                            Toast.makeText(GameActivity.this, "Fallo al insertar la fase", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    BaseDatos.insertFase(GameActivity.this, cancion.getId_cancion(), partida.getId_partida(), 0, new Runnable() {
                        @Override
                        public void run() {
                            //success

                            //guardamos la informacion de la fase que se ha insertado en un objeto de Fase
                            try {
                                JSONObject u = new JSONObject(bbdd.getString("result", ""));

                                int num = u.getInt("insertado");

                                //guardamos el intento en el que ha ganado
                                switch (num){
                                    case 1:
                                        binding.intent1.setText("INCORRECTO");
                                        binding.intent1.setEnabled(false);

                                        duracionReproduccion+= 3;

                                        break;
                                    case 2:

                                        binding.intent2.setText("INCORRECTO");
                                        binding.intent2.setEnabled(false);

                                        duracionReproduccion+=4;

                                        break;
                                    case 3:

                                        binding.intent3.setText("INCORRECTO");
                                        binding.intent3.setEnabled(false);

                                        duracionReproduccion+=5;

                                        break;
                                    case 4:

                                        binding.intent4.setText("INCORRECTO");
                                        binding.intent4.setEnabled(false);

                                        duracionReproduccion+=6;
                                        break;
                                    case 5:

                                        binding.intent5.setText("INCORRECTO");
                                        binding.intent5.setEnabled(false);

                                        duracionReproduccion+= 7;
                                        resultPartida = false;
                                        modifPartida();
                                        mostrarDialogIntentos();
                                        break;

                                }


                            } catch (JSONException e) {
                                Toast.makeText(GameActivity.this, "Fallo al extraer la fase", Toast.LENGTH_SHORT).show();
                            }


                        }
                    }, new Runnable() {
                        @Override
                        public void run() {
                            //error
                            Toast.makeText(GameActivity.this, "Fallo al insertar la fase", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

        //funcion para saltar intento
        binding.btnsaltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseDatos.insertFase(GameActivity.this, cancion.getId_cancion(), partida.getId_partida(), 0, new Runnable() {
                    @Override
                    public void run() {
                        //success

                        //guardamos la informacion de la fase que se ha insertado en un objeto de Fase
                        try {
                            JSONObject u = new JSONObject(bbdd.getString("result", ""));

                            int num = u.getInt("insertado");
                            //guardamos el intento en el que ha ganado
                            switch (num){
                                case 1:
                                    binding.intent1.setText("SALTAR");
                                    binding.intent1.setEnabled(false);

                                    duracionReproduccion+=3;

                                    break;
                                case 2:
                                    binding.intent2.setText("SALTAR");
                                    binding.intent2.setEnabled(false);

                                    duracionReproduccion+=4;
                                    break;
                                case 3:
                                    binding.intent3.setText("SALTAR");
                                    binding.intent3.setEnabled(false);

                                    duracionReproduccion+=5;
                                    break;
                                case 4:
                                    binding.intent4.setText("SALTAR");
                                    binding.intent4.setEnabled(false);

                                    duracionReproduccion+=6;
                                    break;
                                case 5:
                                    binding.intent5.setText("SALTAR");
                                    binding.intent5.setEnabled(false);

                                    mostrarDialogIntentos();
                                    break;
                            }

                        } catch (JSONException e) {
                            Toast.makeText(GameActivity.this, "Fallo al extraer la fase", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Runnable() {
                    @Override
                    public void run() {
                        //error
                        Toast.makeText(GameActivity.this, "Fallo al insertar la fase", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        //funcion para ver informacion del usuario
        binding.btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarVentanaEmergente(v);
            }
        });

    }



    //Metodo para guardar el resultado de la partida en curso
    private void modifPartida() {
        //Si el resultado es true gana
        if(resultPartida){
            BaseDatos.modificarPartida(GameActivity.this, usuario.getId(), partida.getNum_partida(), "Ganador",
                    new Runnable() {
                        @Override
                        public void run() {

                            /*try {
                                //JSONObject u = new JSONObject(bbdd.getString("result",""));
                                //partida = partida.converJson(u);

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }*/

                        }
                    }, new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(GameActivity.this, "Error al modificar partida", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            //si el resultado es falso pierde
            BaseDatos.modificarPartida(GameActivity.this, usuario.getId(), partida.getNum_partida(), "Perdedor",
                    new Runnable() {
                        @Override
                        public void run() {

                            /*try {
                                JSONObject u = new JSONObject(bbdd.getString("result",""));
                                partida = partida.converJson(u);

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }*/

                        }
                    }, new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(GameActivity.this, "Error al modificar partida", Toast.LENGTH_SHORT).show();
                        }
                    });
        }


    }

//Metodo para crear la partida que se va a jugar
    private void creacionPartida() {
        //creamos un objeto de partida para pasarlo por el metido de la base de datos
        Partida p = new Partida();
        p.setId_usuario(usuario.getId());
        p.setResultado("");
        BaseDatos.insertPartida(GameActivity.this, p
                ,new Runnable() {
            @Override
            public void run() {
                //success
                try {
                    //Recuperamos toda la informacion de la partida
                    JSONObject u = new JSONObject(bbdd.getString("result", ""));
                    partida = new Partida();
                    partida.converJson(u);



                } catch (JSONException e) {
                    Toast.makeText(GameActivity.this, "Fallo al extraer la partida", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Runnable() {
            @Override
            public void run() {
                //error
                Toast.makeText(GameActivity.this, "Error al crear partida.", Toast.LENGTH_LONG).show();
            }
        });


    }


//Metodo para extraer cancion random de la base de datos
    private void extraerCancion() {
        //Generamos un num random para elegir una cancion aleatoria
        Random random = new Random();
        int id_cancion = random.nextInt(54) + 1;

        BaseDatos.buscarCancion(GameActivity.this, id_cancion,
                new Runnable() {
                    @Override
                    public void run() {

                        try {
                            //convertimos los datos extraidos al objeto cancion
                            JSONObject cancionJ = new JSONObject(bbdd.getString("result",""));
                            cancion = new Cancion();
                            cancion.converJson(cancionJ);

                            cancion.conver();
                            urlCancion = cancion.getUrl();
                        } catch (JSONException e) {
                            Toast.makeText(GameActivity.this, "Fallo al obtener la url", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(GameActivity.this, "Error al extraer la canción", Toast.LENGTH_LONG).show();
                    }
                });

    }

//Metodo para recuperar la informacion del usuario de la actividad anterior
    private void recuperarUsuario() {
        bundle = this.getIntent().getExtras();
        if(bundle!= null){
            try {
                JSONObject convers = new JSONObject(bundle.getString("usuario"));
                usuario = new Usuario();
                usuario = usuario.converJson(convers);
                binding.username.setText(usuario.getUsername());
            }
            catch (JSONException e) {
                Toast.makeText(GameActivity.this, "Se ha perdido la información del usuario.", Toast.LENGTH_LONG).show();
            }


        }
    }
    //metodo para mostrar un Snackbar donde dicde que supera los intentos
    private void mostrarDialogIntentos() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¡OH NO! FALLASTE");
        builder.setMessage("Has superado el número de intentos. ¿Quieres volver a jugar otra partida?");

        // Botón de "Sí"
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Acciones a realizar si el usuario selecciona "Sí"

                binding.intent1.setText("");
                binding.intent2.setText("");
                binding.intent3.setText("");
                binding.intent4.setText("");
                binding.intent5.setText("");

                binding.intent1.setBackgroundColor(Color.WHITE);
                binding.intent2.setBackgroundColor(Color.WHITE);
                binding.intent3.setBackgroundColor(Color.WHITE);
                binding.intent4.setBackgroundColor(Color.WHITE);
                binding.intent5.setBackgroundColor(Color.WHITE);

                // Lógica para iniciar una nueva partida
                //creamos partida
                creacionPartida();
                //obtener cancion
                extraerCancion();

                dialog.dismiss();
            }
        });

        // Botón de "No"
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Acciones a realizar si el usuario selecciona "No"
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent);
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
    //Metodo para mostrar un Snackbar donde dice que ha acertado la cancion
    private void mostrarDialogAcertado() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¡ENHORABUENA! HAS ACERTADO LA CANCION");
        builder.setMessage("¿Quieres jugar otra partida?");

        // Botón de "Sí"
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Acciones a realizar si el usuario selecciona "Sí"
                binding.intent1.setText("");
                binding.intent2.setText("");
                binding.intent3.setText("");
                binding.intent4.setText("");
                binding.intent5.setText("");
                // Lógica para iniciar una nueva partida
                //creamos partida
                creacionPartida();
                //obtener cancion
                extraerCancion();
                dialog.dismiss();
            }
        });

        // Botón de "No"
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Acciones a realizar si el usuario selecciona "No"
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent);
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }



    //Metodo para mostrar la informacion del usuario y sus partidas
    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    private void mostrarVentanaEmergente(View v) {

        // Inflar el diseño de la ventana emergente
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.info_layout, null);

        nombre = popupView.findViewById(R.id.userNameTextView);
        nick = popupView.findViewById(R.id.userNickTextView);
        edad = popupView.findViewById(R.id.userAgeTextView);
        btnAtras = popupView.findViewById(R.id.btnAtras);
        // Actualizar información del usuario
        nombre.setText("Nombre: " + usuario.getNombre());
        nick.setText("Nick: " + usuario.getUsername());
        edad.setText("Edad: " + usuario.getEdad());


        ListPartida = popupView.findViewById(R.id.gameListView);
        partidas = new ArrayList<>();
        fases = new ArrayList<>();
        adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, partidas);
        // Configurar el adaptador del ListView

        ListPartida.setAdapter(adapter);

        // Crear la instancia de PopupWindow
        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        // Establecer la animación de entrada/salida
        popupWindow.setAnimationStyle(R.style.PopupAnimation);

        // Mostrar la ventana emergente en la ubicación deseada
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        obtenerPartidasJugadas();

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });



    }

    //Metodo para obtener todas las fases de cada partida de un usuario
    private void obtenerFasesPartidas(int id_partida, FasesPartidasCallback callback) {

        BaseDatos.buscarTodasFases(GameActivity.this, id_partida,
                new Runnable() {
                    @Override
                    public void run() {
                        try {


                            JSONArray u = new JSONArray(bbdd.getString("result", ""));
                            Gson gson = new Gson();

                            // Convertir el array en una lista
                            Fase[] FaseList = gson.fromJson(String.valueOf(u), Fase[].class);
                            for(Fase fase: FaseList){
                                fases.add(fase.toString());
                            }

                            callback.onFasesPartidasObtenidas(fases);

                        } catch (JSONException e) {
                            Toast.makeText(GameActivity.this, "Fallo al obtener las fases", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(GameActivity.this, "Error al extraer las fases.", Toast.LENGTH_LONG).show();
                    }
        });


    }

    //Metodo para obtener todas las partidas de un usuario
    private void obtenerPartidasJugadas() {

        BaseDatos.buscarTodasPartidas(GameActivity.this, usuario.getId(),
                new Runnable() {
                    @Override
                    public void run() {

                        try {

                            JSONArray u = new JSONArray(bbdd.getString("result",""));
                            Gson gson = new Gson();
                            Partida[] partidaArray = gson.fromJson(String.valueOf(u),Partida[].class);

                            // Convertir el array en una lista
                            for(Partida partidal : partidaArray){

                                    obtenerFasesPartidas(partidal.getId_partida(), new FasesPartidasCallback() {
                                        @Override
                                        public void onFasesPartidasObtenidas(List<String> fases) {
                                            String lista= "";
                                            for (String fase : fases){
                                                lista += fase+"\n";
                                            }

                                            partidal.setListaFases(lista);
                                            adapter.add(partidal);
                                            fases.clear();

                                        }
                                    });

                            }


                        } catch (JSONException e) {
                            Toast.makeText(GameActivity.this, "Fallo al obtener las partidas", Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Runnable() {
                    @Override
                    public void run() {
                        //error
                        Toast.makeText(GameActivity.this, "Error al extraer las partidas.", Toast.LENGTH_LONG).show();
                    }
                });


    }

}