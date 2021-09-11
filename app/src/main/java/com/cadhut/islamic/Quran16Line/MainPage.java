package com.cadhut.islamic.Quran16Line;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import static com.cadhut.islamic.Quran16Line.MainActivity.ITEM;

public class MainPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{


    InterstitialAd mInterstitialAd;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.intro_screen);

        //setting toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.intro_toolbar);
        setSupportActionBar(toolbar);

        //hiding toolbar
        getSupportActionBar().hide();

        //adding drawer layout toggle and other functions
        addDrawerLayoutFunctions(toolbar);

        //setting Navigation view functions
        setNavigationViewFunctions();

        //for hiding and showing toolbar
        onTouchListenersforHidingAndShowingToolbar(toolbar);

        //setting and showing banner ad plus initialing FullScreen Ad
        initializingFullScreenandBannerAds();




    }


    public void addDrawerLayoutFunctions(Toolbar toolbar){

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.intro_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    public void setNavigationViewFunctions(){

        NavigationView navigationView = (NavigationView) findViewById(R.id.intro_nav_view);

        navigationView.setNavigationItemSelectedListener(this);


        //applying fonts to the navigationview items
        //menuFontApplier(navigationView);

    }

    @SuppressLint("ClickableViewAccessibility")
    public void onTouchListenersforHidingAndShowingToolbar(final Toolbar toolbar){

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_intro_layout);

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (toolbar.isShown()) {

                        //toolbar.animate().translationY(-toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
                        getSupportActionBar().hide();

                    } else {
                        getSupportActionBar().show();
                        //toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
                    }
                    return true;
                } else return false;
            }
        });

    }

    public void initializingFullScreenandBannerAds(){

        MobileAds.initialize(this, getStringResourceByName("ad_app_id"));
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getStringResourceByName("mushaf_inter_ad"));
        //To Load Gogole Admob Interstitial Ad
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int i = item.getItemId();


        if (i == R.id.nav_surah_intro)
        {
            startActivity(new Intent(getApplicationContext(), SuraContents.class));
        } else if (i == R.id.nav_juz_intro)
        {
            startActivity(new Intent(getApplicationContext(), JuzContents.class));
        }
        else if (i == R.id.nav_resume_intro)
        {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("Page_No",0);

            ITEM = pref.getInt("resume",10);

            Intent in = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(in);
        }
        else if (i == R.id.nav_page_intro)
        {
            LayoutInflater layoutInflater =LayoutInflater.from(MainPage.this);

            final View view = layoutInflater.inflate(R.layout.dialog_box, null);
            final AlertDialog alertDialog = new AlertDialog.Builder(MainPage.this).create();
            alertDialog.setCancelable(false);

            final EditText etComments = (EditText) view.findViewById(R.id.etComments);

            Button cancelButton =(Button)view.findViewById(R.id.cancel);
            Button goButton = (Button)view.findViewById(R.id.go);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    alertDialog.dismiss();
                }
            });

            goButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mGo(etComments);

                }
            });



            alertDialog.setView(view);
            alertDialog.show();
        }
        else if (i == R.id.nav_mainAbout)
        {
            aboutAlertdialog();
        }
        else if (i == R.id.nav_mainRate)
        {
            rateApp();
        }
        else if (i == R.id.nav_mainDonate)
        {

            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            mInterstitialAd.show();
        }
        else if(i==R.id.nav_bookmark_intro){

            startActivity(new Intent(getApplicationContext(), BookMark.class));
        }else if(i==R.id.nav_mainShare){
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Holy Quran has a fast interface, elegant style " +
                    "and smooth Nastaliq font for full page readibility. " +
                    "https://play.google.com/store/apps/details?id="+this.getPackageName();
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "16 Line Holy Quran");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.intro_drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    private String getStringResourceByName(String aString) {
        int resId = getResources().getIdentifier(aString, "string", getApplicationContext().getPackageName());
        return getString(resId);
    }

    public void rateApp(){
        Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
        }

    }


    public void aboutAlertdialog(){

        LayoutInflater li= LayoutInflater.from(this);
        final View aboutView= li.inflate(R.layout.about_dialog_box,null);

        android.app.AlertDialog aboutAlertDialog = new android.app.AlertDialog.Builder(this).create();
        aboutAlertDialog.setCancelable(true);
        aboutAlertDialog.setView(aboutView);
        aboutAlertDialog.show();


    }


    public void Resume(View v){

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Page_No",0);

        ITEM = pref.getInt("resume",10);

        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);


    }

    public void Page(View v){

        LayoutInflater layoutInflater =LayoutInflater.from(MainPage.this);

        final View view = layoutInflater.inflate(R.layout.dialog_box, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(MainPage.this).create();
        alertDialog.setCancelable(false);

        final EditText etComments = (EditText) view.findViewById(R.id.etComments);

        Button cancelButton =(Button)view.findViewById(R.id.cancel);
        Button goButton = (Button)view.findViewById(R.id.go);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mGo(etComments);

            }
        });



        alertDialog.setView(view);
        alertDialog.show();

    }



    public void Juz(View v){

        Intent i = new Intent(getApplicationContext(),JuzContents.class);
        startActivity(i);
    }

    public void Surah(View v){

        Intent i = new Intent(getApplicationContext(),SuraContents.class);
        startActivity(i);

    }

    public void mGo(EditText ed){

        int page_no;
        int code_pg_no;
        int total =556;
        int converter;

        if(ed.getText().toString().equals("")){
            final AlertDialog error2 = new AlertDialog.Builder(MainPage.this, R.style.AlertDialogStyle).create();
            error2.setMessage("Please Enter a Page Number");
            error2.show();

        }else {

            page_no = Integer.parseInt(ed.getText().toString());


            if (page_no > 557 || page_no < 1) {
                final AlertDialog error = new AlertDialog.Builder(MainPage.this, R.style.AlertDialogStyle).create();
                error.setMessage("Please Enter a Valid Page Number");
                error.show();
            }else {

                converter = page_no-1;
                code_pg_no =total-converter;

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                ITEM =code_pg_no;
                startActivity(i);


            }



        }

    }




}
