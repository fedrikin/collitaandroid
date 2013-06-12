package com.fedesoft.collitaandroid;

import com.fedesoft.collitaandroid.model.Cuadrilla;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditarCuadrillaActivity extends Activity {
	private EditText nombreCuadrillaEditText;
	private EditText numeroCollidorsEditText;
	private EditText telefonoEditText;
	private Button editarButton;
	private Cuadrilla cuadrilla;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_cuadrilla);
		nombreCuadrillaEditText=(EditText) findViewById(R.id.nombrecuadrillaeditText);
		numeroCollidorsEditText=(EditText) findViewById(R.id.numcollidorseditText);
		telefonoEditText=(EditText) findViewById(R.id.telefonocuadrillaeditText);
		editarButton=(Button) findViewById(R.id.guardarcuadrillabutton);
		editarButton.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
					editarCuadrilla();			
			}			
			
		});
		
		Integer cuadrillaId=getIntent().getIntExtra("cuadrilla_id",0);
		System.out.println("id:"+cuadrillaId);
		CollitaDAO collitaDAO=CollitaDAO.getInstance();
		cuadrilla=collitaDAO.getCuadrillaById(cuadrillaId);
		nombreCuadrillaEditText.setText(cuadrilla.getNombre());
		numeroCollidorsEditText.setText(""+cuadrilla.getNumeroCollidors());		
		telefonoEditText.setText(cuadrilla.getTelefono());
	}
	
	private void editarCuadrilla() {
		String nombrecuadrilla=nombreCuadrillaEditText.getText().toString();
		if (nombrecuadrilla.equals("")){
			Toast toast=Toast.makeText(getApplicationContext(), "Introduce NOMBRE CUADRILLA", Toast.LENGTH_LONG);
			toast.show();
			return;
		}
		cuadrilla.setNombre(nombreCuadrillaEditText.getText().toString());
		String numerocollidors =numeroCollidorsEditText.getText().toString();
		if (numerocollidors.equals("")){
			Toast toast=Toast.makeText(getApplicationContext(), "Introduce NUMERO COLLIDORS", Toast.LENGTH_LONG);
			toast.show();
			return;
		}
		cuadrilla.setNumeroCollidors(Integer.parseInt(numeroCollidorsEditText.getText().toString()));		
		cuadrilla.setTelefono(telefonoEditText.getText().toString());
		CollitaDAO.getInstance().actualizarCuadrilla(cuadrilla);
		setResult(1);
		finish();
	}
}
