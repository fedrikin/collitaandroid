package com.fedesoft.collitaandroid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fedesoft.collitaandroid.model.Cuadrilla;
import com.fedesoft.collitaandroid.model.OrdenCollita;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NuevaOrdenCollitaActivity extends Activity implements OnClickListener {

	private AutoCompleteTextView cuadrillaOrdenAutoComplete;
	private EditText camionOrdenEditText;
	private EditText cajonesOrdenEditText;
	private EditText variedadOrdenEditText;
	private EditText termeOrdenEditText;
	private EditText compradorOrdenEditText;
	private EditText propietarioEditText;
	private Button seleccionCuadrillaButton;
	private Button guardaButton;
	private Button fechaOrdenButton;
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"dd/MM/yyyy");
	Date fecha = new Date();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nueva_orden_collita);

		cuadrillaOrdenAutoComplete = (AutoCompleteTextView) findViewById(R.id.cuadrillaordeneditText);
		camionOrdenEditText = (EditText) findViewById(R.id.camionordeneditText);
		variedadOrdenEditText = (EditText) findViewById(R.id.variedadordeneditText);
		termeOrdenEditText = (EditText) findViewById(R.id.termeordeneditText);
		compradorOrdenEditText = (EditText) findViewById(R.id.compradorordeneditText);
		propietarioEditText = (EditText) findViewById(R.id.propietarioordeneditText);
		cajonesOrdenEditText = (EditText) findViewById(R.id.cajonesEditText);
		fechaOrdenButton = (Button) findViewById(R.id.fechaOrdenbutton);
		seleccionCuadrillaButton = (Button) findViewById(R.id.elijecuadrillaButton);
		// La propia activitat fa les funcions de onclicklistener
		seleccionCuadrillaButton.setOnClickListener(this);
		
		
		guardaButton = (Button) findViewById(R.id.editarrordenbutton);
		guardaButton.setOnClickListener(this);
		fechaOrdenButton.setText(simpleDateFormat.format(fecha));
		fechaOrdenButton.setOnClickListener(new OnClickListener() {
			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new DatePickerFragment(
						fechaOrdenButton);
				newFragment.show(getFragmentManager(), "datePicker");
			}
		});
		cargarCuadrillas();
		cuadrillaOrdenAutoComplete.setThreshold(1);
	}

	private void cargarCuadrillas() {
		List<Cuadrilla> cuadrillas = CollitaDAO.getInstance().recuperarCuadrillas();
		ArrayAdapter<Cuadrilla> adapter=new ArrayAdapter<Cuadrilla>(this,android.R.layout.simple_dropdown_item_1line,cuadrillas.toArray(new Cuadrilla[]{}));
		cuadrillaOrdenAutoComplete.setAdapter(adapter);
	}

	protected void guardarOrden() {
		String fechacollita = fechaOrdenButton.getText().toString();
		try {
			fecha = simpleDateFormat.parse(fechacollita);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		
		String cuadrilla = cuadrillaOrdenAutoComplete.getText().toString();
		String camion = camionOrdenEditText.getText().toString();
		String cajonePrevistos = cajonesOrdenEditText.getText().toString();
		String variedad = variedadOrdenEditText.getText().toString();
		String terme = termeOrdenEditText.getText().toString();
		String comprador = compradorOrdenEditText.getText().toString();
		String propietario = propietarioEditText.getText().toString();
		if (cajonePrevistos.equals("")) {
			Toast toast = Toast.makeText(getApplicationContext(),
					"tindran que collira algo, NO??? POSA CAIXONS!!",
					Toast.LENGTH_LONG);
			toast.show();
			cajonePrevistos = "0";
			return;
		}
		OrdenCollita ordenCollita = new OrdenCollita();
		ordenCollita.setCajonesPrevistos(Integer.parseInt(cajonePrevistos));
		ordenCollita.setPropietario(propietario);
		ordenCollita.setFechaCollita(fecha);
		ordenCollita.setCuadrilla(CollitaDAO.getInstance().buscarCuadrillaPorNombre(cuadrilla));

		/*
		 * Integer cuadrillaId=cuadrillaList.getSelecedItem(); Cuadrilla
		 * cuadrilla=CollitaDAO.getInstance().getCuadrillaById(cuadrillaId);
		 * ordenCollita.setCuadrilla(cuadrilla);
		 */
		CollitaDAOIfc collitaDAO = CollitaDAO.getInstance();
		collitaDAO.guardarOrdenCollita(ordenCollita);
		setResult(1);
		finish();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nueva_orden_collita, menu);
		return true;
	}

	@SuppressLint("ValidFragment")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		private Button button;

		public DatePickerFragment(Button button) {
			this.button = button;
		}

		@SuppressLint("NewApi")
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			// Create a new instance of DatePickerDialog and return it

			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			button.setText("" + day + "/" + (month + 1) + "/" + year);
		}
	}

	@Override
	public void onClick(View v) {
		if (v == guardaButton){
			this.guardarOrden();
		}
		if (v == seleccionCuadrillaButton){
			System.out.println("cuadrillas");
			cuadrillaOrdenAutoComplete.showDropDown();
			
		}
		
	}

}
