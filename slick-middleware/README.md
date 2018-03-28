### Slick Middleware

_**This module is Experimental**_

#### What is Slick Middleware?

Slick middleware is inspired from the most popular PHP web framework, Laravel's Middleware.

Simply a middleware is a clause which should be checked before a process. Imagine your app needs to check if the user has
logged in before the user can add an item into his favorite list, Otherwise the app should navigate the user to login
page and after a successful login, Continue the process from there. Your app needs this kinda checks every now and often,
Buy an Item, Comment on a photo, Like a photo and etc. So what do you do? Write this clause every time?! With the help
of Slick Middleware you can just annotate your method and let the Slick write the dumb codes.

#### How to use?

First of all define your Middleware by extending the abstract Middleware class:
```java
public class MiddlewareLogin extends Middleware {
    
    private SharedPreferences sp;

    public MiddlewareLogin(SharedPreferences sp) {
        this.sp = sp;
    }

    @Override
    public void handle(Request request, BundleSlick data) {
        if (sp.getBoolean("HAS_LOGGED_IN", false)) {
            request.next();// process the next request
        } else {
            //Show the login page to user.
            Navigator.go(ActivityLogin.class);
        }
    }
}
```
Here is another middleware which checks the internet access:
```java
public class MiddlewareInternetAccess extends Middleware {


    private final Context context;

    public MiddlewareInternetAccess(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public void handle(Request request, BundleSlick data) {
        if (isNetworkAvailable(context)) {
            request.next();// process the next request
        } else {
            //Showing an error to user
            Navigator.go(ActivityError.class);
        }
    }
}
```
Then in your Repository, Interactor, UseCase or in my case Router annotate your method that need these checks:
```java
public class RouterLike {

    private RepositoryLike voteRepository = new RepositoryLike();

    @Middleware({MiddlewareInternetAccess.class, MiddlewareLogin.class})
    public String like(String id, Callback<String> stuff) {
        return voteRepository.like(id);
    }

    @Middleware({MiddlewareInternetAccess.class, MiddlewareLogin.class})
    public Single<Boolean> unlike() {
        return voteRepository.unLike();
    }
}
```
Note Slick checks the middlewares in order which you have wrote them.

Use it like:
```java
public class YourPresenter {
    
    private RouterLike router;
    
    public YourPresenter() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        this.router = new RouterLikeSlick(new MiddlewareInternetAccess(this), new MiddlewareLogin(sp));
        //Instead of using the RouterLike's constructor, Use the generated class RouterLikeSlick's constructor.
    }
    
    public void like(String id) {
        //You can provide a callback with the same return type of the method you have annotated(Optional).
        router.like("some_id", new Callback<String>() {
               @Override
               public void onPass(String result) {
                   //show the result 
               }
           });
    }
}
```
Lastly the configuration:
```java

public class ActivityMain extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        //Continue processing the requests.
        RequestStack.getInstance().processLastRequest();
        //This ensures when the user comes back from the login page, the app should like the item if the user has logged in.
    }

    @Override
    public void onBackPressed() {
        //Let the Slick Middleware know of this.
        RequestStack.getInstance().handleBack();
        super.onBackPressed();
    }
}
```
That's it, Now when you request to like an Item, Slicks checks the internet connection and if there was an internet
connection it will checks the user login status and if the user has logged in, It will like the item and return the result.

Slick Middleware supports RxJava2 `Observable`, `Flowable`, `Single`, `Maybe`, `Completable` types, and you can use
Dagger to inject your class that contains the annotated methods.

For more info check the classes inside the test folder of this module and the sample-middleware module.

```groovy
implementation 'com.mrezanasirloo.slick:slick-middleware:x.x.x'

annotationProcessor 'com.mrezanasirloo.slick:slick-compiler:x.x.x'

```

