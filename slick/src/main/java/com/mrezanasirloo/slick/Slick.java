/*
 * Copyright 2018. M. Reza Nasirloo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mrezanasirloo.slick;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-02-25
 *         <p>
 *         Utility class for for binding the presenter to view,
 *         It Uses a bit of reflection to find the presenter class name, no class instansiation just invoking some static method.
 *         It's recomended to use the generated classes for the bindings,
 *         You will notice any type mismatach errors whle editing code in the IDE not at runtime.
 */

@SuppressWarnings("TryWithIdenticalCatches")
public class Slick {

    @Nullable
    public static Object bind(@NonNull Object view, @NonNull Object... args) {
        Object[] presentersArgs = new Object[args.length + 1];
        presentersArgs[0] = view;
        for (int i = 0; i < args.length; i++) {
            presentersArgs[i + 1] = args[i];
        }
        return invokeMethod(view, "bind", presentersArgs);

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

    private static Object invokeMethod(@NonNull Object view, String methodName, Class<?> presenterCls, Object... args) {
        try {
            final Class<?> presenter = Class.forName(presenterCls.getCanonicalName() + "_Slick");
            final Method[] bind = presenter.getDeclaredMethods();
            for (Method method : bind) {
                if (methodName.equals(method.getName()) && Modifier.isStatic(method.getModifiers())) {
                    if (args.length == 0) {
                        return method.invoke(null, view);
                    } else {
                        return method.invoke(null, args);
                    }
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

    private static Object invokeMethod(@NonNull Object view, String methodName, Object... args) {
        for (Field field : view.getClass().getDeclaredFields()) {
            if (field.getAnnotation(Presenter.class) != null) {
                return invokeMethod(view, methodName, field.getType(), args);
            }
        }
        return null;
    }
}
