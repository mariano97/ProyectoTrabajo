package com.moviles.marioandres.proyectotrabajo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moviles.marioandres.proyectotrabajo.modelo.RegistroUsuarios;

public class InicioAdministrador extends AppCompatActivity {

    private Button regisPersonal, regisVehiculos, regisEmpresas, regisCentrosMedicos, btn_Perfil;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_administrador);

        regisPersonal = (Button) findViewById(R.id.btnPersonal);
        regisVehiculos = (Button) findViewById(R.id.btnVehiculos);
        regisEmpresas = (Button) findViewById(R.id.btnEmpresasAmbulancias);
        regisCentrosMedicos = (Button) findViewById(R.id.btncentrosMedicos);
        btn_Perfil = (Button) findViewById(R.id.btnPerfil);


        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("InicioAdministrador", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("InicioAdministrador", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        regisCentrosMedicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(InicioAdministrador.this, RegistarCentrosMedicos.class));

            }
        });

        regisPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(InicioAdministrador.this, RegistrarNuevoPersonal.class));

            }
        });

        regisVehiculos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(InicioAdministrador.this, RegistarNuevosVehiculos.class));

            }
        });

        regisEmpresas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(InicioAdministrador.this, RegistarNuevasEmpresas.class));

            }
        });

        btn_Perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(InicioAdministrador.this, VisualizarPerfil.class));

            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
