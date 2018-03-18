#### Inject Presenter with Dagger

Slick is Dagger ready out of the box, To inject your Presenter with Dagger just inject your Presenter wrapped in
Dagger's `Provider<T>` interface.

```java
public class YourActivity extends AppCompatActivity implements ViewActivity {

    @Inject
    Provider<YourPresenter> provider;
    @Presenter
    YourPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.getDaggerComponent(this).inject(this);
        YourPresenter_Slick.bind(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your);
    }
}
```
And the Presenter:
```java
public class YourPresenter extends SlickPresenter<ViewActivity> {

    @Inject
    public YourPresenter(@NonNull Long lng, String s) {
        
    }
}
```
