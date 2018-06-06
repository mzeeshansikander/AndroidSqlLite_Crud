package com.example.zeeshan.sqloperations;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         db = openOrCreateDatabase("Graduates",MODE_PRIVATE,null);

    }
    public void addStudent(View view){
        EditText roll_no = findViewById(R.id.et_rollNo);
        EditText name = findViewById(R.id.et_name);

        String sRoll_no = roll_no.getText().toString();
        String sName = name.getText().toString();


        String query = "CREATE TABLE IF NOT EXISTS STUDENTS(roll_number TEXT,name TEXT)";
        db.execSQL(query);

        ContentValues content = new ContentValues();
        content.put("roll_number",sRoll_no);
        content.put("name",sName);

        db.insert("STUDENTS",null,content);

    }
    public void searchStudent(View view){
        EditText roll_no = findViewById(R.id.et_rollNo);
        String sRoll_no = roll_no.getText().toString();
        EditText studentName = findViewById(R.id.et_name);

        String query = "Select * from STUDENTS WHERE roll_number = \'"+sRoll_no+"\';";
        Log.e("TAG",query);

        Cursor cr = db.rawQuery(query,null);
        cr.moveToFirst();


            do {
                String id = cr.getString(cr.getColumnIndex("roll_number"));
                String name = cr.getString(cr.getColumnIndex("name"));


                roll_no.setText(id);
                studentName.setText(name);

            } while (cr.moveToNext());
            cr.close();


    }
    public void clearStudent(View view){
        ListView students_lv = findViewById(R.id.lv_students);

        EditText roll_no = findViewById(R.id.et_rollNo);
        EditText studentName = findViewById(R.id.et_name);

        roll_no.setText("");
        studentName.setText("");
        students_lv.setAdapter(null);

    }
    public void viewStudents(View view){
        List<String> studentsList = new ArrayList<>();
        ListView students_lv = findViewById(R.id.lv_students);

        String query = "SELECT * FROM STUDENTS";

        Cursor cr = db.rawQuery(query,null);
        cr.moveToFirst();

        do{
            String name = cr.getString(cr.getColumnIndex("name"));
            studentsList.add(name);

        }while(cr.moveToNext());

        ArrayAdapter adapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,studentsList);
        students_lv.setAdapter(adapter);
    }

    public void deleteStudent(View view){
        EditText roll_no = findViewById(R.id.et_rollNo);
        String sRoll_no = roll_no.getText().toString();

        String query = "DELETE FROM STUDENTS WHERE roll_number = \'"+sRoll_no+"\';";
        Log.e("TAG",query);
        db.execSQL(query);
    }
}
