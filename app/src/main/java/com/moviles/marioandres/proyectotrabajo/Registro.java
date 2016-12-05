package com.moviles.marioandres.proyectotrabajo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moviles.marioandres.proyectotrabajo.modelo.RegistroUsuarios;

public class Registro extends AppCompatActivity {

    private EditText txtnombre, txtapellidos, txtemail, txtconfirmaremail, txtpassword, txtcargo;
    private Button btn_limpiar, btn_registrar, btn_seleccargo;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    RegistroUsuarios user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);



        txtnombre = (EditText) findViewById(R.id.txtnombres);
        txtapellidos = (EditText) findViewById(R.id.txtapellidos);
        txtemail = (EditText) findViewById(R.id.txtemail);
        txtconfirmaremail = (EditText) findViewById(R.id.txtconfirmaremail);
        txtpassword = (EditText) findViewById(R.id.txtpassword);
        txtcargo = (EditText) findViewById(R.id.txtcargo);


        btn_registrar = (Button) findViewById(R.id.btnresgistrar);
        btn_seleccargo = (Button) findViewById(R.id.btncargo);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("Registro", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("Registro", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        btn_seleccargo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerForContextMenu(btn_seleccargo);
                openContextMenu(view);

            }
        });



        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtemail!=null){
                    System.out.println("email TENGO ALGO-------");
                }else{
                    System.out.println("email NO TENGO NADA--------");
                }

                if(txtpassword!=null){
                    System.out.println("pass TENGO ALGO-------");
                }else{
                    System.out.println(" passs NO TENGO NADA--------");
                }

               if(txtemail.getText().toString().trim().equals(txtconfirmaremail.getText().toString().trim())){

                    mAuth.createUserWithEmailAndPassword(txtemail.getText().toString().trim(), txtpassword.getText().toString().trim())
                            .addOnCompleteListener(Registro.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(!task.isSuccessful()){

                                        Toast.makeText(Registro.this, "El Registro ha Fallado: " + task.getException().getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                        System.out.println("---PROBLEMA-- " + task.getException().getMessage());

                                    }
                                    else{

                                        Toast.makeText(Registro.this, "Debe Validar el Email en su Correo", Toast.LENGTH_SHORT).show();
                                        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(Registro.this, new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if(!task.isSuccessful()){
                                                    Toast.makeText(Registro.this, "Error al Enviar Email: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                                    System.out.println("-----HA HABIDO UN PROBLEMA----- " + task.getException().getMessage());
                                                }else {
                                                    Toast.makeText(Registro.this, "Se ha Enviado el Email", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(Registro.this, MainActivity.class));

                                                }
                                            }
                                        });

                                        UserProfileChangeRequest nuevousuario = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(txtnombre.getText().toString().trim()).build();
                                        mAuth.getCurrentUser().updateProfile(nuevousuario);

                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference ReferenciaUsuarios = database.getReference("usuarios").child(mAuth.getCurrentUser().getUid());

                                       // myRef.setValue("Hello, World!");

                                        user = new RegistroUsuarios();
                                        user.setNombres(txtnombre.getText().toString().trim());
                                        user.setApellidos(txtapellidos.getText().toString().trim());
                                        user.setEmail(txtemail.getText().toString().trim());
                                        user.setCargo(txtcargo.getText().toString().trim());

                                        ReferenciaUsuarios.setValue(user);

                                        txtemail.setText("");
                                        txtcargo.setText("");
                                        txtpassword.setText("");
                                        txtconfirmaremail.setText("");
                                        txtapellidos.setText("");
                                        txtnombre.setText("");



                                    }

                                }
                            });
                }else{
                    System.out.println("------ el email no es igual");
                }


            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menus, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menus, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuscargos, menus);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item){

        switch (item.getItemId()){

            case R.id.administrador :
                txtcargo.setText("Administrador");
                Toast.makeText(Registro.this, "Opcion Seleccionada", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.ambulanciasemergencias :
                txtcargo.setText("Ambulancias Emergencia");
                Toast.makeText(Registro.this, "Opcion Seleccionada", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

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
