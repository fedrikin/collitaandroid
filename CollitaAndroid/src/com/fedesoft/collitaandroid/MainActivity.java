package com.fedesoft.collitaandroid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.R.color;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable.Orientation;
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
			"d/M/yyyy");
	private LinearLayout ordenesLinearLayout;
	private Button agregarButton;
	private LinearLayout cajonesVariedadLayout;
	Date fecha = new Date();
	private String filtro=null;;
	private Button diamasButton;
	private Button diamenosButton;
	private Button informesActivityButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fechaCollitaButton = (Button) findViewById(R.id.fechacollitabutton);
		informesActivityButton =(Button) findViewById(R.id.informeButton);
		diamasButton =(Button) findViewById(R.id.fechamasbutton);
		diamenosButton = (Button) findViewById(R.id.fechamenosbutton);
		ordenesLinearLayout = (LinearLayout) findViewById(R.id.ordeneslinearlayout);
		cajonesVariedadLayout = (LinearLayout) findViewById(R.id.cajonesvariedadlinearlayout);
		agregarButton = (Button) findViewById(R.id.agragarordenbutton);
		agregarButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						NuevaOrdenCollitaActivity.class);
				intent.putExtra("ordencollita_fecha", fechaCollitaButton.getText());
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
		diamasButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					Date fechaAux = simpleDateFormat.parse(fechaCollitaButton.getText().toString());
					GregorianCalendar calendar=new GregorianCalendar();
					calendar.setTime(fechaAux);
					calendar.add(GregorianCalendar.DATE, 1);
					fechaCollitaButton.setText(simpleDateFormat.format(calendar.getTime()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				refrescarLista();
			}
		});
	
        diamenosButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					Date fechaAux = simpleDateFormat.parse(fechaCollitaButton.getText().toString());
					GregorianCalendar calendar=new GregorianCalendar();
					calendar.setTime(fechaAux);
					calendar.add(GregorianCalendar.DATE, -1);
					fechaCollitaButton.setText(simpleDateFormat.format(calendar.getTime()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				refrescarLista();
			}
		});
		
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
			LinearLayout ordenCollitalinearLayout = new LinearLayout(getApplicationContext());
			LinearLayout.LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
			LinearLayout.LayoutParams paramsL = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1);
	        
	        
			ordenCollitalinearLayout.setBackgroundResource(R.drawable.shape);		
			ordenCollitalinearLayout.setLayoutParams(paramsL);
		  
			Button cuadrillaButton = new Button(getApplicationContext());
			Button camionButton = new Button(getApplicationContext());
			TextView cajonesTextView = new TextView(getApplicationContext());
			TextView variedadTextView = new TextView(getApplicationContext());
			TextView termeTextView = new TextView(getApplicationContext());
		
			cuadrillaButton.setText("" + ordenCollita.getCuadrilla());
			cuadrillaButton.setLayoutParams(params);
			cuadrillaButton.setTextAppearance(getApplicationContext(),
					android.R.style.TextAppearance_Small);
			cuadrillaButton.setTextColor(Color.BLACK);
			Drawable cuadrillaDrawable = getResources().getDrawable(R.drawable.ic_cuadrilla2);
		    cuadrillaButton.setCompoundDrawablesWithIntrinsicBounds(cuadrillaDrawable,null, null, null);
			cuadrillaButton.setBackgroundResource(R.drawable.shapebotones);	
			camionButton.setText(""+ ordenCollita.getCamion());
			camionButton.setLayoutParams(params);		   
			camionButton.setTextAppearance(getApplicationContext(),
					android.R.style.TextAppearance_Small);
			camionButton.setTextColor(Color.BLACK);
			Drawable camionDrawable = getResources().getDrawable(R.drawable.ic_camion2);
		    camionButton.setCompoundDrawablesWithIntrinsicBounds(camionDrawable ,null, null, null);
			camionButton.setBackgroundResource(R.drawable.shapebotones);
			//...cajones
			cajonesTextView.setText(""+ ordenCollita.getCajonesPrevistos()+ ".c");
			cajonesTextView.setTextAppearance(getApplicationContext(),
					android.R.style.TextAppearance_Small);			
			cajonesTextView.setTextColor(Color.BLACK);
			
			cajonesTextView.setGravity(Gravity.RIGHT);
			cajonesTextView.setBackgroundResource(R.drawable.shapetextos);	
			
			// layout vertical 
			LinearLayout textoslayout = new LinearLayout(getApplicationContext());
			LinearLayout.LayoutParams textparam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1);
			
			textoslayout.setOrientation(LinearLayout.VERTICAL);
			variedadTextView.setText("" + ordenCollita.getVariedad()+ " ");
			variedadTextView.setTextAppearance(getApplicationContext(),
					android.R.style.TextAppearance_Small);
			variedadTextView.setTextColor(Color.BLACK);
			variedadTextView.setGravity(Gravity.CENTER);
			variedadTextView.setBackgroundResource(R.drawable.shapenaranja);
			variedadTextView.setLayoutParams(textparam);
			
			termeTextView.setText("" + ordenCollita.getTerme());
			termeTextView.setTextAppearance(getApplicationContext(),
					android.R.style.TextAppearance_Small);
			termeTextView.setTextColor(Color.BLACK);
			termeTextView.setGravity(Gravity.CENTER);
			termeTextView.setBackgroundResource(R.drawable.shapeazul);
			termeTextView.setLayoutParams(textparam);
			;
			textoslayout.addView(termeTextView);
			textoslayout.addView(variedadTextView);
			textoslayout.setLayoutParams(params);
			
			//..........
			ordenCollitalinearLayout.addView(cuadrillaButton);
			ordenCollitalinearLayout.addView(camionButton);			
			ordenCollitalinearLayout.addView(textoslayout);
			
			ordenCollitalinearLayout.addView(cajonesTextView);
			ordenesLinearLayout.addView(ordenCollitalinearLayout);
			ordenCollitalinearLayout.setOnClickListener(new OnClickListener() {

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
		LinearLayout.LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
		Button totalButton = new Button(getApplicationContext());
		totalButton.setLayoutParams(params);
		totalButton.setText("TOTAL");		
		totalButton.setTextAppearance(getApplicationContext(),
				android.R.style.TextAppearance_Small);
		totalButton.setTextColor(Color.BLACK);
		totalButton.setBackgroundResource(R.drawable.shapeazul);
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
		totalTextView.setBackgroundResource(R.drawable.cantidadestext);
		totalTextView.setTextAppearance(getApplicationContext(),
				android.R.style.TextAppearance_Medium);		
		LinearLayout variedades = new LinearLayout(getApplicationContext());
		LinearLayout cajones = new LinearLayout(getApplicationContext());		
		variedades.setBackgroundResource(R.drawable.layoutcantidades);	
		variedades.addView(totalButton);		
		cajones.setBackgroundResource(R.drawable.layoutcantidades);	
		cajones.addView(totalTextView);
		totalTextView.setTextColor(Color.BLACK);
		Integer total = 0;
		for (final String variedad : cajonesVariedad.keySet()) {
			Button button = new Button(getApplicationContext());			
			button.setLayoutParams(params);
			button.setText(variedad);		
			button.setBackgroundResource(R.drawable.shapenaranja);				
			button.setTextAppearance(getApplicationContext(),
					android.R.style.TextAppearance_Small);	
			button.setTextColor(Color.BLACK);
			variedades.addView(button);
			TextView textView = new TextView(getApplicationContext());
			textView.setLayoutParams(params);
			textView.setGravity(Gravity.RIGHT);
			textView.setBackgroundResource(R.drawable.cantidadestext);	
			textView.setTextAppearance(getApplicationContext(),
					android.R.style.TextAppearance_Medium);
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
