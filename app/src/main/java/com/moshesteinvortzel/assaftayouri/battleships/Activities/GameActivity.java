package com.moshesteinvortzel.assaftayouri.battleships.Activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.TextView;
import android.content.Intent;

import com.moshesteinvortzel.assaftayouri.battleships.Adapters.ComputerGridAdapter;
import com.moshesteinvortzel.assaftayouri.battleships.Adapters.PlayerGridAdapter;
import com.moshesteinvortzel.assaftayouri.battleships.Animations.RectangleAnimationHandler;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Core.BattleShip;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Enum.DifficultyType;
import com.moshesteinvortzel.assaftayouri.battleships.R;
import com.moshesteinvortzel.assaftayouri.battleships.Services.SensorService;

public class GameActivity extends AppCompatActivity implements SensorService.IsensorChangable
{
    private BattleShip battleShip;
    private TextView textView;
    private GridView playerGridView;
    private GridView computerGridView;
    private RelativeLayout rectangleDraw;
    private RectangleAnimationHandler animatorHandler;
    private ProgressBar progressBar;
    private SensorService.SensorApi sensorApi;
    private boolean isBind;
    private boolean toReOrder = false;
    int score;
    private ServiceConnection sensorServiceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder)
        {
            if (iBinder != null)
            {
                sensorApi = (SensorService.SensorApi) iBinder;
                sensorApi.Init(GameActivity.this);
                isBind = true;
            }
            else
            {
                unbindService(this);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName)
        {
            isBind = false;
            sensorApi = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Bundle bundle = getIntent().getExtras();
        score = 0;
        battleShip = new BattleShip(DifficultyType.values()[bundle.getInt(getString(R.string.keyDifficulty))], getApplicationContext());
        textView = (TextView) findViewById(R.id.statingTurn);
        playerGridView = (GridView) findViewById(R.id.playerGrid);
        computerGridView = (GridView) findViewById(R.id.computerGrid);
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
                if (! battleShip.getComputerBoard().checkIfTouched(position))
                {
                    score++;
                    boolean win = battleShip.playerShoot(position);
                    if (win)
                    {
                        ActivatePlayerWinIntent();
                    }

                    battleShip.playerShoot(position);
                    ((ComputerGridAdapter) computerGridView.getAdapter()).notifyDataSetChanged();
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
                                ActivatePlayerLooseIntent();
                            }
                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    ((PlayerGridAdapter) playerGridView.getAdapter()).notifyDataSetChanged();
                                    textView.setText(R.string.playerTurn);
                                    computerGridView.setEnabled(true);
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
    }

    private void activateReOrder()
    {
        if (! toReOrder)
        {
            toReOrder = true;
            if (battleShip.HitPlayerBoard())
            {
                ActivatePlayerLooseIntent();
            }
            ((PlayerGridAdapter) playerGridView.getAdapter()).notifyDataSetChanged();
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
                            Thread.sleep(5000);
                            if (toReOrder)
                            {
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
    }

    private void deactivateReOrder()
    {
        if (toReOrder)
        {
            toReOrder = false;
            animatorHandler.stopAnimating();
        }
    }


    @Override
    protected void onStart()
    {
        super.onStart();
        Intent intent = new Intent(this, SensorService.class);
        bindService(intent, sensorServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        unbindService(sensorServiceConnection);

    }


    @Override
    public void StartAnimationBySensor()
    {
        if (! toReOrder)
        {
            activateReOrder();
        }
    }

    @Override
    public void StopAnimationBySensor()
    {
        if (toReOrder)
        {
            deactivateReOrder();
        }
    }

    public void ActivatePlayerWinIntent()
    {
        Intent finishActivity = new Intent(getApplicationContext(), FinishActivity.class);
        finishActivity.putExtra(getString(R.string.keyDifficulty), battleShip.getDifficultyType().ordinal());
        finishActivity.putExtra(getString(R.string.keyState), getString(R.string.Won));
        finishActivity.putExtra("score", score);
        startActivity(finishActivity);

    }

    public void ActivatePlayerLooseIntent()
    {
        Intent finishActivity = new Intent(getApplicationContext(), FinishActivity.class);
        finishActivity.putExtra(getString(R.string.keyDifficulty), battleShip.getDifficultyType().ordinal());
        finishActivity.putExtra(getString(R.string.keyState), getString(R.string.Loose));
        startActivity(finishActivity);
    }
}

