package ch.simas.beandiff;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class ReflectionHelper {

	public static boolean isPrimitiveOrStringOrWrapperOrBigDecimalOrDate(
			Object obj) {
		Class clazz = obj.getClass();
		boolean assignableFromNumber = Number.class.isAssignableFrom(clazz);
		return clazz.isPrimitive() || String.class == clazz
				|| Character.class == clazz || Boolean.class == clazz
				|| BigDecimal.class == clazz || assignableFromNumber
				|| Date.class == clazz || java.sql.Date.class == clazz
				|| Timestamp.class == clazz;
	}
}
