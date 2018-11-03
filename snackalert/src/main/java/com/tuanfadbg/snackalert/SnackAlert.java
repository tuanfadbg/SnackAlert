package com.tuanfadbg.snackalert;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Tuan FADBG.
 * Date: 11/2/18
 */
public class SnackAlert extends Dialog {
    public static final int SUCCESS = 1;
    public static final int WARNING = 2;
    public static final int ERROR = 3;
    public static final int NORMAL = 4;
    private static final float MIN_DISTANCE = 50;
    Context context;
    private String title;
    private String message;
    private int duration = 2000;
    private int durationAnimationShow = 300;
    private int durationAnimationHide = 100;
    private int durationSwipeToDimiss = 500;
    private RelativeLayout rlMain;
    private TextView txtTitle, txtMessage;
    private ImageView image;
    private int type = SUCCESS;
    private boolean isSwipeToDimiss;
    private Drawable imageDrawable;
    private int titleColor, messageColor, backgroundColor;

    private Interpolator interpolatorShow, interpolatorHide, interpolatorSwipeToDimiss;
    float x1, x2;
    int width;

    public SnackAlert(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        setContentView(R.layout.layout_alert);

        init();
    }


    public SnackAlert(Context context, String title, String message, int duration, int type) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.title = title;
        this.message = message;
        this.duration = duration;
        this.type = type;
        setContentView(R.layout.layout_alert);

        init();
    }

    private void init() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        txtMessage = findViewById(R.id.message);
        txtTitle = findViewById(R.id.title);
        rlMain = findViewById(R.id.rl_main);
        image = findViewById(R.id.image);
    }

    public SnackAlert setImageDrawable(Drawable imageDrawable) {
        this.imageDrawable = imageDrawable;
        return this;
    }

    public SnackAlert setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    public SnackAlert setMessageColor(int messageColor) {
        this.messageColor = messageColor;
        return this;
    }

    public SnackAlert setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public SnackAlert setTitle(String title) {
        this.title = title;
        return this;
    }

    public SnackAlert setMessage(String message) {
        this.message = message;
        return this;
    }

    public SnackAlert setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public SnackAlert setInterpolatorShow(Interpolator interpolatorShow) {
        this.interpolatorShow = interpolatorShow;
        return this;
    }

    public SnackAlert setInterpolatorHide(Interpolator interpolatorHide) {
        this.interpolatorHide = interpolatorHide;
        return this;
    }

    public SnackAlert setType(int type) {
        this.type = type;
        return this;
    }

    public SnackAlert setDurationAnimationShow(int durationAnimationShow) {
        this.durationAnimationShow = durationAnimationShow;
        return this;
    }

    public SnackAlert setDurationAnimationHide(int durationAnimationHide) {
        this.durationAnimationHide = durationAnimationHide;
        return this;
    }

    public SnackAlert setSwipeToDimiss(boolean swipeToDimiss) {

        isSwipeToDimiss = swipeToDimiss;
        if (swipeToDimiss) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            width = displayMetrics.widthPixels;
            rlMain.setOnTouchListener((View v, MotionEvent event) -> {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        x2 = event.getX();
                        if (x1 - x2 > MIN_DISTANCE) {
                            dismissRightToLeft();
                            return false;
                        } else if (x2 - x1 > MIN_DISTANCE) {
                            dismissLeftToRight();
                            return false;
                        }
                        return true;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        if (x1 - x2 > MIN_DISTANCE) {
                            dismissRightToLeft();
                            return false;
                        } else if (x2 - x1 > MIN_DISTANCE) {
                            dismissLeftToRight();
                            return false;
                        }
                        return true;
                }
                return true;
            });
        }
        return this;
    }

    public SnackAlert setDurationSwipeToDimiss(int durationSwipeToDimiss) {
        this.durationSwipeToDimiss = durationSwipeToDimiss;
        return this;
    }

    public SnackAlert setInterpolatorSwipeToDimiss(Interpolator interpolatorSwipeToDimiss) {
        this.interpolatorSwipeToDimiss = interpolatorSwipeToDimiss;
        return this;
    }

    private void dismissRightToLeft() {
        Animation animationToLeft = new TranslateAnimation(0, -width, 0, 0);
        startAndDimissAlert(animationToLeft);
    }

    private void dismissLeftToRight() {
        Animation animationToRight = new TranslateAnimation(0, width, 0, 0);
        startAndDimissAlert(animationToRight);
    }


    private void startAndDimissAlert(Animation a) {
        a.setDuration(durationSwipeToDimiss);
        a.setFillAfter(true);
        if (interpolatorSwipeToDimiss != null) {
            a.setInterpolator(new DecelerateInterpolator());
        }
        a.setFillAfter(true);
        a.setFillEnabled(true);
        rlMain.startAnimation(a);
        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(() -> dismiss(), durationSwipeToDimiss);
    }

    @Override
    public void show() {
        if (backgroundColor == 0) {
            switch (type) {
                case SUCCESS: {
                    rlMain.setBackgroundColor(Color.parseColor("#D91f933a"));
                    break;
                }
                case WARNING: {
                    rlMain.setBackgroundColor(Color.parseColor("#D99F6000"));
                    break;
                }
                case ERROR: {
                    rlMain.setBackgroundColor(Color.parseColor("#D9da251f"));
                    break;
                }
                case NORMAL: {
                    rlMain.setBackgroundColor(Color.parseColor("#D9ffbc40"));
                    break;
                }
            }
        } else {
            rlMain.setBackgroundColor(backgroundColor);
        }
        if (title == null) {
            switch (type) {
                case SUCCESS: {
                    txtTitle.setText("Success");
                    break;
                }
                case WARNING: {
                    txtTitle.setText("Warnning");
                    break;
                }
                case ERROR: {
                    txtTitle.setText("Error");
                    break;
                }
                case NORMAL: {
                    txtTitle.setText("Notification");
                    break;
                }
            }
        } else
            txtTitle.setText(title);
        txtMessage.setText(message);

        if (titleColor != 0) {
            txtTitle.setTextColor(titleColor);
        }
        if (messageColor != 0) {
            txtMessage.setTextColor(messageColor);
        }

        if (imageDrawable != null) {
            image.setImageDrawable(imageDrawable);
        } else image.setVisibility(View.GONE);

        Animation animationShow = new TranslateAnimation(0, 0, -80, 0);
        animationShow.setDuration(durationAnimationShow);
        if (interpolatorShow != null)
            animationShow.setInterpolator(interpolatorShow);
        animationShow.setFillAfter(true);

        Animation animationClose = new TranslateAnimation(0, 0, 0, -80);
        animationClose.setDuration(durationAnimationHide);
        animationClose.setFillAfter(true);
        if (interpolatorHide != null)
            animationClose.setInterpolator(interpolatorHide);


        rlMain.startAnimation(animationShow);
        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(() -> rlMain.startAnimation(animationClose), duration + durationAnimationShow);
        handler.postDelayed(() -> SnackAlert.this.dismiss(), duration + durationAnimationShow + durationAnimationHide);
        super.show();
    }
}


