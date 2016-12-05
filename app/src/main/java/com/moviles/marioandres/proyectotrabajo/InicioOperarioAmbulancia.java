package com.moviles.marioandres.proyectotrabajo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InicioOperarioAmbulancia extends AppCompatActivity {

    private Button btn_perfilo, btn_registraIncidente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_operario_ambulancia);

        btn_registraIncidente = (Button) findViewById(R.id.btnregistrarIncidentes);
        btn_perfilo = (Button) findViewById(R.id.btnPerfilOperario);

        btn_perfilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(InicioOperarioAmbulancia.this, VisualizarPerfil.class));

            }
        });

        System.out.println("------ BIEN -----");

        btn_registraIncidente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(InicioOperarioAmbulancia.this,  RegistrarIncidentes.class));

            }
        });

    }
}
