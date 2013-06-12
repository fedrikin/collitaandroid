package com.fedesoft.collitaandroid;

import com.fedesoft.collitaandroid.exceptions.CompradorYaExisteException;
import com.fedesoft.collitaandroid.model.Comprador;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NuevoCompradorActivity extends Activity {
	private EditText nombreCompradorEditText;	
	private EditText telefonoCompradorEditText;
	private Button guardaButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nuevo_comprador);
		nombreCompradorEditText=(EditText) findViewById(R.id.nombrecompradoreditText);
		telefonoCompradorEditText=(EditText) findViewById(R.id.telefonocompradoreditText);
	    guardaButton=(Button) findViewById(R.id.editarompradorbutton);
	    guardaButton.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
					guardarComprador();			
			}
		});
     }
     
	private void guardarComprador(){
		String nombre=nombreCompradorEditText.getText().toString();
		String telefono=telefonoCompradorEditText.getText().toString();
		if (nombre.equals("")){
			Toast toast=Toast.makeText(getApplicationContext(), "Debe introducir un nombre", Toast.LENGTH_LONG);
			toast.show();
			return;
			
		}
		Comprador comprador=new Comprador();
		comprador.setNombre(nombre);
		comprador.setTelefono(telefono);
		CollitaDAO collitaDAO=CollitaDAO.getInstance();
		try {
			collitaDAO.guardarComprador(comprador);
			setResult(1);
			finish();
		}
            catch (CompradorYaExisteException e) {
			Toast toast=Toast.makeText(getApplicationContext(), "Ya existe comprador con el mismo nombre", Toast.LENGTH_LONG);
			toast.show();
			}
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nuevo_comprador, menu);
		return true;
	}

}
