package ch.simas.beandiff;

import java.lang.reflect.Field;
import java.util.Collection;

public class ToStringBuilder {

    public static String createToString(Object obj) {
        if (obj == null) {
            return "";
        } else if (obj instanceof Collection) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            boolean first = true;
            for (Object o : (Collection) obj) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(createToString(o));
                first = false;
            }
            sb.append("]");
            return sb.toString();
        } else if (ReflectionHelper.isPrimitiveOrStringOrWrapperOrBigDecimal(obj)) {
            return obj.toString();
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            boolean first = true;
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                if (!first) {
                    sb.append(", ");
                }
                try {
                    field.setAccessible(true);
                    sb.append(field.getName()).append(" = ").append(createToString(field.get(obj)));
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    // ignore
                }
                first = false;
            }
            sb.append("}");
            return sb.toString();
        }
    }

}
