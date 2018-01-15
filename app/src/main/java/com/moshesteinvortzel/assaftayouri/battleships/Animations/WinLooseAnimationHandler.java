package com.moshesteinvortzel.assaftayouri.battleships.Animations;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.view.View;

import java.time.OffsetDateTime;

/**
 * Created by assaftayouri on 15/01/2018.
 */

public class WinLooseAnimationHandler
{
    private final int OFFSET = 100;
    private ObjectAnimator downAnimator;
    private ObjectAnimator upAnimator;
    private View imageView;

    public WinLooseAnimationHandler(View imageView)
    {
        this.imageView = imageView;
        downAnimator = ObjectAnimator.ofFloat(imageView, "translationY", this.imageView.getTranslationY(), this.imageView.getTranslationY() + OFFSET);
        downAnimator.setDuration(1000);
        upAnimator = ObjectAnimator.ofFloat(imageView, "translationY", this.imageView.getTranslationY() + OFFSET, this.imageView.getTranslationY());
        upAnimator.setDuration(1000);

        upAnimator.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animator)
            {

            }

            @Override
            public void onAnimationEnd(Animator animator)
            {
                downAnimator.start();
            }

            @Override
            public void onAnimationCancel(Animator animator)
            {

            }

            @Override
            public void onAnimationRepeat(Animator animator)
            {

            }
        });

        downAnimator.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animator)
            {

            }

            @Override
            public void onAnimationEnd(Animator animator)
            {
                upAnimator.start();
            }

            @Override
            public void onAnimationCancel(Animator animator)
            {

            }

            @Override
            public void onAnimationRepeat(Animator animator)
            {

            }
        });
        downAnimator.start();
    }

    public void StartViewAnimation()
    {
        this.upAnimator.start();
    }
}
