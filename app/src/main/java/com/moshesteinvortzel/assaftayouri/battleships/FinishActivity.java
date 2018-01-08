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
import android.widget.TextView;
import android.widget.Button;
import android.view.View;

import com.moshesteinvortzel.assaftayouri.battleships.Logic.Enum.DifficultyType;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.SQL.RecordHandler;
import com.moshesteinvortzel.assaftayouri.battleships.Services.RecordService;

public class FinishActivity extends AppCompatActivity {

    private RecordService.RecordApi recordApi;
    private boolean isBind=false;
    private ServiceConnection recordServiceConnection=new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder)
        {
            System.out.println("Is Bind");
            recordApi=(RecordService.RecordApi)iBinder;
            isBind=true;

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName)
        {
            System.out.println("unbind");
            isBind=false;
            recordApi=null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        Bundle bundle = getIntent().getExtras();
        String state = bundle.getString(getString(R.string.keyState));
        TextView textView = (TextView) findViewById(R.id.stateText);
        textView.setText(state);
        Button changeDifficulty = (Button) findViewById(R.id.restartBtn);
        Button reFight = (Button) findViewById(R.id.againBtn);

        changeDifficulty.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent mainActivity = new Intent(view.getContext(), MainActivity.class);
                startActivity(mainActivity);
            }
        });

        reFight.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent gameActivity = new Intent(view.getContext(), GameActivity.class);
                Bundle bundle1 = getIntent().getExtras();
                int ordinal = bundle1.getInt(getString(R.string.keyDifficulty));
                gameActivity.putExtra(getString(R.string.keyDifficulty), ordinal);
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

    public void onInsertRecordClick(View view)
    {
        if(isBind)
        {
            recordApi.InsertRecord("assaf",6, DifficultyType.Easy);
        }
    }
}
