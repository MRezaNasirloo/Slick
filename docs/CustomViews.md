### CustomViews Instruction

Since the View class does not have appropriate lifecycle callbacks,
Slick provides some delegates which should be called manually.

```java
public class CustomView extends LinearLayout implements ExampleView, SlickLifecycleListener {

    @Presenter
    ViewPresenter presenter;

    public CustomView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewPresenter_Slick.onAttach(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ViewPresenter_Slick.onDetach(this);
    }

    @Override
    public void onBind() {
        ViewPresenter_Slick.bind(this);
    }
}
```
As you can see there are three delegates methods and an interface that has to be implemented and called at appropriate time,
And don't worry about forgetting to implement the interface, The generate class requires you to implement it and if you
don't the IDE will remind you with a red line error.

### onDestroy method
It's quite important to call this method from the custom view's parent when you remove the view from view hierarchy or
when the parent view is finished, Mostly just calling its `onDestory` method in the parent view `onDestroy` method is
enough.
```java
public class CustomViewActivity extends AppCompatActivity {

    private CustomView customView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        customView = (CustomView) findViewById(R.id.custom_view);
        customView.onBind("some_random_id");// <--- Sending a unique id insures you don't lose your presenter if you have mutiple instance of your view
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewPresenter_Slick.onDestroy(customView); //<--- onDestroy notification should be passed to the generated class
        // It's possible to send the `onDestory` callbacks with the view's id if you don't have access to its instance anymore. (View hosted in Fragment)
        // ViewPresenter_Slick.onDestroy("some_random_id", this);
    }
}
```
However you may manually remove a view from its parent too, if you don't want to leak its presenter you should call its
`onDestroy` method.

For more samples take a look at the `sample-app` module

