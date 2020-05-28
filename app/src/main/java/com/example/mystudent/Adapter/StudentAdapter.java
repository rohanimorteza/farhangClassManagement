package com.example.mystudent.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystudent.Model.Student;
import com.example.mystudent.R;
import com.example.mystudent.SearchActivity;

import java.util.List;
import java.util.zip.Inflater;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {


    private Context context;
    private List<Student> studentList;

    public StudentAdapter(Context context, List<Student> studentList){

        this.context = context;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_item_student,parent,false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, final int position) {
            holder.name.setText("نام :"+studentList.get(position).getName());
            holder.course.setText("رشته : "+studentList.get(position).getCourse());

            byte[] p = studentList.get(position).getPhto();
            if(p!=null){
                Bitmap bitmap = BitmapFactory.decodeByteArray(p,0,p.length);
                holder.img.setImageBitmap(bitmap);
            }

            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context,SearchActivity.class);
                    i.putExtra("NUM",studentList.get(position).getNumber());
                    context.startActivity(i);
                }
            });


    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView name,course;
        ImageView img;
        CardView card;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txt_list_name);
            course = itemView.findViewById(R.id.txt_list_course);
            img = itemView.findViewById(R.id.img_list_pic);
            card = itemView.findViewById(R.id.crd_list_student);


        }
    }
}
