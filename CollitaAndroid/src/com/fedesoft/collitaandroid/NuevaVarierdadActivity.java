package com.fedesoft.collitaandroid;

import com.fedesoft.collitaandroid.exceptions.VariedadYaExisteException;
import com.fedesoft.collitaandroid.model.Variedad;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NuevaVarierdadActivity extends Activity {
	private EditText nombreVariedadEditText;
	private EditText precioVariedadEditText;
	private EditText precioCompraVariedadEditText;
	private Button   guardaButton;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nueva_varierdad);
		nombreVariedadEditText= (EditText) findViewById(R.id.nombrevariedadeditText);
		precioVariedadEditText= (EditText) findViewById(R.id.preciovariedadeditText);
		precioCompraVariedadEditText=(EditText) findViewById(R.id.preciocompravariedadedittext);
		guardaButton= (Button) findViewById(R.id.editarvariedadbutton);
	    guardaButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {			
	    		  guadarVariedad();
	    	}
	    });
	}
	
	private void guadarVariedad(){
		String nombre=nombreVariedadEditText.getText().toString();
		String precioKilo=precioVariedadEditText.getText().toString();
		String precioCompraVariedad=precioCompraVariedadEditText.getText().toString();
		if (nombre.equals("")){
			Toast toast=Toast.makeText(getApplicationContext(),"debe introducir un nombre", Toast.LENGTH_LONG);
			toast.show();
			return;
		}		
		if (precioKilo.equals("")){
			Toast toast=Toast.makeText(getApplicationContext(), "Introduce UN PRECIO!", Toast.LENGTH_LONG);
			toast.show();
			return;
		}
		if (precioCompraVariedad.equals("")){
			Toast toast=Toast.makeText(getApplicationContext(), "Introduce UN PRECIO!", Toast.LENGTH_LONG);
			toast.show();
			return;
		}
		Variedad variedad=new Variedad();
		variedad.setNombre(nombre);
		variedad.setPrecioKiloCollita(Double.parseDouble(precioKilo));
		variedad.setPrecioMedioCompra(Double.parseDouble(precioCompraVariedad));
		CollitaDAOIfc collitaDAO=CollitaApplication.getInstance(getApplicationContext()).getCollitaDAO();
		try {
			collitaDAO.guardarVariedad(variedad);
			setResult(1);
			finish();
		} catch (VariedadYaExisteException e) {
			Toast toast=Toast.makeText(getApplicationContext(), "Ya existe variedad con el mismo nombre", Toast.LENGTH_LONG);
			toast.show();
		}
	
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nueva_varierdad, menu);
		return true;
	}

}
