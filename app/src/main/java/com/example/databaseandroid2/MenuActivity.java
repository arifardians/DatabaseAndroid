package com.example.databaseandroid2;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuActivity extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String menu[] = new String[]{"Isi Biodata", "Daftar Biodata", "Exit"};
        
        this.setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,menu));
    }
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id){
		super.onListItemClick(l, v, position, id);
		Object obj = this.getListAdapter().getItem(position);
		String pilihan = obj.toString();
		Intent myIntent = null;
		if(pilihan.equalsIgnoreCase("exit"))
			finish();
		else {
			if(pilihan.equalsIgnoreCase("isi biodata")){
				myIntent = new Intent(this, IsiBiodata.class);
				myIntent.putExtra("status", "baru");
			}
			else if(pilihan.equalsIgnoreCase("daftar biodata"))
				myIntent = new Intent(this, DaftarBiodata.class);
			
			startActivity(myIntent);
		}
	}
}
