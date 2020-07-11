package com.example.mystudent.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystudent.EditStudent;
import com.example.mystudent.MainActivity;
import com.example.mystudent.Model.Course;
import com.example.mystudent.NewStudentActivity;
import com.example.mystudent.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourceViewHolder> {

    private Context context;
    private List<Course> courseList;

    public CourseAdapter(Context context, List<Course> courseList){
        this.context = context;
        this.courseList = courseList;
    }


    @NonNull
    @Override
    public CourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_item_crs,null);
        return new CourceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourceViewHolder holder, final int position) {
        holder.crsName.setText(courseList.get(position).getName());
        holder.crscrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(MainActivity.DEP_POS.equals("2")){
                    ((NewStudentActivity)context).setCourse(courseList.get(position).getId(),courseList.get(position).getName());
                }else if(MainActivity.DEP_POS.equals("1")){

                }else if(MainActivity.DEP_POS.equals("3")){
                    ((EditStudent)context).setCourse(courseList.get(position).getId(),courseList.get(position).getName());
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class CourceViewHolder extends RecyclerView.ViewHolder {

        TextView crsName;
        CardView crscrd;

        public CourceViewHolder(@NonNull View itemView) {
            super(itemView);
            crsName = itemView.findViewById(R.id.txt_list_coursename);
            crscrd = itemView.findViewById(R.id.crd_list_crslist);
        }
    }

}
