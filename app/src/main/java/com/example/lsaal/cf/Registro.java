package com.example.lsaal.cf;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.TextKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javax.microedition.khronos.egl.EGLDisplay;

public class Registro extends AppCompatActivity {
    private EditText edName,edApellidos,edCurp,edIne,edFecha,edDireccion,edCorreo,edTelefono,edContraseña;
    private Button btnRegistrar, btnUbicacion;
    private Register_Users registra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        //EditText
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
        btnRegistrar=(Button)findViewById(R.id.btnRegister);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registra=new Register_Users(getApplicationContext(),
                        edIne.getText().toString(), edCurp.getText().toString(),edName.getText().toString()
                        ,edApellidos.getText().toString(),edFecha.getText().toString(),edDireccion.getText().toString()
                        ,edTelefono.getText().toString(),edCorreo.getText().toString(),edContraseña.getText().toString());

            }
        });

    }
}
