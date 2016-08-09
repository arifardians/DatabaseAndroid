package com.example.databaseandroid2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ardians-PC on 8/9/2016.
 */
public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private ArrayList<Student> items;
    private Activity activity;

    public StudentAdapter(ArrayList<Student> items, Activity activity) {
        this.items = items;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vResult = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return  new ViewHolder(vResult);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Student student = items.get(position);
        holder.vIcon.setImageResource(R.drawable.ic_user);
        holder.vTitle.setText(student.getName().toUpperCase());
        String subtitle = "NIM: " + student.getNim()+"\nAlamat: " + student.getAddress();
        holder.vSubtitle.setText(subtitle);
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AddDataActivity.class);
                intent.putExtra("status", "edot");
                intent.putExtra("nim", student.getNim());
                intent.putExtra("nama", student.getName());
                intent.putExtra("alamat", student.getAddress());

                activity.startActivity(intent);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                dialog.setTitle("Konfirmasi");
                dialog.setMessage("Anda yakin akan menghapus data "+student.getName().toUpperCase()+" (NIM: "+student.getNim()+") ini ?");
                dialog.setNegativeButton("Cancel", null);
                dialog.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        OperasiDatabase oprDatabase = new OperasiDatabase(activity);
                        SQLiteDatabase db = oprDatabase.getWritableDatabase();
                        oprDatabase.createTable(db);
                        oprDatabase.deleteBiodata(db, student.getNim());
                        ((MainActivity) activity).onResume();
                    }
                });
                dialog.show();
            }
        });
    }


    private void actionDelete(){

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

     static class ViewHolder extends RecyclerView.ViewHolder{
         ImageView vIcon;
         TextView vTitle;
         TextView vSubtitle;
         Button btnEdit;
         Button btnDelete;

        public ViewHolder(View view) {
            super(view);
            vIcon       = (ImageView) view.findViewById(R.id.card_item_icon);
            vTitle      = (TextView) view.findViewById(R.id.card_item_title);
            vSubtitle   = (TextView) view.findViewById(R.id.card_item_subtitle);
            btnEdit     = (Button) view.findViewById(R.id.card_item_button_edit);
            btnDelete   = (Button) view.findViewById(R.id.card_item_button_delete);
        }
    }
}
