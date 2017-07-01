package com.example.p_natarajan.wifipoc;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yalantis.guillotine.animation.GuillotineAnimation;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewLocation extends AppCompatActivity {


    private static final long RIPPLE_DURATION = 250;


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.root)
    FrameLayout root;
    @BindView(R.id.content_hamburger)
    View contentHamburger;


    int screenHeight = 1920;
    int screenWidth = 1080;

    int imageHeight = 1920-194;
    int imageWidth = 1080-114;

    int xOffset;
    int yOffset;

    int verticalDivs = 10;
    int horizontalDivs = 10;

    int userImageWidth = 60;
    int userImageHeight = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_view_location);

        ButterKnife.bind(this);


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
        root.addView(guillotineMenu);

        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();
        Uri data = getIntent().getData();
        String scheme = data.getScheme(); // "http"
        String host = data.getHost(); // "twitter.com"
        List<String> params = data.getPathSegments();
        String first = params.get(0); // "status"
        String second = params.get(1); // "1234"
        String third = params.get(2); // "status"
//        Log.d("ToInt",Integer.parseInt(second) + "  " + Integer.getInteger(third).intValue());
        setSenderPosition(Integer.parseInt(second),Integer.parseInt(third));

        Toast.makeText(getApplicationContext(),first + " " + second + " " + third,Toast.LENGTH_SHORT).show();

    }

    public void setSenderPosition(int inpXCoord, int inpYCoord)
    {
        xOffset = (screenWidth - imageWidth)/2; // Gap on left side
        yOffset = (screenHeight - imageHeight)/2; // Gap on top

        int xPosition = (int) (((float)((float)imageWidth/(float)horizontalDivs))*inpXCoord);
        int yPosition = (int) (((float)((float)imageHeight/(float)verticalDivs))*inpYCoord);

        ImageView iv = (ImageView) findViewById(R.id.senderLocation);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(xPosition+userImageWidth/2, yPosition+userImageHeight/2, 0, 0);
        iv.setLayoutParams(lp);

        Toast.makeText(getApplicationContext(),(xPosition-userImageWidth/2) + " " + (yPosition-userImageHeight/2) + " ",Toast.LENGTH_SHORT).show();
    }
}
