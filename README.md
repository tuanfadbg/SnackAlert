# SnackAlert

Snack Alert is a simple alert library for Android and easy to customization.

## Demo

<img width="258" height="473" src="https://i.imgur.com/ih66QeW.gif" title="Snack alert demo">

## Installing
**Step 1:** Add it in your root build.gradle: (Project) at the end of repositories:

```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
 
 
**Step 2:** Add the dependency in build.gradle (Module:app)

```
dependencies {
  ...
  implementation 'com.github.tuanfadbg:SnackAlert:1.1.13'
}
```

## How to use
Simple snack alert
```
new SnackAlert(MainActivity.this)
                .setTitle("Success")
                .setMessage("This is a success message")
                .show());
```
Snack alert with type:

```
new SnackAlert(MainActivity.this)
                        .setTitle("Error")
                        .setMessage("This is a error message")
                        .setType(SnackAlert.ERROR)
                        .show();
```

Type to use default background color, you can change background color by `setBackgroundColor(int backgroundColor)`
  
  `SUCCESS`: background color: `#1F933A`
  
  `WARNING`: background color: `#9F6000`
  
  `ERROR`: background color: `#DA251F`
  
  `NORMAL`: background color: `#FFBC40`
  
### More options to custom
                
``setTitle(String title);``

``setMessage(String message);``

``setImageDrawable(Drawable imageDrawable);`` default null

``setTitleColor(int titleColor);``

``setMessageColor(int messageColor);``

``setBackgroundColor(int backgroundColor);``

``setBackgroundAlpha(float backgroundAlpha);`` default 0.8

``setDuration(int duration);`` default 2000ms

``setType(int type);`` default ``SUCCESS``

``setAutoHide(boolean autoHide);`` default true

``setSwipeToDimiss(boolean swipeToDimiss);`` default false

``setInterpolatorShow(Interpolator interpolatorShow);`` default null

``setInterpolatorHide(Interpolator interpolatorHide);`` default null

``setDurationAnimationShow(int durationAnimationShow);`` default 300ms

``setDurationAnimationHide(int durationAnimationHide);`` default 100ms

``setDurationSwipeToDimiss(int durationSwipeToDimiss);`` default 500ms

``setInterpolatorSwipeToDimiss(Interpolator interpolatorSwipeToDimiss);`` default null

``setOnAlertClickListener(OnAlertClickListener onAlertClickListener);`` 

``show();``

## Author

 *Tuan Nguyen* - Tuan FADBG.
 
 Visit my website [tuanfadbg.com](https://tuanfadbg.com/ "Tuan FADBG")

## Versioning
We use Jitpack for versioning. For the versions available, see the tags on this repository.
