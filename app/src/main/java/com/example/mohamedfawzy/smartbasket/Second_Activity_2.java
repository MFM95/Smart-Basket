package com.example.mohamedfawzy.smartbasket;



import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Second_Activity_2 extends AppCompatActivity {

    String id, name, school_id, level, score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second__activity_2);
        id = getIntent().getExtras().get("id").toString();
        name = getIntent().getExtras().get("name").toString();
        school_id = getIntent().getExtras().get("school_id").toString();
        level = getIntent().getExtras().get("level").toString();
        score = getIntent().getExtras().get("score").toString();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("name", name);
        bundle.putString("school_id", school_id);
        bundle.putString("level", level);
        bundle.putString("score", score);
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.placeHolder, detailsFragment);
        fragmentTransaction.commit();


    }




}
