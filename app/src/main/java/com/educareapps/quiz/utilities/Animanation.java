package com.educareapps.quiz.utilities;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;


public class Animanation {

    public static final int noAnimation = 0;
    public static final int blink = 1;
    public static final int slideTopToBottom = 2;
    public static final int slideBottomToTop = 3;
    public static final int slideLeftToRight = 4;
    public static final int slideRightToLeft = 5;

    public static final String animationName[] = {"No Animation", "Blink", "SlideTopToBottom", "SlideBottomToTop", "SlideLeftToRight", "SlideRightToLeft"};

    public static final String tutorialAnimName[] = {"No Animation", "Without Reverse", "With Reverse"};

    public static void clear(View view) {
        view.setAnimation(null);
    }


    // zoomout animation method by sumon
    public static void zoomOut(final View v) {
        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(v,
                PropertyValuesHolder.ofFloat("scaleX", .80f),
                PropertyValuesHolder.ofFloat("scaleY", .80f));
        scaleDown.setDuration(200);
        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);
        scaleDown.setRepeatCount(1);
        scaleDown.start();
    }

    // zoomout animation method by sumon
    public static void zoomIn(final View v) {
        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(v,
                PropertyValuesHolder.ofFloat("scaleX", 1.10f),
                PropertyValuesHolder.ofFloat("scaleY", 1.10f));
        scaleDown.setDuration(500);
        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);
        scaleDown.setRepeatCount(1);
        scaleDown.start();
    }

    // wiggleAnimation By Rokan
    public static void wiggleAnimation(View v) {
        RotateAnimation rotate = new RotateAnimation(-30, 60, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate.setRepeatMode(2);
        //rotate.setRepeatCount(Animation.INFINITE);
        v.setAnimation(rotate);

    }

    // wiggleFeedBackAnimation By Rokan
    public static void wiggleFeedBackAnimation(View v) {
        RotateAnimation rotate = new RotateAnimation(-30, 60, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate.setRepeatMode(2);
        rotate.setRepeatCount(Animation.INFINITE);
        v.setAnimation(rotate);

    }


    // shakeAnimation Animation
    public static void shakeAnimation(View v) {
        RotateAnimation rotate = new RotateAnimation(-30, 60, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate.setRepeatCount(1);
        v.setAnimation(rotate);
    }

    // wobbleAnimation by Rokan
    public static void wobbleAnimation(final View v) {
        RotateAnimation rotate = new RotateAnimation(-10, 10, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        rotate.setDuration(400);
        rotate.setRepeatMode(2);
        rotate.setRepeatCount(1);
        v.setAnimation(rotate);
        rotate.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                v.clearAnimation();
            }
        });
    }

    // Blink Animation
    public static void blink(final View view) {

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(200); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
//        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(1);
        view.startAnimation(anim);
//        view.startAnimation(toAlpha);
    }

    // Blink Animation2
    public static void blink2(final View view) {

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(2000); //You can manage the blinking time with this parameter
//        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(1);
        view.startAnimation(anim);
//        view.startAnimation(toAlpha);
    }

    /**
     * ANIMATION TO SLIDE up to DOWN
     **/
    public static void topToBottom(View v) {
        TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f, -400.0f, 0.0f);
        animation.setDuration(1000); // animation duration
        //animation.setRepeatCount(1);
        v.setVisibility(View.VISIBLE);
        v.startAnimation(animation);
    }

    public static void bottomToTop(View v) {
        TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f, 400.0f, 0.0f);
        animation.setDuration(1000); // animation duration
        //animation.setRepeatCount(1);
        v.setVisibility(View.VISIBLE);
        v.startAnimation(animation);
    }


    /**********************************************************
     * Feedback Dialog Animation
     *****************************************************************/
    // shakeAnimation Animation
    public static void shakeFeedBackAnimation(View v) {
        RotateAnimation rotate = new RotateAnimation(-30, 60, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate.setRepeatCount(1);
        v.setAnimation(rotate);
    }

    // wobbleAnimation Animation
    public static void wobbleFeedBackAnimation(final View v) {
        RotateAnimation rotate = new RotateAnimation(-10, 10, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        rotate.setDuration(400);
        rotate.setRepeatMode(2);
        rotate.setRepeatCount(Animation.INFINITE);
        v.setAnimation(rotate);
        rotate.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                v.clearAnimation();
            }
        });
    }

    // Blink Animation
    public static void blinkFeedBackAnimation(final View view) {
        final AlphaAnimation toAlpha = new AlphaAnimation(1, 0);
        final AlphaAnimation fromAlpha = new AlphaAnimation(0, 1);
        toAlpha.setDuration(1000);
        toAlpha.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                animation.setRepeatCount(1);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                view.startAnimation(fromAlpha);
            }
        });

        fromAlpha.setDuration(1000);
        fromAlpha.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                animation.setRepeatCount(1);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.startAnimation(toAlpha);
            }
        });

        view.startAnimation(toAlpha);
    }


    /**
     * ANIMATION TO SLIDE up to DOWN
     **/
    public static void bottomToTopFeedBackAnimation(View v) {
        TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f, 700.0f, 0.0f);
        animation.setDuration(1000); // animation duration
        //animation.setRepeatCount(1);
        v.setVisibility(View.VISIBLE);
        v.startAnimation(animation);
    }


    public static void topToBottomFeedBackAnimation(View v) {
        TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f, -700.0f, 0.0f);
        animation.setDuration(1000); // animation duration
        //animation.setRepeatCount(1);
        v.setVisibility(View.VISIBLE);
        v.startAnimation(animation);
    }


    public static void slideRightToLeft(View v) {
        TranslateAnimation animation = new TranslateAnimation(700.0f, 0.0f, 0.0f, 0.0f);
        animation.setDuration(1000); // animation duration
        //animation.setRepeatCount(1);
        v.setVisibility(View.VISIBLE);
        v.startAnimation(animation);
    }

    public static void slideRightToLeftFeedBackAnimation(View v) {
        TranslateAnimation animation = new TranslateAnimation(700.0f, 0.0f, 0.0f, 0.0f);
        animation.setDuration(1000); // animation duration
        //animation.setRepeatCount(1);
        v.setVisibility(View.VISIBLE);
        v.startAnimation(animation);
    }

    public static void slideLeftToRight(View v) {
        TranslateAnimation animation = new TranslateAnimation(-700.0f, 0.0f, 0.0f, 0.0f);
        animation.setDuration(1000); // animation duration
        //animation.setRepeatCount(1);
        v.setVisibility(View.VISIBLE);
        v.startAnimation(animation);
    }

    public static void slideLeftToRightFeedBackAnimation(View v) {
        TranslateAnimation animation = new TranslateAnimation(-700.0f, 0.0f, 0.0f, 0.0f);
        animation.setDuration(1000); // animation duration
        //animation.setRepeatCount(1);
        v.setVisibility(View.VISIBLE);
        v.startAnimation(animation);
    }


    public void moveAnimation(View v) {
        TranslateAnimation animation = new TranslateAnimation(-50.0f, 0.0f, 0.0f, 0.0f);
        animation.setDuration(1500); // animation duration
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(2);
        v.startAnimation(animation);
    }

    public static void rotationAnimation(final View v) {
        AnimationSet as = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0, 1440,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);

        rotate.setDuration(1000);
        rotate.setFillAfter(true);
        rotate.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
               // zoomIn(container);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //rotate.setRepeatMode(ScaleAnimation.REVERSE);
        //rotate.setRepeatCount(2);
        v.setAnimation(rotate);
    }


    // handling tutorial

    public static void tutorialWithOutReverse(View v, int posX, int posY) {
        float animationPosX = (float) posX;
        float animationPosY = (float) posY;
        TranslateAnimation animation = new TranslateAnimation(0.0f, animationPosX, 0.0f, animationPosY);
        animation.setDuration(1000); // animation duration
        animation.setRepeatCount(Animation.INFINITE);
        v.setVisibility(View.VISIBLE);
        v.startAnimation(animation);
    }

    public static void tutorialWithReverse(View v, int posX, int posY) {
        float animationPosX = (float) posX;
        float animationPosY = (float) posY;
        TranslateAnimation animation = new TranslateAnimation(0.0f, animationPosX, 0.0f, animationPosY);
        animation.setDuration(1000); // animation duration
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(TranslateAnimation.REVERSE);
        v.setVisibility(View.VISIBLE);
        v.startAnimation(animation);
    }

    public static void blinkAnim(View view) {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);

        anim.setDuration(1000);
        anim.setFillAfter(true);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setRepeatMode(Animation.REVERSE);
        view.startAnimation(anim);
    }


}
