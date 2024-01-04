package me.sleepyfish.rat.utils.misc;

import net.minecraft.util.Session;
import net.minecraft.client.Minecraft;

import org.apache.commons.lang3.ClassUtils;

import java.util.Arrays;
import java.lang.reflect.Type;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
public class FieldSearcherUtils {

    public static void setSession(Session s) throws Exception {
        Class<? extends Minecraft> mc = MinecraftUtils.mc.getClass();
        try {
            Field session = null;

            for (Field f : mc.getDeclaredFields()) {
                if (f.getType().isInstance(s)) {
                    session = f;
                    System.out.println("Found field " + f + ", injecting...");
                }
            }

            if (session == null) {
                throw new IllegalStateException("No field of type " + Session.class.getCanonicalName() + " declared.");
            }

            session.setAccessible(true);
            session.set(MinecraftUtils.mc, s);
            session.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void callVoid(Object instance, String methodName, Object... params) {
        FieldSearcherUtils.executeMethod(instance.getClass(), instance, methodName, params);
    }

    public static void callVoid(Class<?> clazz, Object instance, String methodName, Object... params) {
        FieldSearcherUtils.executeMethod(clazz, instance, methodName, params);
    }

    private static <T> void executeMethod(Class<?> clazz, Object instance, String methodName, Object... params) {
        try {
            Method[] allMethods = clazz.getDeclaredMethods();

            if (allMethods.length > 0) {
                Class<?>[] paramClasses = Arrays.stream(params).map(p -> p != null ? p.getClass() : null).toArray(Class[]::new);

                for (Method method : allMethods) {
                    String name = method.getName();
                    if (!name.equals(methodName))
                        continue;

                    Type[] pTypes = method.getParameterTypes();
                    if (pTypes.length == paramClasses.length) {
                        boolean goodMethod = true;
                        int i = 0;

                        for (Type pType : pTypes) {
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
        } catch (Exception ignored) {
        }
    }

    public static Minecraft getMinecraft() {
        try {
            Minecraft myObject = new Minecraft(null);
            Class<?> myClass = myObject.getClass();

            Field field = myClass.getDeclaredField("theMinecraft");

            field.setAccessible(true);
            Object mcObj = field.get(myObject);
            field.setAccessible(false);

            return (Minecraft) mcObj;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}