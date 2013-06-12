package com.fedesoft.collitaandroid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NuevaOrdenCollitaActivity extends Activity {

	
	private EditText cuadrillaOrdenEditText;
	private EditText camionOrdenEditText;
	private EditText cajonesOrdenEditText;
	private EditText variedadOrdenEditText;
	private EditText termeOrdenEditText;
	private EditText compradorOrdenEditText;
	private EditText propietarioEditText;
	private Button guardaButton;
	private Button fechaOrdenButton;
	private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
    Date fecha=new Date();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nueva_orden_collita);
		
		cuadrillaOrdenEditText = (EditText) findViewById(R.id.cuadrillaordeneditText);
		camionOrdenEditText = (EditText) findViewById(R.id.camionordeneditText);
		variedadOrdenEditText = (EditText) findViewById(R.id.variedadordeneditText);
		termeOrdenEditText = (EditText) findViewById(R.id.termeordeneditText);
		compradorOrdenEditText = (EditText) findViewById(R.id.compradorordeneditText);
		propietarioEditText = (EditText) findViewById(R.id.propietarioordeneditText);
		cajonesOrdenEditText = (EditText) findViewById(R.id.cajonesEditText);
		fechaOrdenButton = (Button) findViewById(R.id.fechaOrdenbutton);
		guardaButton = (Button) findViewById(R.id.editarrordenbutton);
		guardaButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				guardarOrden();

			}
		});
		
		fechaOrdenButton.setText(simpleDateFormat.format(fecha));
		fechaOrdenButton.setOnClickListener(new OnClickListener() {

		
			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@Override
			public void onClick(View v) {
				DialogFragment newFragment=new DatePickerFragment(fechaOrdenButton);
				newFragment.show(getFragmentManager(), "datePicker");
                String fechacollita = fechaOrdenButton.getText().toString();
				
				
				try {
					fecha = simpleDateFormat.parse(fechacollita);
				} catch (ParseException e) {
				
					e.printStackTrace();
				}
			}

		});
	}

	protected void guardarOrden() {

		String fechacollita = fechaOrdenButton.getText().toString();		
			
			try {
				fecha = simpleDateFormat.parse(fechacollita);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

		String cuadrilla = cuadrillaOrdenEditText.getText().toString();
		String camion = camionOrdenEditText.getText().toString();
		String cajonePrevistos = cajonesOrdenEditText.getText().toString();
		String variedad = variedadOrdenEditText.getText().toString();
		String terme = termeOrdenEditText.getText().toString();
		String comprador = compradorOrdenEditText.getText().toString();
		String propietario = propietarioEditText.getText().toString();
		
		if (cajonePrevistos.equals("")){
			Toast toast= Toast.makeText(getApplicationContext(),"tindran que collira algo, NO??? POSA CAIXONS!!",Toast.LENGTH_LONG);
			toast.show();
			cajonePrevistos="0";
			return;
			}
		OrdenCollita ordenCollita = new OrdenCollita();
		ordenCollita.setCajonesPrevistos(Integer.parseInt(cajonePrevistos));
		ordenCollita.setPropietario(propietario);
		ordenCollita.setFechaCollita(fecha);
		
		/*Integer cuadrillaId=cuadrillaList.getSelecedItem();
		Cuadrilla cuadrilla=CollitaDAO.getInstance().getCuadrillaById(cuadrillaId);
		ordenCollita.setCuadrilla(cuadrilla);*/
		CollitaDAO collitaDAO = CollitaDAO.getInstance();
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

		public DatePickerFragment(Button button){
			this.button=button;
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
			button.setText(""+day+"/"+month+"/"+year);
		}
	}

}

