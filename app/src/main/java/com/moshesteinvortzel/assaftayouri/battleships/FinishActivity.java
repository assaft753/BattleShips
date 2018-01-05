package com.moshesteinvortzel.assaftayouri.battleships;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;

public class FinishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        Bundle bundle = getIntent().getExtras();
        String state = bundle.getString(getString(R.string.keyState));
        TextView textView = (TextView) findViewById(R.id.stateText);
        textView.setText(state);
        Button changeDifficulty = (Button) findViewById(R.id.restartBtn);
        Button reFight = (Button) findViewById(R.id.againBtn);

        changeDifficulty.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(view.getContext(), MainActivity.class);
                startActivity(mainActivity);
            }
        });

        reFight.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gameActivity = new Intent(view.getContext(), GameActivity.class);
                Bundle bundle1 = getIntent().getExtras();
                int ordinal = bundle1.getInt(getString(R.string.keyDifficulty));
                gameActivity.putExtra(getString(R.string.keyDifficulty), ordinal);
                startActivity(gameActivity);
            }
        });
    }
}
