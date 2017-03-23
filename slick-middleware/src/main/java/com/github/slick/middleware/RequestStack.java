package com.github.slick.middleware;

import android.app.Activity;

import java.util.Stack;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-13
 */

public class RequestStack {

    private static RequestStack requestStack;
    private final Stack<Request> stack;
    private boolean changingConfigurations;

    public static RequestStack getInstance() {
        if (requestStack == null) {
            requestStack = new RequestStack();
        }
        return requestStack;
    }

    private RequestStack() {
        stack = new Stack<>();
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

    public void handle() {
        if (stack.size() > 0) {
            stack.pop();
        }
    }

    public void onResume(Activity activity) {
        changingConfigurations = activity.isChangingConfigurations();
    }

    public void onPause(Activity activity) {
        changingConfigurations = activity.isChangingConfigurations();
    }
}
