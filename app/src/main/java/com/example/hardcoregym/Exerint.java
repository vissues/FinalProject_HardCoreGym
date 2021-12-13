package com.example.hardcoregym;

import static com.example.hardcoregym.R.*;
import static com.example.hardcoregym.R.id.*;
import static com.example.hardcoregym.R.id.app_bar;
import static com.example.hardcoregym.R.id.friday;
import static com.example.hardcoregym.R.id.monday;
import static com.example.hardcoregym.R.id.saturday;
import static com.example.hardcoregym.R.id.thursday;
import static com.example.hardcoregym.R.id.tuesday;
import static com.example.hardcoregym.R.id.wednesday;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;


public class Exerint extends AppCompatActivity implements View.OnClickListener {


    public CardView b1 ,b2 ,b3 ,b4 ,b5 ,b6 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exerint);

        b1 = (CardView) findViewById(monday);
        b2 = (CardView) findViewById(tuesday);
        b3 = (CardView) findViewById(wednesday);
        b4 = (CardView) findViewById(thursday);
        b5 = (CardView) findViewById(friday);
        b6 = (CardView) findViewById(saturday);


        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);

        Toolbar toolbar = findViewById(app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

    }


    public void MyWeb(View view)
    {
        openUrl("https://www.youtube.com/watch?v=_PzkTJMgOIo");
    }

    public void MyWeb1(View view)
    {
        openUrl("https://www.youtube.com/watch?v=pSHjTRCQxIw");
    }

    public void MyWeb2(View view)
    {
        openUrl("https://www.youtube.com/watch?v=i9sTjhN4Z3M&t=17s");
    }

    public void MyWeb3(View view)
    {
        openUrl("https://www.youtube.com/watch?v=Qxx0EE4evyI");
    }

    public void MyWeb4(View view)
    {
        openUrl("https://www.youtube.com/watch?v=aclHkVaku9U");
    }


    public void openUrl(String url)
    {
        Uri uri = Uri.parse(url);
        Intent launchWeb=new Intent(Intent .ACTION_VIEW,uri);
        startActivity(launchWeb);
    }



    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){

            case monday:
                i = new Intent(this, com.example.hardcoregym.Monday2.class);
                startActivity(i);
                break;

            case tuesday:
                i = new Intent(this,Tuesday2.class);
                startActivity(i);
                break;

            case wednesday:
                i = new Intent(this, com.example.hardcoregym.Wednesday2.class);
                startActivity(i);
                break;

            case thursday:
                i = new Intent(this,Thursday2.class);
                startActivity(i);
                break;

            case friday:
                i = new Intent(this, com.example.hardcoregym.Friday2.class);
                startActivity(i);
                break;

            case saturday:
                i = new Intent(this, com.example.hardcoregym.Saturday2.class);
                startActivity(i);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}