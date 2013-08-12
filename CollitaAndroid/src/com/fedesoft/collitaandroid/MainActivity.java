package com.fedesoft.collitaandroid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.DatePicker;
import android.widget.LinearLayout.LayoutParams;
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
	private LinearLayout cajonesVariedadLayout;
	Date fecha = new Date();
	private String filtro=null;;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fechaCollitaButton = (Button) findViewById(R.id.fechacollitabutton);
		ordenesLinearLayout = (LinearLayout) findViewById(R.id.ordeneslinearlayout);
		cajonesVariedadLayout = (LinearLayout) findViewById(R.id.cajonesvariedadlinearlayout);
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
			fecha = simpleDateFormat.parse(fechaCollitaButton.getText()
					.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		ordenesLinearLayout.removeAllViews();
		CollitaDAOIfc collitaDAO = CollitaApplication.getInstance(
				getApplicationContext()).getCollitaDAO();
		List<OrdenCollita> ordenes = collitaDAO.recuperarOrdenesCollita(fecha);
		HashMap<String, Integer> cajonesVariedad = new HashMap<String, Integer>();
		for (final OrdenCollita ordenCollita : ordenes) {
			String variedad = ordenCollita.getVariedad().getNombre();
			if (cajonesVariedad.get(variedad) == null) {
				cajonesVariedad.put(variedad,
						ordenCollita.getCajonesPrevistos());
			} else {
				Integer cajones = cajonesVariedad.get(variedad);
				cajonesVariedad.put(variedad,
						cajones + ordenCollita.getCajonesPrevistos());
			}
			if(filtro !=null && !ordenCollita.getVariedad().getNombre().equals(filtro)){			
				continue;
			}
			Button ordenCollitaButton = new Button(getApplicationContext());
			ordenCollitaButton.setText(ordenCollita.getId() + "-"
					+ ordenCollita.getPropietario());
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
		muestraTablaTotales(cajonesVariedad);
	}

	private void muestraTablaTotales(HashMap<String, Integer> cajonesVariedad) {
		cajonesVariedadLayout.removeAllViews();
		LinearLayout.LayoutParams params = new LayoutParams(50, 100, 1);
		Button totalButton = new Button(getApplicationContext());
		totalButton.setLayoutParams(params);
		totalButton.setText("Total");
		totalButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				filtrarVariedad("Total");
			}
		});
		TextView totalTextView = new TextView(getApplicationContext());
		totalTextView.setLayoutParams(params);
		totalTextView.setTextColor(Color.BLACK);
		totalTextView.setGravity(Gravity.CENTER_HORIZONTAL);
		totalTextView.setTextAppearance(getApplicationContext(),
				android.R.style.TextAppearance_Large);
		
		LinearLayout variedades = new LinearLayout(getApplicationContext());
		LinearLayout cajones = new LinearLayout(getApplicationContext());
		variedades.addView(totalButton);
		cajones.addView(totalTextView);
		Integer total = 0;
		for (final String variedad : cajonesVariedad.keySet()) {
			Button button = new Button(getApplicationContext());			
			button.setLayoutParams(params);
			button.setText(variedad);
			variedades.addView(button);
			button.setLayoutParams(params);
			TextView textView = new TextView(getApplicationContext());
			textView.setLayoutParams(params);
			textView.setGravity(Gravity.CENTER_HORIZONTAL);
			textView.setTextAppearance(getApplicationContext(),
					android.R.style.TextAppearance_Large);
			textView.setText("" + cajonesVariedad.get(variedad));
			textView.setTextColor(Color.BLACK);
			cajones.addView(textView);
			total += cajonesVariedad.get(variedad);
			button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					filtrarVariedad(variedad);
				}
			});
		}
		totalTextView.setText("" + total);
		cajonesVariedadLayout.addView(variedades);
		cajonesVariedadLayout.addView(cajones);

	}

	protected void filtrarVariedad(String variedad) {
		if (variedad == "Total") {
			filtro = null;
		} else {
			filtro = variedad;
		}
		refrescarLista();

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
			Intent intent = new Intent(getApplicationContext(),
					CamionesActivity.class);
			startActivity(intent);
		}
		if (item.getItemId() == R.id.cuadrillasitem) {
			
			Intent intent = new Intent(getApplicationContext(),
					CuadrillasActivity.class);
			startActivity(intent);
		}
		if (item.getItemId() == R.id.compradoresitem) {
			
			Intent intent = new Intent(getApplicationContext(),
					CompradoresActivity.class);
			startActivity(intent);
		}
		if (item.getItemId() == R.id.variedadesitem) {
			
			Intent intent = new Intent(getApplicationContext(),
					VariedadesActivity.class);
			startActivity(intent);
		}
		if (item.getItemId() == R.id.termesitem) {
			
			Intent intent = new Intent(getApplicationContext(),
					TermesActivity.class);
			startActivity(intent);
		}
		if (item.getItemId() == R.id.informesitem){
			Intent intent = new Intent(getApplicationContext(),
					InformesActivity.class);
			startActivity(intent);
			
		}
		return true;
	}

}
