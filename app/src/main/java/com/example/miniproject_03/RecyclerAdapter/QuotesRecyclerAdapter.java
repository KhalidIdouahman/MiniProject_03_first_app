package com.example.miniproject_03.RecyclerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject_03.R;
import com.example.miniproject_03.db.Quote;

import java.util.ArrayList;

public class QuotesRecyclerAdapter extends RecyclerView.Adapter<QuotesRecyclerAdapter.QuotesViewHolder> {
    ArrayList<Quote> quotesList;

    public QuotesRecyclerAdapter(ArrayList<Quote> quotesList) {
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
        TextView quoteTv , authorTv;
        CheckBox deleteCb;
        Button updateBtn;
        public QuotesViewHolder(@NonNull View itemView) {
            super(itemView);
            quoteTv = itemView.findViewById(R.id.quote_tv);
            authorTv = itemView.findViewById(R.id.author_tv);
            deleteCb = itemView.findViewById(R.id.delete_checkbox);
            updateBtn = itemView.findViewById(R.id.update_btn);
        }
    }
}
