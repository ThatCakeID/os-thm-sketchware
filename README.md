<img src="https://thatcakeid.com/assets/images/osthmlogo.png" alt="os-thm Logo" width="200"/>

# os-thm-sketchware
[os-thm](https://github.com/ThatCakeID/os-thm-android) implementation for sketchware.

## What is os-thm?
os-thm is a simple library used to manage themes that can be applied globaly.

[Original GitHub Repository](https://github.com/ThatCakeID/os-thm-android)

#### NOTE: THIS REPOSITORY IS DEPRECATED, USE [OS-THM-SKETCHWARE SKETCHUB PROJECT](https://project.sketchub.in/?id=920) INSTEAD.

## Implementing os-thm
There are 2 ways of implementing os-thm to your sketchware app:

1. Injecting the osthm library
2. Using ASD

### Injecting the osthm library
To implement os-thm to your sketchware app,
 - Add your package in the first line of [osthm.java](https://github.com/ThatCakeID/os-thm-sketchware/blob/master/osthm.java)
 
   Example:
   ```java
   package my.newproject23.yay;
   
   // osthm.java contents //
   ```
 
 - Grant READ and WRITE External storage permission.
 - Inject the [osthm.java](https://github.com/ThatCakeID/os-thm-sketchware/blob/master/osthm.java) file, and ignore the `os-thm-sketchware-asd.java`

And you're all set!


### Using ASD
 - Create a new activity named `osthm`
 - Grant READ and WRITE External storage permission. 
 - Add an ASD block in onCreate ontop of the File block you placed
 - Copy and paste the [os-thm-sketchware-asd.java](https://github.com/ThatCakeID/os-thm-sketchware/blob/master/os-thm-sketchware-asd.java) file into the ASD, and ignore the `osthm.java`
 
To grant storage permission, you can use external tools, or write something in a random temporal file and then delete that file.

Note: We suggest you to check if the user denied the dialog because if the permission is denied, os-thm will not work.
 
And you're all set!

## Using os-thm
Here is an example code to apply theme to your sketchware app:

Note: For the people using ASD to implement os-thm, replace the `osthm` word into `OsthmActivity`, Because sketchware is concating the word `Activity` after the given text, and uppercasing the first letter.
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

