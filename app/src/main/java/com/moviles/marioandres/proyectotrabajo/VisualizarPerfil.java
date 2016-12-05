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

public class VisualizarPerfil extends AppCompatActivity {

    private EditText txtnombrePerfil, txtapellidosPerfil, txtemailPerfil, txtcargoPerfil;

    private Button btn_modificarperfil, btn_consultaperfil;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    RegistroUsuarios perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizar_perfil);

        txtnombrePerfil = (EditText) findViewById(R.id.txtNombrePerfil);
        txtapellidosPerfil = (EditText) findViewById(R.id.txtApellidosPerfil);
        txtemailPerfil = (EditText) findViewById(R.id.txtEmailPerfil);
        txtcargoPerfil = (EditText) findViewById(R.id.txtCargoPerfil);

        btn_consultaperfil = (Button) findViewById(R.id.btnConsultarPerfil);
        btn_modificarperfil = (Button) findViewById(R.id.btnModificarPerfil);

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

        btn_consultaperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference referenPerfil = database.getReference("usuarios");

                referenPerfil.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        System.out.println("-----GETUID " + dataSnapshot.child(mAuth.getCurrentUser().getUid()));

                        if(dataSnapshot.hasChild(mAuth.getCurrentUser().getUid())){

                            dataSnapshot.child(mAuth.getCurrentUser().getUid());
                            System.out.println("----PERFIL SI EXISTE ---");

                            perfil = dataSnapshot.child(mAuth.getCurrentUser().getUid()).getValue(RegistroUsuarios.class);

                            txtnombrePerfil.setText(perfil.getNombres());
                            txtapellidosPerfil.setText(perfil.getApellidos());
                            txtcargoPerfil.setText(perfil.getCargo());
                            txtemailPerfil.setText(perfil.getEmail());

                            System.out.println("------ DATOS " + dataSnapshot.child(mAuth.getCurrentUser().getUid()).getChildrenCount() +
                                    "  -  " + dataSnapshot.child(mAuth.getCurrentUser().getUid()).exists());



                        }else {
                            Toast.makeText(VisualizarPerfil.this, "HA HABIDO UN PROBLEMA", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        btn_modificarperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference Referenciaperfil = database.getReference("usuarios").child(mAuth.getCurrentUser().getUid());

                // myRef.setValue("Hello, World!");
                if(txtnombrePerfil.getText().toString().trim().equals("") && txtemailPerfil.getText().toString().trim().equals("")
                        && txtemailPerfil.getText().toString().trim().equals("")){
                    Toast.makeText(VisualizarPerfil.this, "Lene los campos", Toast.LENGTH_SHORT).show();
                    System.out.println(" ----------CAPOS VACIOS");
                }else{


                    perfil = new RegistroUsuarios();
                    perfil.setNombres(txtnombrePerfil.getText().toString().trim());
                    perfil.setApellidos(txtapellidosPerfil.getText().toString().trim());
                    perfil.setEmail(txtemailPerfil.getText().toString().trim());
                    perfil.setCargo(txtcargoPerfil.getText().toString().trim());

                    Referenciaperfil.setValue(perfil);

                    Toast.makeText(VisualizarPerfil.this, "Cambio exitoso", Toast.LENGTH_LONG).show();

                    txtnombrePerfil.setText("");
                    txtapellidosPerfil.setText("");
                    txtemailPerfil.setText("");
                    txtcargoPerfil.setText("");

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
