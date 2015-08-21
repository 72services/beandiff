package ch.simas.beandiff;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ToStringBuilder {

	public static String createToString(Object obj) {
		return createToString(new HashSet<>(), obj);
	}

	public static String createToString(Set<Object> visitedObjects, Object obj) {
		if (obj == null || obj.getClass().getAnnotation(NoFollow.class) != null
				|| visitedObjects.contains(obj)) {
			return "";
		} else {
			visitedObjects.add(obj);

			if (obj instanceof Collection) {
				StringBuilder sb = new StringBuilder();
				sb.append("[");
				boolean first = true;
				for (Object o : (Collection) obj) {
					if (!first) {
						sb.append(", ");
					}
					sb.append(createToString(visitedObjects, o));
					first = false;
				}
				sb.append("]");
				return sb.toString();
			} else if (ReflectionHelper
					.isPrimitiveOrStringOrWrapperOrBigDecimalOrDate(obj)) {
				return obj.toString();
			} else {
				StringBuilder sb = new StringBuilder();
				sb.append("{");
				boolean first = true;
				Field[] declaredFields = obj.getClass().getDeclaredFields();
				for (Field field : declaredFields) {
					if (field.getAnnotation(NoFollow.class) == null
							&& !field.getName().equals("serialVersionUID")) {
						System.out.println("Field: " + field);
						if (!first) {
							sb.append(", ");
						}
						try {
							field.setAccessible(true);
							sb.append(field.getName()).append(" = ")
									.append(createToString(visitedObjects, field.get(obj)));
						} catch (Exception ex) {
							// ignore
						}
						first = false;
					}
				}
				sb.append("}");
				return sb.toString();
			}
		}
	}
}
