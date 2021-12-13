package com.example.hardcoregym;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;



public class AdapterMembersip extends RecyclerView.Adapter<AdapterMembersip.ViewHolder> {

    private OnBuyNowClickListener listener;

    public interface OnBuyNowClickListener{
        void OnButtonClick(int position);
    }

    public void OnBuyNowClickListener (OnBuyNowClickListener listener){
        this.listener = listener;
    }

    List<String> list;
    Context context;

    public AdapterMembersip(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_membership, parent, false);
        return new ViewHolder(view, listener); ///////***-1
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Title.setText(list.get(position).toString());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_of_month;
        public TextView Title, Desc;
        public Button btn_buynow;
        public ViewHolder(@NonNull View itemView, OnBuyNowClickListener listener) ///////***-2
        {
            super(itemView);
            img_of_month = itemView.findViewById(R.id.img_of_month);
            Title = itemView.findViewById(R.id.Title);
            Desc = itemView.findViewById(R.id.Desc);
            btn_buynow = itemView.findViewById(R.id.btn_buynow);

            btn_buynow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!=null && getAdapterPosition()!=RecyclerView.NO_POSITION){
                        listener.OnButtonClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
