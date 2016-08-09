package com.example.databaseandroid2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Ardians-PC on 8/9/2016.
 */
public class AddDataActivity extends AppCompatActivity {
    private OperasiDatabase oprDatabase = null;
    private SQLiteDatabase db = null;
    private EditText txtnim;
    private EditText txtnama;
    private EditText txtalamat;
    private Button btnsimpan;
    private Boolean data_baru;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        String nim ="";
        String nama ="";
        String alamat ="";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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

        txtnim = (EditText) findViewById(R.id.add_data_input_nim);
        txtnim.setText(nim);
        txtnama = (EditText) findViewById(R.id.add_data_input_nama);
        txtnama.setText(nama);
        txtalamat = (EditText) findViewById(R.id.add_data_input_alamat);
        txtalamat.setText(alamat);
        btnsimpan = (Button) findViewById(R.id.add_data_button_save);

        if(data_baru==true){
            btnsimpan.setText("Simpan"); //insert
            setTitle("Tambah Data Baru");
        }
        else{
            btnsimpan.setText("Edit");// update
            setTitle("Edit Data");
        }

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
            Toast.makeText(AddDataActivity.this, "Data berhasil disimpan!", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
