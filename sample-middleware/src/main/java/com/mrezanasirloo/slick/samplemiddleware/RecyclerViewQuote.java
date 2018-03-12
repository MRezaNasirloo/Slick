package com.mrezanasirloo.slick.samplemiddleware;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mrezanasirloo.slick.middleware.Callback;
import com.varunest.sparkbutton.SparkButton;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-04-05
 */

public class RecyclerViewQuote extends RecyclerView.Adapter<RecyclerViewQuote.ViewHolder> {


    private List<String> quotes;
    private RouterLike routerLike;
    private final RouterStar routerStar;

    public RecyclerViewQuote(List<String> quotes, RouterLike routerLike, RouterStar routerStar) {
        this.quotes = quotes;
        this.routerLike = routerLike;
        this.routerStar = routerStar;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textView.setText(quotes.get(position));

        holder.buttonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.buttonLike.isChecked()) {
                    routerLike.unlike().subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(@NonNull Boolean aBoolean) throws Exception {
                            holder.buttonLike.setChecked(false);
                        }
                    });
                } else {
                    routerLike.like("some_other_id", new Callback<String>() {
                        @Override
                        public void onPass(String s) {
                            holder.buttonLike.playAnimation();
                            holder.buttonLike.setChecked(true);
                        }
                    });
                }
            }
        });

        holder.buttonStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.buttonStar.isChecked()) {
                    routerStar.unStar("some_id").subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            holder.buttonStar.setChecked(false);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });
                } else {
                    routerStar.star("some_other_id").subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(@NonNull Boolean aBoolean) throws Exception {
                            holder.buttonStar.playAnimation();
                            holder.buttonStar.setChecked(true);

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {

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
        final SparkButton buttonLike;
        final SparkButton buttonStar;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = ((TextView) itemView.findViewById(R.id.textView_quote));
            buttonLike = ((SparkButton) itemView.findViewById(R.id.button_like));
            buttonStar = ((SparkButton) itemView.findViewById(R.id.button_star));
        }
    }
}
