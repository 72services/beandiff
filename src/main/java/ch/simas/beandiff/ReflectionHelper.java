package ch.simas.beandiff;

import java.math.BigDecimal;

public class ReflectionHelper {

    public static boolean isPrimitiveOrStringOrWrapperOrBigDecimal(Object obj) {
        Class clazz = obj.getClass();
        boolean assignableFromNumber = Number.class.isAssignableFrom(clazz);
        return clazz.isPrimitive() || String.class == clazz || Character.class == clazz || Boolean.class == clazz || BigDecimal.class == clazz || assignableFromNumber;
    }
}
