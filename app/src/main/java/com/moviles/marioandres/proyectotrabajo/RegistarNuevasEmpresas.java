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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moviles.marioandres.proyectotrabajo.modelo.RegistrarEmpresas;

public class RegistarNuevasEmpresas extends AppCompatActivity {

    private EditText txtidentEmpresa, txtnomEmpresa, txtdireccionEmpres, txttelefonoEmpresa, txtestadoEmpresa;
    private Button btn_registrarEmpresa, btn_estadoEmpresa;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    RegistrarEmpresas regisEmpresas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registar_nuevas_empresas);

        txtdireccionEmpres = (EditText) findViewById(R.id.txtDireccionEmpresa);
        txtestadoEmpresa = (EditText) findViewById(R.id.txtEstadoEmpresa);
        txtidentEmpresa = (EditText) findViewById(R.id.txtIdentificacionEmpresa);
        txtnomEmpresa = (EditText) findViewById(R.id.txtNombreEmpresa);
        txttelefonoEmpresa = (EditText) findViewById(R.id.txtTelefonoEmpresa);

        btn_estadoEmpresa = (Button) findViewById(R.id.btnEsatadodeEmpresa);
        btn_registrarEmpresa = (Button) findViewById(R.id.btnRegistrarEmpresa);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("RegistarNuevasEmpresas", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("RegistarNuevasEmpresas", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        btn_estadoEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerForContextMenu(btn_estadoEmpresa);
                openContextMenu(view);

            }
        });

        btn_registrarEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("------ IDENTIFICACION " + txtidentEmpresa.getText().toString());
                System.out.println("-----NOMBRE "+ txtnomEmpresa.getText().toString());
                System.out.println("-----DIRECCION "+ txtdireccionEmpres.getText().toString());
                System.out.println("-----TEL" + txttelefonoEmpresa.getText().toString());
                System.out.println("-----ESTADO "+ txtestadoEmpresa.getText().toString());

                if(txtidentEmpresa.getText().toString().trim().equals("") && txtestadoEmpresa.getText().toString().trim().equals("")
                        && txtnomEmpresa.getText().toString().trim().equals("")){
                    Toast.makeText(RegistarNuevasEmpresas.this, "Por Favor Llene todos los campos", Toast.LENGTH_SHORT).show();
                    System.out.println("NO SE LLENARON LOS CAMPOS");
                }else{

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference refeEmpresas = database.getReference("EmpresasPrestadora_Vehiculos").child(txtidentEmpresa.getText().toString().trim());

                    regisEmpresas = new RegistrarEmpresas();
                    regisEmpresas.setDireccion(txtdireccionEmpres.getText().toString().trim());
                    regisEmpresas.setEstado(txtestadoEmpresa.getText().toString().trim());
                    regisEmpresas.setIdentificacion(txtidentEmpresa.getText().toString().trim());
                    regisEmpresas.setNombre(txtnomEmpresa.getText().toString().trim());
                    regisEmpresas.setTelefono(txttelefonoEmpresa.getText().toString().trim());

                    refeEmpresas.setValue(regisEmpresas);



                    txtidentEmpresa.setText("");
                    txtnomEmpresa.setText("");
                    txtestadoEmpresa.setText("");
                    txtdireccionEmpres.setText("");
                    txttelefonoEmpresa.setText("");

                    Toast.makeText(RegistarNuevasEmpresas.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                    System.out.println("----TODO VA BIEN----");

                    regisEmpresas = null;

                }

            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menus, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menus, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menunuevasempresas, menus);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item){

        switch (item.getItemId()){

            case R.id.perfecto :
                txtestadoEmpresa.setText("Bien");
                Toast.makeText(RegistarNuevasEmpresas.this, "Opcion Seleccionada", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.cerrada :
                txtestadoEmpresa.setText("Cerrada");
                Toast.makeText(RegistarNuevasEmpresas.this, "Opcion Seleccionada", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.suspendida :
                txtestadoEmpresa.setText("Funcionamiento Suspendido");
                Toast.makeText(RegistarNuevasEmpresas.this, "Opcion Seleccionada", Toast.LENGTH_SHORT).show();
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
