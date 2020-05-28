package com.example.mystudent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mystudent.Adapter.CourseAdapter;
import com.example.mystudent.Adapter.StudentAdapter;

import java.io.ByteArrayOutputStream;

public class CourseActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText name;
    Button add;
    dbHandler dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        setTitle("مدیریت رشته ها");


        recyclerView = findViewById(R.id.rec_course);
        add = findViewById(R.id.btn_crs_add);
        name = findViewById(R.id.edt_crs_name);
        dbh = new dbHandler(this);

        showCourse();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(name.getText().toString().equals("")){
                    Toast.makeText(CourseActivity.this,R.string.fill_course,Toast.LENGTH_SHORT).show();
                }

                if(!name.getText().toString().equals("")  ){


                    dbh.open();
                    dbh.insertCrs(name.getText().toString());
                    dbh.close();
                    name.setText("");
                    Toast.makeText(CourseActivity.this,R.string.saved,Toast.LENGTH_SHORT).show();
                    showCourse();
                    //finish();
                }

            }
        });


    }

    private void showCourse() {

        dbHandler dbh = new dbHandler(this);
        dbh.open();
        CourseAdapter studentAdapter = new CourseAdapter(this,dbh.crsList());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(studentAdapter);
        dbh.close();

    }
}
