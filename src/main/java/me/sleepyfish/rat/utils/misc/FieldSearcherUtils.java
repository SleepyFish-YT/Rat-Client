package me.sleepyfish.rat.utils.misc;

import net.minecraft.util.Session;

import org.apache.commons.lang3.ClassUtils;

import java.util.Arrays;
import java.lang.reflect.Type;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class FieldSearcherUtils {

    public static void setSession(final Session s) throws Exception {
        try {
            Field session = null;

            for (final Field f : MinecraftUtils.mc.getClass().getDeclaredFields()) {
                if (f.getType().isInstance(s))
                    session = f;
            }

            if (session == null)
                return;

            session.setAccessible(true);
            session.set(MinecraftUtils.mc, s);
            session.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void callVoid(final Object instance, final String methodName, final Object... params) {
        FieldSearcherUtils.executeMethod(instance.getClass(), instance, methodName, params);
    }

    public static void callVoid(final Class<?> clazz, final Object instance, final String methodName, final Object... params) {
        FieldSearcherUtils.executeMethod(clazz, instance, methodName, params);
    }

    private static <T> void executeMethod(final Class<?> clazz, final Object instance, final String methodName, final Object... params) {
        try {
            final Method[] allMethods = clazz.getDeclaredMethods();

            if (allMethods.length > 0) {
                final Class<?>[] paramClasses = Arrays.stream(params).map(p -> p != null ? p.getClass() : null).toArray(Class[]::new);

                for (final Method method : allMethods) {
                    final String name = method.getName();
                    if (!name.equals(methodName))
                        continue;

                    final Type[] pTypes = method.getParameterTypes();
                    if (pTypes.length == paramClasses.length) {
                        boolean goodMethod = true;
                        int i = 0;

                        for (final Type pType : pTypes) {
                            if (!ClassUtils.isAssignable(paramClasses[i++], (Class<?>) pType)) {
                                goodMethod = false;
                                break;
                            }
                        }

                        if (goodMethod) {
                            method.setAccessible(true);
                            method.invoke(instance, params);
                            return;
                        }
                    }
                }

                throw new NoSuchMethodException("There are no methods found with name " + methodName + " and params " +
                        Arrays.toString(paramClasses));
            }

            throw new NoSuchMethodException("There are no methods found with name " + methodName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}