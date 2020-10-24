package com.unud1805551051.tugas1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;

    EditText editNama, editNilai, editId;
    Button btnTambah, btnViewAll, btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editNama = (EditText)findViewById(R.id.editTextTextPersonName);
        editNilai = (EditText)findViewById(R.id.editTextTextPersonName2);
        editId = (EditText)findViewById(R.id.editTextTextPersonName3);
        btnTambah = (Button)findViewById(R.id.button);
        btnViewAll = (Button)findViewById(R.id.button2);
        btnUpdate = (Button)findViewById(R.id.button3);
        btnDelete = (Button)findViewById(R.id.button4);
        tambahData();
        viewAll();
        updateData();
        deleteData();
    }

    public void tambahData(){
        btnTambah.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editNama.getText().toString(), editNilai.getText().toString());
                        if (isInserted==true){
                            Toast.makeText(MainActivity.this,"Data Ditambahkan",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(MainActivity.this,"Data Gagal Ditambahkan",Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void viewAll(){
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor result = myDb.getAllData();
                        if (result.getCount() == 0){
                            showMessage("Error", "Tidak Ada Data");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (result.moveToNext()){
                            buffer.append("ID :"+ result.getString(0)+"\n");
                            buffer.append("Nama :"+ result.getString(1)+"\n");
                            buffer.append("Nilai :"+ result.getInt(2)+"\n");
                        }

                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }

    public void updateData(){
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdated = myDb.updateData(editId.getText().toString(), editNama.getText().toString(), editNilai.getText().toString());
                        if (isUpdated == true){
                            Toast.makeText(MainActivity.this,"Data Diubah",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(MainActivity.this,"Data Gagal Diubah",Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void deleteData(){
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(editId.getText().toString());
                        if (deletedRows > 0 ){
                            Toast.makeText(MainActivity.this,"Data Dihapus",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(MainActivity.this,"Data Gagal Dihapus",Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }


    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}