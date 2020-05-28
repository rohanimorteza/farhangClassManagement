package com.example.mystudent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mystudent.Adapter.StudentAdapter;
import com.example.mystudent.Model.Student;

import static android.os.Build.VERSION.SDK_INT;

public class MainActivity extends AppCompatActivity {

    //Button insert,search;
    public static String DEP_POS="0";
    CardView insert,search,course,stdlist,departmant;
    RecyclerView recyclerView;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insert = findViewById(R.id.crd_main_new);
        search = findViewById(R.id.crd_main_search);
        course = findViewById(R.id.crd_main_new_course);
        departmant = findViewById(R.id.crd_main_new_departmant);
        stdlist = findViewById(R.id.crd_main_list_student);

        recyclerView = findViewById(R.id.rec);

        showStudents();
        DEP_POS = "0";
        setTitle("");
        if (SDK_INT >17){
            getWindow().peekDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }


        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,NewStudentActivity.class));
            }
        });
        departmant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CourseActivity.class));
            }
        });
        stdlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,StdudentListActivity.class));
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(MainActivity.this,SearchActivity.class));
                search();
            }
        });

    }

    public void search(){
        View alertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.alert_search,null);
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("جستجو");
        alert.setView(alertView);

        refreshSearch(alertView);

        dialog= alert.create();
        dialog.show();

    }

    private void refreshSearch(View alertView) {
        final EditText edt = alertView.findViewById(R.id.edt_alert_search);
        final RecyclerView recyclerView = alertView.findViewById(R.id.rec_alert);

        dbHandler dbh = new dbHandler(this);
        dbh.open();

        StudentAdapter studentAdapter = new StudentAdapter(this,dbh.stdList());
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(studentAdapter);

        dbh.close();

        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                dbHandler dbh = new dbHandler(MainActivity.this);
                dbh.open();

                if(dbh.isGet(edt.getText().toString())){
                    StudentAdapter studentAdapter = new StudentAdapter(MainActivity.this,dbh.stdList(edt.getText().toString()));
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                    recyclerView.setAdapter(studentAdapter);

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

    @Override
    protected void onResume() {
        super.onResume();
        showStudents();
    }

    private void showStudents() {

        dbHandler dbh = new dbHandler(this);
        dbh.open();
        StudentAdapter studentAdapter = new StudentAdapter(this,dbh.stdList());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(studentAdapter);
        dbh.close();

    }
}
