package com.fedesoft.collitaandroid;

import com.fedesoft.collitaandroid.model.Cuadrilla;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class EditarCuadrillaActivity extends Activity {
	private EditText nombreCuadrillaEditText;
	private EditText numeroCollidorsEditText;
	private EditText telefonoEditText;
	private Button editarButton;
	private CheckBox activaCheckBox;
	private Cuadrilla cuadrilla;
	private CollitaDAOIfc collitaDAO;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_cuadrilla);
		nombreCuadrillaEditText=(EditText) findViewById(R.id.nombrecuadrillaeditText);
		numeroCollidorsEditText=(EditText) findViewById(R.id.numcollidorseditText);
		telefonoEditText=(EditText) findViewById(R.id.telefonocuadrillaeditText);
		activaCheckBox=(CheckBox) findViewById(R.id.cuadrillaactivacheckBox);
		editarButton=(Button) findViewById(R.id.guardarcuadrillabutton);
		editarButton.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
					editarCuadrilla();			
			}			
			
		});
	
		Integer cuadrillaId=getIntent().getIntExtra("cuadrilla_id",0);
		collitaDAO=CollitaApplication.getInstance(getApplicationContext()).getCollitaDAO();
		cuadrilla=collitaDAO.getCuadrillaById(cuadrillaId);
		nombreCuadrillaEditText.setText(cuadrilla.getNombre());
		numeroCollidorsEditText.setText(""+cuadrilla.getNumeroCollidors());		
		telefonoEditText.setText(cuadrilla.getTelefono());		
		activaCheckBox.setChecked(cuadrilla.isActiva());		
	}
	
	protected void eliminarCuadrilla() {
	/*	Builder builder = new AlertDialog.Builder(EditarCuadrillaActivity.this);
		builder.setMessage("Va a eliminar la cuadrilla. ¿Está seguro?");
		builder.setPositiveButton("Sí",new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Integer cuadrillaId=cuadrilla.getId();
				collitaDAO.eliminarCuadrilla(cuadrillaId);
				dialog.dismiss();
				finish();				
			}
		} );
		builder.setNegativeButton("No",new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();				
			}
		} );
		AlertDialog dialog = builder.create();
		dialog.show();*/
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
		cuadrilla.setActiva(activaCheckBox.isChecked());
		collitaDAO.actualizarCuadrilla(cuadrilla);
		setResult(1);
		finish();
	}
}
