package com.mrezanasirloo.slick.middleware;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.Stack;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-03-13
 */

public class RequestStack {

    private static RequestStack requestStack;
    private final Stack<Request> stack;
    private boolean changingConfigurations;
    private ActivityListener activityListener = new ActivityListener();

    public static RequestStack getInstance() {
        if (requestStack == null) {
            requestStack = new RequestStack();
        }
        return requestStack;
    }

    private RequestStack() {
        stack = new Stack<>();
    }

    public void init(Application application) {
        application.registerActivityLifecycleCallbacks(activityListener);
    }

    public RequestStack push(Request request) {
        if (stack.contains(request)) {
            return this;
        }
        stack.push(request);
        return this;
    }

    public void processLastRequest() {
        if (!changingConfigurations && stack.size() > 0) {
            final Request peek = stack.peek();
            peek.refill();
            peek.next();
        }
    }

    public Request pop() {
        return stack.pop();
    }

    public Request peek() {
        return stack.peek();
    }

    public int capacity() {
        return stack.capacity();
    }

    public int size() {
        return stack.size();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public boolean contains(Request request) {
        return stack.contains(request);
    }

    public void clear() {
        stack.clear();
    }

    public void handleBack() {
        if (stack.size() > 0) {
            stack.pop();
        }
    }

    private class ActivityListener implements Application.ActivityLifecycleCallbacks {


        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }

        @Override
        public void onActivityResumed(Activity activity) {
            changingConfigurations = activity.isChangingConfigurations();
        }

        @Override
        public void onActivityPaused(Activity activity) {
            changingConfigurations = activity.isChangingConfigurations();
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
        }
    }

}
