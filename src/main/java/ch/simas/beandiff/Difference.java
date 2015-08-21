package ch.simas.beandiff;

import java.lang.reflect.Field;

public class Difference {

	private final String path;
	private final Object left;
	private final Object right;
	private final Object target;
	private final Object source;

	public Difference(Object source, Object target, String path,
			Object leftValue, Object rightValue) {
		this.source = source;
		this.target = target;
		this.path = path;
		this.left = leftValue;
		this.right = rightValue;
	}

	public String getPath() {
		return path;
	}

	public Object getLeft() {
		return left;
	}

	public Object getRight() {
		return right;
	}

	public String getLeftAsString() {
		return ToStringBuilder.createToString(left);
	}

	public String getRightAsString() {
		return ToStringBuilder.createToString(right);
	}

	@Override
	public String toString() {
		return "Difference{" + "path=" + path + ", left=" + getLeftAsString()
				+ ", right=" + getRightAsString() + '}';
	}

	public void patch() {

		String[] pathParts = this.path.split("/");
		String localPath = pathParts[pathParts.length - 1];
		try {

			if (target != null) {
				Field sourceField = source.getClass().getDeclaredField(localPath);
				Field targetField = target.getClass().getDeclaredField(localPath);
				sourceField.setAccessible(true);
				targetField.setAccessible(true);
				Object value = sourceField.get(source);
				targetField.set(target, value);
			}
		} catch (Exception e) {
			throw new IllegalStateException("Trying to access field at path: "	+ localPath, e);
		}
	}

}
