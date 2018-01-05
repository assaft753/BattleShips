package com.moshesteinvortzel.assaftayouri.battleships;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Button;
import android.content.Intent;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Enum.DifficultyType;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{

    HashMap<Integer, Integer> Images = new HashMap<Integer, Integer>();
    HashMap<Integer, DifficultyType> Difficulty = new HashMap<Integer, DifficultyType>();
    DifficultyType difficultyType;
    Button fightBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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
}

