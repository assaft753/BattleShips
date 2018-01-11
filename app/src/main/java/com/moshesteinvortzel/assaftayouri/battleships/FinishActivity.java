package com.moshesteinvortzel.assaftayouri.battleships;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;

import com.moshesteinvortzel.assaftayouri.battleships.Logic.Enum.DifficultyType;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.SQL.RecordHandler;
import com.moshesteinvortzel.assaftayouri.battleships.Services.RecordService;

public class FinishActivity extends AppCompatActivity
{

    private RecordService.RecordApi recordApi;
    private TextView stateTextView;
    private Button changeDifficulty;
    private Button reFight;
    private EditText playerName;
    private TextView scoreText;
    private TextView enterNameLabel;
    private Bundle bundle;
    private String state;
    private DifficultyType difficultyType;
    private int score;
    boolean isWon = false;
    private boolean isBind = false;
    private ServiceConnection recordServiceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder)
        {
            recordApi = (RecordService.RecordApi) iBinder;
            isBind = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName)
        {
            isBind = false;
            recordApi = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        stateTextView = (TextView) findViewById(R.id.stateText);
        changeDifficulty = (Button) findViewById(R.id.restartBtn);
        enterNameLabel = (TextView) findViewById(R.id.EnterNameText);
        reFight = (Button) findViewById(R.id.againBtn);
        playerName = (EditText) findViewById(R.id.nameInput);
        scoreText = (TextView) findViewById(R.id.textScore);
        scoreText.setAlpha(0);
        enterNameLabel.setAlpha(0);
        playerName.setAlpha(0);
        bundle = getIntent().getExtras();
        state = bundle.getString(getString(R.string.keyState));
        int ordinal = bundle.getInt(getString(R.string.keyDifficulty));
        difficultyType = DifficultyType.values()[ordinal];
        stateTextView.setText(state);
        if (getString(R.string.Won).equals(state))
        {
            score = bundle.getInt("score");
            scoreText.setText(String.valueOf(score));
            scoreText.setAlpha(1);
            enterNameLabel.setAlpha(1);
            playerName.setAlpha(1);
            isWon = true;
        }

        changeDifficulty.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                EnterPlayerRecord();
                Intent mainActivity = new Intent(view.getContext(), MainActivity.class);
                startActivity(mainActivity);
            }
        });

        reFight.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                EnterPlayerRecord();
                Intent gameActivity = new Intent(view.getContext(), GameActivity.class);
                gameActivity.putExtra(getString(R.string.keyDifficulty), difficultyType.ordinal());
                startActivity(gameActivity);
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Intent intent = new Intent(this, RecordService.class);
        bindService(intent, recordServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        unbindService(recordServiceConnection);

    }

    private void EnterPlayerRecord()
    {
        if (isWon)
        {
            if (isBind)
            {
                recordApi.InsertRecord(playerName.getText().toString(), score,difficultyType);
            }
        }
    }
}
