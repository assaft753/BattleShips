package com.moshesteinvortzel.assaftayouri.battleships;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.OnNmeaMessageListener;
import android.os.Debug;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.TextView;
import android.content.Intent;

import com.moshesteinvortzel.assaftayouri.battleships.Adapters.ComputerGridAdapter;
import com.moshesteinvortzel.assaftayouri.battleships.Adapters.PlayerGridAdapter;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Core.BattleShip;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Enum.DifficultyType;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Enum.PlayerType;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.SQL.RecordHandler;

public class GameActivity extends AppCompatActivity
{
    private BattleShip battleShip;
    private TextView textView;
    private GridView playerGridView;
    private GridView computerGridView;
    private RelativeLayout relativeLayout;
    private RelativeLayout rectangleDraw;
    private RectangleAnimationHandler animatorHandler;
    private ProgressBar progressBar;
    private boolean toReOrder = false;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        System.out.println("create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Bundle bundle = getIntent().getExtras();
        battleShip = new BattleShip(DifficultyType.values()[bundle.getInt(getString(R.string.keyDifficulty))], getApplicationContext());
        textView = (TextView) findViewById(R.id.statingTurn);
        playerGridView = (GridView) findViewById(R.id.playerGrid);
        computerGridView = (GridView) findViewById(R.id.computerGrid);
        relativeLayout = (RelativeLayout) findViewById(R.id.relative);
        rectangleDraw = (RelativeLayout) findViewById(R.id.rectangle);
        animatorHandler = new RectangleAnimationHandler(rectangleDraw.getBackground());
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
        playerGridView.setEnabled(false);
        computerGridView.setAdapter(new ComputerGridAdapter(battleShip.getComputerBoard(), Resources.getSystem().getDisplayMetrics().heightPixels / 2 / 9, getApplicationContext()));
        playerGridView.setAdapter(new PlayerGridAdapter(battleShip.getPlayerBoard(), Resources.getSystem().getDisplayMetrics().heightPixels / 4 / 9, getApplicationContext()));
        textView.setText(R.string.playerTurn);
        computerGridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
            {
                if (battleShip.getComputerBoard().checkIfTouched(position) == false)
                {
                    boolean win = battleShip.playerShoot(position);
                    if (win)
                    {
                        Intent finishActivity = new Intent(view.getContext(), FinishActivity.class);
                        finishActivity.putExtra(getString(R.string.keyDifficulty), battleShip.getDifficultyType().ordinal());
                        finishActivity.putExtra(getString(R.string.keyState), getString(R.string.Won));
                        startActivity(finishActivity);
                    }

                    battleShip.playerShoot(position);
                    ((ComputerGridAdapter) computerGridView.getAdapter()).notifyDataSetChanged();//
                    progressBar.setVisibility(View.VISIBLE);
                    computerGridView.setEnabled(false);
                    textView.setText(R.string.computerTurn);

                    Thread thd = new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            boolean win = battleShip.computerShoot();
                            if (win)
                            {
                                Intent finishActivity = new Intent(getApplicationContext(), FinishActivity.class);
                                finishActivity.putExtra(getString(R.string.keyDifficulty), battleShip.getDifficultyType().ordinal());
                                finishActivity.putExtra(getString(R.string.keyState), getString(R.string.Loose));
                                startActivity(finishActivity);
                            }
                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    ((PlayerGridAdapter) playerGridView.getAdapter()).notifyDataSetChanged();
                                    textView.setText(R.string.playerTurn);
                                    if (toReOrder == false)
                                    {
                                        computerGridView.setEnabled(true);
                                    }
                                }
                            });

                        }
                    });

                    thd.start();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Cube already played", Toast.LENGTH_SHORT).show();
                }

            }
        });

        /*new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(10000);
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            activateReOrder();
                        }
                    });
                    Thread.sleep(8000);
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            deactivateReOrder();
                        }
                    });
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

            }
        }).start();*/
        Intent finishActivity = new Intent(getApplicationContext(), FinishActivity.class);
        finishActivity.putExtra(getString(R.string.keyDifficulty), battleShip.getDifficultyType().ordinal());
        finishActivity.putExtra(getString(R.string.keyState), getString(R.string.Won));
        startActivity(finishActivity);
    }

    private void activateReOrder()
    {
        //System.out.println("activate");
        this.computerGridView.setEnabled(false);
        toReOrder = true;
        animatorHandler.startAnimating();
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    while (toReOrder)
                    {
                        Thread.sleep(2000);
                        if (toReOrder)
                        {
                            //System.out.println("enter toreorder");
                            battleShip.reArrangeShips();
                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    ((ComputerGridAdapter) computerGridView.getAdapter()).notifyDataSetChanged();

                                }
                            });
                        }
                    }
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void deactivateReOrder()
    {
        //System.out.println("deactivate");
        this.computerGridView.setEnabled(true);
        toReOrder = false;
        animatorHandler.stopAnimating();
    }

}

