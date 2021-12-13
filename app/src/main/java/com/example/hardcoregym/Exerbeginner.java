package com.example.hardcoregym;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

public class Exerbeginner extends AppCompatActivity implements View.OnClickListener {


    public CardView b1 ,b2 ,b3 ,b4 ,b5 ,b6 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exerbeginner);

        b1 = (CardView) findViewById(R.id.monday);
        b2 = (CardView) findViewById(R.id.tuesday);
        b3 = (CardView) findViewById(R.id.wednesday);
        b4 = (CardView) findViewById(R.id.thursday);
        b5 = (CardView) findViewById(R.id.friday);
        b6 = (CardView) findViewById(R.id.saturday);


        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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

            case R.id.monday: 
                i = new Intent(this,Monday1.class);
                startActivity(i);
                break;

            case R.id.tuesday: 
                i = new Intent(this, com.example.hardcoregym.Tuesday1.class);
                startActivity(i);
                break;

            case R.id.wednesday:
                i = new Intent(this,Wednesday1.class);
                startActivity(i);
                break;

            case R.id.thursday: 
                i = new Intent(this, com.example.hardcoregym.Thursday1.class);
                startActivity(i);
                break;

            case R.id.friday:
                i = new Intent(this,Friday1.class);
                startActivity(i);
                break;

            case R.id.saturday:
                i = new Intent(this, com.example.hardcoregym.Saturday1.class);
                startActivity(i);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}