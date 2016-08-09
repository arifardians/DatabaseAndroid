package com.example.databaseandroid2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressLint("NewApi")
public class DaftarBiodata extends Activity{
	private OperasiDatabase oprDatabase = null;
	private SQLiteDatabase db = null;
    private Cursor dbCursor = null;  
	private Button btnhapusemua;
	private Button btnhapus;
	private Button btnedit;
	private Button btnrefresh;
	private String nim, nama, alamat;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftarbiodata);
        
        oprDatabase = new OperasiDatabase(this);  
        db = oprDatabase.getWritableDatabase();  
        oprDatabase.createTable(db);
        TampilkanData();
        
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        
        btnhapusemua = (Button) findViewById(R.id.btnhapusemua);// tombol hapus semua==
        btnhapusemua.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {
            	dialog.setTitle("Konfirmasi");
            	dialog.setMessage("Anda yakin akan menghapus seluruh data?");
            	dialog.setNegativeButton("Cancel", null);
            	dialog.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
					public void onClick(DialogInterface dialog, int arg1) {
						oprDatabase.deleteAllBiodata(db);
		            	TampilkanData();
					}
				});
            	dialog.show();
            }  
        });  //========================================================================
        
        btnedit = (Button) findViewById(R.id.btnedit);// tombol edit/update=================
        btnedit.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {  
            	if(!nim.isEmpty()){
            		dbCursor = oprDatabase.selectBiodata(db, "SELECT nama, alamat FROM biodata WHERE nim='"+nim+"'");  
                    dbCursor.moveToFirst(); 
                    
                    nama = dbCursor.getString(0);
                    alamat = dbCursor.getString(1);
                    TampilkanEditBiodata();
            	}
            }  
        });//========================================================================
        
        btnhapus = (Button) findViewById(R.id.btnhapus);// tombol hapus======================
        btnhapus.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {
	            	if(!nim.isEmpty()){
	            		dialog.setTitle("Konfirmasi");
	                	dialog.setMessage("Anda yakin akan menghapus data "+nim+" ini ?");
	                	dialog.setNegativeButton("Cancel", null);
	                	dialog.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
	    					public void onClick(DialogInterface dialog, int arg1) {
	    						oprDatabase.deleteBiodata(db, nim);
	    	            		TampilkanData();
	    	            		nim = "";nama="";alamat="";
	    					}
	    				});
	                	dialog.show();
	            	}
            	}
 
        });//==================================================================================
        
        btnrefresh = (Button) findViewById(R.id.btnrefresh);// tombol refresh========
        btnrefresh.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {
            	TampilkanData();
            }  
        });//========================================================================
    }
	
	private void TampilkanData(){  // fungsi tampilkan data=============================
        TableLayout TL=(TableLayout) findViewById(R.id.tableLayout); 
        TL.removeAllViews();
  
		dbCursor = oprDatabase.selectBiodata(db, "SELECT * FROM biodata");  
        dbCursor.moveToFirst();  
        int jml_baris=dbCursor.getCount();  
        
        if(jml_baris == 0) return;
        
        int kol_nim=dbCursor.getColumnIndex("nim");  
        int kol_nama=dbCursor.getColumnIndex("nama");  
        int kol_alamat=dbCursor.getColumnIndex("alamat");  
        int indeks=1;  
        String[][] data=new String[jml_baris][3];  
          
        data[0][0]=dbCursor.getString(kol_nim);  
        data[0][1]=dbCursor.getString(kol_nama);  
        data[0][2]=dbCursor.getString(kol_alamat);  
          
        if(dbCursor!=null){  
            while(dbCursor.moveToNext()){  
                data[indeks][0]=dbCursor.getString(kol_nim);  
                data[indeks][1]=dbCursor.getString(kol_nama);  
                data[indeks][2]=dbCursor.getString(kol_alamat);  
                indeks++;  
            }  
        }  
          
        TableLayout.LayoutParams ParameterTableLayout = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        for(int awal=0; awal<jml_baris;awal++){  
            TableRow TR=new TableRow(this);  
            TR.setBackgroundColor(Color.BLACK);  
            TR.setLayoutParams(ParameterTableLayout);  
            TableRow.LayoutParams ParameterTableRow=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);  
            ParameterTableRow.setMargins(1,1,1,1);
            
            final CheckBox chk = new CheckBox(this);
            chk.setTag(data[awal][0]);
            TR.addView(chk,ParameterTableRow );
            chk.setOnClickListener(new OnClickListener() {  
                @Override  
                public void onClick(View v) {  
                    if(chk.isChecked())
                    	nim = chk.getTag().toString();
                    else 
                    	nim = "";
                }  
              });  
            
        	for(int kolom = 0;kolom < 3; kolom++){
                TextView TV=new TextView(this);
                TV.setText(data[awal][kolom]);  
                TV.setTextColor(Color.BLACK);  
                TV.setPadding(1, 4, 1, 4);  
                TV.setGravity(Gravity.LEFT);  
                TV.setBackgroundColor(Color.WHITE);  
                TR.addView(TV,ParameterTableRow );  
            }  
            TL.addView(TR);  
        }  
          
    }  //========================================================================
	private void TampilkanEditBiodata(){// fungsi edit biodata========
		Intent intentIsiBiodata = new Intent(this, IsiBiodata.class);
		intentIsiBiodata.putExtra("status", "edit");
		intentIsiBiodata.putExtra("nim", nim);
		intentIsiBiodata.putExtra("nama", nama);
		intentIsiBiodata.putExtra("alamat", alamat);
		nim = "";nama="";alamat="";
		startActivity(intentIsiBiodata);
	}// ================================================================

	public void onDestroy() {  
        super.onDestroy();  
        dbCursor.close();  
        db.close();  
    }  
}
