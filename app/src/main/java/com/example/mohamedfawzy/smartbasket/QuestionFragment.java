package com.example.mohamedfawzy.smartbasket;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import POJO.Question;
import POJO.Student;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QuestionFragment extends Fragment {


    String TAG = getClass().getSimpleName();
    String Q, A, choiceA, choiceB, choiceC, subject, name;
    int school_id, level, id, score;

    int correctAnswer, studentAnswer = -1;

    TextView tv_name, tv_level, tv_score, tv_Q;
    Button b_choiceA, b_choiceB, b_choiceC;
    ProgressBar progressBar;
    APIInterface apIinterface;
    public QuestionFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static QuestionFragment newInstance(String param1, String param2) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apIinterface = APIClient.getClient().create(APIInterface.class);


        if (getArguments() != null) {
            Q = getArguments().getString("Q");
            A = getArguments().getString("A");
            choiceA = getArguments().getString("choiceA");
            choiceB = getArguments().getString("choiceB");
            choiceC = getArguments().getString("choiceC");
            subject = getArguments().getString("subject");
            school_id = Integer.parseInt(getArguments().getString("school_id"));
            level = Integer.parseInt(getArguments().getString("level"));
            id = Integer.parseInt(getArguments().getString("id"));
            score = Integer.parseInt(getArguments().getString("score"));
            name = getArguments().getString("name");
        }
        if(A.equals(choiceA))
            correctAnswer = 1;
        else if(A.equals(choiceB))
            correctAnswer = 2;
        else if(A.equals(choiceC))
            correctAnswer = 3;
        else correctAnswer = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_question, container, false);

        InitializeComponents(view);
        tv_name.setText(name);
        tv_level.setText("Level : " + level);
        tv_score.setText("Your Score : " + score);
        tv_Q.setText(Q);
        b_choiceA.setText("A.  " + choiceA);
        b_choiceB.setText("B.  " + choiceB);
        b_choiceC.setText("C.  " + choiceC);

        HandleEvents();
        return  view;
    }

    private void InitializeComponents(View view)
    {
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_level = (TextView) view.findViewById(R.id.tv_level);
        tv_score = (TextView) view.findViewById(R.id.tv_score);
        tv_Q = (TextView) view.findViewById(R.id.tv_Q);
        b_choiceA = (Button) view.findViewById(R.id.b_choiceA);
        b_choiceB = (Button) view.findViewById(R.id.b_choiceB);
        b_choiceC = (Button) view.findViewById(R.id.b_choiceC);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
    }


    private void HandleEvents()
    {

        /**
         *
         * Take user answer from bluetooth
         *
         *
         */




        b_choiceA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show progress bar and hide  button
                progressBar.setVisibility(View.VISIBLE);
                b_choiceB.setVisibility(View.GONE);
                b_choiceC.setVisibility(View.GONE);

                studentAnswer = 1;
                if(studentAnswer == correctAnswer)
                    RightAnswer();
                else
                    WrongAnswer();
            }
        });
        b_choiceB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show progress bar and hide  button
                progressBar.setVisibility(View.VISIBLE);
                b_choiceA.setVisibility(View.GONE);
                b_choiceC.setVisibility(View.GONE);

                studentAnswer = 2;
                if(studentAnswer == correctAnswer)
                    RightAnswer();
                else
                    WrongAnswer();
            }
        });
        b_choiceC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show progress bar and hide  button
                progressBar.setVisibility(View.VISIBLE);
                b_choiceA.setVisibility(View.GONE);
                b_choiceB.setVisibility(View.GONE);

                studentAnswer = 3;
                if(studentAnswer == correctAnswer)
                    RightAnswer();
                else
                    WrongAnswer();
            }
        });
    }

    private void RightAnswer()
    {
        score += 5;
        AddNewScore(score, 1);

    }
    private void WrongAnswer()
    {
        score += 1;
        AddNewScore(score, 0);

    }

    private void AddNewScore(int newscore, final int state)
    {
        Call <Void> call = apIinterface.AddScore(id, newscore);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call <Void>call, Response <Void>response) {
                Log.v("TAG", "AddScore / Response");

                // Hide progress bar and show  buttons
                progressBar.setVisibility(View.GONE);
                b_choiceA.setVisibility(View.VISIBLE);
                b_choiceB.setVisibility(View.VISIBLE);
                b_choiceC.setVisibility(View.VISIBLE);

                try {
                    display(state);

                } catch (Exception e) {
                    // Hide progress bar and show  buttons
                    progressBar.setVisibility(View.GONE);
                    b_choiceA.setVisibility(View.VISIBLE);
                    b_choiceB.setVisibility(View.VISIBLE);
                    b_choiceC.setVisibility(View.VISIBLE);

                    Log.v("TAG", "AddScore / catch");
                    e.printStackTrace();
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setMessage("Sorry..Something went wrong with the Connection!").setTitle("Error");
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }

                    });
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {

                // Hide progress bar and show  buttons
                progressBar.setVisibility(View.GONE);
                b_choiceA.setVisibility(View.VISIBLE);
                b_choiceB.setVisibility(View.VISIBLE);
                b_choiceC.setVisibility(View.VISIBLE);

                Log.v("TAG", "AddScore / Failure");
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
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

    void display(int state)
    {
        if(state == 1)
        {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setMessage("Great...Your score has increased by 5 points, your new Score is \n" + score).setTitle("Right Answer");
            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);

                }
            });
            alertDialog.create();
            alertDialog.show();
        }
        else
        {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setMessage("Oops...but don't worry, your score has increased by 1 point, your new Score is \n" + score).setTitle("Wrong Answer");
            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            });
            alertDialog.create();
            alertDialog.show();
        }

    }
}
