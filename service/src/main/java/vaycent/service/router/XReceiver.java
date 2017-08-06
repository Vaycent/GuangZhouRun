package vaycent.service.router;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import vaycent.service.router.annotations.ClassName;
import vaycent.service.router.annotations.DefaultBoolean;
import vaycent.service.router.annotations.DefaultByte;
import vaycent.service.router.annotations.DefaultChar;
import vaycent.service.router.annotations.DefaultDouble;
import vaycent.service.router.annotations.DefaultFloat;
import vaycent.service.router.annotations.DefaultInt;
import vaycent.service.router.annotations.DefaultLong;
import vaycent.service.router.annotations.DefaultShort;
import vaycent.service.router.annotations.Key;


public class XReceiver<T> {

    public void receive(Intent intent, T receiver) {
        receive(intent.getExtras(), receiver);
    }

    public void receive(Bundle bundle, T receiver) {
        Class<?> classes[] = receiver.getClass().getInterfaces();
        if (classes.length == 0)
            throw new IllegalArgumentException("reciever must implements route interface.");
        if (classes.length > 1)
            throw new IllegalArgumentException("reciever can only implements one route interface.");

        Method[] methods = classes[0].getMethods();
        for (Method method : methods) {
            Annotation[] methodAnnotations = method.getAnnotations();
            String mClassName = null;
            for (Annotation annotation : methodAnnotations) {
                if (annotation instanceof ClassName) {
                    mClassName = ((ClassName) annotation).value();
                    break;
                }
            }
            if (TextUtils.isEmpty(mClassName)) continue;

            // 参数类型
            Type[] types = method.getGenericParameterTypes();

            if (types.length == 0) return;
            Object[] returnArgs = new Object[types.length];

            // 参数名称
            Annotation[][] parameterAnnotationsArray = method.getParameterAnnotations();

            for (int i = 0; i < types.length; i++) {
                Annotation[] parameterAnnotations = parameterAnnotationsArray[i];
                String key = null;
                Object defaultValue = null;
                for (Annotation annotation : parameterAnnotations) {
                    if (annotation instanceof Key) key = ((Key) annotation).value();
                    else defaultValue = getDefaultValue(annotation, types[i]);
                }
                if (TextUtils.isEmpty(key)) continue;
                returnArgs[i] = TypeParser.recieveValue(bundle, types[i], key, defaultValue);
            }
            try {
                method.invoke(receiver, returnArgs);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        throw new RuntimeException("ClassName annotation is required.");
    }

    private Object getDefaultValue(Annotation annotation, Type type) {
        if (annotation instanceof DefaultBoolean) {
            Class<?> c = TypeParser.getRawType(type);
            if (c != boolean.class && c != Boolean.class) {
                throw new RuntimeException("Default Boolean type did not match.");
            } else return ((DefaultBoolean) annotation).value();
        } else if (annotation instanceof DefaultByte) {
            Class<?> c = TypeParser.getRawType(type);
            if (c != byte.class && c != Byte.class) {
                throw new RuntimeException("Default Byte type did not match.");
            } else return ((DefaultByte) annotation).value();
        } else if (annotation instanceof DefaultChar) {
            Class<?> c = TypeParser.getRawType(type);
            if (c != char.class) {
                throw new RuntimeException("Default Char type did not match.");
            } else return ((DefaultChar) annotation).value();
        } else if (annotation instanceof DefaultDouble) {
            Class<?> c = TypeParser.getRawType(type);
            if (c != double.class && c != Double.class) {
                throw new RuntimeException("Default Double type did not match.");
            } else return ((DefaultDouble) annotation).value();
        } else if (annotation instanceof DefaultFloat) {
            Class<?> c = TypeParser.getRawType(type);
            if (c != float.class && c != Float.class) {
                throw new RuntimeException("Default Float type did not match.");
            } else return ((DefaultFloat) annotation).value();
        } else if (annotation instanceof DefaultInt) {
            Class<?> c = TypeParser.getRawType(type);
            if (c != int.class && c != Integer.class) {
                throw new RuntimeException("Default Int type did not match.");
            } else return ((DefaultInt) annotation).value();
        } else if (annotation instanceof DefaultLong) {
            Class<?> c = TypeParser.getRawType(type);
            if (c != long.class && c != Long.class) {
                throw new RuntimeException("Default Long type did not match.");
            } else return ((DefaultLong) annotation).value();
        } else if (annotation instanceof DefaultShort) {
            Class<?> c = TypeParser.getRawType(type);
            if (c != short.class && c != Short.class) {
                throw new RuntimeException("Default Short type did not match.");
            } else return ((DefaultShort) annotation).value();
        }
        return null;
    }
}
