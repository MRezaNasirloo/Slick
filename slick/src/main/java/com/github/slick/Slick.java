package com.github.slick;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-25
 */

public class Slick {

    private static final String TAG = Slick.class.getSimpleName();

    public static Object bind(Object view) {
        try {
            final Class<?> presenter = Class.forName(view.getClass().getCanonicalName() + "_Slick");
            final Method[] bind = presenter.getDeclaredMethods();
            for (Method method : bind) {
                if ("bind".equals(method.getName())) {
                    return method.invoke(null, view);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object bind(Object view, Object... args) {
        try {
            Object[] presentersArgs = new Object[args.length + 1];
            presentersArgs[0] = view;
            for (int i = 0; i < args.length; i++) {
                presentersArgs[i + 1] = args[i];
            }
            final Class<?> presenter = Class.forName(view.getClass().getCanonicalName() + "_Slick");
            final Method[] bind = presenter.getDeclaredMethods();
            for (Method method : bind) {
                if ("bind".equals(method.getName())) {
                    return method.invoke(null, presentersArgs);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void onStart(Object view) {
        try {
            final Class<?> presenter = Class.forName(view.getClass().getCanonicalName() + "_Slick");
            final Method[] bind = presenter.getDeclaredMethods();
            for (Method method : bind) {
                if ("onStart".equals(method.getName())&& Modifier.isStatic(method.getModifiers())) {
                    method.invoke(null, view);
                    return;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void onStop(Object view) {
        try {
            final Class<?> presenter = Class.forName(view.getClass().getCanonicalName() + "_Slick");
            final Method[] bind = presenter.getDeclaredMethods();
            for (Method method : bind) {
                if ("onStop".equals(method.getName())&& Modifier.isStatic(method.getModifiers())) {
                    method.invoke(null, view);
                    return;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void onDestroy(Object view) {
        try {
            final Class<?> presenter = Class.forName(view.getClass().getCanonicalName() + "_Slick");
            final Method[] bind = presenter.getDeclaredMethods();
            for (Method method : bind) {
                if ("onDestroy".equals(method.getName()) && Modifier.isStatic(method.getModifiers())) {
                    method.invoke(null, view);
                    return;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
