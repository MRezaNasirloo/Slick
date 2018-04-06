### Multi Instance Custom Views and Fragments

If you have multiple instance of a Fragment or a CustomView at the same time you need to implement `SlickUniqueId`
interface, Since there is no way to differentiate them from each other out of the box.
Here is its implementation.
####Fragment:
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
####CustomView:
```java
public class CustomView extends AppCompatTextView implements SlickUniqueId {

    private String id;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }    

    @Nullable
    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("superState", super.onSaveInstanceState());
        bundle.putString(SLICK_UNIQUE_KEY, this.id);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            this.id = bundle.getString(SLICK_UNIQUE_KEY);
            state = bundle.getParcelable("superState");
        }
        super.onRestoreInstanceState(state);
    }

    @Override
    public String getUniqueId() {
        return id = (id != null ? id : UUID.randomUUID().toString());
    }
}
```
You can just copy & paste them to your base class and you are good to go.

In Slick the Activity and Conductor Controller classes have this feature out of the box, There is no need for any of
this for them. Because of Android API limitation it wasn't possible to this for Fragment and View classes.
