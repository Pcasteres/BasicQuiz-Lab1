package es.ulpgc.eite.da.basicquizlab;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class QuestionActivity extends AppCompatActivity {

  public static final String TAG = "Quiz.QuestionActivity";

  public static final int CHEAT_REQUEST = 1;

  public final static String STATE_INDEX = "STATE_INDEX";
  public final static String STATE_NEXT = "STATE_NEXT";
  public final static String STATE_ANSWER = "STATE_ANSWER";

  private Button falseButton, trueButton,cheatButton, nextButton;
  private TextView questionText, replyText;

  private String[] questionArray;
  private int questionIndex=0;
  private int[] replyArray;
  private boolean nextButtonEnabled, trueButtonEnabled;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_question);

    getSupportActionBar().setTitle(R.string.question_title);


    Log.d(TAG, "onCreate()");

    // savedInstanceState != null: si estoy recreando la actividad
    if (savedInstanceState != null) {
      questionIndex=savedInstanceState.getInt(STATE_INDEX);
      nextButtonEnabled=savedInstanceState.getBoolean(STATE_NEXT);
      trueButtonEnabled=savedInstanceState.getBoolean(STATE_ANSWER);
    }

    initLayoutData();
    linkLayoutComponents();
    updateLayoutContent();
    enableLayoutButtons();

    // si estoy recreando la actividad (no creandola por primera vez)
    if (savedInstanceState != null) {

      // has contestado a la pregunta
      if(nextButtonEnabled) {

        if(trueButtonEnabled) { // si has contestado true
          if(replyArray[questionIndex] == 1) {
            replyText.setText(R.string.correct_text);
          } else {
            replyText.setText(R.string.incorrect_text);
          }

        } else { // si has contestado false
          if(replyArray[questionIndex] == 0) {
            replyText.setText(R.string.correct_text);
          } else {
            replyText.setText(R.string.incorrect_text);
          }
        }
      }

    }
  }


  private void enableLayoutButtons() {

    trueButton.setOnClickListener(v -> onTrueButtonClicked());
    falseButton.setOnClickListener(v -> onFalseButtonClicked());
    nextButton.setOnClickListener(v -> onNextButtonClicked());
    cheatButton.setOnClickListener(v -> onCheatButtonClicked());
  }

  private void initLayoutData() {
    questionArray=getResources().getStringArray(R.array.question_array);
    replyArray=getResources().getIntArray(R.array.reply_array);
  }


  private void linkLayoutComponents() {
    falseButton = findViewById(R.id.falseButton);
    trueButton = findViewById(R.id.trueButton);
    cheatButton = findViewById(R.id.cheatButton);
    nextButton = findViewById(R.id.nextButton);

    questionText = findViewById(R.id.questionText);
    replyText = findViewById(R.id.replyText);
  }


  private void updateLayoutContent() {
    questionText.setText(questionArray[questionIndex]);

    // if(nextButtonEnabled == false)
    if(!nextButtonEnabled) {
      replyText.setText(R.string.empty_text);
    }

    nextButton.setEnabled(nextButtonEnabled);
    cheatButton.setEnabled(!nextButtonEnabled);
    falseButton.setEnabled(!nextButtonEnabled);
    trueButton.setEnabled(!nextButtonEnabled);
  }


  private void onTrueButtonClicked() {

    /*
    if(nextButtonEnabled) {
      return;
    }
    */

    if(replyArray[questionIndex] == 1) {
      replyText.setText(R.string.correct_text);
    } else {
      replyText.setText(R.string.incorrect_text);
    }

    nextButtonEnabled = true;
    trueButtonEnabled = true;
    updateLayoutContent();
  }

  private void onFalseButtonClicked() {

    /*
    if(nextButtonEnabled) {
      return;
    }
    */

    if(replyArray[questionIndex] == 0) {
      replyText.setText(R.string.correct_text);
    } else {
      replyText.setText(R.string.incorrect_text);
    }

    nextButtonEnabled = true;
    trueButtonEnabled=false;
    updateLayoutContent();

  }

  private void onCheatButtonClicked() {

    /*
    if(nextButtonEnabled) {
      return;
    }
    */

    Intent intent = new Intent(this, CheatActivity.class);
    intent.putExtra(CheatActivity.EXTRA_ANSWER, replyArray[questionIndex]);
    startActivityForResult(intent, CHEAT_REQUEST);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    Log.d(TAG, "onActivityResult()");

    if (requestCode == CHEAT_REQUEST) {
      if (resultCode == RESULT_OK) {

        boolean answerCheated =
            data.getBooleanExtra(CheatActivity.EXTRA_CHEATED, false);

        Log.d(TAG, "answerCheated: " + answerCheated);

        if(answerCheated) {
          nextButtonEnabled = true;
          onNextButtonClicked();
        }
      }
    }
  }


  @Override
  protected void onResume() {
    super.onResume();

    Log.d(TAG, "onResume()");
  }

  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);

    Log.d(TAG, "onSaveInstanceState()");

    // indice para acceder a pregunta y respuesta actuales
    outState.putInt(STATE_INDEX, questionIndex);
    // boolean para saber si has contestado o no
    // nextButtonEnabled=true: has contestado a la pregunta
    // nextButtonEnabled=false: no has contestado a la pregunta
    outState.putBoolean(STATE_NEXT, nextButtonEnabled);
    // boolean para saber si has contestado true o false
    // trueButtonEnabled=true: has contestado true
    // trueButtonEnabled=false: has contestado false
    outState.putBoolean(STATE_ANSWER, trueButtonEnabled);
  }

  @Override
  protected void onPause() {
    super.onPause();

    Log.d(TAG, "onPause()");
  }


  @Override
  protected void onDestroy() {
    super.onDestroy();

    Log.d(TAG, "onDestroy()");
  }


  private void onNextButtonClicked() {
    Log.d(TAG, "onNextButtonClicked()");

    /*
    if(!nextButtonEnabled) {
      return;
    }
    */

    nextButtonEnabled = false;
    questionIndex++;

    // si queremos que el quiz acabe al llegar
    // a la ultima pregunta debemos comentar esta linea
    checkIndexData();

    if(questionIndex < questionArray.length) {
      updateLayoutContent();
    }

  }

  private void checkIndexData() {

    // hacemos que si llegamos al final del quiz
    // volvamos a empezarlo nuevamente
    if(questionIndex == questionArray.length) {
      questionIndex=0;
    }

  }

}
