###Slick Unidirectional Data Flow and Immutable ViewState (UDAFIV)

To use this feature you need to pull its package and extend the `SlickPresenterUni<V, S>` :
```groovy
implementation 'com.mrezanasirloo.slick:slick-uni:x.x.x'
```

```java
public class YourPresenterUni extends SlickPresenterUni<ViewActivity, ViewStateActivity> {

    public YourPresenterUni(Scheduler main, Scheduler io) {
        super(main, io);
    }

    /**
    * This method is only called once
    */
    @Override
    protected void start(ViewActivity view) {
        
    }
	
    /**
    * Abstract method to update the view, It's called every time there's a new viewState and
    * on every onViewUp call to deliver the latest view state, e.g: screen orientation
    */
    @Override
    protected void render(@NonNull ViewStateActivity state, @NonNull ViewActivity view) {
        
    }
}
```
First of all! What is `ViewStateActivity`? It’s a simple Immutable POJO class that holds the view’s state, 
And it doesn't contains any Android specific dependency. Imagine a simple view that is responsible to show a movie 
details and the users comments about the movie. It needs the movie details e.g Title, Poster, Ratings, Comments, 
And is it in favorite list?, I just write the last two:

```java
public class ViewStateActivity {

    private List<String> comments;
    private boolean liked;

    public ViewStateActivity(List<String> comments, boolean liked) {
        this.comments = comments;
        this.liked = liked;
    }

    public List<String> comments() {
        return comments;
    }

    public boolean isLiked() {
        return liked;
    }
}
```
Dead simple, Also there are two abstract methods `start()` and `render()`, The `start()` method is called only once when the
presenter’s `onViewUp()` is called, We use this method to create our ViewState stream.


The second one is the `render()` method, This method is called every time there’s a new `ViewState`, Also it gets called on 
every `onViewUp()` call to deliver the last ViewState, e.g: Screen orientation


Let’s fill up these methods, I begin with the `start()` method: 

```java
public class YourPresenterUni extends SlickPresenterUni<ViewActivity, ViewStateActivity> {

    /* ... */

    /**
    * This method is only called once
    */
    @Override
    protected void start(ViewActivity view) {
    int id = view.getId();
    //Command stream to execute
    Observable<PartialViewState<ViewStateActivity>> like = command(ViewActivity::likeMovie)
            .flatMap(liked -> repositoryMovies.like(id, liked).subscribeOn(io))//call to backend
            //map the backend result to partial view state
            .map(PartialViewStateLiked::new);

    Observable<PartialViewState<ViewStateActivity>> loadComments = command(ViewActivity::loadComments)
            .flatMap(ignored -> repositoryComments.load(id).subscribeOn(io))
            .map(PartialViewStateComments::new);

    //The initial view state before any call to backend which will be rendered as soon as possible
    ViewStateActivity initialState = new ViewStateActivity(Collections.emptyList(), false);
    //Merges the partial view state stream into one stream
    Observable<PartialViewState<ViewStateActivity>>	viewStateStream = merge(like, loadComments);
    //Key method, This will join each upcoming PartialViewState into a new ViewStateActivity
    reduce(initialState, viewStateStream).subscribe(this);//<--- Don't forget to call subscribe(this) !!!
	
    }
}
```
There are two key methods here the `command()` and `reduce()` methods, I will dedicate another post to explain whats 
``going on under the hood, But for now I just explain what they do. 

![Alt Text](https://cdn-images-1.medium.com/max/880/1*D4Zxe8gKFTtoF4LcZ8CoNw.gif, "How Commands and Reduce works")

So what is a command? Commands are given to execute, e.g: A General commands its solders to march or fire or whatever
that they do 😄

Same analogy here, Your app has commands too, e.g: Post a picture, Like a comment, These commands need to be executed 
and their result should be delivered to View. There comes the ViewState.

But every command’s result is a subset of this class, So we need another class to represent the partial data updates. 
Meet the `PartialViewState<ViewState>` interface, every command result should return an instance of this interface,
here is an implementation for movie favorite state:

```java
public class PartialViewStateLiked implements PartialViewState<ViewStateActivity> {

    private boolean isLiked;

    public PartialViewStateLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }

    @Override
    public ViewStateExample reduce(ViewStateExample state) {
        return new ViewStateActivity(state.text(), isLiked);
    }
}
```

As you can see we create a new ViewState and change only the part which has updated. 
Using AutoValue is highly recommended here.

And here is the `ViewActivity` interface:

```java
public interface ViewActivity {

    Observable<Integer> loadComments();

    Observable<Boolean> likeMovie();

    void showComments(List<String> comments);
    
    void showNoComments();

    void setLike(boolean liked);
    
    int getId();
}
```

The first two are command stream methods that we saw earlier.

Here is a simple implementation of `likeMovie()` :

```
@Overide
public Observable<Boolean> likeMovie(){
    return RxCompoundButton.checkedChanges(likeView).throttleLast(1, TimeUnit.SECONDS);
}
```

And finally the last part last part is rendering the ViewStates to View:

```
@Override
protected void render(@NonNull ViewStateActivity state, @NonNull ViewActivity view) {
    if (!state.comments().isEmpty()) { view.showComments(state.comments()); }
    else { view.showNoComments(); }
    view.setLike(state.isLiked());
}
```

That's it.


