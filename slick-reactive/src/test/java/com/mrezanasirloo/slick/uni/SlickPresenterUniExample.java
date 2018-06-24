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

package com.mrezanasirloo.slick.uni;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 * Created on: 2018-03-11
 */

public class SlickPresenterUniExample extends SlickPresenterUni<ViewExample, ViewStateExample> {

    public SlickPresenterUniExample(Scheduler main, Scheduler io) {
        super(main, io);
    }

    @Override
    protected void start(@NonNull ViewExample view) {
        Observable<PartialViewState<ViewStateExample>> like = command(new CommandProvider<Boolean, ViewExample>() {
            @NonNull
            @Override
            public Observable<Boolean> provide(@NonNull ViewExample viewExample) {
                return viewExample.likeComment();
            }
        }).flatMap(new Function<Boolean, ObservableSource<Boolean>>() {
            @Override
            public ObservableSource<Boolean> apply(Boolean aBoolean) {
                return Observable.just(aBoolean).subscribeOn(io);//call to backend
            }
        })
                .map(new Function<Boolean, PartialViewState<ViewStateExample>>() {
                    @Override
                    public PartialViewState<ViewStateExample> apply(Boolean isLiked) {
                        return new PartialViewStateLiked(isLiked);
                    }
                });


        Observable<PartialViewState<ViewStateExample>> loadText = command(new CommandProvider<Integer, ViewExample>() {
            @NonNull
            @Override
            public Observable<Integer> provide(@NonNull ViewExample viewExample) {
                return viewExample.loadText();
            }
        })
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override

                    public ObservableSource<String> apply(Integer aBoolean) {
                        return Observable.just("Foo Bar").subscribeOn(io);//call to backend
                    }
                })
                .map(new Function<String, PartialViewState<ViewStateExample>>() {
                    @Override
                    public PartialViewState<ViewStateExample> apply(String text) {
                        return new PartialViewStateText(text);
                    }
                });

        scan(new ViewStateExample(null, false), merge(like, loadText)).subscribe(this);

    }

    @Override
    protected void render(@NonNull ViewStateExample state, @NonNull ViewExample view) {
        System.out.println("state = [" + state + "]");
        if (state.text() != null) view.setText(state.text());
        view.setLike(state.isLiked());
    }
}
