<img src="https://thatcakeid.com/assets/images/osthmlogo.png" alt="os-thm Logo" width="200"/>

# os-thm-sketchware
[os-thm](https://github.com/ThatCakeID/os-thm-android) implementation for sketchware.

## What is os-thm?
os-thm is a simple library used to manage themes that can be applied globaly.

[Original GitHub Repository](https://github.com/ThatCakeID/os-thm-android)

## Implementing os-thm
There are 2 ways of implementing os-thm to your sketchware app:

1. Injecting the osthm library
2. Using ASD

### Injecting the osthm library
To implement os-thm to your sketchware app, inject the [osthm.java]() file, ignore the `os-thm-sketchware-asd.java`

And you're all set!


### Using ASD
 - Create a new activity named `osthm`
 - Add an ASD block in onCreate
 - Copy and paste the [os-thm-sketchware-asd.java]() file into the ASD, ignore the `osthm.java`
 
And you're all set!

## Using os-thm
Here is an example code to apply theme to your sketchware app:
```java
// Put this in onStart

// Getting current applied theme
osthm.Theme currentTheme = osthm.getCurrentTheme();

// Applying color to the ActionBar
getSupportActionBar().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(currentTheme.colorPrimary));

// Applying color to the StatusBar
// Check if the android version is 5+
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
    android.view.Window window = getWindow();
    window.addFlags(android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    window.setStatusBarColor(currentTheme.colorPrimaryDark);
}

// Applying color to the FAB
_fab.setBackgroundTintList(android.graphics.drawable.ColorDrawable.valueOf(currentTheme.colorAccent));

// Applying ripple color to the FAB
_fab.setRippleColor(currentTheme.colorControlHighlight);

// etc.
```

Don't understand what are the colors means? [Click Here](https://github.com/ThatCakeID/os-thm-android#colors-meanings)

Still don't understand? Download the [os-thm Theme Manager](https://github.com/ThatCakeID/os-thm-android/releases/latest), and try playing around with the Theme Editor to determine which colors point to which.

Have questions? [Join our discord server](https://discord.gg/9xCpW8E).

Documentation written by [Iyxan23](https://github.com/Iyxan23).

(c) Copyright 2020 [ThatCakeID](https://github.com/ThatCakeID)

