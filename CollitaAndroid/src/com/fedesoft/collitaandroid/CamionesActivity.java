package com.fedesoft.collitaandroid;

import java.util.List;

import com.fedesoft.collitaandroid.model.Camion;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class CamionesActivity extends Activity {
	
	private Button agregarCamionesButton;
    private LinearLayout camionesLinearLayout; 
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camiones);
		agregarCamionesButton=(Button)findViewById(R.id.agregarcuadrillabutton);
		camionesLinearLayout=(LinearLayout)findViewById(R.id.listacuadrillaslinearlayout);
		
		agregarCamionesButton.setOnClickListener(new OnClickListener() {			
		    @Override
		    public void onClick(View v) {		    	
			    Intent intent=new Intent(getApplicationContext(),NuevoCamionActivity.class);
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
		camionesLinearLayout.removeAllViews();
		CollitaDAOIfc collitaDAO=CollitaApplication.getInstance(getApplicationContext()).getCollitaDAO();
		List<Camion> camiones= collitaDAO.recuperarCamiones(null);
		for(final Camion c:camiones){
			Button b=new Button(getApplicationContext());
			b.setText(c.getNombre());
			camionesLinearLayout.addView(b);
			b.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(getApplicationContext(),EditaCamionActivity.class);
					intent.putExtra("camion_id", c.getId());
					startActivityForResult(intent,3);				
				}
			});
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.camiones, menu);
		return true;
	}
	@Override
	public boolean onMenuItemSelected(int featuredId, MenuItem item) {
		
		if (item.getItemId() == R.id.cuadrillasitem) {
			
			Intent intent = new Intent(getApplicationContext(),
					CuadrillasActivity.class);
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
