package com.cadhut.islamic.Quran16Line;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class BookMark extends AppCompatActivity {


    ListView bkMarkList;
    BookMarkArrayAdapter adapter;
    //public static Boolean checkboxShown=false;
    //public static ArrayList<Integer> checks=new ArrayList<Integer>();
    ArrayList<Integer> PgNoOfBkMarks=new ArrayList<>();
    ArrayList<String> paraName= new ArrayList<>();
    ArrayList<String> SuraName= new ArrayList<>();

    public static MyApplication2 Myapp2=new MyApplication2();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);

        Toolbar toolbar = (Toolbar) findViewById(R.id.bk_toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Bookmarks");

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor =settings.edit();

        //ArrayList<Integer> PgNoOfBkMarks=new ArrayList<>();
        //ArrayList<String> paraName= new ArrayList<>();
        //ArrayList<String> SuraName= new ArrayList<>();

        for(int index =1; index<=settings.getInt("bookmark_no",0);index++){

            PgNoOfBkMarks.add(index-1,settings.getInt("bookmark_"+index,0));


            Myapp2.getChecks().add(index-1,0);

            for(int para=1;para<=30;para++){
                       int nextpara;
                      if(para<30) {
                          nextpara = para + 1;
                      }else{
                          nextpara =para;
                      }
                if(PgNoOfBkMarks.get(index-1)<=PageNo("para"+para)&&PgNoOfBkMarks.get(index-1)>PageNo("para"+nextpara)){  //constrainging page no of paras to get to the bookmarked para

                    paraName.add(index-1,getStringResourceByName("para"+para));
                }else if(para==nextpara&&PgNoOfBkMarks.get(index-1)<=PageNo("para"+para)){
                    paraName.add(index-1,getStringResourceByName("para"+para));
                }
            }

            for(int sura=1;sura<=114;sura++){

                int nextSura;
                if(sura<114) {
                    nextSura = sura + 1;
                }else{
                    nextSura =sura;
                }
                if(PgNoOfBkMarks.get(index-1)<=PageNo("sura"+sura)&&PgNoOfBkMarks.get(index-1)>PageNo("sura"+nextSura)){      //constrainging page no of suras to get to the bookmarked para

                    SuraName.add(index-1,getStringResourceByName("sura"+sura));
                }else if(sura==nextSura&&PgNoOfBkMarks.get(index-1)<=PageNo("sura"+sura)){
                    SuraName.add(index-1,getStringResourceByName("sura"+sura));
                }
            }


        }

        bkMarkList =(ListView)findViewById(R.id.bkListView);
        adapter = new BookMarkArrayAdapter(this, SuraName,paraName,PgNoOfBkMarks);
        bkMarkList.setAdapter(adapter);

    }


    private String getStringResourceByName(String aString) {
        int resId = getResources().getIdentifier(aString, "string", getApplicationContext().getPackageName());
        return getString(resId);
    }

    private int PageNo(String paraOrSurah){
        int code_pg_no;
        int total =556;
        int converter;
        int res2Id= this.getResources().getIdentifier(paraOrSurah,"integer",this.getApplicationContext().getPackageName());

        int page_no = this.getApplicationContext().getResources().getInteger(res2Id);

        converter = page_no-1;
        code_pg_no =total-converter;
        return code_pg_no;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.bookmark_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.del_bkmarks) {

          final Button bkdeletebtn=(Button)findViewById(R.id.bkmrk_delete_btn);
          final Button bkcancelbtn=(Button)findViewById(R.id.bkmrk_cancel_btn);

          bkcancelbtn.setVisibility(View.VISIBLE);
          bkdeletebtn.setVisibility(View.VISIBLE);


            Myapp2.setCheckboxShown(true);
            //bkMarkList.deferNotifyDataSetChanged();
            bkMarkList.setAdapter(adapter);


           bkcancelbtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   Myapp2.setCheckboxShown(false);
            //checkboxShown=false;
            bkcancelbtn.setVisibility(View.GONE);
            bkdeletebtn.setVisibility(View.GONE);

                   bkMarkList.setAdapter(adapter);
               }
           });



            bkdeletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for(int i=0;i<Myapp2.getChecks().size();i++){

                        if(Myapp2.getChecks().get(i)==1){

                            int index=i+1;

                            //remove items from the list here for example from ArryList
                            Myapp2.getChecks().remove(i);
                            PgNoOfBkMarks.remove(i);
                            paraName.remove(i);
                            SuraName.remove(i);
                            //similarly remove other items from the list from that particular postion
                            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor =settings.edit();

                            editor.remove("bookmark_"+index);
                            editor.putInt("bookmark_no",settings.getInt("bookmark_no",0)-1);
                            editor.apply();



                            i--;
                        }
                    }
                    Myapp2.setCheckboxShown(false);
                    bkcancelbtn.setVisibility(View.GONE);
                    bkdeletebtn.setVisibility(View.GONE);
                    bkMarkList.setAdapter(adapter);
                }
            });


            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
