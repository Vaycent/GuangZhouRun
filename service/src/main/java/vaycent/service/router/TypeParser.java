package vaycent.service.router;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.SparseArray;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;

class TypeParser {
    /**
     * 解析参数存储到Bundle中
     *
     * @param bundleExtra 存储的Bundle
     * @param type        参数类型
     * @param key         参数名称
     * @param arg         参数值
     */
    static void parseParameter(Bundle bundleExtra, Type type, String key, Object arg) {
        if (arg == null) return;
        Class<?> rawParameterType = getRawType(type);

        if (rawParameterType == String.class) {
            bundleExtra.putString(key, arg.toString());
        } else if (rawParameterType == String[].class) {
            bundleExtra.putStringArray(key, (String[]) arg);
        } else if (rawParameterType == int.class || rawParameterType == Integer.class) {
            bundleExtra.putInt(key, Integer.parseInt(arg.toString()));
        } else if (rawParameterType == int[].class || rawParameterType == Integer[].class) {
            bundleExtra.putIntArray(key, (int[]) arg);
        } else if (rawParameterType == short.class || rawParameterType == Short.class) {
            bundleExtra.putShort(key, Short.parseShort(arg.toString()));
        } else if (rawParameterType == short[].class || rawParameterType == Short[].class) {
            bundleExtra.putShortArray(key, (short[]) arg);
        } else if (rawParameterType == long.class || rawParameterType == Long.class) {
            bundleExtra.putLong(key, Long.parseLong(arg.toString()));
        } else if (rawParameterType == long[].class || rawParameterType == Long[].class) {
            bundleExtra.putLongArray(key, (long[]) arg);
        } else if (rawParameterType == char.class) {
            bundleExtra.putChar(key, arg.toString().toCharArray()[0]);
        } else if (rawParameterType == char[].class) {
            bundleExtra.putCharArray(key, arg.toString().toCharArray());
        } else if (rawParameterType == double.class || rawParameterType == Double.class) {
            bundleExtra.putDouble(key, Double.parseDouble(arg.toString()));
        } else if (rawParameterType == double[].class || rawParameterType == Double[].class) {
            bundleExtra.putDoubleArray(key, (double[]) arg);
        } else if (rawParameterType == float.class || rawParameterType == Float.class) {
            bundleExtra.putFloat(key, Float.parseFloat(arg.toString()));
        } else if (rawParameterType == float[].class || rawParameterType == Float[].class) {
            bundleExtra.putFloatArray(key, (float[]) arg);
        } else if (rawParameterType == byte.class || rawParameterType == Byte.class) {
            bundleExtra.putByte(key, Byte.parseByte(arg.toString()));
        } else if (rawParameterType == byte[].class || rawParameterType == Byte[].class) {
            bundleExtra.putByteArray(key, (byte[]) arg);
        } else if (rawParameterType == boolean.class || rawParameterType == Boolean.class) {
            bundleExtra.putBoolean(key, Boolean.parseBoolean(arg.toString()));
        } else if (rawParameterType == boolean[].class || rawParameterType == Boolean[].class) {
            bundleExtra.putBooleanArray(key, (boolean[]) arg);
        } else if (rawParameterType == Serializable.class) {
            bundleExtra.putSerializable(key, (Serializable) arg);
        } else if (rawParameterType == Parcelable.class) {
            bundleExtra.putParcelable(key, (Parcelable) arg);
        } else if (rawParameterType == Bundle.class) {
            if (TextUtils.isEmpty(key)) bundleExtra.putAll((Bundle) arg);
            else bundleExtra.putBundle(key, (Bundle) arg);
        } else if (rawParameterType == SparseArray.class) {
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                Type actualTypeArgument = actualTypeArguments[0];

                if (actualTypeArgument instanceof Class) {
                    Class<?>[] interfaces = ((Class) actualTypeArgument).getInterfaces();
                    for (Class<?> interfaceClass : interfaces) {
                        if (interfaceClass == Parcelable.class) {
                            @SuppressWarnings("unchecked")
                            SparseArray<Parcelable> saArgs = (SparseArray<Parcelable>) arg;
                            bundleExtra.putSparseParcelableArray(key, saArgs);
                            return;
                        }
                    }
                    throw new RuntimeException("SparseArray的泛型必须实现Parcelable接口");
                }
            } else {
                throw new RuntimeException("SparseArray的泛型必须实现Parcelable接口");
            }
        } else if (rawParameterType == ArrayList.class) {
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments(); // 泛型类型数组
                if (actualTypeArguments == null || actualTypeArguments.length != 1) {
                    throw new RuntimeException("ArrayList的泛型必须实现Parcelable接口");
                }

                Type actualTypeArgument = actualTypeArguments[0]; // 获取第一个泛型类型
                if (actualTypeArgument == String.class) {
                    @SuppressWarnings("unchecked")
                    ArrayList<String> alArgs = (ArrayList<String>) arg;
                    bundleExtra.putStringArrayList(key, alArgs);
                } else if (actualTypeArgument == Integer.class) {
                    @SuppressWarnings("unchecked")
                    ArrayList<Integer> alArgs = (ArrayList<Integer>) arg;
                    bundleExtra.putIntegerArrayList(key, alArgs);
                } else if (actualTypeArgument == CharSequence.class) {
                    @SuppressWarnings("unchecked")
                    ArrayList<CharSequence> alArgs = (ArrayList<CharSequence>) arg;
                    bundleExtra.putCharSequenceArrayList(key, alArgs);
                } else if (actualTypeArgument instanceof Class) {
                    Class<?>[] interfaces = ((Class) actualTypeArgument).getInterfaces();
                    for (Class<?> interfaceClass : interfaces) {
                        if (interfaceClass == Parcelable.class) {
                            @SuppressWarnings("unchecked")
                            ArrayList<Parcelable> alArgs = (ArrayList<Parcelable>) arg;
                            bundleExtra.putParcelableArrayList(key, alArgs);
                            return;
                        }
                    }
                    throw new RuntimeException("ArrayList的泛型必须实现Parcelable接口");
                }
            } else {
                throw new RuntimeException("ArrayList的泛型必须实现Parcelable接口");
            }
        } else {
            if (rawParameterType.isArray()) {// Parcelable[]
                Class<?>[] interfaces = rawParameterType.getComponentType().getInterfaces();
                for (Class<?> interfaceClass : interfaces) {
                    if (interfaceClass == Parcelable.class) {
                        bundleExtra.putParcelableArray(key, (Parcelable[]) arg);
                        return;
                    } else if (interfaceClass == CharSequence.class) {
                        bundleExtra.putCharSequenceArray(key, (CharSequence[]) arg);
                        return;
                    }
                }
                throw new RuntimeException("Object[]数组中的对象必须全部实现了Parcelable接口");
            } else { // 其他接口
                Class<?>[] interfaces = rawParameterType.getInterfaces();
                for (Class<?> interfaceClass : interfaces) {
                    if (interfaceClass == Parcelable.class) {
                        bundleExtra.putParcelable(key, (Parcelable) arg);
                        return;
                    } else if (interfaceClass == Serializable.class) {
                        bundleExtra.putSerializable(key, (Serializable) arg);
                        return;
                    }
                }
                throw new RuntimeException("Bundle不支持的类型, 参数: " + key);
            }

        }
    }

    static Object recieveValue(Bundle bundle, Type type, String key, Object defaultValue) {
        Class<?> rawParameterType = getRawType(type);

        if (rawParameterType == String.class) {
            return bundle.getString(key);
        } else if (rawParameterType == String[].class) {
            return bundle.getStringArray(key);
        } else if (rawParameterType == int.class || rawParameterType == Integer.class) {
            return bundle.getInt(key, (Integer) defaultValue);
        } else if (rawParameterType == int[].class || rawParameterType == Integer[].class) {
            return bundle.getIntArray(key);
        } else if (rawParameterType == short.class || rawParameterType == Short.class) {
            return bundle.getShort(key, (Short) defaultValue);
        } else if (rawParameterType == short[].class || rawParameterType == Short[].class) {
            return bundle.getShortArray(key);
        } else if (rawParameterType == long.class || rawParameterType == Long.class) {
            return bundle.getLong(key, (Long) defaultValue);
        } else if (rawParameterType == long[].class || rawParameterType == Long[].class) {
            return bundle.getLongArray(key);
        } else if (rawParameterType == char.class) {
            return bundle.getChar(key, (char) defaultValue);
        } else if (rawParameterType == char[].class) {
            return bundle.getCharArray(key);
        } else if (rawParameterType == double.class || rawParameterType == Double.class) {
            return bundle.getDouble(key, (Double) defaultValue);
        } else if (rawParameterType == double[].class || rawParameterType == Double[].class) {
            return bundle.getDoubleArray(key);
        } else if (rawParameterType == float.class || rawParameterType == Float.class) {
            return bundle.getFloat(key, (Float) defaultValue);
        } else if (rawParameterType == float[].class || rawParameterType == Float[].class) {
            return bundle.getFloatArray(key);
        } else if (rawParameterType == byte.class || rawParameterType == Byte.class) {
            return bundle.getByte(key, (Byte) defaultValue);
        } else if (rawParameterType == byte[].class || rawParameterType == Byte[].class) {
            return bundle.getByteArray(key);
        } else if (rawParameterType == boolean.class || rawParameterType == Boolean.class) {
            return bundle.getBoolean(key, (Boolean) defaultValue);
        } else if (rawParameterType == boolean[].class || rawParameterType == Boolean[].class) {
            return bundle.getBooleanArray(key);
        } else if (rawParameterType == Bundle.class) {
            return bundle.getBundle(key);
        } else if (rawParameterType == Serializable.class) {
            return bundle.getSerializable(key);
        } else if (rawParameterType == Parcelable.class) {
            return bundle.getParcelable(key);
        } else if (rawParameterType == ArrayList.class) {
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments(); // 泛型类型数组
                if (actualTypeArguments == null || actualTypeArguments.length != 1) {
                    throw new RuntimeException("ArrayList的泛型必须实现Parcelable接口");
                }

                Type actualTypeArgument = actualTypeArguments[0]; // 获取第一个泛型类型
                if (actualTypeArgument == String.class) {
                    return bundle.getStringArrayList(key);
                } else if (actualTypeArgument == Integer.class) {
                    return bundle.getIntegerArrayList(key);
                } else if (actualTypeArgument == CharSequence.class) {
                    return bundle.getCharSequenceArrayList(key);
                } else if (actualTypeArgument instanceof Class) {
                    Class<?>[] interfaces = ((Class) actualTypeArgument).getInterfaces();
                    for (Class<?> interfaceClass : interfaces) {
                        if (interfaceClass == Parcelable.class) {
                            return bundle.getParcelableArrayList(key);
                        }
                    }
                    throw new RuntimeException("ArrayList的泛型必须实现Parcelable接口");
                }
            } else {
                throw new RuntimeException("ArrayList的泛型必须实现Parcelable接口");
            }
        } else {
            if (rawParameterType.isArray()) {// Parcelable[]
                Class<?>[] interfaces = rawParameterType.getComponentType().getInterfaces();
                for (Class<?> interfaceClass : interfaces) {
                    if (interfaceClass == Parcelable.class) {
                        return bundle.getParcelableArray(key);
                    } else if (interfaceClass == CharSequence.class) {
                        return bundle.getCharSequenceArray(key);
                    }
                }
                throw new RuntimeException("Object[]数组中的对象必须全部实现了Parcelable接口");
            } else {// 其他接口
                Class<?>[] interfaces = rawParameterType.getInterfaces();
                for (Class<?> interfaceClass : interfaces) {
                    if (interfaceClass == Parcelable.class) {
                        Object o = bundle.getParcelable(key);
                        if (o != null) return o;
                        return bundle.getSerializable(key);
                    } else if (interfaceClass == Serializable.class) {
                        Object o = bundle.getSerializable(key);
                        if (o != null) return o;
                        return bundle.getParcelable(key);
                    }
                }
                throw new RuntimeException("Bundle不支持的类型, 参数: " + key);
            }

        }
        return null;
    }


    static Class<?> getRawType(Type type) {
        if (type == null) throw new NullPointerException("type == null");

        if (type instanceof Class<?>) {
            // Type is a normal class.
            return (Class<?>) type;
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;

            // I'm not exactly sure why getRawType() returns Type instead of Class. Neal isn't either but
            // suspects some pathological case related to nested classes exists.
            Type rawType = parameterizedType.getRawType();
            if (!(rawType instanceof Class)) throw new IllegalArgumentException();
            return (Class<?>) rawType;
        }
        if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            return Array.newInstance(getRawType(componentType), 0).getClass();
        }
        if (type instanceof TypeVariable) {
            // We could use the variable's bounds, but that won't work if there are multiple. Having a raw
            // type that's more general than necessary is okay.
            return Object.class;
        }
        if (type instanceof WildcardType) {
            return getRawType(((WildcardType) type).getUpperBounds()[0]);
        }

        throw new IllegalArgumentException("Expected a Class, ParameterizedType, or "
                + "GenericArrayType, but <" + type + "> is of type " + type.getClass().getName());
    }
}
