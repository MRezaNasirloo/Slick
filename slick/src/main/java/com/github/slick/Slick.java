package com.github.slick;

import android.support.annotation.NonNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-25
 */

@SuppressWarnings("TryWithIdenticalCatches")
public class Slick {

    public static Object bind(@NonNull Object view, @NonNull Object... args) {
        Object[] presentersArgs = new Object[args.length + 1];
        presentersArgs[0] = view;
        for (int i = 0; i < args.length; i++) {
            presentersArgs[i + 1] = args[i];
        }
        return invokeMethod(presentersArgs, "bind");

    }

    public static Object bind(@NonNull Object view) {
        return invokeMethod(view, "bind");
    }

    public static void onStart(@NonNull Object view) {
        invokeMethod(view, "onStart");
    }

    public static void onStop(@NonNull Object view) {
        invokeMethod(view, "onStop");
    }

    public static void onDestroy(@NonNull Object view) {
        invokeMethod(view, "onDestroy");
    }

    public static void onAttach(@NonNull Object view) {
        invokeMethod(view, "onAttach");
    }

    public static void onDetach(@NonNull Object view) {
        invokeMethod(view, "onDetach");
    }

    private static Object invokeMethod(@NonNull Object view, String methodName) {
        try {
            final Class<?> presenter = Class.forName(view.getClass().getCanonicalName() + "_Slick");
            final Method[] bind = presenter.getDeclaredMethods();
            for (Method method : bind) {
                if (methodName.equals(method.getName()) && Modifier.isStatic(method.getModifiers())) {
                    return method.invoke(null, view);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
