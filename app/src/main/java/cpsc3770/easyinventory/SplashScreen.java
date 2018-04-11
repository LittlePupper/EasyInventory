package cpsc3770.easyinventory;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreen extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EasySplashScreen config = new EasySplashScreen(SplashScreen.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(1000)
                .withBackgroundColor(Color.parseColor("#2699FB"))
                .withBeforeLogoText("Easy Inventory");

        config.getBeforeLogoTextView().setTextColor(android.graphics.Color.WHITE);
//        setContentView(R.layout.splash_screen);

        // Set to view
        View view = config.create();

        // Set view to content view
        setContentView(view);
    }
}
