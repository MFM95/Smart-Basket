package com.example.mohamedfawzy.smartbasket;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import POJO.Student;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    String TAG = this.getClass().getSimpleName();
    Button b_go;
    EditText et_id;
    ProgressBar progressBar;

    APIInterface apIinterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitializeComponents();
        apIinterface = APIClient.getClient().create(APIInterface.class);

        b_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tryParseInt(et_id.getText().toString()))
                {
                    int id = Integer.parseInt(et_id.getText().toString());

                    // show progress bar and hide go button
                    progressBar.setVisibility(View.VISIBLE);
                    b_go.setVisibility(View.GONE);

                    GetStudentById(id);
                }
                else
                {
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                    alertDialog.setMessage("Sorry..Something went wrong with your ID!").setTitle("Error");
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.create();
                    alertDialog.show();
                }



            }
        });



    }

    boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void InitializeComponents()
    {
        b_go = (Button) findViewById(R.id.b_go);
        et_id = (EditText) findViewById(R.id.et_id);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.GONE);
    }

    // GET STUDENT BY ID
    private void GetStudentById(int id)
    {
        Log.v(TAG, "GetStudentById");
        Call<Student> call = apIinterface.getStudentById(id);
        call.enqueue(new Callback<Student>() {


            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                try {
                    Log.v("TAG", "GetStudentById / onResponse");
                    Student student = response.body();
                    String id = String.valueOf(student.getID());
                    String name = student.getStudent_name();
                    String school_id = String.valueOf(student.getSchool_id());
                    String level = String.valueOf(student.getLevel());
                    String score = String.valueOf(student.getScore());


                    Intent intent = new Intent(MainActivity.this, Second_Activity_2.class);
                    intent.putExtra("id", id);
                    intent.putExtra("name", name);
                    intent.putExtra("school_id", school_id);
                    intent.putExtra("level", level);
                    intent.putExtra("score", score);
                    startActivity(intent);
                    overridePendingTransition(R.anim.lef_to_right, R.anim.right_to_left);
                    // Hide progress bar and show go button
                    progressBar.setVisibility(View.GONE);
                    b_go.setVisibility(View.VISIBLE);

                }
                catch (Exception e) {

                    // Hide progress bar and show go button
                    progressBar.setVisibility(View.GONE);
                    b_go.setVisibility(View.VISIBLE);
                    Log.v("TAG", "GetStudentById ..catch");
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                    alertDialog.setMessage("Sorry..Something went wrong with the Connection!").setTitle("Error");
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.create();
                    alertDialog.show();
                }

            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Log.v("TAG", "GetStudentById ..onFailure");

                // Hide progress bar and show go button
                progressBar.setVisibility(View.GONE);
                b_go.setVisibility(View.VISIBLE);

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setMessage("Sorry..Something went wrong with the Connection!").setTitle("Error");
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.create();
                alertDialog.show();
            }
        });


    }

}
