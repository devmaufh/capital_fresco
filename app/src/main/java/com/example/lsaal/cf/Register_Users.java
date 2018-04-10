package com.example.lsaal.cf;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by mauri on 07/04/2018.
 */

public class Register_Users implements Response.Listener<JSONObject>, Response.ErrorListener {
    String ip="192.168.1.228";
    RequestQueue rq;
    JsonRequest jrq;
    private String ine,curp,nombre,apellidos,fecha,direccion,correo,contraseña,telefono,aceptado,resul="";
    boolean existe=false;


    //Constructor de la clase, recibe los datos del user como parámetros para insertar los datos en la bd
    public Register_Users(Context c,String ine, String curp, String nombre, String apellidos, String fecha, String direccion, String telefono, String correo,
                          String contraseña)
    {

        if(ine.length()>0&&ine.length()<15&&curp.length()>0&&curp.length()<19&&nombre.length()>0&&
                apellidos.length()>0&&fecha.length()>0&&direccion.length()>0&&telefono.length()>0&&
                telefono.length()<11&&correo.length()>0&&contraseña.length()>0){
            this.ine=ine;
            this.nombre=nombre;
            this.apellidos=apellidos;
            this.fecha=fecha;
            this.direccion=direccion;
            this.telefono=telefono;
            this.correo=correo;
            this.contraseña=contraseña;
            rq= Volley.newRequestQueue(c);
            Log.d("CHECA","-----------------------------Verificando user");
            check_User();

        }else Log.d("CHECA","-----------------------------NO entra condicion");
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("CHECA","-----------------------------Usuario NO existe");
       // inserta();

    }

    @Override
    public void onResponse(JSONObject response) {
      Log.d("CHECA","----------------------Usuario si existe");
    }
    private void check_User(){
        String url="http://"+ip+"/capital/checkUser.php?ine="+ine+"&curp="+curp+"&correo="+correo;
        jrq= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }
    private void inserta(){
        Log.d("Inserta","Insertando datos... wait ;v");
        String url="http://"+ip+"/capital/register.php?ine="+ine+"&curp="+
                curp+"&nombre="+nombre+"&apellidos="+apellidos+"&fecha="+
                fecha+"&direccion="+direccion+"&telefono="+telefono+"&correo="+
                correo+"&contraseña="+contraseña;

        jrq= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
        Log.d("Registro: ","------  EXITOSO -----");
    }

}
