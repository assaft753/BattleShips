package com.moshesteinvortzel.assaftayouri.battleships;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by assaftayouri on 31/12/2017.
 */

public class RectangleAnimationHandler
{
    private ObjectAnimator startAnimator;
    private ObjectAnimator reverseAnimator;
    private ObjectAnimator currentAnimator;
    private Drawable background;

    public RectangleAnimationHandler(Drawable background)
    {
        this.background = background;
        this.background.setAlpha(0);
        this.startAnimator = ObjectAnimator.ofInt(background, "alpha", 0, 255);
        this.reverseAnimator = ObjectAnimator.ofInt(background, "alpha", 255, 0);
        this.currentAnimator = startAnimator;

        startAnimator.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {

            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                currentAnimator = reverseAnimator;
                reverseAnimator.start();
            }

            @Override
            public void onAnimationCancel(Animator animation)
            {

            }

            @Override
            public void onAnimationRepeat(Animator animation)
            {

            }
        });

        reverseAnimator.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {

            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                currentAnimator = startAnimator;
                startAnimator.start();
            }

            @Override
            public void onAnimationCancel(Animator animation)
            {

            }

            @Override
            public void onAnimationRepeat(Animator animation)
            {

            }
        });

    }

    public void startAnimating()
    {

        currentAnimator.start();
    }

    public void stopAnimating()
    {
        currentAnimator.pause();
        this.background.setAlpha(0);


    }
}
