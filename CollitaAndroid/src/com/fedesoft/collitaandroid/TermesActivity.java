package com.fedesoft.collitaandroid;

import java.util.List;

import com.fedesoft.collitaandroid.model.Terme;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class TermesActivity extends Activity {
	private Button agregarTermesButton;
    private LinearLayout listaTermesLinearLayout;
    

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
		CollitaDAO collitaDAO=CollitaDAO.getInstance();
		List<Terme> termes= collitaDAO.recuperarTermes();
		for(final Terme terme:termes){
			Button b=new Button(getApplicationContext());
			b.setText(terme.getNombre());
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

}
