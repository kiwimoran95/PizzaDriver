package com.example.kiwi.pizzatrack;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by kiwi_ on 19/1/2018.
 */

public class Consumir extends AsyncTask<Void, Void, String> {
    private Context httpContext;
    ProgressDialog progressDialog;
    public String resultadoapi="";
    private String linkrequestAPI="";
    public String id="";
    private String latitud="";
    private String longitud="";

    public Consumir(Context ctx, String linkAPI, String id, String  lat, String longit ){
        this.httpContext=ctx;
        this.linkrequestAPI=linkAPI;
        this.id=id;
        this.latitud=lat;
        this.longitud=longit;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        String result= null;

        String wsURL = linkrequestAPI;
        URL url = null;
        try {
            url = new URL(wsURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            //crear el objeto json para actualizar en el PUT
            JSONObject parametrosPost= new JSONObject();
            parametrosPost.put("ID",id);
            parametrosPost.put("LATITUD",latitud);
            parametrosPost.put("LONGITUD",longitud);

            //DEFINIR PARAMETROS DE CONEXION
            urlConnection.setReadTimeout(15000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("PUT");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            //OBTENER EL RESULTADO DEL REQUEST
            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(parametrosPost));
            writer.flush();
            writer.close();
            os.close();

            int responseCode=urlConnection.getResponseCode();// conexion OK?
            if(responseCode== HttpURLConnection.HTTP_OK){
                BufferedReader in= new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                StringBuffer sb= new StringBuffer("");
                String linea="";
                while ((linea=in.readLine())!= null){
                    sb.append(linea);
                    break;

                }
                in.close();
                result= sb.toString();
            }
            else{
                result= new String("Error: "+ responseCode);


            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    public String getPostDataString(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = params.keys();
        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }
}