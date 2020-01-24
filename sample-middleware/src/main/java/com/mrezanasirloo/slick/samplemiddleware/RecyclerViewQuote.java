/*
 * Copyright 2018. M. Reza Nasirloo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mrezanasirloo.slick.samplemiddleware;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mrezanasirloo.slick.middleware.Callback;
import com.varunest.sparkbutton.SparkButton;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
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
                        public void accept(@NonNull Boolean aBoolean) {
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
                        public void accept(@NonNull Boolean aBoolean) {
                            holder.buttonStar.playAnimation();
                            holder.buttonStar.setChecked(true);

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) {

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
            textView = itemView.findViewById(R.id.textView_quote);
            buttonLike = itemView.findViewById(R.id.button_like);
            buttonStar = itemView.findViewById(R.id.button_star);
        }
    }
}
