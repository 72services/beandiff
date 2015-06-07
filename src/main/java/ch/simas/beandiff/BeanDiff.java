package ch.simas.beandiff;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BeanDiff {

    private final Map<String, Difference> differences = new HashMap<>();

    public void diff(Object o1, Object o2) {
        diff("", o1, o2);
    }

    private void diff(String path, Object o1, Object o2) {
        try {
            if (o1 == null) {
                if (o2 != null) {
                    differences.put(path, new Difference(path, "", o2));
                }
            } else {
                Field[] fields = o1.getClass().getDeclaredFields();

                for (Field field1 : fields) {
                    if (field1.getAnnotation(NoFollow.class) == null && !field1.getName().equals("serialVersionUID")) {
                        field1.setAccessible(true);
                        Object value1 = field1.get(o1);
                        if (o2 == null) {
                            String p = path + "/" + field1.getName();
                            differences.put(p, new Difference(p, value1, ""));
                        } else {
                            Field f2 = o2.getClass().getDeclaredField(
                                    field1.getName());
                            f2.setAccessible(true);
                            Object value2 = f2.get(o2);

                            if (value1 == null) {
                                if (value2 != null) {
                                    String p = path + "/" + f2.getName();
                                    differences.put(p, new Difference(p, "",
                                            value2));
                                }
                            } else if (value1 instanceof Collection) {
                                Collection col1 = (Collection) value1;
                                Collection col2 = (Collection) value2;
                                if (col2 != null && col1.size() == col2.size()) {
                                    for (int i = 0; i < col1.size(); i++) {
                                        diff(path + "/" + field1.getName()
                                                + "/" + i, col1.iterator()
                                                .next(), col2.iterator().next());
                                    }
                                } else {
                                    String p = path + "/" + field1.getName();
                                    differences.put(p, new Difference(p, col1,
                                            col2));
                                }

                            } else if (ReflectionHelper
                                    .isPrimitiveOrStringOrWrapperOrBigDecimal(value1)) {
                                if (!value1.equals(value2)) {
                                    String p = path + "/" + field1.getName();
                                    differences.put(p, new Difference(p,
                                            value1, value2));
                                }
                            } else {
                                diff(path + "/" + field1.getName(), value1,
                                        value2);
                            }
                        }
                    }
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException ex) {
        }
    }

    public Collection<Difference> getDifferences() {
        return differences.values();
    }

    public boolean hasDifference(String path) {
        return differences.containsKey(path);
    }

    public Difference getDifference(String path) {
        return differences.get(path);
    }

    public boolean hasDifferences() {
        return !differences.isEmpty();
    }

}
