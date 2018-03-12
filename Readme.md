# Slick

A MVP framework which is *Slick* to use.

Slick how? see it yourself :)

No need to extend anything
```java
public class YourActivity extends AppCompatActivity implements ViewActivity {

    @Presenter <-- Just annotate your presenter
    YourPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        YourPresenter_Slick.bind(this, 123, "foo");//And call bind on the generated class with your presenter args
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your);
    }
}
```
The Presenter:
```java
public class YourPresenter extends SlickPresenter<ActivityView> {

    public ExampleActivityPresenter(@NonNull int code, String s) {
    }

    @Override
    public void onViewUp(ActivityView view) {
        //view onStart
    }

    @Override
    public void onViewDown() {
        //view onStop
    }

    @Override
    public void onDestroy() {
        //only is called when view is finishing
    }
}
```
And a simple View interface, no need to extend anything
```java
public interface ActivityView {

}
```
Other View types have the same logic, for more detailed instruction head to Wiki

####  Features:

1. Supports Activity, Fragment, CustomView, and Conductor Controller
2. No Dark magic, just simple debuggable generated code
3. Do not need to extend any base classes for your views
4. Retains Presenters in short lived singletons
5. Supports multiple Presenter for same view
6. Inspired from MVI and Elm architecture
7. Fully Type-safe
8. Dagger ready!
8.

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