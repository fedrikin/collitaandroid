package com.fedesoft.collitaandroid;

import java.util.List;

import com.fedesoft.collitaandroid.model.Terme;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class TermesActivity extends Activity {
	private Button agregarTermesButton;
    private LinearLayout listaTermesLinearLayout;
    private CollitaDAOIfc collitaDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_termes);
		agregarTermesButton= (Button) findViewById(R.id.agregartermesbutton);
		listaTermesLinearLayout=(LinearLayout) findViewById(R.id.listatermelinearlayout);
		
		agregarTermesButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
		        Intent intent=new Intent(getApplicationContext(),NuevoTermeActivity.class);
				startActivityForResult(intent, 3);
			}
		});
	    refrescarlista();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {			
		if (resultCode==1){
			refrescarlista();			
		}					
	}
    private void refrescarlista(){
    	listaTermesLinearLayout.removeAllViews();
	    collitaDAO=CollitaApplication.getInstance(getApplicationContext()).getCollitaDAO();
		List<Terme> termes= collitaDAO.recuperarTermes();
		for(final Terme terme:termes){
			Button b=new Button(getApplicationContext());
			b.setText(terme.getNombre()+ "-"+ terme.getId());
			listaTermesLinearLayout.addView(b);
			b.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(getApplicationContext(),EditarTermesActivity.class);
					intent.putExtra("terme_id",terme.getId());
					startActivityForResult(intent, 3);		
		        }
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.termes, menu);
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
