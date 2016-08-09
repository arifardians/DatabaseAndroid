package com.example.databaseandroid2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IsiBiodata extends AppCompatActivity{
	private OperasiDatabase oprDatabase = null;
	private SQLiteDatabase db = null;
	private EditText txtnim;
	private EditText txtnama;
	private EditText txtalamat;
	private Button btnsimpan;
	private Boolean data_baru;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		String nim ="";
		String nama ="";
		String alamat ="";
		 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.isibiodata);
        
        oprDatabase = new OperasiDatabase(this);
        db = oprDatabase.getWritableDatabase();  
        oprDatabase.createTable(db);
        
        Intent sender = getIntent();
        String status = sender.getExtras().getString("status");// cek status
        
        if(status.equalsIgnoreCase("baru")){//status = baru -> MenuActivity
        	data_baru = true;
        } else {							//status = edit -> OperasiDatabase
        	data_baru = false;
        	nim = sender.getExtras().getString("nim");
        	nama = sender.getExtras().getString("nama");
        	alamat = sender.getExtras().getString("alamat");
        }
        
        txtnim = (EditText) findViewById(R.id.txtnim);
        txtnim.setText(nim);
        txtnama = (EditText) findViewById(R.id.txtnama);
        txtnama.setText(nama);
        txtalamat = (EditText) findViewById(R.id.txtalamat);
        txtalamat.setText(alamat);
        btnsimpan = (Button) findViewById(R.id.btnsimpan);
        
        if(data_baru==true)
        	btnsimpan.setText("Simpan"); //insert
        else 
        	btnsimpan.setText("Edit");// update
        
        btnsimpan.setOnClickListener(new View.OnClickListener() {// panggil fungsi simpan data
            @Override  
            public void onClick(View v) {
                  simpandata();
            }  
        });
    }
	private void simpandata(){// fungsi simpan data
		String[] data = new String[]{
				txtnim.getText().toString(),
				txtnama.getText().toString(), 
				txtalamat.getText().toString()
		};
		if(data_baru==true) { //insert
			oprDatabase.insertBiodata(db, data); //panggil fungsi insertBiodata(Operasi Database)
			txtnim.setText("");
			txtnama.setText("");
			txtalamat.setText("");
		}
		else if(data_baru==false){// update
			oprDatabase.updateBiodata(db, data);//panggil fungsi updateBiodata(Operasi Database)
			finish();
		}
		
	}
	public void onDestroy() {  
        super.onDestroy();  
        db.close();  
    }  
}
