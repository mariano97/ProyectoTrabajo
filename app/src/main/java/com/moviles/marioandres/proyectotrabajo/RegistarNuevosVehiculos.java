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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moviles.marioandres.proyectotrabajo.modelo.RegistroVehiculos;

public class RegistarNuevosVehiculos extends AppCompatActivity {

    private EditText txtPlacasdelVehiculo, txtcoddelVehiculo, txtEmpresaAdscritaVehiculo, txttipodelVehiculo, txtestadodelVehiculo,
            txtbuscarplaca;
    private Button btn_EstadoVehiculos, btn_tipodelVehiculo, btn_registrarVehiculo, btn_buscarplaca;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    RegistroVehiculos regisVehi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registar_nuevos_vehiculos);

        txtcoddelVehiculo = (EditText) findViewById(R.id.txtCodigoVehiculo);
        txtPlacasdelVehiculo = (EditText) findViewById(R.id.txtPlacaVehiculo);
        txtEmpresaAdscritaVehiculo = (EditText) findViewById(R.id.txtEmpresaAdscritaVehiculos);
        txttipodelVehiculo = (EditText) findViewById(R.id.txtTipoVehiculo);
        txtestadodelVehiculo = (EditText) findViewById(R.id.txtestadoVehiculo);
        txtbuscarplaca = (EditText) findViewById(R.id.txtplacaBuscar);

        btn_buscarplaca = (Button) findViewById(R.id.btnBuscarPlaca);
        btn_EstadoVehiculos = (Button) findViewById(R.id.btnEstadoVehiculos);
        btn_registrarVehiculo = (Button) findViewById(R.id.btnRegistrarVehiculo);
        btn_tipodelVehiculo = (Button) findViewById(R.id.btnTipoVehiculo);



        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("RegistarNuevosVehiculos", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("RegistarNuevosVehiculos", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        btn_buscarplaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference mibuscarplaca = database.getReference("vehiculos");

                mibuscarplaca.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        System.out.println("-----AQUI " + dataSnapshot.child(txtbuscarplaca.getText().toString().trim()).getChildrenCount());

                        if(dataSnapshot.hasChild(txtbuscarplaca.getText().toString().trim())){
                            dataSnapshot.hasChild(txtbuscarplaca.getText().toString().trim());

                            regisVehi = dataSnapshot.child(txtbuscarplaca.getText().toString().trim()).getValue(RegistroVehiculos.class);

                            txttipodelVehiculo.setText(regisVehi.getTipodelaAmbulancia());
                            txtestadodelVehiculo.setText(regisVehi.getEstadodelVehiculo());
                            txtEmpresaAdscritaVehiculo.setText(regisVehi.getEmpresaAdscritaVehiculo());
                            txtcoddelVehiculo.setText(regisVehi.getCodigodelaAmbulancia());
                            txtPlacasdelVehiculo.setText(regisVehi.getPlacadelvehiculo());

                        }else{

                            Toast.makeText(RegistarNuevosVehiculos.this, "no existe vehiculo", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

        btn_tipodelVehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerForContextMenu(btn_tipodelVehiculo);
                openContextMenu(view);

            }
        });

        btn_EstadoVehiculos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerForContextMenu(btn_EstadoVehiculos);
                openContextMenu(view);

            }
        });

        btn_registrarVehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("------ PLCAS    "+ txtPlacasdelVehiculo.getText().toString());
                System.out.println("-----EMPRESA "  + txtEmpresaAdscritaVehiculo.getText().toString());
                System.out.println("----- TIPO AMBULANCIA " + txttipodelVehiculo.getText().toString());
                System.out.println("----- CODIGO AMBULANCIA " + txtcoddelVehiculo.getText().toString());
                System.out.println("----- ESTADO " + txtestadodelVehiculo.getText().toString());

                if(txtcoddelVehiculo.getText().toString().trim().equals("") && txtPlacasdelVehiculo.getText().toString().trim().equals("")
                        && txttipodelVehiculo.getText().toString().trim().equals("") && txtestadodelVehiculo.getText().toString().trim().equals("")){
                    Toast.makeText(RegistarNuevosVehiculos.this, "Por Favor Llene Todos Los Campos", Toast.LENGTH_SHORT).show();
                    System.out.println("----- NO SE LLENARON LOS CAMPOS");
                }else{

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference referencevehiculos = database.getReference("vehiculos").child(txtPlacasdelVehiculo.getText().toString().trim());

                    regisVehi = new RegistroVehiculos();
                    regisVehi.setTipodelaAmbulancia(txttipodelVehiculo.getText().toString().trim());
                    regisVehi.setPlacadelvehiculo(txtPlacasdelVehiculo.getText().toString().trim());
                    regisVehi.setEstadodelVehiculo(txtestadodelVehiculo.getText().toString().trim());
                    regisVehi.setEmpresaAdscritaVehiculo(txtEmpresaAdscritaVehiculo.getText().toString().trim());
                    regisVehi.setCodigodelaAmbulancia(txtcoddelVehiculo.getText().toString().trim());

                    referencevehiculos.setValue(regisVehi);

                    txtestadodelVehiculo.setText("");
                    txttipodelVehiculo.setText("");
                    txtcoddelVehiculo.setText("");
                    txtPlacasdelVehiculo.setText("");
                    txtEmpresaAdscritaVehiculo.setText("");

                    System.out.println("----- TODO VA BIEN, APLICACION BN ----");
                    Toast.makeText(RegistarNuevosVehiculos.this, "Se realizo el Registro Existosamnet", Toast.LENGTH_SHORT).show();

                    regisVehi = null;

                }

            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menus, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menus, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menusvehiculos, menus);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item){

        switch (item.getItemId()){

            case R.id.tecnicomecanica :
                txtestadodelVehiculo.setText("Problemas de Tecnicomecanica");
                Toast.makeText(RegistarNuevosVehiculos.this, "Opcion Seleccionada", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.infraccionada :
                txtestadodelVehiculo.setText("Infraccionada");
                Toast.makeText(RegistarNuevosVehiculos.this, "Opcion Seleccionada", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.patios :
                txtestadodelVehiculo.setText("Inmovilizada");
                Toast.makeText(RegistarNuevosVehiculos.this, "Opcion Seleccionada", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.taller :
                txtestadodelVehiculo.setText("Taller");
                Toast.makeText(RegistarNuevosVehiculos.this, "Opcion Seleccionada", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.bien :
                txtestadodelVehiculo.setText("Perfesto Estado");
                Toast.makeText(RegistarNuevosVehiculos.this, "Opcion Seleccionada", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.medicalizada :
                txttipodelVehiculo.setText("Vehiculos Medicalizados");
                Toast.makeText(RegistarNuevosVehiculos.this, "Opcion Seleccionada", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.basica :
                txttipodelVehiculo.setText("Vehiculos Basicos");
                Toast.makeText(RegistarNuevosVehiculos.this, "Opcion Seleccionada", Toast.LENGTH_SHORT).show();
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
