package com.moviles.marioandres.proyectotrabajo;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moviles.marioandres.proyectotrabajo.modelo.RegistroCentrosMedicos;

public class RegistarCentrosMedicos extends AppCompatActivity {

    private EditText txtnombrecentro, txtdireccion, txtnumeroidentificacioncentro,
            txtnumerotelefonocentro, txtnivelcentro, txtestadocentros, txtbuscarcentro;

    private Button btn_NivelesCentrosMedicos, btn_EstadosCentros, btn_IngresarRegistro, btn_bucarCentro;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    RegistroCentrosMedicos rcentrosMedicos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registar_centros_medicos);

        txtdireccion = (EditText) findViewById(R.id.txtDireccion);
        txtnombrecentro = (EditText) findViewById(R.id.txtNombreCentro);
        txtnumeroidentificacioncentro = (EditText) findViewById(R.id.txtNumIdentificacion);
        txtnumerotelefonocentro = (EditText) findViewById(R.id.txtTelefono);
        txtnivelcentro = (EditText) findViewById(R.id.txtNivel);
        txtestadocentros = (EditText) findViewById(R.id.txtEstadoCentroMedicos);
        txtbuscarcentro = (EditText) findViewById(R.id.txtnumeroIdentificacionCentro);

        btn_bucarCentro = (Button) findViewById(R.id.btnbuscarIdentificacionCentro);
        btn_EstadosCentros =(Button) findViewById(R.id.btnEstadoCentros);
        btn_IngresarRegistro = (Button) findViewById(R.id.btnIngresarRegistroCentroMedico);
        btn_NivelesCentrosMedicos = (Button) findViewById(R.id.btnNivelMedico);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("RegistroCentrosMediscos", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("RegistroCentrosMedicos", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        btn_NivelesCentrosMedicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerForContextMenu(btn_NivelesCentrosMedicos);
                openContextMenu(view);

            }
        });

        btn_EstadosCentros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerForContextMenu(btn_EstadosCentros);
                openContextMenu(view);

            }
        });

        btn_bucarCentro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference mibucarcentro = database.getReference("centros_medicos");

                mibucarcentro.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.hasChild(txtbuscarcentro.getText().toString().trim())){
                            System.out.println("-----GETUID " + dataSnapshot.child(txtbuscarcentro.getText().toString().trim()));
                            dataSnapshot.hasChild(txtbuscarcentro.getText().toString().trim());

                            rcentrosMedicos = dataSnapshot.child(txtbuscarcentro.getText().toString().trim()).getValue(RegistroCentrosMedicos.class);

                            txtnivelcentro.setText(rcentrosMedicos.getNiveldelcentro());
                            txtestadocentros.setText(rcentrosMedicos.getEstadocentrosmedicos());
                            txtnumerotelefonocentro.setText(rcentrosMedicos.getNumerotelefonico());
                            txtdireccion.setText(rcentrosMedicos.getDireccioncentro());
                            txtnombrecentro.setText(rcentrosMedicos.getNombrecentro());
                            txtnumeroidentificacioncentro.setText(rcentrosMedicos.getNumeroidentificacion());

                            System.out.println("----- AQUI " + dataSnapshot.child(txtbuscarcentro.getText().toString().trim()).getChildrenCount());

                        }else{
                            Toast.makeText(RegistarCentrosMedicos.this, "No existe el centro medico", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

        btn_IngresarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("----NOMBRE CENTRO " + txtnombrecentro.getText().toString().trim());
                System.out.println("--- DIRECCION CENTRO " + txtdireccion.getText().toString().trim());
                System.out.println("----- NUMERO DE IDENTIFICACION " + txtnumeroidentificacioncentro.getText().toString().trim());
                System.out.println("--- TELEFONO CENTRO " + txtnumerotelefonocentro.getText().toString().trim());
                System.out.println("----- NIVEL CENTRO " + txtnivelcentro.getText().toString().trim());
                System.out.println("----ESTADO CENTRO " + txtestadocentros.getText().toString().trim());

                if(txtnombrecentro.getText().toString().trim().equals("") && txtdireccion.getText().toString().trim().equals("") &&
                        txtnumeroidentificacioncentro.getText().toString().trim().equals("")){
                    Toast.makeText(RegistarCentrosMedicos.this, "Por Favor Llene Todos los Campos", Toast.LENGTH_SHORT).show();
                    System.out.println("---- HAY UN PROBLEMA ----");
                }else{

                    // Write a message to the database
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference referenciaCentrosMedicos = database.getReference("centros_medicos").child(txtnumeroidentificacioncentro.getText().toString().trim());

                    rcentrosMedicos = new RegistroCentrosMedicos();
                    rcentrosMedicos.setNombrecentro(txtnombrecentro.getText().toString().trim());
                    rcentrosMedicos.setDireccioncentro(txtdireccion.getText().toString().trim());
                    rcentrosMedicos.setNumeroidentificacion(txtnumeroidentificacioncentro.getText().toString().trim());
                    rcentrosMedicos.setNumerotelefonico(txtnumerotelefonocentro.getText().toString().trim());
                    rcentrosMedicos.setNiveldelcentro(txtnivelcentro.getText().toString().trim());
                    rcentrosMedicos.setEstadocentrosmedicos(txtestadocentros.getText().toString().trim());

                    referenciaCentrosMedicos.setValue(rcentrosMedicos);

                    txtdireccion.setText("");
                    txtestadocentros.setText("");
                    txtnivelcentro.setText("");
                    txtnumerotelefonocentro.setText("");
                    txtnombrecentro.setText("");
                    txtnumeroidentificacioncentro.setText("");

                    System.out.println("----- TODO VA BIEN, NUEVA ACTIVITY INICIADA----");
                    Toast.makeText(RegistarCentrosMedicos.this, "Registro del Centro Medico Exitoso", Toast.LENGTH_SHORT).show();

                    rcentrosMedicos = null;

                }



            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menus, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menus, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menucentrosmedicos, menus);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item){

        switch (item.getItemId()){

            case R.id.nivel01 :
                txtnivelcentro.setText("Nivel 1");
                Toast.makeText(RegistarCentrosMedicos.this, "Opcion Seleccionada", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.nivel02 :
                txtnivelcentro.setText("Nivel 2");
                Toast.makeText(RegistarCentrosMedicos.this, "Opcion Seleccionada", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.nivel03 :
                txtnivelcentro.setText("Nivel 3");
                Toast.makeText(RegistarCentrosMedicos.this, "Opcion Seleccionada", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.nivel04 :
                txtnivelcentro.setText("Nivel 4");
                Toast.makeText(RegistarCentrosMedicos.this, "Opcion Seleccionada", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.clausurado :
                txtestadocentros.setText("Clausurado");
                Toast.makeText(RegistarCentrosMedicos.this, "Opcion Seleccionada", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.funcionando :
                txtestadocentros.setText("Funcionando");
                Toast.makeText(RegistarCentrosMedicos.this, "Opcion Seleccionada", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.sancionado :
                txtestadocentros.setText("Sancionado");
                Toast.makeText(RegistarCentrosMedicos.this, "Opcion Seleccionada", Toast.LENGTH_SHORT).show();
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
