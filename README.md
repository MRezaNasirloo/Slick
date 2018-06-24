# Slick

[![Build Status](https://travis-ci.org/MRezaNasirloo/Slick.svg?branch=master)](https://travis-ci.org/MRezaNasirloo/Slick)  [ ![Download](https://api.bintray.com/packages/mrezanasirloo/slick/slick/images/download.svg) ](https://bintray.com/mrezanasirloo/slick/slick/_latestVersion)

A Reactive Android MVP Framework which is *Slick* to use.

Slick how? see it yourself :)

No need to extend anything
```java
public class YourActivity extends AppCompatActivity implements ViewActivity {

    @Presenter //<-- Just annotate your presenter
    YourPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //And call bind on the generated class with your presenter args
        YourPresenter_Slick.bind(this, 123, "foo");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your);
    }
    
    @Override
    public showMessage(String message) {
        
    }
}
```
The Presenter:
```java
public class YourPresenter extends SlickPresenter<ActivityView> {

    public YourPresenter(@NonNull int code, String s) {
    }

    @Override
    public void onViewUp(@NonNull ActivityView view) {
        //view onStart
    }

    @Override
    public void onViewDown() {
        //view onStop
    }

    @Override
    public void onDestroy() {
        //only is called once when view is finishing
    }
}
```
And a simple View interface, no need to extend anything
```java
public interface ActivityView {
    void showMessage(String message);
}
```
Other View types have the same logic, for more detailed instruction head to [Wiki](https://github.com/MRezaNasirloo/Slick/wiki)

###  Features:

1. Supports Activity, Fragment, CustomView, and Conductor Controller
2. No dark magic involved, Just simple debuggable generated code
3. Retains Presenters in disposable, lifecycle-aware singletons
4. Reactive support inspired by MVI and Elm architecture
5. Do not need to extend any base class for your views
6. Easily test presenters on JVM with junit
7. Use multiple Presenter for one view
8. Fully Type-safe
9. Dagger ready!

### Reactive MVP Features:

1. Unidirectional Data Flow & Immutable ViewState
2. Automatic subscription and disposing of RxJava2 streams


```java
public class YourPresenterUni extends SlickPresenterUni<ViewActivity, ViewStateActivity> {

    /** ... **/

    @Override
    protected void start(@NonNull ViewActivity view) {
        Observable<PartialViewState<ViewStateActivity>> like = command(ViewActivity::likeMovie)
                .flatMap(id -> repositoryMovies.like(id).subscribeOn(io))//call to backend
                .map(PartialViewStateLiked::new);

        Observable<PartialViewState<ViewStateActivity>> loadComments = command(ViewActivity::loadComments)
                .flatMap(id -> repositoryComments.load(id).subscribeOn(io))
                .map(PartialViewStateComments::new);

        subscribe(new ViewStateActivity(Collections.emptyList(), false), merge(like, loadComments));
    }

    @Override
    protected void render(@NonNull ViewStateActivity state, @NonNull ViewActivity view) {
        if (!state.comments().isEmpty()) view.showComments(state.comments());
        else view.showNoComments();
        view.setLike(state.isLiked());
    }
}

```
For more detailed guide on Reactive Features read the [Wiki](https://github.com/MRezaNasirloo/Slick/wiki) 

### Downloads

Packages are available in `jcenter`

``` groovy
// Base features
implementation 'com.mrezanasirloo:slick:1.1.4'

// Reactive features
implementation 'com.mrezanasirloo:slick-reactive:1.1.4'

implementation 'com.mrezanasirloo:slick-conductor:1.1.4'
implementation 'com.mrezanasirloo:slick-support-fragment:1.1.4'

annotationProcessor 'com.mrezanasirloo:slick-compiler:1.1.4'
```

Since Slick packages are not tied to a specific dependency you need to provide them.
``` groovy
// Conductor
implementation 'com.bluelinelabs:conductor:2.x.y'

// RxJava for Reactive Features
implementation 'io.reactivex.rxjava2:rxjava:2.x.y'
```


**If you liked the project don't forget to start it** :star: == :heart:

### Licence

```
Copyright 2018. M. Reza Nasirloo

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
