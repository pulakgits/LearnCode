package com.basetechz.quizo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.basetechz.quizo.databinding.ActivityQuizBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    ActivityQuizBinding binding;
    ArrayList<Question> questions;
    Question questionInd;
    int index = 0;
    CountDownTimer timer;
    FirebaseFirestore database;
    int correctAnswers= 0;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        questions = new ArrayList<>();
        database = FirebaseFirestore.getInstance();


        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.quiz_custom_dialogbox);
        dialog.setCancelable(false);
//
        TextView noBtn = dialog.findViewById(R.id.noBtn);
        TextView yesBtn = dialog.findViewById(R.id.yesBtn);


        MediaPlayer mediaPlayer1 = MediaPlayer.create(this,R.raw.btnsoundiii);

        binding.quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer1.start();
                dialog.show();
            }
        });
//
        MediaPlayer mediaPlayer2 = MediaPlayer.create(this,R.raw.btnsoundi);

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                mediaPlayer2.start();
                dialog.dismiss();
            }
        });
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer2.start();
                onBackPressed();
                dialog.dismiss();
            }
        });


        // get category id from RecyclerCategoryAdapter
            String catId = getIntent().getStringExtra("catId");
            String categoryPartId = getIntent().getStringExtra("categoryPartId");

//        // for generate random question under 7
        Random random = new Random();
        final int rand = random.nextInt(14);
//

        database.collection("categories").document(catId).collection("categoriesPart").document(categoryPartId)
                .collection("questions")
                                        .whereGreaterThanOrEqualTo("index",rand)
                                                .orderBy("index")
                                                        .limit(14).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.getDocuments().size()<14){
                            database.collection("categories").document(catId).collection("categoriesPart").document(categoryPartId)
                                    .collection("questions")
                                                .whereGreaterThanOrEqualTo("index",rand)
                                                    .orderBy("index")
                                                        .limit(14).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            for(DocumentSnapshot snapshot : queryDocumentSnapshots){
                                                Question question = snapshot.toObject(Question.class);
                                                questions.add(question);
                                            }
                                            resetTimer();
                                            setNextQuestion();
                                        }
                                    });
//
                        }else{
                            for(DocumentSnapshot snapshot : queryDocumentSnapshots){
                                Question question = snapshot.toObject(Question.class);
                                questions.add(question);
                            }
                            resetTimer();
                            setNextQuestion();

                        }
                    }
                });
//



    }

////     for timer
    void resetTimer(){
        timer = new CountDownTimer(60000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                binding.timer.setText(String.valueOf(millisUntilFinished/1000));
            }


//            // when time finished move to next question
//            // question complete then intent move to quizActivity to Result Activity
            @Override
            public void onFinish() {
                index++;
                if(questions.size()>index){
                    setNextQuestion();
                }else{

                    Intent intent = new Intent(QuizActivity.this,ResultActivity.class);
                    intent.putExtra("correct",correctAnswers);
                    intent.putExtra("total question",questions.size());
                    startActivity(intent);
                    finish();
                }


            }

        };

    }
//
//
//
//    // for change next question
    @SuppressLint("DefaultLocale")
    void setNextQuestion(){
        timer.start();
        if(index<questions.size()){


            // for questionCounter question index change
            binding.questionCounter.setText(String.format("%d/%d", (index+1), questions.size()));
            binding.quesNum.setText(String.format("%d)",(index+1)));

            questionInd = questions.get(index);
            binding.question.setText(questionInd.getQuestion());
            String queImg = questionInd.getQuestionImg();
            Picasso.get().load(queImg).into(binding.questionImg);
            binding.option1.setText(questionInd.getOption_1());
            binding.option2.setText(questionInd.getOption_2());
            binding.option3.setText(questionInd.getOption_3());
            binding.option4.setText(questionInd.getOption_4());
        }


    }



//    // one index option is worong and red
//    // then check which answer is right and background green
    void showAnswer(){
        if(questionInd.getAnswer().equals(binding.option1.getText().toString())){
            binding.option1.setBackgroundResource(R.drawable.option_right);
        }else if(questionInd.getAnswer().equals(binding.option2.getText().toString())){
            binding.option2.setBackgroundResource(R.drawable.option_right);
        }else if(questionInd.getAnswer().equals(binding.option3.getText().toString())){
            binding.option3.setBackgroundResource(R.drawable.option_right);
        }else if(questionInd.getAnswer().equals(binding.option4.getText().toString())){
            binding.option4.setBackgroundResource(R.drawable.option_right);
        }
    }


//    // create function to check answer is right or wrong
    void checkAnswer(TextView textView){
        String selectedAnswer = textView.getText().toString();
        if(selectedAnswer.equals(questionInd.getAnswer())){

            MediaPlayer mediaPlayer5 = MediaPlayer.create(this,R.raw.correctsound);
            mediaPlayer5.start();

            // for counter correctAnswers
            correctAnswers++;
            textView.setBackgroundResource(R.drawable.option_right);
        }else{
            MediaPlayer mediaPlayer3 = MediaPlayer.create(this,R.raw.wrondsound);
            mediaPlayer3.start();
            textView.setBackgroundResource(R.drawable.option_wrong);
            showAnswer();
        }
    }
//
//    // Reset Method
//    // Option background unselected
//
    void reset1(){
        binding.option1.setBackgroundResource(R.drawable.option_outline);
        binding.option2.setBackgroundResource(R.drawable.option_outline);
        binding.option3.setBackgroundResource(R.drawable.option_outline);
        binding.option4.setBackgroundResource(R.drawable.option_outline);
    }

    void reset2(){
        binding.option1.setBackgroundResource(R.drawable.option_outline_darkmode);
        binding.option2.setBackgroundResource(R.drawable.option_outline_darkmode);
        binding.option3.setBackgroundResource(R.drawable.option_outline_darkmode);
        binding.option4.setBackgroundResource(R.drawable.option_outline_darkmode);
    }





    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) throws InterruptedException {
        switch (view.getId()){
            case R.id.option_1:
            case R.id.option_2:
            case R.id.option_3:
            case R.id.option_4:

                // if one index question selected timer is off
                if(timer!=null)
                    timer.cancel();
                // when user click option selected variable is pass on check Answer method
                // checkAnswer method check answer is right or wrong
                TextView selected = (TextView)view;
                checkAnswer(selected);
                break;


             case R.id.nextBtn:
                 MediaPlayer mediaPlayer4 = MediaPlayer.create(this,R.raw.gamesoundi);
                 mediaPlayer4.start();
                 // when click next button reset method start
                 // reset method use for background unselected
                 reset2();
                 reset1();

                // Next question when press NextBtn
                 index++;
                 if(questions.size()>index){
                     setNextQuestion();
                 }else{
                     Intent intent = new Intent(QuizActivity.this,ResultActivity.class);
                     intent.putExtra("correct",correctAnswers);
                     intent.putExtra("total question",questions.size());
                     startActivity(intent);
                     finish();
                 }

        }
    }



}