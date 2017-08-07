package vaycent.service.router;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

import vaycent.service.router.annotations.ClassName;
import vaycent.service.router.annotations.IntentFlags;
import vaycent.service.router.annotations.RequestCode;

public class XRouter {

    /**
     * 用于无参数的页面跳转,调用方式：
     * <p>XRouter.getRaw(service, context).start();</p>
     *
     * @param service 用于跳转的动态代理服务接口
     * @param context 一般传入 Activity，若传入其他 context，则会在 Intent 中加入
     *                {@link Intent#FLAG_ACTIVITY_NEW_TASK} 用于指定在新的任务栈中启动页面
     */
    public static <T> IntentWrapper getRaw(final Class<T> service, final Context context) {
        validateInterface(service);

        String className = getServiceClassName(service);
        Integer requestCode = getServiceRequestCode(service);
        Integer intentFlags = getServceIntentFlags(service);

        Method[] methods = service.getMethods();
        if (methods == null || methods.length == 0) {
            return new IntentWrapper.Builder(context, null).setClassName(className)
                    .setRequestCode(requestCode).setFlags(intentFlags).build();
        }

        // 获取函数接口中方法的参数类型
        Type[] types = methods[0].getGenericParameterTypes();

        // 当前是无参数的页面跳转，所以全部参数设置为空
        Object[] args = new Object[types.length];
        for (int i = 0; i < args.length; i++) args[i] = null;

        return new IntentWrapper.Builder(context, methods[0], args).setClassName(className)
                .setRequestCode(requestCode).setFlags(intentFlags).build();
    }

    /**
     * 用于带参数的页面跳转，调用方式：
     * <p>XRouter.get(service, context).toXXXActivity(params...).start();</p>
     *
     * @param service 用于跳转的动态代理服务接口
     * @param context 一般传入 Activity，若传入其他 context，则会在 Intent 中加入
     *                {@link Intent#FLAG_ACTIVITY_NEW_TASK} 用于指定在新的任务栈中启动页面
     */
    public static <T> T get(final Class<T> service, final Context context) {
        validateInterface(service);
        String className = getServiceClassName(service);
        Integer requestCode = getServiceRequestCode(service);
        Integer intentFlags = getServceIntentFlags(service);

        ClassLoader classLoader = service.getClassLoader();
        InvocationHandler handler = (proxy, method, args) -> new IntentWrapper.Builder(context, method,
                args).setClassName(className).setRequestCode(requestCode).setFlags(intentFlags).build();
        @SuppressWarnings("unchecked")
        T t = (T) Proxy.newProxyInstance(classLoader, new Class<?>[]{service}, handler);
        return t;
    }

    /**
     * 检查传进来的接口是否符合要求
     *
     * @param service 用于跳转的动态代理服务接口
     */
    private static void validateInterface(Class<?> service) {
        if (service == null)// 检查传进来的类型是否为空
            throw new NullPointerException("Functional interface is required.");

        if (!service.isInterface())// 检查传进来的是否是一个接口
            throw new IllegalArgumentException("Functional interface is required.");

        if (!service.isAnnotationPresent(ClassName.class))// 判断接口是否带有 ClassName 注解
            throw new IllegalArgumentException("Functional interface with ClassName annotation is required.");

        Method[] methods = service.getMethods();

        if (methods.length == 0) return;// 允许无方法的接口，默认就是不带参的跳转

        if (methods.length > 1)// 检查接口是否是一个函数接口
            throw new IllegalArgumentException("Functional interface is required.");

        Class returnType = methods[0].getReturnType();
        if (returnType != IntentWrapper.class) // 检查函数接口的方法的返回类型是否为 IntentWrapper
            throw new RuntimeException("Method return type only support 'IntentWrapper'");
    }

    /**
     * 获取传进来的跳转服务接口的 ClassName 注解，即需要跳转的 Activity 的类名（不能为空）
     *
     * @param service 用于跳转的动态代理服务接口
     */
    private static String getServiceClassName(Class<?> service) {
        Annotation[] methodAnnotations = service.getAnnotations();
        String mClassName = null;
        for (Annotation annotation : methodAnnotations) {
            if (!(annotation instanceof ClassName)) continue;
            mClassName = ((ClassName) annotation).value();
            break;
        }
        // 检查传进来的函数接口是否有 ClassName 注解或注解为空
        if (!TextUtils.isEmpty(mClassName)) return mClassName;
        throw new RuntimeException("Interface with ClassName annotation is required.");
    }

    /**
     * 获取跳转服务接口的 RequestCode 注解，即当调用 startActivityForResult 时需要的请求码，
     * 若为空，则会按普通的 startActivity 执行。
     *
     * @param service 用于跳转的动态代理服务接口
     */
    private static Integer getServiceRequestCode(Class<?> service) {
        Annotation[] methodAnnotations = service.getAnnotations();
        for (Annotation annotation : methodAnnotations) {
            if (!(annotation instanceof RequestCode)) continue;
            return ((RequestCode) annotation).value();
        }
        return null;
    }

    /**
     * 获取跳转服务接口的 IntentFlags 注解。
     *
     * @param service 用于跳转的动态代理服务接口
     */
    private static Integer getServceIntentFlags(Class<?> service) {
        Annotation[] methodAnnotations = service.getAnnotations();
        for (Annotation annotation : methodAnnotations) {
            if (!(annotation instanceof IntentFlags)) continue;
            return ((IntentFlags) annotation).value();
        }
        return null;
    }
}
