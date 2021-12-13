package com.example.hardcoregym;

import static com.example.hardcoregym.R.id.app_bar;
import static com.example.hardcoregym.R.id.position;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Membership extends AppCompatActivity implements PaymentResultListener {

    RecyclerView RV_membership;
    AdapterMembersip mAdapter;
    TextView PayStatus;
    ImageView img_of_month;
    String months[] ={"1 Month","2 Month","3 Month"};
    int bg_images[]={
            R.drawable.img_1month,
            R.drawable.img_2month,
            R.drawable.img_3month};

    public static void setOnClickListener(View.OnClickListener onClickListener) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);

        RV_membership = findViewById(R.id.RV_membership);
        RV_membership.setHasFixedSize(true);
        PayStatus=findViewById(R.id.Payment_Status);
        img_of_month=findViewById(R.id.img_of_month);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RV_membership.setLayoutManager(layoutManager);

        ArrayList<String> dataMembership=new ArrayList<>();
        dataMembership.add(months[0]);
        dataMembership.add(months[1]);
        dataMembership.add(months[2]);
        
        /*ArrayList<Integer> imagesMembership = new ArrayList<>();
        imagesMembership.add(bg_images[0]);
        imagesMembership.add(bg_images[1]);
        imagesMembership.add(bg_images[2]);*/
        
        

        mAdapter = new AdapterMembersip(dataMembership, this);
        RV_membership.setAdapter(mAdapter);


        Toolbar toolbar = findViewById(app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        RV_membership.setPadding(130,100,130,100);
///////*********-3 start
        mAdapter.OnBuyNowClickListener(new AdapterMembersip.OnBuyNowClickListener() {
            @Override
            public void OnButtonClick(int position) {
                if (position==0){
                    PaymentOf1month("100");
                }
                if(position==1){
                    PaymentOf3month("100");
                }
                if(position==2){
                    PaymentOf8month("100");
                }
            }
        });
        ///////*********-3 end
        final SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(RV_membership);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RecyclerView.ViewHolder viewHolder = RV_membership.findViewHolderForAdapterPosition(0);
                RelativeLayout RL1= viewHolder.itemView.findViewById(R.id.rl1);
                RL1.animate().scaleY(1).scaleX(1).setDuration(350).setInterpolator(new AccelerateInterpolator()).start();
            }
        },100);

        RV_membership.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                View view = snapHelper.findSnapView(layoutManager);
                int pos = layoutManager.getPosition(view);

                RecyclerView.ViewHolder viewHolder = RV_membership.findViewHolderForAdapterPosition(pos);
                RelativeLayout RL1= viewHolder.itemView.findViewById(R.id.rl1);

                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    RL1.animate().setDuration(350).scaleX(1).scaleY(1).setInterpolator(new AccelerateInterpolator()).start();
                }else{
                    RL1.animate().setDuration(350).scaleX(0.75f).scaleY(0.75f).setInterpolator(new AccelerateInterpolator()).start();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void PaymentOf1month(String amount) {
        double finalAmount = Float.parseFloat(amount)*100;
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_naprypcDLVjn81");
        checkout.setImage(R.drawable.dpforpayment);
        JSONObject object = new JSONObject();
        try {
            object.put("name", "HardCore Gym");
            object.put("description", "Test Payment");
            object.put("theme_color", "#0093DD");
            object.put("currency", "INR");
            object.put("amount", "30000");
            object.put("prefill.contact","");
            object.put("prefill.email","");
            checkout.open(Membership.this,object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void PaymentOf3month(String amount) {
        double finalAmount = Float.parseFloat(amount)*100;
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_naprypcDLVjn81");
        checkout.setImage(R.drawable.dpforpayment);
        JSONObject object = new JSONObject();
        try {
            object.put("name", "HardCore Gym");
            object.put("description", "Test Payment");
            object.put("theme_color", "#0093DD");
            object.put("currency", "INR");
            object.put("amount", "50000");
            object.put("prefill.contact","");
            object.put("prefill.email","");
            checkout.open(Membership.this,object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void PaymentOf8month(String amount) {
        double finalAmount = Float.parseFloat(amount)*100;
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_naprypcDLVjn81");
        checkout.setImage(R.drawable.dpforpayment);
        JSONObject object = new JSONObject();
        try {
            object.put("name", "HardCore Gym");
            object.put("description", "Test Payment");
            object.put("theme_color", "#0093DD");
            object.put("currency", "INR");
            object.put("amount", "80000");
            object.put("prefill.contact","");
            object.put("prefill.email","");
            checkout.open(Membership.this,object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(Membership.this, s, Toast.LENGTH_SHORT).show();
        //AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Payment ID");
        //builder.setMessage(s);
        //builder.show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(position==0){
            img_of_month.setImageResource(R.drawable.img_1month);
        }
        if(position==1){
            img_of_month.setImageResource(R.drawable.img_2month);
        }
        if(position==2){
            img_of_month.setImageResource(R.drawable.img_3month);
        }
    }
}