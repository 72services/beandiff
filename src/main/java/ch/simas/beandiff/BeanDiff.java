package ch.simas.beandiff;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BeanDiff {

    private final List<Difference> differences = new ArrayList<>();

    public void diff(String path, Object o1, Object o2) {
        try {
            if (o1 == null) {
                if (o2 != null) {
                    differences.add(new Difference(path, "", o2.toString()));
                }
            } else {
                Field[] fields = o1.getClass().getDeclaredFields();

                for (Field field1 : fields) {
                    field1.setAccessible(true);
                    Object value1 = field1.get(o1);
                    if (o2 == null) {
                        differences.add(new Difference(path + "/" + field1.getName(), value1.toString(), ""));
                    } else {
                        Field f2 = o2.getClass().getDeclaredField(field1.getName());
                        f2.setAccessible(true);
                        Object value2 = f2.get(o2);

                        if (value1 == null) {
                            if (value2 != null) {
                                differences.add(new Difference(path, "", value2.toString()));
                            }
                        }
                        else if (value1 instanceof Collection) {
                            Collection col1 = (Collection) value1;
                            Collection col2 = (Collection) value2;
                            if (col2 != null && col1.size() == col2.size()) {
                                for (int i = 0; i < col1.size(); i++) {
                                    diff(path + "/" + field1.getName() + "/" + i, col1.iterator().next(), col2.iterator().next());
                                }
                            } else {
                                differences.add(new Difference(path + "/" + field1.getName(), col1.toString(), col2 == null ? "" : col2.toString()));
                            }

                        } else if (isPrimitiveOrStringOrWrapperOrBigDecimal(value1.getClass())) {
                            if (!value1.equals(value2)) {
                                differences.add(new Difference(path + "/" + field1.getName(), value1.toString(), value2 == null ? "" : value2.toString()));
                            }
                        } else {
                            diff(field1.getName(), value1, value2);
                        }
                    }
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException ex) {
            ex.printStackTrace();
        }
    }

    private boolean isPrimitiveOrStringOrWrapperOrBigDecimal(Class clazz) {
        return clazz.isPrimitive() || String.class == clazz || Character.class == clazz || Boolean.class == clazz || clazz.isAssignableFrom(Number.class);
    }

    public List<Difference> getDifferences() {
        return differences;
    }

    public class Difference {

        private final String path;
        private final String leftValue;
        private final String rightValue;

        public Difference(String path, String leftValue, String rightValue) {
            this.path = path;
            this.leftValue = leftValue;
            this.rightValue = rightValue;
        }

        public String getPath() {
            return path;
        }

        public String getLeftValue() {
            return leftValue;
        }

        public String getRightValue() {
            return rightValue;
        }

        @Override
        public String toString() {
            return "Difference{" + "path=" + path + ", leftValue=" + leftValue + ", rightValue=" + rightValue + '}';
        }

    }

}
