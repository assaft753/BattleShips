package com.moshesteinvortzel.assaftayouri.battleships;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.Image;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Button;
import android.content.Intent;

import com.moshesteinvortzel.assaftayouri.battleships.Logic.Enum.DifficultyType;
import com.moshesteinvortzel.assaftayouri.battleships.Services.SensorService;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{

    HashMap<Integer, Integer> Images = new HashMap<Integer, Integer>();
    HashMap<Integer, DifficultyType> Difficulty = new HashMap<Integer, DifficultyType>();
    DifficultyType difficultyType;
    Button fightBtn;
    Button recordBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        System.out.println("sss");
        CheckLocationPermission();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.Images.put(R.id.easy, R.drawable.easy);
        this.Images.put(R.id.medium, R.drawable.medium);
        this.Images.put(R.id.hard, R.drawable.hard);
        this.Difficulty.put(R.id.easy, DifficultyType.Easy);
        this.Difficulty.put(R.id.medium, DifficultyType.Medium);
        this.Difficulty.put(R.id.hard, DifficultyType.Hard);
        setDifficulty(R.id.easy);

        fightBtn = (Button) findViewById(R.id.okButton);
        recordBtn = (Button) findViewById(R.id.recordButton);

        fightBtn.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent gameActivity = new Intent(view.getContext(), GameActivity.class);
                gameActivity.putExtra(getString(R.string.keyDifficulty), difficultyType.ordinal());
                startActivity(gameActivity);
            }
        });

        recordBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent recordsActivity = new Intent(view.getContext(), RecordsActivity.class);
                recordsActivity.putExtra(getString(R.string.keyDifficulty), difficultyType.ordinal());
                startActivity(recordsActivity);
            }
        });
    }

    private void setDifficulty(int diff)
    {
        ((RadioButton) findViewById(diff)).toggle();
        int imageId = this.Images.get(diff);
        ((ImageView) findViewById(R.id.diffImage)).setImageResource(imageId);
        difficultyType = Difficulty.get(diff);

    }

    public void onButtonRadioClick(View view)
    {
        setDifficulty(view.getId());
    }

    private void CheckLocationPermission()
    {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }
}



