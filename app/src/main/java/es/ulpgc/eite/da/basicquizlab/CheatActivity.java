package es.ulpgc.eite.da.basicquizlab;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CheatActivity extends AppCompatActivity {

  public static final String TAG = "Quiz.CheatActivity";

  public final static String EXTRA_ANSWER = "EXTRA_ANSWER";
  public final static String EXTRA_CHEATED = "EXTRA_CHEATED";

  //public final static String STATE_ANSWER = "STATE_ANSWER";
  public final static String STATE_YES = "STATE_YES";
  public final static String STATE_CHEATED = "STATE_CHEATED";


  private Button noButton, yesButton;
  private TextView answerText;

  private int currentAnswer;
  private boolean answerCheated, yesButtonEnabled;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cheat);

    getSupportActionBar().setTitle(R.string.cheat_title);

    if (savedInstanceState != null) {
      currentAnswer=savedInstanceState.getInt(EXTRA_ANSWER);
      yesButtonEnabled=savedInstanceState.getBoolean(STATE_YES);
      answerCheated=savedInstanceState.getBoolean(STATE_CHEATED);

    }

    initLayoutData();
    updateLayoutContent();
    linkLayoutComponents();
    enableLayoutButtons();

    if (savedInstanceState != null) {
      if(yesButtonEnabled) {
        if (currentAnswer == 0) {
          answerText.setText(R.string.false_text);
        } else {
          answerText.setText(R.string.true_text);
        }
      }else{
        answerText.setText(R.string.empty_text);
       /* yesButton.setEnabled(false);
        noButton.setEnabled(false);

        returnCheatedStatus();*/

        }

      }

  }



  private void initLayoutData() {
    currentAnswer = getIntent().getExtras().getInt(EXTRA_ANSWER);
  }

  private void linkLayoutComponents() {
    noButton = findViewById(R.id.noButton);
    yesButton = findViewById(R.id.yesButton);

    answerText = findViewById(R.id.answerText);
  }

  private void enableLayoutButtons() {

    noButton.setOnClickListener(v -> onNoButtonClicked());
    yesButton.setOnClickListener(v -> onYesButtonClicked());
  }

  private void returnCheatedStatus() {
    Log.d(TAG, "returnCheatedStatus()");
    Log.d(TAG, "answerCheated: " + answerCheated);

    Intent intent = new Intent();
    intent.putExtra(EXTRA_CHEATED, answerCheated);
    setResult(RESULT_OK, intent);

    finish();
  }

  private void updateLayoutContent() {
    if(!yesButtonEnabled){
      answerText.setText(R.string.empty_text);
    }
    yesButton.setEnabled(yesButtonEnabled);
    noButton.setEnabled(!yesButtonEnabled);
   /* questionText.setText(questionArray[questionIndex]);

    // if(nextButtonEnabled == false)
    if(!nextButtonEnabled) {
      replyText.setText(R.string.empty_text);
    }

    nextButton.setEnabled(nextButtonEnabled);
    cheatButton.setEnabled(!nextButtonEnabled);
    falseButton.setEnabled(!nextButtonEnabled);
    trueButton.setEnabled(!nextButtonEnabled);*/
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
    //El estado de la respuesta
    outState.putInt(EXTRA_ANSWER, currentAnswer);
    outState.putBoolean(STATE_CHEATED, answerCheated);
    //El estado de los botones
    outState.putBoolean(STATE_YES, yesButtonEnabled);



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

  @Override
  public void onBackPressed() {
    Log.d(TAG, "onBackPressed()");

    returnCheatedStatus();
  }


  private void onYesButtonClicked() {
    yesButton.setEnabled(false);
    noButton.setEnabled(false);
    answerCheated = true;

    if(currentAnswer == 0) {
      answerText.setText(R.string.false_text);
    } else {
      answerText.setText(R.string.true_text);

    }
    yesButtonEnabled = true;
    updateLayoutContent();
  }

  private void onNoButtonClicked() {
    /*yesButton.setEnabled(false);
    noButton.setEnabled(false);*/

    returnCheatedStatus();
    yesButtonEnabled = false;
    updateLayoutContent();
  }

}
