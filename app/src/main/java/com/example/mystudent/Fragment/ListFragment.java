package com.example.mystudent.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystudent.Adapter.StudentAdapter;
import com.example.mystudent.Model.Student;
import com.example.mystudent.R;
import com.example.mystudent.dbHandler;

import java.util.List;
import java.util.zip.Inflater;

public class ListFragment extends Fragment {

    RecyclerView rv;
    List<Student> items;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rv = (RecyclerView) inflater.inflate(R.layout.each_fragment_list_content,container,false);
        setupRecycler(rv);
        return rv;
    }

    private void setupRecycler(RecyclerView recyclerView){

        dbHandler dbh = new dbHandler(getContext());
        dbh.open();

        for (int i=1;i<=dbh.getCrsCount();i++){
            if(getArguments().get("FRG").equals(""+i) && dbh.getItemListCount(""+i)>0){
                items=dbh.display2(""+i);
            }
        }

       /*
        if(getArguments().get("FRG").equals("COMP")){
            items=dbh.display2("1");
        }
        if(getArguments().get("FRG").equals("MATH")){
            items=dbh.display2("2");
        }
        */
        dbh.close();

        if(items!=null){
            StudentAdapter studentAdapter = new StudentAdapter(getContext(),items);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(studentAdapter);

        }else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(null);
        }



    }


}
