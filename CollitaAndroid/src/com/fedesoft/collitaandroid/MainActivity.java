package com.fedesoft.collitaandroid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.DatePicker;
import android.widget.TextView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;

import com.fedesoft.collitaandroid.model.OrdenCollita;

public class MainActivity extends Activity {
	private Button fechaCollitaButton;
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"dd/MM/yyyy");
	private LinearLayout ordenesLinearLayout;
	private Button agregarButton;
	Date fecha = new Date();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fechaCollitaButton = (Button) findViewById(R.id.fechacollitabutton);
		ordenesLinearLayout = (LinearLayout) findViewById(R.id.ordeneslinearlayout);
		agregarButton = (Button) findViewById(R.id.agragarordenbutton);
		agregarButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						NuevaOrdenCollitaActivity.class);
				startActivityForResult(intent, 3);

			}
		});
		
		fechaCollitaButton.setText(simpleDateFormat.format(fecha));
		fechaCollitaButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new DatePickerFragment(
						fechaCollitaButton);
				newFragment.show(getFragmentManager(), "datePicker");
			}
			
		});
		refrescarLista();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 1) {
			refrescarLista();
		}

	}

	private void refrescarLista() {
		try {
			fecha = simpleDateFormat.parse(fechaCollitaButton.getText().toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		ordenesLinearLayout.removeAllViews();
		CollitaDAOIfc collitaDAO = CollitaDAO.getInstance();
		List<OrdenCollita> ordenes = collitaDAO.recuperarOrdenesCollita(fecha);
		for (final OrdenCollita ordenCollita : ordenes) {
			Button ordenCollitaButton = new Button(getApplicationContext());
			ordenCollitaButton.setText(ordenCollita.getId() +"-"+ ordenCollita.getPropietario());
			ordenesLinearLayout.addView(ordenCollitaButton);
			ordenCollitaButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getApplicationContext(),
							EditarOrdenColiitaActivity.class);
					intent.putExtra("ordencollita_id", ordenCollita.getId());
					startActivityForResult(intent, 3);
				}
			});
		}
	}

	public class DatePickerFragment extends DialogFragment implements
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
			refrescarLista();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featuredId, MenuItem item) {
		if (item.getItemId() == R.id.camionesitem) {
			System.out.println("Selecciono camiones");
			Intent intent = new Intent(getApplicationContext(),
					CamionesActivity.class);
			startActivity(intent);
		}
		if (item.getItemId() == R.id.cuadrillasitem) {
			System.out.println("Selecciono cuadrillas");
			Intent intent = new Intent(getApplicationContext(),
					CuadrillasActivity.class);
			startActivity(intent);
		}
		if (item.getItemId() == R.id.compradoresitem) {
			System.out.println("Selecciono compradores");
			Intent intent = new Intent(getApplicationContext(),
					CompradoresActivity.class);
			startActivity(intent);
		}
		if (item.getItemId() == R.id.variedadesitem) {
			System.out.println("Selecciono Variedades");
			Intent intent = new Intent(getApplicationContext(),
					VariedadesActivity.class);
			startActivity(intent);
		}
		if (item.getItemId() == R.id.termesitem) {
			System.out.println("Selecciono Termes");
			Intent intent = new Intent(getApplicationContext(),
					TermesActivity.class);
			startActivity(intent);
		}
		return true;
	}

}
