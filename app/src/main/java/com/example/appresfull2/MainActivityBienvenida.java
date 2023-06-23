package com.example.appresfull2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import webService.Asynchtask;
import webService.WebService;

public class MainActivityBienvenida extends AppCompatActivity implements Asynchtask {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle bundle= this.getIntent().getExtras();



        Map<String, String> datos = new HashMap<String, String>();
        datos.put("fuente","1");
        WebService ws= new WebService(
                "https://api.uealecpeterson.net/public/productos/search",
                //  + bundle.getString("Usr") + "&pass=" + bundle.getString("clave"),
                datos, MainActivityBienvenida.this, MainActivityBienvenida.this);
        ws.execute("POST", "Authorization","Bearer "+bundle.getString("Token"));




    }

    @Override
    public void processFinish(String result) throws JSONException {
        TextView txtSaludo= (TextView) findViewById(R.id.textBienvenida);

        String lstClientes;
        JSONObject JSONObj= new JSONObject(result);

        JSONArray jsonlista = JSONObj.getJSONArray("clientes");
        for(int i=0; i< jsonlista.length();i++){
            JSONObject cliente= jsonlista.getJSONObject(i);
            lstClientes = lstClientes + " " + i + " " +
                    cliente.getString("identificacion") +
                    " " + cliente.getString("nombre") + "\n";
        }

        txtSaludo.setText(result);



    }
}