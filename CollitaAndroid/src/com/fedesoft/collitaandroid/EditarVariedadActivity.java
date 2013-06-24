package com.fedesoft.collitaandroid;

import com.fedesoft.collitaandroid.model.Variedad;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditarVariedadActivity extends Activity {
	private EditText nombreVariedadEditText;
	private EditText precioKgEditText;
	private Button   editaVariedadEditText;
    private Variedad variedad;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_variedad);
		nombreVariedadEditText=(EditText) findViewById(R.id.nombrevariedadeditText);
		precioKgEditText=(EditText) findViewById(R.id.preciovariedadeditText);
		editaVariedadEditText=(Button) findViewById(R.id.editarvariedadbutton);		
		editaVariedadEditText.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				editarVariedad();
			}
			
			
		});
		
		Integer variedadId=getIntent().getIntExtra("variedad_id",0);
		System.out.println("id:"+variedadId);
		CollitaDAOIfc collitaDAO=CollitaDAOSqlite.getInstance(getApplicationContext());
	    variedad=collitaDAO.getVariedadById(variedadId);
		nombreVariedadEditText.setText(variedad.getNombre());
		precioKgEditText.setText(""+variedad.getPrecioKilo());
					
	}
	
	private void editarVariedad() {
		String nombrevariedad=nombreVariedadEditText.getText().toString();
		if (nombrevariedad.equals("")){
			Toast toast=Toast.makeText(getApplicationContext(), "Introduce NOMBRE VARIEDAD", Toast.LENGTH_LONG);
			toast.show();
			return;
		}
		variedad.setNombre(nombrevariedad);
		String preciovariedad=precioKgEditText.getText().toString();
		if (preciovariedad.equals("")){
			Toast toast=Toast.makeText(getApplicationContext(), "Introduce UN PRECIO", Toast.LENGTH_LONG);
			toast.show();
			return;
		}
		variedad.setPrecioKilo(Double.parseDouble(preciovariedad));		
		CollitaDAOSqlite.getInstance(getApplicationContext()).actualizaVariedad(variedad);
		setResult(1);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.editar_variedad, menu);
		return true;
	}

}
