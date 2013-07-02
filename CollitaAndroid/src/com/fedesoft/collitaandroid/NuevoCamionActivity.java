package com.fedesoft.collitaandroid;

import com.fedesoft.collitaandroid.exceptions.CamionYaExisteException;
import com.fedesoft.collitaandroid.model.Camion;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NuevoCamionActivity extends Activity {
	private EditText nombreCamionEditText;
	private EditText ConductorEditText;
	private EditText numeroCajonesEditText;
	private EditText telefonoConductorEditText;
	private Button guardaButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nuevo_camion);
		nombreCamionEditText = (EditText) findViewById(R.id.nombrecamioneditText);
		ConductorEditText = (EditText) findViewById(R.id.nombreconductoreditText);
		numeroCajonesEditText = (EditText) findViewById(R.id.numerocajoneseditText);
		telefonoConductorEditText = (EditText) findViewById(R.id.telefonoconductoreditText);
		guardaButton = (Button) findViewById(R.id.guardarnuevocamionbutton);
		guardaButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				guardarCamion();

			}
		});
	}

	private void guardarCamion() {
		String nombre = nombreCamionEditText.getText().toString();
		String conductor = ConductorEditText.getText().toString();
		String cajonesmaximo = numeroCajonesEditText.getText().toString();
		String telefono = telefonoConductorEditText.getText().toString();
		if (nombre.equals("")) {
			Toast toast = Toast.makeText(getApplicationContext(),
					"Debe introducir un nombre", Toast.LENGTH_LONG);
			toast.show();
			return;
		}
		if (cajonesmaximo.equals("")) {
			Toast toast = Toast.makeText(getApplicationContext(),
					"Debe introducir Cajones Maximo", Toast.LENGTH_LONG);
			toast.show();
			return;
		}
		Camion camion = new Camion();
		camion.setNombre(nombre);
		camion.setConductor(conductor);
		camion.setCajonesMaximo(Integer.parseInt(cajonesmaximo));
		camion.setTelefono(telefono);
		camion.setActivo(true);
		CollitaDAOIfc collitaDAO = CollitaApplication.getInstance(getApplicationContext()).getCollitaDAO();
		try {
			collitaDAO.guardarCamion(camion);
			setResult(1);
			finish();
		} catch (CamionYaExisteException exception) {
			Toast toast = Toast.makeText(getApplicationContext(),
					"Ya existe camion con el mismo nombre", Toast.LENGTH_LONG);
			toast.show();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nuevo_camion, menu);
		return true;
	}

}
