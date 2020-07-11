package com.example.mystudent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystudent.Adapter.CourseAdapter;
import com.example.mystudent.Model.Student;

public class EditStudent extends AppCompatActivity {

    dbHandler dbh;
    Bundle b;
    EditText name;
    Student student;
    LinearLayout course;
    TextView courseName;
    Dialog dialog;
    String iidd;
    CardView save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);
        setTitle("ویرایش مشخصات دانشجو");

        b = getIntent().getExtras();
        name = findViewById(R.id.edt_edit_name);
        course = findViewById(R.id.lin_edit_course);
        courseName = findViewById(R.id.txt_edit_course);
        save = findViewById(R.id.crd_edit_save);

        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.DEP_POS = "3";
                CourseSelect();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbh.open();
                dbh.edit(b.getString("ID"),name.getText().toString(),iidd);
                dbh.close();
                Toast.makeText(getApplicationContext(),"ویرایش شد.",Toast.LENGTH_SHORT).show();
                finish();
            }
        });


        dbh = new dbHandler(this);
        showData(b.getString("ID"));

    }

    private void showData(String num) {

        dbh.open();
        //if(dbh.getCount(number.getText().toString())==0){
        if(dbh.getCount(num)==0){
            //Toast.makeText(SearchActivity.this,R.string.not_found,Toast.LENGTH_SHORT).show();
            name.setText("نام : "+"-");
            //course.setText("رشته : "+"-");
        }else {
            //student = dbh.namayesh(number.getText().toString());
            student = dbh.namayesh(num);

            name.setText(student.getName());
            courseName.setText(student.getCourse());
/*
            byte[] p = student.getPhto();
            if(p!=null){
                Bitmap bitmap = BitmapFactory.decodeByteArray(p,0,p.length);
                logo.setImageBitmap(bitmap);
            }else {
                logo.setImageResource(R.mipmap.ico_student);
            }
            */
        }
        dbh.close();

    }
    private void CourseSelect() {
        View alertView = LayoutInflater.from(EditStudent.this).inflate(R.layout.alert_search,null);
        AlertDialog.Builder alert = new AlertDialog.Builder(EditStudent.this);
        alert.setTitle("انتخاب دپارتمان ");
        alert.setView(alertView);

        refreshCourse(alertView);

        dialog= alert.create();
        dialog.show();
    }
    private void refreshCourse(View alertView) {
        final EditText edt = alertView.findViewById(R.id.edt_alert_search);
        final RecyclerView recyclerView = alertView.findViewById(R.id.rec_alert);

        dbHandler dbh = new dbHandler(this);
        dbh.open();
        CourseAdapter courseAdapter = new CourseAdapter(this,dbh.crsList());
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(courseAdapter);

        dbh.close();

        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                dbHandler dbh = new dbHandler(EditStudent.this);
                dbh.open();

                if(dbh.isGetCourse(edt.getText().toString())){
                    CourseAdapter courseAdapter = new CourseAdapter(EditStudent.this,dbh.crsList(edt.getText().toString()));
                    recyclerView.setLayoutManager(new GridLayoutManager(EditStudent.this,2));
                    recyclerView.setAdapter(courseAdapter);

                }else {
                    recyclerView.setAdapter(null);
                }
                dbh.close();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public  void setCourse( String id, String crsename){
        courseName.setText(crsename);
        iidd = id;
        dialog.dismiss();
    }

}
