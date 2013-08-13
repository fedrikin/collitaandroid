package com.fedesoft.collitaandroid;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import android.widget.LinearLayout.LayoutParams;

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
	private LinearLayout totalVariedadLinearLayout;
	private Button totalButton;
	private TextView totalCantidadTextView;
	private TextView totalEurosTextView;
	private int opcionSeleccionada = 0;
	private int unidadSeleccionada = 0;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private Date desde;
	private Date hasta;
	private CollitaDAOIfc collitaDAO;
	private DecimalFormat df = new DecimalFormat("#.00");
	private List<OrdenCollita> ordenes;
	private HashMap<String, List<OrdenCollita>> ordenesCuadrillas = new HashMap<String, List<OrdenCollita>>();
	private HashMap<String, List<OrdenCollita>> ordenesCompradores = new HashMap<String, List<OrdenCollita>>();
	private HashMap<String, List<OrdenCollita>> ordenesCamiones = new HashMap<String, List<OrdenCollita>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_informes);
		desdeButton = (Button) findViewById(R.id.informesdesdebutton);
		hastaButton = (Button) findViewById(R.id.informeshastabutton);
		opcionesButton = (Button) findViewById(R.id.opcionesbutton);
		unidadesButton = (Button) findViewById(R.id.unidadesbutton);
		datosLinearLayout = (LinearLayout) findViewById(R.id.datosLinearLayout);
		totalesLinearLayout = (LinearLayout) findViewById(R.id.totalesLinearLayout);
		totalVariedadLinearLayout = (LinearLayout) findViewById(R.id.totalVariedadLinearLayout);
		totalButton = new Button(getApplicationContext());
		LinearLayout.LayoutParams params = new LayoutParams(50, 100, 1);
		totalButton.setLayoutParams(params);
		totalButton.setText("Total:");
		totalCantidadTextView = new TextView(getApplicationContext());
		totalCantidadTextView.setLayoutParams(params);
		totalEurosTextView = new TextView(getApplicationContext());
		totalEurosTextView.setLayoutParams(params);
		totalVariedadLinearLayout.addView(totalButton);
		totalVariedadLinearLayout.addView(totalCantidadTextView);
		totalVariedadLinearLayout.addView(totalEurosTextView);
		totalButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				muestraTotalesVariedad();
			}
		});
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
		muestraDatos();
	}

	protected void muestraTotalesVariedad() {
		if (totalesLinearLayout.getChildCount() == 2) {
			totalesLinearLayout.removeViewAt(1);
		} else {
			totalesLinearLayout.addView(creaLayoutVariedades(ordenes));
		}

	}

	private void muestraDatos() {
		opcionesButton.setText(opciones[opcionSeleccionada]);
		unidadesButton.setText(unidades[unidadSeleccionada]);
		ordenes = collitaDAO.recuperarOrdenesCollita(desde, hasta);
		System.out.println("ordenes:" + ordenes.size());
		datosLinearLayout.removeAllViews();
		ordenesCuadrillas = new HashMap<String, List<OrdenCollita>>();
		ordenesCompradores = new HashMap<String, List<OrdenCollita>>();
		ordenesCamiones = new HashMap<String, List<OrdenCollita>>();
		Integer total = 0;
		Double totalEuros=0d;
		
		for (OrdenCollita ordenCollita : ordenes) {
			String nombreComprador = ordenCollita.getComprador().getNombre();
			Integer kilos=ordenCollita.getCajonesPrevistos()
					* ordenCollita.getVariedad().getKilosPorCajon();
			if (ordenesCompradores.get(nombreComprador) == null) {
				List<OrdenCollita> ordenesCollita = new ArrayList<OrdenCollita>();
				ordenesCollita.add(ordenCollita);
				ordenesCompradores.put(nombreComprador, ordenesCollita);
			} else {
				ordenesCompradores.get(nombreComprador).add(ordenCollita);
			}
			String nombreCuadrilla = ordenCollita.getCuadrilla().getNombre();
			if (ordenesCuadrillas.get(nombreCuadrilla) == null) {
				List<OrdenCollita> ordenesCollita = new ArrayList<OrdenCollita>();
				ordenesCollita.add(ordenCollita);
				ordenesCuadrillas.put(nombreCuadrilla, ordenesCollita);
			} else {
				ordenesCuadrillas.get(nombreCuadrilla).add(ordenCollita);
			}
			String nombreCamion = ordenCollita.getCamion().getNombre();
			if (ordenesCamiones.get(nombreCamion) == null) {
				List<OrdenCollita> ordenesCollita = new ArrayList<OrdenCollita>();
				ordenesCollita.add(ordenCollita);
				ordenesCamiones.put(nombreCamion, ordenesCollita);
			} else {
				ordenesCamiones.get(nombreCamion).add(ordenCollita);
			}
			switch (unidadSeleccionada) {
			case 0:
				total += kilos;
				break;
			case 1:
				total += Double.valueOf(kilos / 12.78).intValue();
				break;
			case 2:
				total += ordenCollita.getCajonesPrevistos();
				break;
			}
			totalEuros+=kilos*ordenCollita.getVariedad().getPrecioMedioCompra();

		}
		totalCantidadTextView.setText("" + total);
		totalEurosTextView.setText("" + df.format(totalEuros) );
		if (totalesLinearLayout.getChildCount() == 2) {		
			totalesLinearLayout.removeViewAt(1);
			totalesLinearLayout.addView(creaLayoutVariedades(ordenes));
		}		
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
		HashMap<String, DatosCollita> datos = new HashMap<String, DatosCollita>();
		for (OrdenCollita ordenCollita : ordenes) {
			String nombreComprador = ordenCollita.getComprador().getNombre();
			DatosCollita datosCollita = new DatosCollita();
			Integer kilos = ordenCollita.getCajonesPrevistos()
					* ordenCollita.getVariedad().getKilosPorCajon();
			datosCollita.setKilos(kilos);
			datosCollita.setCajones(ordenCollita.getCajonesPrevistos());
			Double euros = (kilos*ordenCollita.getVariedad().getPrecioMedioCompra())*0.03d;
			datosCollita.setEuros(euros);
			DatosCollita dato = datos.get(nombreComprador);
			if (dato != null) {
				dato.setKilos(dato.getKilos() + datosCollita.getKilos());
				dato.setCajones(dato.getCajones() + datosCollita.getCajones());
				dato.setEuros(dato.getEuros() + datosCollita.getEuros());
			} else {
				datos.put(nombreComprador, datosCollita);
			}
		}
		for (final String comprador : datos.keySet()) {
			final LinearLayout datosCompradorLinearLayout = new LinearLayout(
					getApplicationContext());
			DatosCollita dato = datos.get(comprador);
			datosCompradorLinearLayout.setOrientation(LinearLayout.VERTICAL);
			LinearLayout linearLayout = new LinearLayout(
					getApplicationContext());
			Button nombreButton = new Button(getApplicationContext());
			nombreButton.setLayoutParams(new LayoutParams(50, 100, 1));
			nombreButton.setText(comprador);
			TextView cantidadTextView = new TextView(getApplicationContext());
			Integer cantidad = 0;
			switch (unidadSeleccionada) {
			case 0:
				cantidad = dato.getKilos();
				break;
			case 1:
				cantidad = dato.getArrobes();
				break;
			case 2:
				cantidad = dato.getCajones();
				break;
			}
			cantidadTextView.setText("" + cantidad);
			cantidadTextView.setLayoutParams(new LayoutParams(50, 50, 1));
			TextView eurosTextView = new TextView(getApplicationContext());
			eurosTextView.setLayoutParams(new LayoutParams(50, 50, 1));
			eurosTextView.setText(df.format(dato.getEuros()));
			linearLayout.addView(nombreButton);
			linearLayout.addView(cantidadTextView);
			linearLayout.addView(eurosTextView);
			nombreButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (datosCompradorLinearLayout.getChildCount() == 2) {
						datosCompradorLinearLayout.removeViewAt(1);
					} else {
						datosCompradorLinearLayout
								.addView(creaLayoutVariedades(ordenesCompradores
										.get(comprador)));
					}
				}
			});
			datosCompradorLinearLayout.addView(linearLayout);
			datosLinearLayout.addView(datosCompradorLinearLayout);
		}
	}

	private LinearLayout creaLayoutVariedades(List<OrdenCollita> ordenes) {
		HashMap<String, DatosCollita> datos = new HashMap<String, DatosCollita>();
		for (OrdenCollita ordenCollita : ordenes) {
			String variedad = ordenCollita.getVariedad().getNombre();
			DatosCollita datosCollita = new DatosCollita();
			Integer kilos = ordenCollita.getCajonesPrevistos()
					* ordenCollita.getVariedad().getKilosPorCajon();
			datosCollita.setKilos(kilos);
			datosCollita.setCajones(ordenCollita.getCajonesPrevistos());
			Double euros=0d;
           
			switch (opcionSeleccionada) {
			case 0:
				euros=(kilos*ordenCollita.getVariedad().getPrecioKiloCollita())/(ordenCollita.getCuadrilla().getNumeroCollidors());
				break;
			case 1:
				
				break;

			case 2:
				euros=(kilos*ordenCollita.getVariedad().getPrecioMedioCompra())*0.03d;
				break;
			
			default:
				
				break;
			}
			 if (totalButton != null){
					euros=kilos*ordenCollita.getVariedad().getPrecioMedioCompra();//sera aci?
		            }
			 
			datosCollita.setEuros(euros);			
			DatosCollita dato = datos.get(variedad);
			if (dato != null) {
				dato.setKilos(dato.getKilos() + datosCollita.getKilos());
				dato.setCajones(dato.getCajones() + datosCollita.getCajones());
				dato.setEuros(dato.getEuros() + datosCollita.getEuros());
				
			} else {
				datos.put(variedad, datosCollita);
			}
		}
		final LinearLayout datosVariedadesLinearLayout = new LinearLayout(
				getApplicationContext());
		datosVariedadesLinearLayout.setOrientation(LinearLayout.VERTICAL);
		for (final String variedad : datos.keySet()) {
			DatosCollita dato = datos.get(variedad);
			LinearLayout linearLayout = new LinearLayout(
					getApplicationContext());
			TextView nombreTextView = new TextView(getApplicationContext());
			nombreTextView.setLayoutParams(new LayoutParams(50, 50, 1));
			nombreTextView.setText(variedad);
			Integer cantidad = 0;
			switch (unidadSeleccionada) {
			case 0:
				cantidad = dato.getKilos();
				break;
			case 1:
				cantidad = dato.getArrobes();
				break;
			case 2:
				cantidad = dato.getCajones();
				break;
			}
			TextView cantidadTextView = new TextView(getApplicationContext());
			cantidadTextView.setText("" + cantidad);
			cantidadTextView.setLayoutParams(new LayoutParams(50, 50, 1));
			TextView eurosTextView = new TextView(getApplicationContext());
			eurosTextView.setLayoutParams(new LayoutParams(50, 50, 1));
			eurosTextView.setText(df.format(dato.getEuros()));
			linearLayout.addView(nombreTextView);
			linearLayout.addView(cantidadTextView);	
		    linearLayout.addView(eurosTextView);		
			datosVariedadesLinearLayout.addView(linearLayout);
		}
		return datosVariedadesLinearLayout;
	}

	private LinearLayout creaLayoutTermes(String nombreCamion) {

		return null;
	}

	private void muestraDatosCamiones(List<OrdenCollita> ordenes) {
		HashMap<String, Integer> datos = new HashMap<String, Integer>();
		for (OrdenCollita ordenCollita : ordenes) {
			String nombreCamion = ordenCollita.getCamion().getNombre();
			Integer kilos = ordenCollita.getCajonesPrevistos()
					* ordenCollita.getVariedad().getKilosPorCajon();
			Integer dato = datos.get(nombreCamion);
			if (dato != null) {
				datos.put(nombreCamion, dato + kilos);
			} else {
				datos.put(nombreCamion, kilos);
			}
		}
		for (String camion : datos.keySet()) {
			System.out.println(camion + ":" + datos.get(camion));
		}
	}

	private void muestraDatosCuadrillas(List<OrdenCollita> ordenes) {
		HashMap<String, DatosCollita> datos = new HashMap<String, DatosCollita>();
		for (OrdenCollita ordenCollita : ordenes) {
			String nombreCuadrilla = ordenCollita.getCuadrilla().getNombre();
			DatosCollita datosCollita = new DatosCollita();
			Integer kilos = ordenCollita.getCajonesPrevistos()
					* ordenCollita.getVariedad().getKilosPorCajon();
			datosCollita.setKilos(kilos);
			datosCollita.setCajones(ordenCollita.getCajonesPrevistos());
			Double euros = (kilos*ordenCollita.getVariedad().getPrecioKiloCollita())/(ordenCollita.getCuadrilla().getNumeroCollidors());
			datosCollita.setEuros(euros);
			DatosCollita dato = datos.get(nombreCuadrilla);
			if (dato != null) {
				dato.setKilos(dato.getKilos() + datosCollita.getKilos());
				dato.setCajones(dato.getCajones() + datosCollita.getCajones());
				dato.setEuros(dato.getEuros() + datosCollita.getEuros());
			} else {
				datos.put(nombreCuadrilla, datosCollita);
			}
		}
		for (final String cuadrilla : datos.keySet()) {
			final LinearLayout datosCuadrillaLinearLayout = new LinearLayout(
					getApplicationContext());
			DatosCollita dato = datos.get(cuadrilla);
			datosCuadrillaLinearLayout.setOrientation(LinearLayout.VERTICAL);
			LinearLayout linearLayout = new LinearLayout(
					getApplicationContext());
			Button nombreButton = new Button(getApplicationContext());
			nombreButton.setLayoutParams(new LayoutParams(50, 100, 1));
			nombreButton.setText(cuadrilla);
			TextView cantidadTextView = new TextView(getApplicationContext());
			Integer cantidad = 0;
			switch (unidadSeleccionada) {
			case 0:
				cantidad = dato.getKilos();
				break;
			case 1:
				cantidad = dato.getArrobes();
				break;
			case 2:
				cantidad = dato.getCajones();
				break;
			}
			cantidadTextView.setText("" + cantidad);
			cantidadTextView.setLayoutParams(new LayoutParams(50, 50, 1));
			TextView eurosTextView = new TextView(getApplicationContext());
			eurosTextView.setLayoutParams(new LayoutParams(50, 50, 1));
			eurosTextView.setText(df.format(dato.getEuros()));
			linearLayout.addView(nombreButton);
			linearLayout.addView(cantidadTextView);
			linearLayout.addView(eurosTextView);
			nombreButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (datosCuadrillaLinearLayout.getChildCount() == 2) {
						datosCuadrillaLinearLayout.removeViewAt(1);
					} else {
						datosCuadrillaLinearLayout
								.addView(creaLayoutVariedades(ordenesCuadrillas
										.get(cuadrilla)));
					}
				}
				
				
			});
			datosCuadrillaLinearLayout.addView(linearLayout);
			datosLinearLayout.addView(datosCuadrillaLinearLayout);
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

	private class DatosCollita {
		private Integer cajones;
		private Integer kilos;
		private Double euros;

		public Integer getCajones() {
			return cajones;
		}

		public void setCajones(Integer cajones) {
			this.cajones = cajones;
		}

		public Integer getKilos() {
			return kilos;
		}

		public void setKilos(Integer kilos) {
			this.kilos = kilos;
		}

		private Integer getArrobes() {
			return Double.valueOf(kilos / 12.78).intValue();
		}

		public Double getEuros() {
			return euros;
		}

		public void setEuros(Double euros) {
			this.euros = euros;
		}
	}
}
