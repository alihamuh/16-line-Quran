package com.cadhut.islamic.Quran16Line;

import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity{

    public ArrayList<Integer> images;
    private BitmapFactory.Options options;
    private ViewPager viewPager;
    private View btnNext, btnPrev;
    private FragmentStatePagerAdapter adapter;
    //private LinearLayout thumbnailsContainer;
    private int[] resourceIDs;

    public MyApplication2 app2 =new MyApplication2();


    private ActionMenuView amvMenu;
    private float x1,x2;
    static int ITEM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null


        amvMenu = (ActionMenuView)toolbar.findViewById(R.id.amvMenu);
        amvMenu.setOnMenuItemClickListener(new ActionMenuView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return onOptionsItemSelected(menuItem);
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        //toolbar.setNavigationIcon(R.drawable.ic_chevron_left_black_24dp);
        images = new ArrayList<>();

        idCatcher();



        //find view by id
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        setImagesData();


        // init viewpager adapter and attach
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), images);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(ITEM);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // use amvMenu here
        inflater.inflate(R.menu.menu_main, amvMenu.getMenu());
        return true;
    }

    ArrayList<Integer> checks;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_night_mode) {

            app2.setNightmode(!app2.getNightmode());

            //nightMode =!nightMode;
            ITEM =viewPager.getCurrentItem();
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(ITEM);
            if(app2.getNightmode()){
                Toast.makeText(this, "night mode on", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "night mode off", Toast.LENGTH_SHORT).show();
            }

        }
        if(id==R.id.action_bookmark){

            Boolean actionUse=true;

            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

            SharedPreferences.Editor editor = settings.edit();

            for(int index =1; index<=settings.getInt("bookmark_no",0);index++){

                if(settings.getInt("bookmark_"+index,0)==viewPager.getCurrentItem()){
                    actionUse =false;
                }
            }

            if(actionUse) {
                editor.putInt("bookmark_no", settings.getInt("bookmark_no", 0) + 1);
                editor.apply();

                editor.putInt("bookmark_" + settings.getInt("bookmark_no", 0), viewPager.getCurrentItem());
                editor.apply();

                Toast.makeText(this, "bookmarked", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "already bookmarked", Toast.LENGTH_SHORT).show();
            }

        }if(id==R.id.highlight_mode){


            app2.setHighlight(!app2.getHighlight());
            //highlight=!highlight;
            ITEM =viewPager.getCurrentItem();
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(ITEM);
            if(app2.getHighlight()){
                Toast.makeText(this, "highlight mode on", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "highlight mode off", Toast.LENGTH_SHORT).show();
            }
        }



        return super.onOptionsItemSelected(item);
    }

/*
    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {

        MenuItem item = menu.findItem(R.id.action_bookmark);

        if (myItemShouldBeEnabled) {
            item.setEnabled(true);
            item.getIcon().setAlpha(255);
        } else {
            // disabled
            item.setEnabled(false);
            item.getIcon().setAlpha(130);
        }
    }
*/



    private View.OnClickListener onClickListener(final int i) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i > 0) {
                    //next page
                    if (viewPager.getCurrentItem() < viewPager.getAdapter().getCount() - 1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    }
                } else {
                    //previous page
                    if (viewPager.getCurrentItem() > 0) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                    }
                }
            }
        };
    }

    private void setImagesData() {
        for (int i = 0; i < resourceIDs.length; i++) {
            images.add(resourceIDs[i]);
        }
    }

    /*
    private void inflateThumbnails() {
        for (int i = 0; i < images.size(); i++) {
            View imageLayout = getLayoutInflater().inflate(R.layout.item_image, null);
            ImageView imageView = (ImageView) imageLayout.findViewById(R.id.img_thumb);
            imageView.setOnClickListener(onChagePageClickListener(i));
            options = new BitmapFactory.Options();
            options.inSampleSize = 3;
            options.inDither = false;
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), images.get(i), options );
            imageView.setImageBitmap(bitmap);
            //set to image view
            imageView.setImageBitmap(bitmap);
            //add imageview
            thumbnailsContainer.addView(imageLayout);
        }
    }

    */

    private View.OnClickListener onChagePageClickListener(final int i) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(i);
            }
        };
    }

    void idCatcher(){

        resourceIDs = new int[557];

        for(int i=0;i<557; i++){

            int index=557-i;
            resourceIDs[i] = getResources().getIdentifier("page_" + index, "drawable", getPackageName());

        }

    }


    @Override
    protected void onStop() {
        super.onStop();


        SharedPreferences pref = getApplicationContext().getSharedPreferences("Page_No", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("resume",viewPager.getCurrentItem());
        editor.apply();

    }





}

