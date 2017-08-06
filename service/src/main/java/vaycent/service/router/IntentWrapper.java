package vaycent.service.router;

import android.app.Activity;
import android.content.Context;
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
import vaycent.service.router.annotations.IntentFlags;
import vaycent.service.router.annotations.Key;
import vaycent.service.router.annotations.RequestCode;


public class IntentWrapper {

    private Context mContext;
    private Bundle mExtras;
    private int mRequestCode = -1;
    private Intent mIntent;

    private IntentWrapper(Context context, String className, Bundle extras, int flags, int requestCode) {
        this.mContext = context;
        this.mExtras = extras;
        this.mRequestCode = requestCode;

        this.mIntent = new Intent();
        this.mIntent.setClassName(this.mContext, className);
        this.mIntent.putExtras(this.mExtras);
        this.mIntent.addFlags(flags);
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public IntentWrapper addFlags(int flags) {
        this.mIntent.addFlags(flags);
        return this;
    }

    public void start() {
        if (this.mRequestCode == -1) startActivity();
        else startActivityForResult(this.mRequestCode);
    }

    public void startActivity() {
        if (!(this.mContext instanceof Activity))
            this.mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.mContext.startActivity(this.mIntent);
    }

    public void startActivityForResult(int requestCode) {
        if (!(this.mContext instanceof Activity)) {
            throw new RuntimeException("StartActivityForResult only works for activity context");
        } else ((Activity) this.mContext).startActivityForResult(this.mIntent, requestCode);
    }

    public static final class Builder {

        private int mFlags;
        private int mRequestCode;
        private Context mContext;
        private Method mMethod;
        private Object[] mArgs;
        private String mClassName;

        public Builder(Context context, Method method, Object... args) {
            this.mContext = context;
            this.mMethod = method;
            this.mArgs = args;
        }

        public Builder addFlags(int flags) {
            this.mFlags |= flags;
            return this;
        }

        public IntentWrapper build() {
            Annotation[] methodAnnotations = this.mMethod.getAnnotations();
            for (Annotation annotation : methodAnnotations) {
                parseMethodAnnotation(annotation);
            }
            if (TextUtils.isEmpty(this.mClassName)) {
                throw new RuntimeException("ClassName annotation is required.");
            }
            // 参数类型
            Type[] types = this.mMethod.getGenericParameterTypes();
            // 参数名称
            Annotation[][] parameterAnnotationsArray = this.mMethod.getParameterAnnotations();

            Bundle bundleExtra = new Bundle();
            for (int i = 0; i < types.length; i++) {
                Annotation[] parameterAnnotations = parameterAnnotationsArray[i];
                String key = null;
                Object defaultValue = null;
                for (Annotation annotation : parameterAnnotations) {
                    if (annotation instanceof Key) {
                        key = ((Key) annotation).value();
                    } else if (annotation instanceof DefaultBoolean) {
                        Class<?> c = TypeParser.getRawType(types[i]);
                        if (c != boolean.class && c != Boolean.class) {
                            throw new RuntimeException("Default Boolean type did not match.");
                        } else defaultValue = ((DefaultBoolean) annotation).value();
                    } else if (annotation instanceof DefaultByte) {
                        Class<?> c = TypeParser.getRawType(types[i]);
                        if (c != byte.class && c != Byte.class) {
                            throw new RuntimeException("Default Byte type did not match.");
                        } else defaultValue = ((DefaultByte) annotation).value();
                    } else if (annotation instanceof DefaultChar) {
                        Class<?> c = TypeParser.getRawType(types[i]);
                        if (c != char.class) {
                            throw new RuntimeException("Default Char type did not match.");
                        } else defaultValue = ((DefaultChar) annotation).value();
                    } else if (annotation instanceof DefaultDouble) {
                        Class<?> c = TypeParser.getRawType(types[i]);
                        if (c != double.class && c != Double.class) {
                            throw new RuntimeException("Default Double type did not match.");
                        } else defaultValue = ((DefaultDouble) annotation).value();
                    } else if (annotation instanceof DefaultFloat) {
                        Class<?> c = TypeParser.getRawType(types[i]);
                        if (c != float.class && c != Float.class) {
                            throw new RuntimeException("Default Float type did not match.");
                        } else defaultValue = ((DefaultFloat) annotation).value();
                    } else if (annotation instanceof DefaultInt) {
                        Class<?> c = TypeParser.getRawType(types[i]);
                        if (c != int.class && c != Integer.class) {
                            throw new RuntimeException("Default Int type did not match.");
                        } else defaultValue = ((DefaultInt) annotation).value();
                    } else if (annotation instanceof DefaultLong) {
                        Class<?> c = TypeParser.getRawType(types[i]);
                        if (c != long.class && c != Long.class) {
                            throw new RuntimeException("Default Long type did not match.");
                        } else defaultValue = ((DefaultLong) annotation).value();
                    } else if (annotation instanceof DefaultShort) {
                        Class<?> c = TypeParser.getRawType(types[i]);
                        if (c != short.class && c != Short.class) {
                            throw new RuntimeException("Default Short type did not match.");
                        } else defaultValue = ((DefaultShort) annotation).value();
                    }
                }
                TypeParser.parseParameter(bundleExtra, types[i], key, this.mArgs[i] != null ?
                        this.mArgs[i] : defaultValue);
            }
            return new IntentWrapper(this.mContext, this.mClassName, bundleExtra, this.mFlags,
                    this.mMethod.isAnnotationPresent(RequestCode.class) ? this.mRequestCode : -1);
        }

        private void parseMethodAnnotation(Annotation annotation) {
            if (annotation instanceof ClassName) {
                this.mClassName = ((ClassName) annotation).value();
            } else if (annotation instanceof RequestCode) {
                this.mRequestCode = ((RequestCode) annotation).value();
            } else if (annotation instanceof IntentFlags) {
                this.mFlags = ((IntentFlags) annotation).value();
            }
        }
    }
}
