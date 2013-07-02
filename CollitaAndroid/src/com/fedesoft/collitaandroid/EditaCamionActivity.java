package com.fedesoft.collitaandroid;

import com.fedesoft.collitaandroid.model.Camion;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class EditaCamionActivity extends Activity {
    private EditText nombreCamionEditText;
    private EditText nombreConductorEditText;
    private EditText cajonesMaximoEditText;
    private EditText telefonoEditText;
    private Button  editarCamionButton;
    private CheckBox activoCheckBox;
    private Camion camion;
    private CollitaDAOIfc collitaDAO;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edita_camion);
		nombreCamionEditText= (EditText) findViewById(R.id.nombrecamioneditText);
		nombreConductorEditText= (EditText) findViewById(R.id.nombreconductoreditText);
		cajonesMaximoEditText=(EditText) findViewById(R.id.numerocajoneseditText);
		telefonoEditText=(EditText) findViewById(R.id.telefonoconductoreditText);
		activoCheckBox=(CheckBox) findViewById(R.id.camionaActivocheckBox);
		editarCamionButton=(Button) findViewById(R.id.guardarnuevocamionbutton);
		editarCamionButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				editarcamion();				
			}		
		});
		
		Integer camionId=getIntent().getIntExtra("camion_id",0);		
	    collitaDAO=CollitaApplication.getInstance(getApplicationContext()).getCollitaDAO();
		camion=collitaDAO.getCamionById(camionId);
		nombreCamionEditText.setText(camion.getNombre());
		nombreConductorEditText.setText(camion.getConductor());
		cajonesMaximoEditText.setText(""+camion.getCajonesMaximo());	
		telefonoEditText.setText(camion.getTelefono());
		activoCheckBox.setChecked(camion.isActivo());		
	}
	    private void editarcamion() {
	     
	      String nombrecamio=nombreCamionEditText.getText().toString();
	      if (nombrecamio.equals("")){
	    	  Toast toast=Toast.makeText(getApplicationContext(), "Introduce NOMBRE CAMION!!!", Toast.LENGTH_LONG);
				toast.show();
				return;
	      }
	      camion.setNombre(nombreCamionEditText.getText().toString());
	      camion.setConductor(nombreConductorEditText.getText().toString());
	      camion.setTelefono(telefonoEditText.getText().toString());
	      String cajonemaximo=cajonesMaximoEditText.getText().toString();	      
	      if (cajonemaximo.equals("")){
	    	  Toast toast=Toast.makeText(getApplicationContext(), "Introduce CAJONES MAXIMO!!!", Toast.LENGTH_LONG);
				toast.show();
				return;
	      }
	      camion.setCajonesMaximo(Integer.parseInt(cajonesMaximoEditText.getText().toString()));
	      camion.setActivo(activoCheckBox.isChecked());
	      CollitaDAOSqlite.getInstance(getApplicationContext()).actualizarCamion(camion);
	      setResult(1);
	      finish();
	    }
     }