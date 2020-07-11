package com.example.mystudent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystudent.Model.Student;

public class SearchActivity extends AppCompatActivity {

    EditText number;
    CardView search,delete,edit;
    TextView name,course;
    dbHandler dbh;
    Student student;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setTitle("مشخصات دانشجو");


        number = findViewById(R.id.edt_search_number);
        search = findViewById(R.id.crd_search_search);
        delete = findViewById(R.id.crd_search_delete);
        edit = findViewById(R.id.crd_search_edit);
        name = findViewById(R.id.txt_search_name);
        course = findViewById(R.id.txt_search_course);
        logo = findViewById(R.id.img_search_logo);
        student = new Student();
        dbh = new dbHandler(this);


        Intent intent = getIntent();
        if (intent.hasExtra("NUM")) {
            Bundle b = getIntent().getExtras();
            //Toast.makeText(getApplicationContext(),""+b.getString("NUM"),Toast.LENGTH_LONG).show();
            showData(b.getString("NUM"));

        } else {
            //Toast.makeText(getApplicationContext(),"خالی",Toast.LENGTH_LONG).show();
        }


        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                name.setText("نام : "+"-");
                course.setText("رشته : "+"-");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(number.getText().toString().equals("")){
                    Toast.makeText(SearchActivity.this,R.string.fill_number,Toast.LENGTH_SHORT).show();
                }
                if(!number.getText().toString().equals("")){

                    showData(number.getText().toString());

                }

            }


        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = getIntent().getExtras();
                dbh.open();
                dbh.del(b.getString("NUM"));
                dbh.close();
                Toast.makeText(getApplicationContext(),"حذف شد"+b.getString("NUM"),Toast.LENGTH_LONG).show();
                finish();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = getIntent().getExtras();
                Intent i = new Intent(SearchActivity.this,EditStudent.class);
                i.putExtra("ID",b.getString("NUM"));
                startActivity(i);
            }
        });



    }
    private void showData(String num) {

        dbh.open();
        //if(dbh.getCount(number.getText().toString())==0){
        if(dbh.getCount(num)==0){
            Toast.makeText(SearchActivity.this,R.string.not_found,Toast.LENGTH_SHORT).show();
            name.setText("نام : "+"-");
            course.setText("رشته : "+"-");
        }else {
            //student = dbh.namayesh(number.getText().toString());
            student = dbh.namayesh(num);

            name.setText("نام : "+student.getName());
            course.setText("رشته : "+student.getCourse());

            byte[] p = student.getPhto();
            if(p!=null){
                Bitmap bitmap = BitmapFactory.decodeByteArray(p,0,p.length);
                logo.setImageBitmap(bitmap);
            }else {
                logo.setImageResource(R.mipmap.ico_student);
            }
        }
        dbh.close();

    }

}
