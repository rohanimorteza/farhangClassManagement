package com.example.mystudent;

import android.os.Bundle;

import com.example.mystudent.Adapter.FragmentAdapter;
import com.example.mystudent.Fragment.ListFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.View;

public class StdudentListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stdudent_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("");
        init();

    }

    private void init(){
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        if(viewPager != null){
            setupViewPager(viewPager);
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }


    public void setupViewPager(ViewPager viewPager){

        dbHandler dbHandler = new dbHandler(this);
        dbHandler.open();

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        ListFragment[] listFragment = new ListFragment[dbHandler.getCrsCount()];
        for (int i=0;i<dbHandler.getCrsCount();i++){

            Bundle bundle = new Bundle();
            int m = i+1;
            bundle.putString("FRG",""+m);
            listFragment[i] = new ListFragment();
            listFragment[i].setArguments(bundle);

            adapter.addFragment(listFragment[i],dbHandler.getCrsTitle(m+""));
        }

        dbHandler.close();
/*
        ListFragment cmp = new ListFragment();
        Bundle bundleCmp = new Bundle();
        bundleCmp.putString("FRG","COMP");
        cmp.setArguments(bundleCmp);

        ListFragment math = new ListFragment();
        Bundle bundleMath = new Bundle();
        bundleMath.putString("FRG","MATH");
        math.setArguments(bundleMath);


        adapter.addFragment(cmp,"کامپیوتر");
        adapter.addFragment(math,"ریاضی");
*/
        viewPager.setAdapter(adapter);
        viewPager.computeScroll();
        //viewPager.setCurrentItem(1);

    }



}
