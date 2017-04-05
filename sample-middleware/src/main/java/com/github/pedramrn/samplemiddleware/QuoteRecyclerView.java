package com.github.pedramrn.samplemiddleware;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.slick.middleware.Callback;
import com.varunest.sparkbutton.SparkButton;

import java.util.List;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-04-05
 */

public class QuoteRecyclerView extends RecyclerView.Adapter<QuoteRecyclerView.ViewHolder> {


    private List<String> quotes;
    private LikeRouter likeRouter;

    public QuoteRecyclerView(List<String> quotes, LikeRouter likeRouter) {
        this.quotes = quotes;
        this.likeRouter = likeRouter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textView.setText(quotes.get(position));

        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.likeButton.isChecked()) {
                    likeRouter.unlike("some_id", new Callback<String>() {
                        @Override
                        public void onPass(String s) {
                            holder.likeButton.setChecked(false);
                        }
                    });
                } else {
                    likeRouter.like("some_other_id", new Callback<String>() {
                        @Override
                        public void onPass(String s) {
                            holder.likeButton.playAnimation();
                            holder.likeButton.setChecked(true);
                        }
                    });
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return quotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;
        final SparkButton likeButton;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = ((TextView) itemView.findViewById(R.id.textView_quote));
            likeButton = ((SparkButton) itemView.findViewById(R.id.button_like));
        }
    }
}
