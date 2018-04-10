package com.example.lsaal.cf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class Login extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    usuario usr= new usuario();//Para salvar datos

    //Para conexion
    String ip="192.168.1.228";
    RequestQueue rq;
    JsonRequest jrq;
    //
    private EditText inicio;
    EditText pass;
    private Button btnLogin,btnRegister;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pb=(ProgressBar)findViewById(R.id.pbLoading);
        inicio = findViewById(R.id.txtUser);
        pass=findViewById(R.id.txtPassword);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        btnRegister=(Button)findViewById(R.id.btnRegister);
        rq= Volley.newRequestQueue(getApplicationContext());

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, Registro.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ProgresBar
                pb.setVisibility(View.VISIBLE);

                //
                login();

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_registro);
            }
        });
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //Conexion error
        Toast.makeText(getApplicationContext(),"Error usuario o contraseña incorrectos",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResponse(JSONObject response) {
        //Conexion Succesfull
        JSONArray jsonArray= response.optJSONArray("datos");
        JSONObject jsonObject=null;
        Toast.makeText(getApplicationContext(),"Inicio de sesión correcto",Toast.LENGTH_SHORT).show();


        try {
            jsonObject=jsonArray.getJSONObject(0);
            usr.setCorreo(jsonObject.optString("correo"));//Guarda los datos en objeto para salvar el user
            usr.setContraseña(jsonObject.optString("contraseña"));
            Toast.makeText(getApplicationContext(),"Correo: "+usr.getCorreo(),Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_registro);
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


}
