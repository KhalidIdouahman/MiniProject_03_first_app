package com.example.miniproject_03.RecyclerAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject_03.CrudActivity;
import com.example.miniproject_03.R;
import com.example.miniproject_03.db.Quote;

import java.util.ArrayList;

public class QuotesRecyclerAdapter extends RecyclerView.Adapter<QuotesRecyclerAdapter.QuotesViewHolder> {
    ArrayList<Quote> quotesList;
    Context context;

    public QuotesRecyclerAdapter(Context context , ArrayList<Quote> quotesList) {
        this.context = context;
        this.quotesList = quotesList;
    }

    @NonNull
    @Override
    public QuotesRecyclerAdapter.QuotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quote_recycler , parent , false);
        return new QuotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuotesRecyclerAdapter.QuotesViewHolder holder, int position) {
        Quote quote = quotesList.get(position);

        holder.quoteTv.setText(quote.getQuote());
        holder.authorTv.setText(quote.getAuthor());

        holder.deleteCb.setOnClickListener(v ->  {
            Toast.makeText(v.getContext(), "checkbox clicked", Toast.LENGTH_SHORT).show();
        });

        holder.updateBtn.setOnClickListener(v ->  {
            Toast.makeText(v.getContext(), "update btn clicked", Toast.LENGTH_SHORT).show();

        });

        @SuppressLint("ResourceType") ArrayList<String> colors = CrudActivity.getColorsFromXmlFile(context.getResources().openRawResource(R.xml.colors_to_read)); // Replace with your XML file name
        if (colors != null) {
            if (position % 3 == 0) {
                holder.linearQuotesContainer.setBackgroundColor(Color.parseColor(colors.get(0)));
            }
            else if (position % 2 != 0) {
                holder.linearQuotesContainer.setBackgroundColor(Color.parseColor(colors.get(1)));
            } else {
                holder.linearQuotesContainer.setBackgroundColor(Color.parseColor(colors.get(2)));
            }
        }
    }

    @Override
    public int getItemCount() {
        return quotesList.size();
    }

    public void setAndNotifyData(ArrayList<Quote> quotes) {
        this.quotesList = quotes;
        notifyDataSetChanged();
    }

    public static class QuotesViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearQuotesContainer;
        TextView quoteTv , authorTv;
        CheckBox deleteCb;
        Button updateBtn;
        public QuotesViewHolder(@NonNull View itemView) {
            super(itemView);
            linearQuotesContainer = itemView.findViewById(R.id.linear_quote_container);
            quoteTv = itemView.findViewById(R.id.quote_tv);
            authorTv = itemView.findViewById(R.id.author_tv);
            deleteCb = itemView.findViewById(R.id.delete_checkbox);
            updateBtn = itemView.findViewById(R.id.update_btn);
        }
    }
}
