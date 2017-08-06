package vaycent.service.router;

import android.content.Context;
import android.text.TextUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

import vaycent.service.router.annotations.ClassName;


public class XRouter {

    public static <T> IntentWrapper getRaw(final Class<T> service, final Context context) {
        if (!service.isInterface())
            throw new IllegalArgumentException("Functional interface is required.");

        Method[] methods = service.getMethods();
        if (methods.length != 1)
            throw new IllegalArgumentException("Functional interface is required.");

        Annotation[] methodAnnotations = methods[0].getAnnotations();
        String mClassName = null;
        for (Annotation annotation : methodAnnotations) {
            if (annotation instanceof ClassName) {
                mClassName = ((ClassName) annotation).value();
                break;
            }
        }
        if (TextUtils.isEmpty(mClassName))
            throw new RuntimeException("Method with ClassName annotation is required.");

        // 参数类型
        Type[] types = methods[0].getGenericParameterTypes();

        Object[] args = new Object[types.length];
        for (int i = 0; i < args.length; i++) args[i] = null;

        return new IntentWrapper.Builder(context, methods[0], args).build();
    }

    public static <T> T get(final Class<T> service, final Context context) {
        ClassLoader classLoader = service.getClassLoader();
        InvocationHandler handler = (proxy, method, args) -> invoke(context, method, args);
        @SuppressWarnings("unchecked")
        T t = (T) Proxy.newProxyInstance(classLoader, new Class<?>[]{service}, handler);
        return t;
    }

    private static Object invoke(Context context, Method method, Object... args) throws Throwable {
        IntentWrapper intentWrapper = new IntentWrapper.Builder(context, method, args).build();
        Class returnType = method.getReturnType();
        if (returnType == IntentWrapper.class) return intentWrapper;
        throw new RuntimeException("Method return type only support 'IntentWrapper'");
    }
}
