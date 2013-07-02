package com.fedesoft.collitaandroid;

import com.fedesoft.collitaandroid.exceptions.CuadrillaYaExisteException;
import com.fedesoft.collitaandroid.model.Cuadrilla;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NuevaCuadrillaActivity extends Activity {
	private EditText nombreCuadrillaEditText;
	private EditText numeroCollidorsEditText;
	private EditText telefonoEditText;
	private Button guardaButton;
	private CollitaDAOIfc collitaDAO;
			

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nueva_cuadrilla);
		nombreCuadrillaEditText=(EditText) findViewById(R.id.nombrecuadrillaeditText);
		numeroCollidorsEditText=(EditText) findViewById(R.id.numcollidorseditText);
		telefonoEditText=(EditText) findViewById(R.id.telefonocuadrillaeditText);
		guardaButton=(Button) findViewById(R.id.guardarcuadrillabutton);
		guardaButton.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
					guardarCuadrilla();			
			}
		});
		
	}
	
	
	private void guardarCuadrilla(){
		String nombre=nombreCuadrillaEditText.getText().toString();
		String numeroCollidors=numeroCollidorsEditText.getText().toString();
		String telefono=telefonoEditText.getText().toString();
		if (nombre.equals("")){
			Toast toast=Toast.makeText(getApplicationContext(), "Debe introducir un nombre", Toast.LENGTH_LONG);
			toast.show();
			return;
		}
		if (numeroCollidors.equals("")){
			Toast toast=Toast.makeText(getApplicationContext(), "Debe introducir nº cullidors", Toast.LENGTH_LONG);
			toast.show();
			return;
		}
		Cuadrilla cuadrilla=new Cuadrilla();
		cuadrilla.setNombre(nombre);		
		cuadrilla.setNumeroCollidors(Integer.parseInt(numeroCollidors));
		cuadrilla.setTelefono(telefono);
		cuadrilla.setActiva(true);
		collitaDAO=CollitaApplication.getInstance(getApplicationContext()).getCollitaDAO();
		try{
			collitaDAO.guardarCuadrilla(cuadrilla);
			setResult(1);
			finish();			
		}catch (CuadrillaYaExisteException exception){
			Toast toast=Toast.makeText(getApplicationContext(), "Ya existe cuadrilla con el mismo nombre", Toast.LENGTH_LONG);
			toast.show();
		}	
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_nueva_cuadrilla, menu);
		return true;
	}

}
