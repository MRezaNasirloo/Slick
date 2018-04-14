### Multi Instance Custom Views and Fragments

If you have multiple instance of a Fragment or a CustomView at the same time, you need to implement `SlickUniqueId`
interface, since there is no way to differentiate them from each other out of the box.
Here is its implementation.

#### Fragment:
```java
public abstract class SlickFragment extends Fragment implements SlickUniqueId {

    private String id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            id = savedInstanceState.getString(SLICK_UNIQUE_KEY);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SLICK_UNIQUE_KEY, id);
    }

    @Override
    public String getUniqueId() {
        return id = id != null ? id : UUID.randomUUID().toString();
    }
}
```
You can just copy & paste the above code to your base class and you are good to go.

#### CustomView:

Since the View's lifecylces are not rich enough, its parent should provide a unique id for it when it calls its `onBind(String)` method.
```java
public class CustomView extends AppCompatTextView implements SlickLifecycleListener, SlickUniqueId {

    private String id;

    /* ... */

    @Override
    public void onBind(@NonNull String instanceId) {
        this.id = instanceId;
        ViewPresenter_Slick.bind(this);
    }

    @Override
    public String getUniqueId() {
        return id;
    }
}
```
In Slick the Activity and Conductor Controller classes have this feature out of the box, There is no need for any of
this for them. Because of Android API limitation it wasn't possible to do this for Fragment and View classes.

For an advance sample take a look at the sample-app module. package: sample -> customeview -> infinite
