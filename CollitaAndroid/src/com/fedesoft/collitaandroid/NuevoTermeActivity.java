package com.fedesoft.collitaandroid;

import com.fedesoft.collitaandroid.exceptions.TermeYaExisteException;
import com.fedesoft.collitaandroid.model.Terme;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NuevoTermeActivity extends Activity {
	private EditText nombreTermeEditText;
	private EditText precioTermeEditText;
	private Button guardaButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nuevo_terme);
		nombreTermeEditText = (EditText) findViewById(R.id.nombretermeeditText);
		precioTermeEditText = (EditText) findViewById(R.id.preciotermeeditText);
		guardaButton = (Button) findViewById(R.id.editatermebutton);
		guardaButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				guardarTerme();

			}

		});
	}

	private void guardarTerme() {
		String nombre = nombreTermeEditText.getText().toString();
		String precio = precioTermeEditText.getText().toString();
		if (nombre.equals("")) {
			Toast toast = Toast.makeText(getApplicationContext(),
					"debe introducir un nombre", Toast.LENGTH_LONG);
			toast.show();
			return;
		}
		if (precio.equals("")){
			Toast toast=Toast.makeText(getApplicationContext(),
					"Introduce PRECIO!", Toast.LENGTH_LONG);
			toast.show();
			return;
		}
		Terme terme = new Terme();
		terme.setNombre(nombre);		
		terme.setPrecioKilo(Double.parseDouble(precio));
		CollitaDAOIfc collitaDAO=CollitaDAO.getInstance();
		try {
			collitaDAO.guardarTerme(terme);
			setResult(1);
			finish();
		} catch (TermeYaExisteException e) {
			Toast toast=Toast.makeText(getApplicationContext(), "Ya existe Terme con el mismo nombre", Toast.LENGTH_LONG);
			toast.show();
			
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nuevo_terme, menu);
		return true;
	}

}
