package com.fedesoft.collitaandroid;

import java.util.List;

import javax.xml.transform.Result;

import com.fedesoft.collitaandroid.model.Cuadrilla;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class CuadrillasActivity extends Activity {
	
	private Button agregarCuadrillaButton;
	private LinearLayout cuadrillasLinearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cuadrillas);
		agregarCuadrillaButton=(Button)findViewById(R.id.agregarcuadrillabutton);
		cuadrillasLinearLayout=(LinearLayout)findViewById(R.id.listacuadrillaslinearlayout);
		agregarCuadrillaButton.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {						
				Intent intent=new Intent(getApplicationContext(),NuevaCuadrillaActivity.class);				
				startActivityForResult(intent,3);
			}
		});						
		refrescarLista();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {			
		if (resultCode==1){
			refrescarLista();			
		}					
	}

	private void refrescarLista() {
		cuadrillasLinearLayout.removeAllViews();
		CollitaDAOIfc collitaDAO=CollitaApplication.getInstance(getApplicationContext()).getCollitaDAO();
		List<Cuadrilla> cuadrillas= collitaDAO.recuperarCuadrillas(null); 
		for(final Cuadrilla c:cuadrillas){
			Button b=new Button(getApplicationContext());
			b.setText(c.getNombre());
			cuadrillasLinearLayout.addView(b);
			b.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(getApplicationContext(),EditarCuadrillaActivity.class);
					intent.putExtra("cuadrilla_id", c.getId());
					startActivityForResult(intent,3);				
				}
			});
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_cuadrillas, menu);
		return true;
	}
	@Override
	public boolean onMenuItemSelected(int featuredId, MenuItem item) {
		if (item.getItemId() == R.id.camionesitem) {			
			Intent intent = new Intent(getApplicationContext(),
					CamionesActivity.class);
			startActivity(intent);
			setResult(1);
			finish();
		}
		
		if (item.getItemId() == R.id.compradoresitem) {
			
			Intent intent = new Intent(getApplicationContext(),
					CompradoresActivity.class);
			startActivity(intent);
			setResult(1);
			finish();
		}
		if (item.getItemId() == R.id.variedadesitem) {
			
			Intent intent = new Intent(getApplicationContext(),
					VariedadesActivity.class);
			startActivity(intent);
			setResult(1);
			finish();
		}
		if (item.getItemId() == R.id.termesitem) {
			
			Intent intent = new Intent(getApplicationContext(),
					TermesActivity.class);
			startActivity(intent);
			setResult(1);
			finish();
		}
		if (item.getItemId() == R.id.informesitem){
			Intent intent = new Intent(getApplicationContext(),
					InformesActivity.class);
			startActivity(intent);
			setResult(1);
			finish();
			
		}
		return true;
	}
}
