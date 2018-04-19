package com.example.lsaal.cf;
//Pendiente: Modificar JSON para extraer todos los datos de tabla users

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Login extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    Boolean log=false;
    //Para conexion
    String ip="192.168.1.75";
    RequestQueue rq;
    JsonRequest jrq;
    //
    private EditText inicio;
    private EditText pass;
    private Button btnLogin,btnRegister;
    private Switch switchRember;
    private String ine="";

    private SharedPreferences prefs; //Persistencia de datos.
    private void ClearAllData(){
        inicio.setText("");
        pass.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prefs=getSharedPreferences("Preferences", Context.MODE_PRIVATE); //Para lectura
        rq= Volley.newRequestQueue(getApplicationContext());
        bindUI();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               launchRegister();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=inicio.getText().toString();
                String password=pass.getText().toString();
                if(isValidData(email,password)){
                    login();

                }

            }
        });
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //Conexion error
        if(log){
            Toast.makeText(this,"Historial de sesion actualizado.",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Error usuario o contraseña incorrectos",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onResponse(JSONObject response) {
        //Conexion Succesfull

            log=true;
            JSONArray jsonArray= response.optJSONArray("datos");
            JSONObject jsonObject=null;
            Toast.makeText(getApplicationContext(),"Inicio de sesión correcto",Toast.LENGTH_SHORT).show();
            try {
                jsonObject=jsonArray.getJSONObject(0);
                String ine=jsonObject.optString("clave_ine");
                this.ine=ine;
                String curp=jsonObject.optString("curp");
                String nombre=jsonObject.optString("nombre");
                String apellidos=jsonObject.optString("apellios");
                String fecha=jsonObject.optString("fecha_nac");
                String direccion=jsonObject.optString("direccion");
                String telefono=jsonObject.optString("telefono");
                String correo=jsonObject.optString("correo");
                String aceptado=jsonObject.optString("aceptado");
                historial_login();
                goToMain();
                ClearAllData();
                saveDataOnPreferences(ine,curp,nombre,apellidos,fecha,direccion,telefono,correo,aceptado);//Aquí se

            }catch (JSONException e){
                e.printStackTrace();
            }


    }
    private void login(){
        String url="http://"+ip+"/capital/login.php?correo="+inicio.getText().toString()+
                "&contraseña="+pass.getText().toString();
        jrq= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }
    private void historial_login() {
        Toast.makeText(this,"INE: "+ine, Toast.LENGTH_LONG).show();
        String deviceName = Build.MANUFACTURER + "%20" + Build.MODEL.replace(" ","%20");
        String ubicacion = "Mexico city".replace(" ","%20");
        Date date = new Date();
        DateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String f=fecha.format(date).replace(" ","%20");
        String url="http://"+ip+"/capital/register_history.php?dispositivo="+deviceName+"&fecha="+f+"&lugar="+ubicacion+"&ine="+ine;
        jrq= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);

    }
    private void bindUI(){
        inicio=(EditText) findViewById(R.id.txtUser);
        pass=(EditText)findViewById(R.id.txtPassword);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        btnRegister=(Button)findViewById(R.id.btnRegister);
        switchRember=(Switch)findViewById(R.id.switchRemember);

    }

    private boolean isValidEmail(String email){//Valida el email
        return !TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private boolean isValidPassword(String password){ //Valída la contraseña
        return password.length()>4;
    }
    private boolean isValidData(String email, String password){
        if(!isValidEmail(email)){
            Toast.makeText(getApplicationContext(),"El correo no es válido, intentelo nuevamente",Toast.LENGTH_SHORT).show();
            return false;
        }else if(!isValidPassword(password)){
            Toast.makeText(getApplicationContext(),"La contraseña debe contener más de 4 caractéres",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }
    private void goToMain(){
        //Mandar a pantalla principal, o home.
    }
    private void saveDataOnPreferences(String ine,String curp,String nombre,String apellidos,
                                       String fecha,String direccion,String telefono,String
                                               correo,String status){//Guarda los datos en el archivo Preferences
        if(switchRember.isChecked()){
            SharedPreferences.Editor editor= prefs.edit();//Para escribir
            editor.putString("ine",ine);
            editor.putString("curp",curp);
            editor.putString("nombre",nombre);
            editor.putString("apellidos",apellidos);
            editor.putString("fecha",fecha);
            editor.putString("direccion",direccion);
            editor.putString("telefono",telefono);
            editor.putString("correo",correo);
            editor.putString("status",status);
            editor.commit();//Detiene el código hasta que se guarden los datos en el archivo preferences
           // editor.apply(); //Continua la ejecución del código se guarden o no los datos
            Toast.makeText(this,"Datos guardados en preferences",Toast.LENGTH_SHORT).show();

        }
    }
    private void launchRegister(){
        //startActivity(new Intent(this,Registro.class));
        startActivity(new Intent(this,Registro.class));
    }

}
