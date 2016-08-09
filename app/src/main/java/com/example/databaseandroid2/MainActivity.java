package com.example.databaseandroid2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private OperasiDatabase oprDatabase = null;
    private SQLiteDatabase db = null;
    private Cursor dbCursor = null;

    private RecyclerView recList;
    private TextView labelError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("List Data Mahasiswa");

        //**** initiate recycler view
        recList = (RecyclerView) findViewById(R.id.content_main_card_list);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);


        labelError = (TextView) findViewById(R.id.content_main_label_error);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Menambahkan data baru", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, AddDataActivity.class);
                        intent.putExtra("status", "baru");
                        startActivity(intent);
                    }
                });

        //*** database operation ****

        tampilkanData();



    }

    /**
     * method untuk menampilkan list data mahasiswa
     */

    private void tampilkanData(){
        oprDatabase = new OperasiDatabase(this);
        db = oprDatabase.getWritableDatabase();
        oprDatabase.createTable(db);


        dbCursor = oprDatabase.selectBiodata(db, "SELECT * FROM biodata");
        dbCursor.moveToFirst();

        int kol_nim=dbCursor.getColumnIndex("nim");
        int kol_nama=dbCursor.getColumnIndex("nama");
        int kol_alamat=dbCursor.getColumnIndex("alamat");

        Student student;
        ArrayList<Student> students = new ArrayList<>();
        if(dbCursor!=null){
            while(dbCursor.moveToNext()){
                student = new Student();
                student.setNim(dbCursor.getString(kol_nim));
                student.setName(dbCursor.getString(kol_nama));
                student.setAddress(dbCursor.getString(kol_alamat));
                students.add(student);
            }
        }

        StudentAdapter adapter = new StudentAdapter(students, this);
        recList.setAdapter(adapter);

        if (students.size() > 0){
            recList.setVisibility(View.VISIBLE);
            labelError.setVisibility(View.GONE);
        }else{
            recList.setVisibility(View.GONE);
            labelError.setVisibility(View.VISIBLE);
        }

        db.close();
    }


    @Override
    protected void onResume() {
        tampilkanData();
        super.onResume();

    }

    /*
     * method untuk menghapus semua data
     */
    private void deleteAllData(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Konfirmasi");
        dialog.setMessage("Anda yakin akan menghapus seluruh data?");
        dialog.setNegativeButton("Cancel", null);
        dialog.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                oprDatabase = new OperasiDatabase(MainActivity.this);
                db = oprDatabase.getWritableDatabase();
                oprDatabase.createTable(db);
                oprDatabase.deleteAllBiodata(db);
                oprDatabase.close();
                db.close();

                onResume();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete_all){
            // show dialog, delete all data
            deleteAllData();
        }else if (item.getItemId() == R.id.action_exit){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
