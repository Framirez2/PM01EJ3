package com.example.pm01ej3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pm01ej3.Configuracion.SQLiteConexion;
import com.example.pm01ej3.Tablas.Personas;
import com.example.pm01ej3.Tablas.Transacciones;

import java.util.ArrayList;

public class ActivityList extends AppCompatActivity {

    SQLiteConexion conexion;
    ListView listperson;
    ArrayList<Personas> lista;
    ArrayList<String> listaconcatenada;
    Spinner sppersonas;
    EditText nombres, apellidos, correo,direccion,edad,txtid;
    Button btnactualizar,btnborrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
        sppersonas = (Spinner) findViewById(R.id.sppersonas);
        txtid = (EditText) findViewById(R.id.txtid);
        nombres = (EditText) findViewById(R.id.list_txtnombres);
        apellidos = (EditText) findViewById(R.id.list_txtapellidos);
        edad = (EditText) findViewById(R.id.list_txtedad);
        correo = (EditText) findViewById(R.id.list_txtcorreo);
        direccion = (EditText) findViewById(R.id.list_txtdir);
        btnactualizar = (Button) findViewById(R.id.btnactualizar);
        btnborrar = (Button) findViewById(R.id.btnborrar);

        ObtenerPersonas();

        ArrayAdapter<CharSequence> adp = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listaconcatenada);
        sppersonas.setAdapter(adp);


        sppersonas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                try
                {
                    txtid.setText(lista.get(i).getId().toString());
                    nombres.setText(lista.get(i).getNombres());
                    apellidos.setText(lista.get(i).getApellidos());
                    edad.setText(lista.get(i).getEdad().toString());
                    correo.setText(lista.get(i).getCorreo());
                    direccion.setText(lista.get(i).getDir());

                }
                catch (Exception ex)
                {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnactualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActualizarPersona();
            }
        });

        btnborrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EliminarPersona();
            }
        });

    }

    private void ObtenerPersonas()
    {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Personas listapersonas = null;
        lista = new ArrayList<Personas>();

        Cursor cursor = db.rawQuery("SELECT * FROM "+ Transacciones.TbPersonas, null);

        while(cursor.moveToNext())
        {
            listapersonas = new Personas();
            listapersonas.setId(cursor.getInt(0));
            listapersonas.setNombres(cursor.getString(1));
            listapersonas.setApellidos(cursor.getString(2));
            listapersonas.setEdad(cursor.getInt(3));
            listapersonas.setCorreo(cursor.getString(4));
            listapersonas.setDir(cursor.getString(5));

            lista.add(listapersonas);
        }

        cursor.close();

        LLenarLista();
    }

    private void LLenarLista()
    {
        listaconcatenada = new ArrayList<String>();

        for(int i =0;  i < lista.size(); i++)
        {
            listaconcatenada.add(lista.get(i).getNombres() + " " +
                    lista.get(i).getApellidos() + " - " +
                    lista.get(i).getCorreo());
        }
    }

    private void ActualizarPersona()
    {
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Transacciones.nombres, nombres.getText().toString());
        valores.put(Transacciones.apellidos, apellidos.getText().toString());
        valores.put(Transacciones.edad, edad.getText().toString());
        valores.put(Transacciones.correo, correo.getText().toString());
        valores.put(Transacciones.dir, direccion.getText().toString());

        Long resultado  = Long.valueOf(db.update(Transacciones.TbPersonas, valores, "id = "+txtid.getText() ,null));

        Toast.makeText(getApplicationContext(), "Registro Alterado"
                , Toast.LENGTH_SHORT).show();

        db.close();

    }

    private void EliminarPersona()
    {
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        db.delete(Transacciones.TbPersonas,"id = "+txtid.getText(),null );

        Toast.makeText(getApplicationContext(), "Registro Eliminado"
                , Toast.LENGTH_SHORT).show();

        db.close();

    }

}