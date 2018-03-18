## CustomViews Instruction

Since the View class does not have appropriate lifecycle callbacks,
Slick provides some delegates which should called manually.

```java
public class CustomView extends LinearLayout implements ExampleView, OnDestroyListener {

    @Presenter
    ViewPresenter presenter;

    public CustomView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewPresenter_Slick.bind(this);
        ViewPresenter_Slick.onAttach(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ViewPresenter_Slick.onDetach(this);
    }

    @Override
    public void onDestroy() {
        ViewPresenter_Slick.onDestroy(this);
    }
}
```
As you can see there are 3 delegates methods and an interface that has to be implemented and called at appropriate time,
And don't worry about forgetting to implement the interface, The generate class requires you to implement it and if you
don't the IDE will remind you with red line error.

### onDestroy method
It's quite important to call this method from the custom view parent when you remove the view from view hierarchy or
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        customView.onDestroy(); //<--- onDestroy notification should be passed to view
    }
}
```
However you may manually remove a view from its parent too, if you don't want to leak its presenter you should call its
`onDestroy` method.

