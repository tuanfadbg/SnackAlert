package com.tuanfadbg.snackalert;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private float backgroundAlpha = (float) 0.8;
    private String title;
    private String message;
    private int duration = 2000;
    private int durationAnimationShow = 300;
    private int durationAnimationHide = 100;
    private int durationSwipeToDimiss = 500;
    private RelativeLayout rlMain;
    private TextView txtTitle;
    private TextView txtMessage;
    private ImageView image;
    private int type = SUCCESS;
    private boolean isSwipeToDimiss;
    private Drawable imageDrawable;
    private int titleColor, messageColor, backgroundColor;
    private boolean autoHide = true;
    private Interpolator interpolatorShow, interpolatorHide, interpolatorSwipeToDimiss;
    private float x1, x2, y1, y2;
    private int width, height;
    private OnAlertClickListener onAlertClickListener;
    private boolean backPressToDismiss = false;


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
        Window window = this.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.TOP;
        window.setAttributes(wlp);
//        getWindow().getAttributes().flags
        int flag = ((Activity) context).getWindow().getAttributes().flags;
        window.setFlags(flag, flag);
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        txtMessage = findViewById(R.id.message);
        txtTitle = findViewById(R.id.title);
        rlMain = findViewById(R.id.rl_main);
        image = findViewById(R.id.image);

        float dip = 80;
        Resources r = context.getResources();
        height = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                r.getDisplayMetrics()
        );

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            Toast.makeText(context, "out", Toast.LENGTH_SHORT).show();
            this.dismiss();
        }
        ((Activity) context).dispatchTouchEvent(event);
        return false;
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
                        y1 = event.getY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        x2 = event.getX();
                        y2 = event.getY();
                        return checkToDimiss();
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        y2 = event.getY();
                        return checkToDimiss();
                }
                return true;
            });
        }
        return this;
    }

    private boolean checkToDimiss() {
        if (x1 - x2 > MIN_DISTANCE) {
            dismissRightToLeft();
            return false;
        } else if (x2 - x1 > MIN_DISTANCE) {
            dismissLeftToRight();
            return false;
        }
        if (y1 - y2 > MIN_DISTANCE) {
            dismissToTop();
        }
        return true;
    }

    private void dismissToTop() {
        rlMain.startAnimation(createAnimationClose());
        new Handler().postDelayed(() -> SnackAlert.this.dismiss(), durationAnimationHide);

    }


    public SnackAlert setDurationSwipeToDimiss(int durationSwipeToDimiss) {
        this.durationSwipeToDimiss = durationSwipeToDimiss;
        return this;
    }

    public SnackAlert setInterpolatorSwipeToDimiss(Interpolator interpolatorSwipeToDimiss) {
        this.interpolatorSwipeToDimiss = interpolatorSwipeToDimiss;
        return this;
    }

    public SnackAlert setBackgroundAlpha(float backgroundAlpha) {
        this.backgroundAlpha = backgroundAlpha;
        return this;
    }

    public SnackAlert setAutoHide(boolean autoHide) {
        this.autoHide = autoHide;
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
                    rlMain.setBackgroundColor(Color.parseColor("#1f933a"));
                    break;
                }
                case WARNING: {
                    rlMain.setBackgroundColor(Color.parseColor("#9F6000"));
                    break;
                }
                case ERROR: {
                    rlMain.setBackgroundColor(Color.parseColor("#da251f"));
                    break;
                }
                case NORMAL: {
                    rlMain.setBackgroundColor(Color.parseColor("#ffbc40"));
                    break;
                }
            }
        } else {
            rlMain.setBackgroundColor(backgroundColor);
        }

        rlMain.setAlpha(backgroundAlpha);

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
        if (message != null && txtMessage != null)
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

        Animation animationShow = createAnimationShow();

        Animation animationClose = createAnimationClose();

        rlMain.startAnimation(animationShow);
        if (autoHide) {
            android.os.Handler handler = new android.os.Handler();
            handler.postDelayed(() -> rlMain.startAnimation(animationClose), duration + durationAnimationShow);
            handler.postDelayed(() -> SnackAlert.this.dismiss(), duration + durationAnimationShow + durationAnimationHide);
        }
        setCanceledOnTouchOutside(true);
        super.show();
    }

    private Animation createAnimationShow() {
        Animation animationShow = new TranslateAnimation(0, 0, -height, 0);
        animationShow.setDuration(durationAnimationShow);
        if (interpolatorShow != null)
            animationShow.setInterpolator(interpolatorShow);
        animationShow.setFillAfter(true);
        return animationShow;
    }

    private Animation createAnimationClose() {
        Animation animationClose = new TranslateAnimation(0, 0, 0, -height);
        animationClose.setDuration(durationAnimationHide);
        animationClose.setFillAfter(true);
        if (interpolatorHide != null)
            animationClose.setInterpolator(interpolatorHide);
        return animationClose;
    }

    public SnackAlert setOnAlertClickListener(OnAlertClickListener onAlertClickListener) {
        this.onAlertClickListener = onAlertClickListener;
        rlMain.setOnClickListener(v -> onAlertClickListener.onClick());
        return this;
    }

    @Override
    public void onBackPressed() {
        if (backPressToDismiss)
            super.onBackPressed();
    }
}



