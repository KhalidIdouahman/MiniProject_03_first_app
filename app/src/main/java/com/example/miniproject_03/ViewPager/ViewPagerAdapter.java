package com.example.miniproject_03.ViewPager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject_03.R;

import java.util.ArrayList;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolderPager> {
    ArrayList<ViewPagerModel> contentList ;
    Context context;

    public ViewPagerAdapter(ArrayList<ViewPagerModel> contentList, Context context) {
        this.contentList = contentList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewPagerAdapter.ViewHolderPager onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view_pager_2 , parent , false);
        return new ViewHolderPager(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerAdapter.ViewHolderPager holder, int position) {
        ViewPagerModel obj = contentList.get(position);

        holder.textTv.setText(obj.getText());
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }

    public static class ViewHolderPager extends RecyclerView.ViewHolder{
        TextView textTv;
        public ViewHolderPager(@NonNull View itemView) {
            super(itemView);
            textTv = itemView.findViewById(R.id.item_vp_tv);
        }
    }
}
