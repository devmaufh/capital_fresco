package com.example.lsaal.cf;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class Registro extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    private RequestQueue rq;
    private JsonRequest jrq;
    private EditText edName,edApellidos,edCurp,edIne,edFecha,edDireccion,edCorreo,edTelefono,edContraseña;
    private Button btnRegistrar, btnUbicacion;
    private String ip="192.168.43.207";
    private SharedPreferences prefs; //Persistencia de datos.
    private String ine,curp,nombre,apellidos,fecha,direccion,telefono,correo,contraseña;
    private void ClearAll(){
        edName.setText("");
        edApellidos.setText("");
        edCurp.setText("");
        edIne.setText("");
        edFecha.setText("");
        edDireccion.setText("");
        edCorreo.setText("");
        edTelefono.setText("");
        edContraseña.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        prefs=getSharedPreferences("Preferences", Context.MODE_PRIVATE); //Para lectura
        bindUI();
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carga_webService();

            }
        });
    }
    public void bindUI(){
        edName=(EditText)findViewById(R.id.edtNombre);
        edApellidos=(EditText)findViewById(R.id.edtApellidos);
        edCurp=(EditText)findViewById(R.id.edtCurp);
        edIne=(EditText)findViewById(R.id.edtINE);
        edFecha=(EditText)findViewById(R.id.edtFechaNa);
        edDireccion=(EditText)findViewById(R.id.edtUbicacion);
        edCorreo=(EditText)findViewById(R.id.edtCorreo);
        edTelefono=(EditText)findViewById(R.id.edtNumTelefonico);
        edContraseña=(EditText)findViewById(R.id.edtContraseña);
        //Botones
        btnUbicacion = findViewById(R.id.ubicacion);
        btnRegistrar=(Button)findViewById(R.id.btnEnviar_D);
        rq= Volley.newRequestQueue(getApplicationContext());
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,"Cuenta registrada correctamente",Toast.LENGTH_SHORT).show();
        goToMain();
        saveDataOnPreferences(ine,curp,nombre,apellidos,fecha,direccion,telefono,correo,"f");
        ClearAll();
    }
    @Override
    public void onResponse(JSONObject response) {
        JSONArray jsonArray= response.optJSONArray("datos");
        JSONObject jsonObject=null;
        try{
            jsonObject=jsonArray.getJSONObject(0);
            if(jsonObject.optString("clave_ine").equals("null")){
                Toast.makeText(this,"Error al conectar con servidor. Intentelo más tarde",Toast.LENGTH_SHORT);
            }else{
                Toast.makeText(this,"Error al registrar. Curp, ine o correo ya están registrados en otra cuenta.\nContactenos support@capitalfresco.com",Toast.LENGTH_SHORT).show();
            }

        }catch(Exception e){
            Toast.makeText(this,"Error 404. Reportar bug.",Toast.LENGTH_SHORT).show();
        }

    }
    public boolean isValidCurp(String curp){
        return !TextUtils.isEmpty(curp)&&curp.length()==18;
    }
    public boolean isValidIne(String ine){
        return !TextUtils.isEmpty(ine)&&ine.length()==14;
    }
    public boolean isValidFecha(String fecha){
        return !TextUtils.isEmpty(fecha);
    }
    private boolean isValidEmail(String email){//Valida el email
        return !TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private boolean isValidPassword(String password){ //Valída la contraseña
        return password.length()>4;
    }
    private boolean isValidNumber(String number){
        return !TextUtils.isEmpty(number)&&number.length()!=10;
    }
    private boolean isValidAllData(String curp, String ine,String fecha,String email,String password, String number){

        if(!isValidCurp(curp)){
            Toast.makeText(this,"La curp no es válida, debe contener 18 caracteres",Toast.LENGTH_SHORT).show();
            return false;
        }else if(!isValidIne(ine)){
            Toast.makeText(this,"EL código CIC INE no es válido, debe contener 14 caracteres",Toast.LENGTH_SHORT).show();
            return false;
        }else if(!isValidFecha(fecha)){
            Toast.makeText(this,"La fecha debe contener un formato correcto", Toast.LENGTH_LONG).show();
            return false;
        }else if(!isValidEmail(email)){
            Toast.makeText(this,"El email debe contener un formato válido\nEjemplo: sujeto@domain.com",Toast.LENGTH_SHORT).show();
            return false;
        }else if(isValidNumber(number)){
            Toast.makeText(this,"El número telefónico debe contener 10 digitos", Toast.LENGTH_LONG).show();
            return false;
        }else if(!isValidPassword(password)){
            Toast.makeText(this,"La contraseña debe contener más de 4 caracteres",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
    private void carga_webService(){
        ine=edIne.getText().toString();
        curp=edCurp.getText().toString();
        nombre=edName.getText().toString();
        apellidos=edApellidos.getText().toString();
        fecha=edFecha.getText().toString();
        direccion=edDireccion.getText().toString();
        telefono=edTelefono.getText().toString();
        correo=edCorreo.getText().toString();
        contraseña=edContraseña.getText().toString();
        if(isValidAllData(curp,ine,fecha,correo,contraseña,telefono)) {
            String url = "http://" + ip + "/capital/RegisterNewUser.php" +
                    "?ine=+" + ine + "+&curp=" + curp + "&nombre=" + nombre + "&apellidos=" + apellidos + "" +
                    "&fecha=" + fecha + "&direccion=" + direccion + "&telefono=" + telefono + "&correo=" + correo + "&contraseña=" + contraseña;

            url = url.replace(" ", "%20");
            jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            rq.add(jrq);
        }
    }
    private void saveDataOnPreferences(String ine,String curp,String nombre,String apellidos,
                                       String fecha,String direccion,String telefono,String
                                               correo,String status){//Guarda los datos en el archivo Preferences
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
    private void goToMain(){
        //Mandar a pantalla principal
    }

}
