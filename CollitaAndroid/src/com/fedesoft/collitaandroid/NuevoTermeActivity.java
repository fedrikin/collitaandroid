package com.fedesoft.collitaandroid;

import java.util.concurrent.ExecutionException;

import com.fedesoft.collitaandroid.exceptions.TermeYaExisteException;
import com.fedesoft.collitaandroid.model.Terme;

import android.os.AsyncTask;
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
	private CollitaDAOIfc collitaDAO;

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
		// collitaDAO=CollitaApplication.getInstance(getApplicationContext()).getCollitaDAO();
		collitaDAO = new CollitaDAOServidor();
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
		if (precio.equals("")) {
			Toast toast = Toast.makeText(getApplicationContext(),
					"Introduce PRECIO!", Toast.LENGTH_LONG);
			toast.show();
			return;
		}
		Terme terme = new Terme();
		terme.setNombre(nombre);
		terme.setPrecioKilo(Double.parseDouble(precio));
		GuardarTermeTask guardarTermeTask = new GuardarTermeTask();
		Integer error;
		try {
			error = guardarTermeTask.execute(terme).get();
			if (error==0){
				setResult(1);
				finish();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nuevo_terme, menu);
		return true;
	}

	private class GuardarTermeTask extends AsyncTask<Terme, Void, Integer> {

		@Override
		protected Integer doInBackground(Terme... params) {
			try {
				collitaDAO.guardarTerme(params[0]);
			} catch (TermeYaExisteException e) {
				Toast toast = Toast.makeText(getApplicationContext(),
						"Ya existe Terme con el mismo nombre",
						Toast.LENGTH_LONG);
				toast.show();
				return 1;
			}
			return 0;
		}

	}

}
