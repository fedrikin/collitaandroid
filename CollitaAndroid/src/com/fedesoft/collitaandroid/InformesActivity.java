package com.fedesoft.collitaandroid;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import com.fedesoft.collitaandroid.model.OrdenCollita;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InformesActivity extends Activity implements OnClickListener {
	private static final String[] opciones = { "cuadrillas", "camiones",
			"comprador" };
	private static final String[] unidades = { "Kilos", "arrobas", "cajones" };
	private Button desdeButton;
	private Button hastaButton;
	private Button opcionesButton;
	private Button unidadesButton;
	private LinearLayout datosLinearLayout;
	private LinearLayout totalesLinearLayout;
	private Button totalButton;
	private TextView totalKilosTextView;
	
	private int opcionSeleccionada = 0;
	private int unidadSeleccionada = 0;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private Date desde;
	private Date hasta;
	private CollitaDAOIfc collitaDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_informes);
		desdeButton = (Button) findViewById(R.id.informesdesdebutton);
		hastaButton = (Button) findViewById(R.id.informeshastabutton);
		opcionesButton = (Button) findViewById(R.id.opcionesbutton);
		unidadesButton = (Button) findViewById(R.id.unidadesbutton);
		datosLinearLayout=(LinearLayout) findViewById(R.id.datosLinearLayout);
		totalesLinearLayout=(LinearLayout) findViewById(R.id.totalesLinearLayout);
		
		desdeButton.setOnClickListener(this);
		hastaButton.setOnClickListener(this);
		opcionesButton.setOnClickListener(this);
		unidadesButton.setOnClickListener(this);
		Calendar hoy = new GregorianCalendar();
		
		desde = hoy.getTime();
		hasta = hoy.getTime();
		desdeButton.setText(sdf.format(desde));
		hastaButton.setText(sdf.format(hasta));
		collitaDAO = CollitaApplication.getInstance(getApplicationContext())
				.getCollitaDAO();
		totalButton.setText("Total:");
		
		muestraDatos();
	}

	private void muestraDatos() {
		opcionesButton.setText(opciones[opcionSeleccionada]);
		unidadesButton.setText(unidades[unidadSeleccionada]);
		List<OrdenCollita> ordenes = collitaDAO.recuperarOrdenesCollita(desde,
				hasta);		
		System.out.println("ordenes:" + ordenes.size());
		datosLinearLayout.removeAllViews();
		switch (opcionSeleccionada) {
		case 0:
			muestraDatosCuadrillas(ordenes);
			break;
		case 1:
			muestraDatosCamiones(ordenes);
			break;
		case 2:
			muestraDatosComprador(ordenes);
			break;

		}

	}

	private void muestraDatosComprador(List<OrdenCollita> ordenes) {
		LinearLayout linearLayout=new LinearLayout(getApplicationContext());
		HashMap<String,Integer> datos=new HashMap<String, Integer>();
		Integer total=0;
		for (OrdenCollita ordenCollita : ordenes) {
			String nombreComprador=ordenCollita.getComprador().getNombre();
			Integer kilos=ordenCollita.getCajonesPrevistos()*ordenCollita.getVariedad().getKilosPorCajon();
			Integer dato=datos.get(nombreComprador);
			if (dato != null){
				datos.put(nombreComprador, dato+kilos);
			}else{
				datos.put(nombreComprador, kilos);
			}
			total+=kilos;
		}
		totalKilosTextView.setText(""+total);
		for (String comprador : datos.keySet()) {
			Button nombreButton=new Button(getApplicationContext());
			nombreButton.setText(comprador);
			TextView kilosTextView= new TextView(getApplicationContext());
			kilosTextView.setText(""+datos.get(comprador));
			TextView eurosTextView= new TextView(getApplicationContext());
			eurosTextView.setText(""+datos.get(comprador)*0.20);
			linearLayout.addView(nombreButton);
			linearLayout.addView(kilosTextView);
			linearLayout.addView(eurosTextView);
			System.out.println(comprador+":"+datos.get(comprador));
			
			datosLinearLayout.addView(linearLayout);
		}
		
		
	}

	private void muestraDatosCamiones(List<OrdenCollita> ordenes) {
		HashMap<String,Integer> datos=new HashMap<String, Integer>();
		for (OrdenCollita ordenCollita : ordenes) {
			String nombreCamion=ordenCollita.getCamion().getNombre();
			Integer kilos=ordenCollita.getCajonesPrevistos()*ordenCollita.getVariedad().getKilosPorCajon();
			Integer dato=datos.get(nombreCamion);
			if (dato != null){
				datos.put(nombreCamion, dato+kilos);
			}else{
				datos.put(nombreCamion, kilos);
			}
		}
		for (String camion : datos.keySet()) {
			System.out.println(camion+":"+datos.get(camion));
		}
	}

	private void muestraDatosCuadrillas(List<OrdenCollita> ordenes) {
		HashMap<String,Integer> datos=new HashMap<String, Integer>();
		for (OrdenCollita ordenCollita : ordenes) {
			String nombreCuadrilla=ordenCollita.getCuadrilla().getNombre();
			Integer kilos=ordenCollita.getCajonesPrevistos()*ordenCollita.getVariedad().getKilosPorCajon();
			Integer dato=datos.get(nombreCuadrilla);
			if (dato != null){
				datos.put(nombreCuadrilla, dato+kilos);
			}else{
				datos.put(nombreCuadrilla, kilos);
			}
		}
		for (String cuadrilla : datos.keySet()) {
			System.out.println(cuadrilla+":"+datos.get(cuadrilla));
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.informes, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v == opcionesButton) {
			opcionSeleccionada = (opcionSeleccionada + 1) % 3;
			muestraDatos();
		}
		if (v == unidadesButton) {
			unidadSeleccionada = (unidadSeleccionada + 1) % 3;
			muestraDatos();
		}
		if (v == desdeButton) {
			DatePickerFragment fragment = new DatePickerFragment(desdeButton,
					true);
			fragment.show(getFragmentManager(), "Seleccione fecha");
			System.out.println("cambio desde");
		}
		if (v == hastaButton) {
			DatePickerFragment fragment = new DatePickerFragment(hastaButton,
					false);
			fragment.show(getFragmentManager(), "Seleccione fecha");
			System.out.println("cambio hasta");
		}

	}

	@SuppressLint("ValidFragment")
	public class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		private Button button;
		private Calendar calendar;
		private boolean desde;

		public DatePickerFragment(Button button, boolean desde) {

			this.button = button;
			this.desde = desde;
		}

		@SuppressLint("NewApi")
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			DatePickerDialog datePickerDialog = new DatePickerDialog(
					getActivity(), this, year, month, day);
			return datePickerDialog;
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, month);
			calendar.set(Calendar.DAY_OF_MONTH, day);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.HOUR, 0);
			calendar.set(Calendar.SECOND, 0);
			button.setText(sdf.format(calendar.getTime()));
			if (desde) {
				InformesActivity.this.desde = calendar.getTime();
			} else {
				InformesActivity.this.hasta = calendar.getTime();
			}
			muestraDatos();
		}

	}
}
