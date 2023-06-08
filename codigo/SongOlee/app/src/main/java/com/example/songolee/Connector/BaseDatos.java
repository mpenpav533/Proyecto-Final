package com.example.songolee.Connector;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.songolee.Model.Partida;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.entity.UrlEncodedFormEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.EntityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BaseDatos {

    public static String posturl = "http://proxmox.iesmartinezm.es:8101/ProyectoGrupal/";
    //Metodo para realizar el login
    public static void buscarUsuario(Context context, String username, Runnable success, Runnable error){
        Servidor servidorLogin = new Servidor("buscarUsuario.php", context );//Inicializa la clase servidor con el parametro del archivo que tiene que conectarse
        servidorLogin.setParams("username", username);//Parametros que va a recibir el archivo php
        servidorLogin.execute(success, error);// Lanza el hilo pasando por parametros las dos posibles acciones que puede hacer despues
    }

    //Metodo para insertar usuario
    public static void insertUsuario(Context context, String username, String nombre, String edad,String rol, Runnable success, Runnable error){

        Servidor servidorInsert = new Servidor("insertarUsuario.php", context );//Inicializa la clase servidor con el parametro del archivo que tiene que conectarse
        servidorInsert.setParams("username", username);//Parametros que va a recibir el archivo php
        servidorInsert.setParams("nombre", nombre);//Parametros que va a recibir el archivo php
        servidorInsert.setParams("edad", edad);//Parametros que va a recibir el archivo php
        servidorInsert.setParams("rol", rol);//Parametros que va a recibir el archivo php
        servidorInsert.execute(success, error);// Lanza el hilo pasando por parametros las dos posibles acciones que puede hacer despues
    }
    //Metodo para insertar partido
    public static void insertPartida(Context context, Partida partida, Runnable success, Runnable error){
        Servidor servidorInsert = new Servidor("insertarPartida.php", context );//Inicializa la clase servidor con el parametro del archivo que tiene que conectarse
        servidorInsert.setParams("id_usuario", String.valueOf(partida.getId_usuario()));//Parametros que va a recibir el archivo php
        servidorInsert.setParams("resultadoP", partida.getResultado());//Parametros que va a recibir el archivo php
        servidorInsert.execute(success, error);// Lanza el hilo pasando por parametros las dos posibles acciones que puede hacer despues
    }
    //Metodo para insertar fase
    public static void insertFase(Context context, int id_cancion , int id_partida, int puntos, Runnable success, Runnable error){
        Servidor servidorInsert = new Servidor("insertarFase.php", context );//Inicializa la clase servidor con el parametro del archivo que tiene que conectarse
        servidorInsert.setParams("id_partida", String.valueOf(id_partida));//Parametros que va a recibir el archivo php
        servidorInsert.setParams("id_cancion", String.valueOf(id_cancion));//Parametros que va a recibir el archivo php
        servidorInsert.setParams("puntos", String.valueOf(puntos));//Parametros que va a recibir el archivo php
        servidorInsert.execute(success, error);// Lanza el hilo pasando por parametros las dos posibles acciones que puede hacer despues
    }
    //Metodo para buscar la cancion que se reproducira en cada partida
    public static void buscarCancion(Context context, int id_cancion , Runnable success, Runnable error){
        Servidor servidorInsert = new Servidor("buscarCancion.php", context );//Inicializa la clase servidor con el parametro del archivo que tiene que conectarse
        servidorInsert.setParams("id_cancion", String.valueOf(id_cancion));//Parametros que va a recibir el archivo php

        servidorInsert.execute(success, error);// Lanza el hilo pasando por parametros las dos posibles acciones que puede hacer despues
    }
//Metodo para recoger todas las partidas de un usuario
    public static void buscarTodasPartidas(Context context, int id_usuario , Runnable success, Runnable error){
        Servidor servidorInsert = new Servidor("buscarTodasPartidas.php", context );//Inicializa la clase servidor con el parametro del archivo que tiene que conectarse
        servidorInsert.setParams("id_usuario", String.valueOf(id_usuario));//Parametros que va a recibir el archivo php

        servidorInsert.execute(success, error);// Lanza el hilo pasando por parametros las dos posibles acciones que puede hacer despues
    }

   //Metodo para recoger todas las canciones de la base de datos
    public static void buscarTodasCanciones (Context context, Runnable succes, Runnable error){
        Servidor servidorB = new Servidor("buscarTodasCanciones.php", context);
        servidorB.execute(succes,error);
    }
    //Metodo para recoger todas las fases de la partida
    public static void buscarTodasFases(Context context, int id_partida , Runnable success, Runnable error){
        Servidor servidorInsert = new Servidor("buscarTodasFases.php", context );//Inicializa la clase servidor con el parametro del archivo que tiene que conectarse
        servidorInsert.setParams("id_partida", String.valueOf(id_partida));//Parametros que va a recibir el archivo php

        servidorInsert.execute(success, error);// Lanza el hilo pasando por parametros las dos posibles acciones que puede hacer despues
    }
    //Metodo para modificar la partida
    public  static void modificarPartida (Context context,int id_usuario, int num_partida, String resultado, Runnable success, Runnable error){
        Servidor servidorInsert = new Servidor("modificarPartida.php", context );//Inicializa la clase servidor con el parametro del archivo que tiene que conectarse
        servidorInsert.setParams("id_usuario", String.valueOf(id_usuario));//Parametros que va a recibir el archivo php
        servidorInsert.setParams("num_partida", String.valueOf(num_partida));//Parametros que va a recibir el archivo php
        servidorInsert.setParams("resultado", resultado);//Parametros que va a recibir el archivo php
        servidorInsert.execute(success, error);// Lanza el hilo pasando por parametros las dos posibles acciones que puede hacer despues
    }



    private static class Servidor extends AsyncTask<Runnable, String, Boolean> {
        SharedPreferences bbdd;
        String url;
        List<NameValuePair> params;//parametros
        ProgressDialog d;//Objeto de cargando

        Context context; //actividad donde debe ejecutarse
        Runnable success;
        Runnable error;

        String mensajeError;//almacena el error
        String result;//almacena la informacion obtenida

//constructor de la clase
        public Servidor(String url, Context context) {
            this.url = url;
            params = new ArrayList<NameValuePair>();
            this.context = context;
            this.bbdd = context.getSharedPreferences("bbdd",Context.MODE_PRIVATE);
        }
//inicializa el progress y restablece los valores por defecto
        @Override
        protected void onPreExecute() {
            d = new ProgressDialog(context);
            d.setCancelable(false);
            d.setMessage("carcagndo...");
            d.show();
            mensajeError = "";
            result = "";
        }
        //AÑADIR PARAMETROS
        public void setParams(String name, String value) {

            this.params.add(new BasicNameValuePair(name,value));
        }

        @Override
        protected Boolean doInBackground(Runnable... runs) {
            try {
                //guardamos los runnable
                if (runs.length > 0) success = runs[0];
                if(runs.length > 1) error = runs[1];
                /*Creamos el objeto de HttpClient que nos permitira conectarnos mediante peticiones http*/
                HttpClient httpclient = new DefaultHttpClient();
                /*El objeto HttpPost permite que enviemos una peticion de tipo POST a una URL especificada*/
                HttpPost httppost = new HttpPost(posturl+url);

                /*Una vez añadidos los parametros actualizamos la entidad de httppost, esto quiere decir en pocas palabras anexamos los parametros al objeto para que al enviarse al servidor envien los datos que hemos añadido*/
                httppost.setEntity(new UrlEncodedFormEntity(params));

                /*Finalmente ejecutamos enviando la info al server*/
                HttpResponse resp = httpclient.execute(httppost);
                HttpEntity ent = resp.getEntity();
                /*y obtenemos una respuesta*/
                result = EntityUtils.toString(ent);
                //comprobamos si la respuesta es una error
                try {
                    if(result.startsWith("[")){
                        JSONArray jsonArray = new JSONArray(result);

                    }else{

                        JSONObject u = new JSONObject(result);
                        if(u.has("error")){
                            throw new Exception(u.getString("error"));
                        }

                    }

                }catch (JSONException jsonException){
                    throw new Exception("Error no controlado al convertir la respuesta");

                }
                return true;

            }
            catch(Exception e) {
                mensajeError = e.getMessage();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            d.dismiss();
            if(!aBoolean){
                if (error != null){
                    bbdd.edit().putString("error",mensajeError).apply();
                    error.run();
                }else{


                    Toast.makeText(context, "Error en consulta y perdida la referencia.", Toast.LENGTH_SHORT).show();
                }
            }else{
                if (success != null){
                    bbdd.edit().putString("result",result).apply();
                    success.run();

                }else{
                    Toast.makeText(context, "Valor devuelto pero referencia perdida.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
