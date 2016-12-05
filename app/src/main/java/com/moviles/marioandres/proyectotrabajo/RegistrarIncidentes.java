package com.moviles.marioandres.proyectotrabajo;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moviles.marioandres.proyectotrabajo.modelo.RegistrarlosIncidentes;

public class RegistrarIncidentes extends AppCompatActivity {

    private EditText txtconsecutivo, txtdireccionIncidente, txtvehiculoAsignado, txtcentromedicoIncidente,
            txtdiagnosticoIncidentes, txtprocedimientoIncidente;
    private Button btn_registrarIncidente;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    RegistrarlosIncidentes regisIncidentes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_incidentes);

        txtcentromedicoIncidente = (EditText) findViewById(R.id.txtcentroMedicoAsignado);
        txtconsecutivo = (EditText) findViewById(R.id.txtConsecutivoIncidentes);
        txtdireccionIncidente = (EditText) findViewById(R.id.txtdireccionIncidente);
        txtvehiculoAsignado = (EditText) findViewById(R.id.txtplacaVehiculoIncidente);
        txtdiagnosticoIncidentes = (EditText) findViewById(R.id.txtdiagnosticoHerido);
        txtprocedimientoIncidente = (EditText) findViewById(R.id.txtprocedimientoIncidente);


        btn_registrarIncidente = (Button) findViewById(R.id.btnregistrarIncidente);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("RegistrarIncidentes", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("RegistrarIncidentes", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        btn_registrarIncidente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtconsecutivo.getText().toString().trim().equals("") && txtcentromedicoIncidente.getText().toString().trim().equals("")
                        && txtdireccionIncidente.getText().toString().trim().equals("") && txtdiagnosticoIncidentes.getText().toString().trim().equals("")){

                    Toast.makeText(RegistrarIncidentes.this, "Por favor Llene todos los campos", Toast.LENGTH_SHORT).show();
                    System.out.println("------CAMPOS VACIOS -----");

                }else{

                    // Write a message to the database
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference referenciaIncidentes = database.getReference("incidentes").child(txtconsecutivo.getText().toString().trim());

                    regisIncidentes = new RegistrarlosIncidentes();
                    regisIncidentes.setConsecutivo(txtconsecutivo.getText().toString().trim());
                    regisIncidentes.setCentromedicoasignado(txtcentromedicoIncidente.getText().toString().trim());
                    regisIncidentes.setDiagnosticoincidentes(txtdiagnosticoIncidentes.getText().toString().trim());
                    regisIncidentes.setDireccionincidente(txtdireccionIncidente.getText().toString().trim());
                    regisIncidentes.setProcedimeintoincidente(txtprocedimientoIncidente.getText().toString().trim());
                    regisIncidentes.setVehiculoasignado(txtvehiculoAsignado.getText().toString().trim());

                    referenciaIncidentes.setValue(regisIncidentes);

                    txtdiagnosticoIncidentes.setText("");
                    txtconsecutivo.setText("");
                    txtdireccionIncidente.setText("");
                    txtcentromedicoIncidente.setText("");
                    txtprocedimientoIncidente.setText("");
                    txtvehiculoAsignado.setText("");

                    System.out.println("----TODO VA BBIEN -----");
                    Toast.makeText(RegistrarIncidentes.this, "Datos Registrados", Toast.LENGTH_SHORT).show();


                }

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
