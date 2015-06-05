package ch.simas.beandiff;

public class ReflectionHelper {

    public static boolean isPrimitiveOrStringOrWrapperOrBigDecimal(Object obj) {
        Class clazz = obj.getClass();
        return clazz.isPrimitive() || String.class == clazz || Character.class == clazz || Boolean.class == clazz || Number.class.isAssignableFrom(clazz);
    }
}
