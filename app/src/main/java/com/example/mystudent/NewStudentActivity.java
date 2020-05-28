package com.example.mystudent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystudent.Adapter.CourseAdapter;
import com.example.mystudent.Adapter.StudentAdapter;

import java.io.ByteArrayOutputStream;

public class NewStudentActivity extends AppCompatActivity {

    EditText number,name;
    TextView courseName;
    Button save;
    dbHandler dbh;
    ImageView avatar,gallery,camera;
    LinearLayout courseSelect;
    byte[] imageInByte;
    Dialog dialog;
    String iidd="";
    private static final String TAG = "NewStudentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_student);


        MainActivity.DEP_POS = "2";

        setTitle("دانشجوی جدید");
        name = findViewById(R.id.edt_new_name);
        number = findViewById(R.id.edt_new_sh);
        //course = findViewById(R.id.edt_new_course);
        save = findViewById(R.id.btn_new_save);
        avatar = findViewById(R.id.img_new_avatar);
        gallery = findViewById(R.id.img_new_galery);
        camera = findViewById(R.id.img_new_camera);
        courseSelect = findViewById(R.id.lin_new_course);
        courseName = findViewById(R.id.txt_new_course);
        dbh = new dbHandler(this);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1);
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,2);
            }
        });




        courseSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseSelect();
            }

        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(name.getText().toString().equals("")){
                    Toast.makeText(NewStudentActivity.this,R.string.fill_name,Toast.LENGTH_SHORT).show();
                }

                if(number.getText().toString().equals("")){
                    Toast.makeText(NewStudentActivity.this,R.string.fill_number,Toast.LENGTH_SHORT).show();
                }else {
                    dbh.open();
                    if(dbh.getCount(number.getText().toString())>0){
                        Toast.makeText(NewStudentActivity.this,R.string.duplicate_number,Toast.LENGTH_SHORT).show();
                    }
                    dbh.close();
                }

                if(iidd.equals("")){
                    Toast.makeText(NewStudentActivity.this,R.string.fill_course,Toast.LENGTH_SHORT).show();
                }

                if(!iidd.equals("") && !name.getText().toString().equals("") && !number.getText().toString().equals("") && dbh.getCount(number.getText().toString())==0 ){

                    Bitmap bitmap = ((BitmapDrawable) avatar.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , baos);
                    imageInByte = baos.toByteArray();

                    dbh.open();
                    dbh.insert(name.getText().toString(),number.getText().toString(),iidd,imageInByte);
                    dbh.close();
                    name.setText("");
                    number.setText("");
                    //course.setText("");
                    Toast.makeText(NewStudentActivity.this,R.string.saved,Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode== RESULT_OK && data!=null){
            Uri img = data.getData();
            try {
                avatar.setImageURI(img);
            }catch (Exception e){

            }
        }else if(requestCode==2 && resultCode== RESULT_OK && data!=null){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            avatar.setImageBitmap(photo);

        }

    }

    private void CourseSelect() {
        View alertView = LayoutInflater.from(NewStudentActivity.this).inflate(R.layout.alert_search,null);
        AlertDialog.Builder alert = new AlertDialog.Builder(NewStudentActivity.this);
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


                dbHandler dbh = new dbHandler(NewStudentActivity.this);
                dbh.open();

                if(dbh.isGetCourse(edt.getText().toString())){
                    CourseAdapter courseAdapter = new CourseAdapter(NewStudentActivity.this,dbh.crsList(edt.getText().toString()));
                    recyclerView.setLayoutManager(new GridLayoutManager(NewStudentActivity.this,2));
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
