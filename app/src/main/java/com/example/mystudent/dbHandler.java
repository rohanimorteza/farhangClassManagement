package com.example.mystudent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.mystudent.Model.Course;
import com.example.mystudent.Model.Student;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class dbHandler extends SQLiteOpenHelper {

    SQLiteDatabase db;
    private static String DBPATH ;
    private static String DBNAME = "student.db" ;
    private Context context;

    public dbHandler(Context context) {
        super(context, "student", null, 1);
        this.context = context;
        DBPATH = context.getCacheDir().getPath()+"/"+DBNAME;

    }


    public void open(){
        if(DbExist()){

            try {
                //File file = new File(DBPATH);
                db = SQLiteDatabase.openDatabase(DBPATH,null,SQLiteDatabase.OPEN_READWRITE);
            }catch (Exception e){
                e.printStackTrace();
            }

        }else {
            if(CopyDb()){
                open();
            }
        }
    }

    public boolean DbExist(){
        File file = new File(DBPATH);
        if(file.exists()){
            return true;
        }else {
            return false;
        }
    }

    public boolean CopyDb(){
        try {

            FileOutputStream out = new FileOutputStream(DBPATH);
            InputStream in = context.getAssets().open(DBNAME);

            byte[] buffer = new byte[1024];
            int ch;

            while((ch=in.read(buffer))>0){
                out.write(buffer,0,ch);
            }

            out.flush();
            out.close();
            in.close();

            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public synchronized void close() {
        super.close();
    }

    public Student namayesh(String  number){
        Cursor cursor = db.rawQuery("select * from tbl_student where identifier='"+number+"'",null);
        cursor.moveToFirst();

        Student student = new Student();
        student.setId(cursor.getString(0));
        student.setNumber(cursor.getString(1));
        student.setName(cursor.getString(2));
        student.setCourse(cursor.getString(3));
        student.setPhto(cursor.getBlob(4));

        return student;
    }

        public List<Student> stdList(){
        List<Student> studentList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select id, identifier , name , crs_name , ax from tbl_student JOIN tbl_crs on tbl_crs.id_crs=tbl_student.reshte ",null);
        cursor.moveToFirst();

        do{
            Student student = new Student();
            student.setId(cursor.getString(0));
            student.setNumber(cursor.getString(1));
            student.setName(cursor.getString(2));
            student.setCourse(cursor.getString(3));
            student.setPhto(cursor.getBlob(4));

            studentList.add(student);

        }while (cursor.moveToNext());

        return studentList;
    }


        public List<Student> stdList(String name){
        List<Student> studentList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select id, identifier , name , crs_name , ax from tbl_student JOIN tbl_crs on tbl_crs.id_crs=tbl_student.reshte where name like'%"+name+"%'",null);
        cursor.moveToFirst();

        do{
            Student student = new Student();
            student.setId(cursor.getString(0));
            student.setNumber(cursor.getString(1));
            student.setName(cursor.getString(2));
            student.setCourse(cursor.getString(3));
            student.setPhto(cursor.getBlob(4));

            studentList.add(student);

        }while (cursor.moveToNext());

        return studentList;
    }

    public List<Student> display2(String id){
        List<Student> studentList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select id, identifier , name , crs_name , ax ,reshte from tbl_student JOIN tbl_crs on tbl_crs.id_crs=tbl_student.reshte where reshte ="+id,null);
        cursor.moveToFirst();

        do{
            Student student = new Student();
            student.setId(cursor.getString(0));
            student.setNumber(cursor.getString(1));
            student.setName(cursor.getString(2));
            student.setCourse(cursor.getString(3));
            student.setPhto(cursor.getBlob(4));

            studentList.add(student);

        }while (cursor.moveToNext());

        return studentList;
    }


    public List<Course> crsList(String name){
        List<Course> crsList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from tbl_crs where crs_name like '%"+name+"%'",null);
        cursor.moveToFirst();

        do{
            Course course = new Course();
            course.setId(cursor.getString(0));
            course.setName(cursor.getString(1));

            crsList.add(course);

        }while (cursor.moveToNext());

        return crsList;
    }

    public List<Course> crsList(){
        List<Course> crsList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from tbl_crs ",null);
        cursor.moveToFirst();

        do{
            Course course = new Course();
            course.setId(cursor.getString(0));
            course.setName(cursor.getString(1));

            crsList.add(course);

        }while (cursor.moveToNext());

        return crsList;
    }



    public void insert(String name,String number,String course , byte[] pic){
        ContentValues cv = new ContentValues();
        cv.put("identifier",number);
        cv.put("name",name);
        cv.put("reshte",course);
        cv.put("ax",pic);

        db.insert("tbl_student","name",cv);
    }
    public void insertCrs(String name){
        ContentValues cv = new ContentValues();
        cv.put("crs_name",name);

        db.insert("tbl_crs","name",cv);
    }

    public int getCount(String number){
        Cursor cursor = db.rawQuery("select name from tbl_student where identifier='"+number+"'",null);
        cursor.moveToFirst();
        return cursor.getCount();
    }
    public String getCrsTitle(String id){
        Cursor cursor = db.rawQuery("select crs_name from tbl_crs where id_crs='"+id+"'",null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }
    public int getCrsCount(){
        Cursor cursor = db.rawQuery("select crs_name from tbl_crs ",null);
        cursor.moveToFirst();
        return cursor.getCount();
    }
    public int getItemListCount(String id){
        Cursor cursor = db.rawQuery("select id, identifier , name , crs_name , ax ,reshte from tbl_student JOIN tbl_crs on tbl_crs.id_crs=tbl_student.reshte where reshte ="+id,null);
        cursor.moveToFirst();
        return cursor.getCount();
    }

    public boolean isGet(String name){
        Cursor cursor = db.rawQuery("select name from tbl_student where name like '%"+name+"%'",null);
        cursor.moveToFirst();
        return cursor.getCount()>0;
    }
    public boolean isGetCourse(String name){
        Cursor cursor = db.rawQuery("select crs_name from tbl_crs where crs_name like '%"+name+"%'",null);
        cursor.moveToFirst();
        return cursor.getCount()>0;
    }

   /*
    public String show(int i,int j){
        Cursor cursor = db.rawQuery("select name from tbl_book",null);
        cursor.moveToFirst();
        cursor.moveToPosition(i);

        return cursor.getString(j);
    }

    public Book show(int id){
        Book book = new Book();
        Cursor cursor = db.rawQuery("select * from tbl_book where id="+id,null);
        cursor.moveToFirst();
        book.setId(cursor.getString(0));
        book.setName(cursor.getString(1));
        book.setAuthor(cursor.getString(2));
        book.setPic(cursor.getBlob(3));

        return book;
    }



*/
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
