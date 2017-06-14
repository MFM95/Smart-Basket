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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import POJO.Question;
import POJO.Student;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    String TAG = getClass().getSimpleName();
    //Spinner spinner;
    String selectedSubject = "Arabic";
    TextView tv_name, tv_level, tv_score;
    Button b_getQ, b_subjects;
    ProgressBar progressBar;
    static String id, name, school_id, level, score;
    String [] subjects = new  String[] {"Arabic", "Math", "English", "Science", "Geographic&History"};
    APIInterface apIinterface;



    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance() {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }
    public DetailsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            id = getArguments().getString("id");
            name = getArguments().getString("name");
            school_id = getArguments().getString("school_id");
            level = getArguments().getString("level");
            score = getArguments().getString("score");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        apIinterface = APIClient.getClient().create(APIInterface.class);

        View view = inflater.inflate(R.layout.fragment_details, container, false);
        InitializeComponents(view);
        //SetupSpinner();
        tv_name.setText(name);
        tv_level.setText("Level : " + level);
        tv_score.setText("Your Score : " + score);
        return  view;
    }


    private void InitializeComponents(View view)
    {
        tv_name = (TextView)  view.findViewById(R.id.tv_name);
        tv_level = (TextView) view.findViewById(R.id.tv_level);
        tv_score = (TextView) view.findViewById(R.id.tv_score);
      //  spinner = (Spinner)   view.findViewById(R.id.spinner);
        b_getQ = (Button) view.findViewById(R.id.b_getQ);
        b_subjects = (Button) view.findViewById(R.id.b_subjects);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);

        b_getQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // show progress bar and hide  button
                progressBar.setVisibility(View.VISIBLE);
                b_getQ.setVisibility(View.GONE);

                getQuestion();
            }
        });

        b_subjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateSubjectsList();
            }
        });
    }

    private void SetupSpinner()
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, subjects);
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        //    spinner.setOnItemSelectedListener(this);
         //   spinner.setAdapter(adapter);
        }


    List<Question> questions;

    private void getQuestion()
    {
        Log.v("TAG", "getQuestion");
        Call<List<Question>> call = apIinterface.getQuestionForStudent(Integer.parseInt(school_id), Integer.parseInt(level.toString()), selectedSubject);
        call.enqueue(new Callback<List<Question>>() {
                         @Override
                         public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                             Log.v("TAG", "getQuestion / Response");


                             try {
                                 questions = response.body();

                                 if(questions.isEmpty())
                                 {
                                 //    ShowDialog("Sorry..No Questions are available for this subject!", "No Questions");
                                 }

                                 Question question = getRandQuestion(questions);
                                 String Q = question.getQ();
                                 String A = question.getA();
                                 String choiceA = question.getChoiceA();
                                 String choiceB = question.getChoiceB();
                                 String choiceC = question.getChoiceC();
                                 int school_id = question.getSchool_id();
                                 int level = question.getLevel();
                                 String subject = question.getSubject();


                                 // Hide progress bar and show button
                                 progressBar.setVisibility(View.GONE);
                                 b_getQ.setVisibility(View.VISIBLE);

                                 FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                                 Bundle bundle = new Bundle();
                                 bundle.putString("school_id", String.valueOf(school_id));
                                 bundle.putString("Q", Q);
                                 bundle.putString("A", A);
                                 bundle.putString("choiceA", choiceA);
                                 bundle.putString("choiceB", choiceB);
                                 bundle.putString("choiceC", choiceC);
                                 bundle.putString("level", String.valueOf(level));
                                 bundle.putString("subject", subject);
                                 bundle.putString("id", id);
                                 bundle.putString("name", name);
                                 bundle.putString("score", score);
                                 QuestionFragment questionFragment = new QuestionFragment();
                                 questionFragment.setArguments(bundle);

                               //  fragmentTransaction.setCustomAnimations(R.anim.right_to_left, R.anim.lef_to_right);
                                 fragmentTransaction.replace(R.id.placeHolder, questionFragment);

                                 fragmentTransaction.commit();
                             } catch (Exception e) {
                                 Log.v("TAG", "getQuestion / catch");

                                 // Hide progress bar and show button
                                 progressBar.setVisibility(View.GONE);
                                 b_getQ.setVisibility(View.VISIBLE);


                                 e.printStackTrace();

                                 String msg = "Sorry..Something went wrong with the Connection ";
                                 String title = "Error";
                                 if(questions.isEmpty())
                                 {
                                     msg += "... No Questions are available for this subject!";
                                     title = "No Questions";
                                 }

                                 ShowDialog(msg, title);

                             }
                         }
                         @Override
                         public void onFailure(Call<List<Question>> call, Throwable t) {
                             Log.v("TAG", "getQuestion / Failure");

                             // Hide progress bar and show button
                             progressBar.setVisibility(View.GONE);
                             b_getQ.setVisibility(View.VISIBLE);

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



    void ShowDialog(String msg, String title)
    {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setMessage(msg).setTitle(title);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        });
        alertDialog.create();
        alertDialog.show();
    }

    private Question getRandQuestion(List<Question> questions)
    {
        int size = questions.size();
        Random random = new Random();
        int i = random.nextInt(size);
        return questions.get(i);
    }


    // dialog ListView for Subjects
    void CreateSubjectsList()
    {
        AlertDialog.Builder dialogList = new AlertDialog.Builder(getActivity());
        dialogList.setTitle("Select a Subject");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_singlechoice );
        arrayAdapter.addAll(subjects);
        dialogList.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogList.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedSubject = arrayAdapter.getItem(which);
                b_subjects.setText(selectedSubject);
            }
        });
        dialogList.show();

    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        selectedSubject = subjects[position];


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
