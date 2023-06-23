package com.example.appresfull2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import webService.Asynchtask;
import webService.WebService;

public class MainActivity extends AppCompatActivity implements Asynchtask {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //crear un metodo para limpiar
    public void limpiar(View view )
    {
        //sacar la referencia de los controles
        EditText usuario=findViewById(R.id.txtNombre);
        EditText contraseña=findViewById(R.id.TextPassword);
        usuario.setText("");
        contraseña.setText("");
    }
    public void ingresar( View view)
    {
        EditText Usuario=findViewById(R.id.txtNombre);
        EditText Contraseña=findViewById(R.id.TextPassword);
        String usuario;
        String pass;
        usuario=Usuario.getText().toString();
        pass=Contraseña.getText().toString();
       /* Bundle b = new Bundle();
        b.putString("USUSARIO", usuario);
        b.putString("PASSWORD", pass);
        Intent intent =new Intent(MainActivity.this, MainActivityBienvenida.class);
        intent.putExtras(b);
        startActivity(intent);*/

        Map<String, String> datos = new HashMap<String, String>();
        datos.put("correo",usuario);
        datos.put("clave",pass);
        WebService ws= new WebService(
                "https://api.uealecpeterson.net/public/login",
                      //  + bundle.getString("Usr") + "&pass=" + bundle.getString("clave"),
                datos, MainActivity.this, MainActivity.this);
        ws.execute("POST");

    }

    @Override
    public void processFinish(String result) throws JSONException {
        TextView txtRes= findViewById(R.id.textRespuesta);
        JSONObject jsonResp = new JSONObject(result);
        if (jsonResp.has( "error"))
        {
            txtRes.setText("Rep:"+jsonResp.getString("error"));
        }else {
            txtRes.setText("Token:"+jsonResp.getString("access_token"));
            Bundle b = new Bundle();
        b.putString("Token", jsonResp.getString("access_token"));
        Intent intent =new Intent(MainActivity.this, MainActivityBienvenida.class);
        intent.putExtras(b);
        startActivity(intent);

        }
    }
}