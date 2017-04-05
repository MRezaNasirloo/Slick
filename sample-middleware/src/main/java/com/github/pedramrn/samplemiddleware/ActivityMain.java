package com.github.pedramrn.samplemiddleware;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.github.slick.middleware.RequestStack;

import java.util.ArrayList;
import java.util.List;

public class ActivityMain extends AppCompatActivity {

    private static final String TAG = ActivityMain.class.getSimpleName();
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppRouter.bind(this);
        setContentView(R.layout.activity_main);

        List<String> quotes = new ArrayList<>(10);
        quotes.add(
                "“All life is an experiment. The more experiments you make the better.” \n—Ralph Waldo Emerson");
        quotes.add(
                "“All of life is peaks and valleys. Don’t let the peaks get too high and the valleys too low.” —John Wooden");
        quotes.add("“Find ecstasy in life; the mere sense of living is joy enough.”\n —Emily Dickinson");
        quotes.add(
                "“My mission in life is not merely to survive, but to thrive; and to do so with some passion, some compassion, some humor, and some style.” —Maya Angelou");
        quotes.add(
                "“However difficult life may seem, there is always something you can do and succeed at.” —Stephen Hawking");
        quotes.add(
                "“Life is like riding a bicycle. To keep your balance, you must keep moving.” —Albert Einstein");
        quotes.add(
                "“The more you praise and celebrate your life, the more there is in life to celebrate.” —Oprah Winfrey");
        quotes.add(
                "“The most important thing is to enjoy your life—to be happy—it’s all that matters. —Audrey Hepburn");

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        LikeRouter router = new LikeRouterSlick(new MiddlewareInternetAccess(this), new MiddlewareLogin(sp));
        final QuoteRecyclerView quoteRecyclerView = new QuoteRecyclerView(quotes, router);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_quote);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(quoteRecyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RequestStack.getInstance().processLastRequest();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppRouter.unbind();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            sp.edit().remove("HAS_LOGGED_IN").apply();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        RequestStack.getInstance().handleBack();
        super.onBackPressed();
    }
}
