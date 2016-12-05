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
import com.moviles.marioandres.proyectotrabajo.modelo.RegistrarPersonal;

public class RegistrarNuevoPersonal extends AppCompatActivity {

    private EditText txtNombre, txtapellidos, txttipodocumento, txtnumerodocumento, txtnumerotelefono,
            txtespecializacion, txtcodigoAmbulancias, txtEstadoPersonal, txtcargopersonal, txtbuscardoc;
    private Button btn_tiposDocumentos, btn_especializacion, btn_ingresarelpersonal, btn_EstadosdelPersonal, btn_cargoPerfil, btn_buscarpersonal;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    RegistrarPersonal registPersonal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_nuevo_personal);

        txtNombre = (EditText) findViewById(R.id.txtNombrePersonal);
        txtapellidos = (EditText) findViewById(R.id.txtApellidosPersonal);
        txttipodocumento = (EditText) findViewById(R.id.txtTipoDocumentoPersonal);
        txtnumerodocumento = (EditText) findViewById(R.id.txtnumeroDocumentoPersonal);
        txtnumerotelefono = (EditText) findViewById(R.id.txtTelefonoPersonal);
        txtespecializacion = (EditText) findViewById(R.id.txtEspecializacionPersonal);
        txtcodigoAmbulancias = (EditText) findViewById(R.id.txtcodAmbulanciaAdscritoPersonal);
        txtEstadoPersonal = (EditText) findViewById(R.id.txtEstadodelPersonal);
        txtcargopersonal = (EditText) findViewById(R.id.txtcargoPersonal);
        txtbuscardoc = (EditText) findViewById(R.id.txtdocBuscarPersonal);

        btn_buscarpersonal = (Button) findViewById(R.id.btnBucarDocPersonal);
        btn_cargoPerfil = (Button) findViewById(R.id.btncargoPersonal);
        btn_especializacion = (Button) findViewById(R.id.btnEspecializaciondelPersonal);
        btn_ingresarelpersonal = (Button) findViewById(R.id.btnIngresarPersonal);
        btn_tiposDocumentos = (Button) findViewById(R.id.btntipodocumentoPersonal);
        btn_EstadosdelPersonal = (Button) findViewById(R.id.btnEstadosPersonal);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("RegistrarNuevoPersonal", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("RegistrarNuevoPersonal", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        btn_buscarpersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference mibuscardoc = database.getReference("personal");

                mibuscardoc.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        System.out.println("------ AQUI " + dataSnapshot.hasChild(txtbuscardoc.getText().toString().trim()));

                        if (dataSnapshot.hasChild(txtbuscardoc.getText().toString().trim())){

                            System.out.println("------ AQUI " + dataSnapshot.hasChild(txtbuscardoc.getText().toString().trim()));

                            dataSnapshot.hasChild(txtbuscardoc.getText().toString().trim());

                            registPersonal = dataSnapshot.child(txtbuscardoc.getText().toString().trim()).getValue(RegistrarPersonal.class);

                            txtNombre.setText(registPersonal.getNombre());
                            txttipodocumento.setText(registPersonal.getTipodocPersonal());
                            txtcargopersonal.setText(registPersonal.getCargo());
                            txtnumerotelefono.setText(registPersonal.getTelefono());
                            txtEstadoPersonal.setText(registPersonal.getEstadopersonal());
                            txtapellidos.setText(registPersonal.getApellidos());
                            txtcodigoAmbulancias.setText(registPersonal.getAmbulanciapersonal());
                            txtespecializacion.setText(registPersonal.getEspecializacion());
                            txtnumerodocumento.setText(registPersonal.getNumerodocPersonal());

                        }else {

                            Toast.makeText(RegistrarNuevoPersonal.this, "el personal No existe", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

        btn_especializacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerForContextMenu(btn_especializacion);
                openContextMenu(view);

            }
        });

        btn_EstadosdelPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerForContextMenu(btn_EstadosdelPersonal);
                openContextMenu(view);

            }
        });

        btn_tiposDocumentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerForContextMenu(btn_tiposDocumentos);
                openContextMenu(view);

            }
        });

        btn_cargoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerForContextMenu(btn_cargoPerfil);
                openContextMenu(view);

            }
        });

        btn_ingresarelpersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("-----NOMBRES " + txtNombre.getText().toString().trim());
                System.out.println("-----APELLIDOS " + txtapellidos.getText().toString().trim());
                System.out.println("------TIPO DOC " + txttipodocumento.getText().toString().trim());
                System.out.println("-----NUMERODOC "+ txtnumerodocumento.getText().toString());
                System.out.println("------ESPECIALIZAACION " + txtespecializacion.getText().toString());
                System.out.println("-----numero telefono" + txtnumerotelefono.getText().toString());
                System.out.println("-----  AMBULANCIA" + txtcodigoAmbulancias.getText().toString());
                System.out.println("----- ESTADO " + txtEstadoPersonal.getText().toString());

                if(txtnumerodocumento.getText().toString().trim().equals("") && txtNombre.getText().toString().trim().equals("") &&
                        txtapellidos.getText().toString().trim().equals("") && txtespecializacion.getText().toString().trim().equals("")){
                    Toast.makeText(RegistrarNuevoPersonal.this, "Profavor Llene los Campos", Toast.LENGTH_SHORT).show();
                    System.out.println("------NO SE LLENARON LOS CAMPOS ----");
                }else{

                    // Write a message to the database
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference ReferencianuevoPersonal = database.getReference("personal").child(txtnumerodocumento.getText().toString().trim());

                    registPersonal = new RegistrarPersonal();
                    registPersonal.setNombre(txtNombre.getText().toString().trim());
                    registPersonal.setApellidos(txtapellidos.getText().toString().trim());
                    registPersonal.setTipodocPersonal(txttipodocumento.getText().toString().trim());
                    registPersonal.setNumerodocPersonal(txtnumerodocumento.getText().toString().trim());
                    registPersonal.setEspecializacion(txtespecializacion.getText().toString().trim());
                    registPersonal.setTelefono(txtnumerotelefono.getText().toString().trim());
                    registPersonal.setAmbulanciapersonal(txtcodigoAmbulancias.getText().toString().trim());
                    registPersonal.setEstadopersonal(txtEstadoPersonal.getText().toString().trim());
                    registPersonal.setCargo(txtcargopersonal.getText().toString().trim());

                    ReferencianuevoPersonal.setValue(registPersonal);

                    txtNombre.setText("");
                    txtapellidos.setText("");
                    txtespecializacion.setText("");
                    txtnumerodocumento.setText("");
                    txtcodigoAmbulancias.setText("");
                    txtEstadoPersonal.setText("");
                    txtnumerotelefono.setText("");
                    txttipodocumento.setText("");
                    txtcargopersonal.setText("");

                    System.out.println("-----TODO VA BIEN, ACTIVITY INICIADA---");
                    Toast.makeText(RegistrarNuevoPersonal.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();

                    registPersonal = null;


                }

            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menus, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menus, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menunuevopersonal, menus);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item){

        switch (item.getItemId()){

            case R.id.cedula :
                txttipodocumento.setText("Cedula de Ciudadania");
                Toast.makeText(RegistrarNuevoPersonal.this, "Opcion Seleccionada", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.cedulaExtranjera :
                txttipodocumento.setText("Cedula de Extranjeria");
                Toast.makeText(RegistrarNuevoPersonal.this, "Opcion Seleccionada", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.permisotipodoc :
                txttipodocumento.setText("Permiso Trabajo");
                Toast.makeText(RegistrarNuevoPersonal.this, "Opcion Seleccionada", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.paramedico :
                txtespecializacion.setText("Paramedico");
                Toast.makeText(RegistrarNuevoPersonal.this, "Opcion Seleccionada", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.medico :
                txtespecializacion.setText("Medico");
                Toast.makeText(RegistrarNuevoPersonal.this, "Opcion Seleccionada", Toast.LENGTH_SHORT).show();
                return true;


            case R.id.despedido :
                txtEstadoPersonal.setText("Despedido");
                Toast.makeText(RegistrarNuevoPersonal.this, "Opcion Seleccionada", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.buenestado :
                txtEstadoPersonal.setText("Buen Estado");
                Toast.makeText(RegistrarNuevoPersonal.this, "Opcion Seleccionada", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.fallecido :
                txtEstadoPersonal.setText("Fallecido");
                Toast.makeText(RegistrarNuevoPersonal.this, "Opcion Seleccionada", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.administradorpersonal :
                txtcargopersonal.setText("Administrador");
                Toast.makeText(RegistrarNuevoPersonal.this, "Opcion Seleccionada", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.ambulanciasemergenciaspersonal :
                txtcargopersonal.setText("Operario Ambulancias");
                Toast.makeText(RegistrarNuevoPersonal.this, "Opcion Seleccionada", Toast.LENGTH_SHORT).show();
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
