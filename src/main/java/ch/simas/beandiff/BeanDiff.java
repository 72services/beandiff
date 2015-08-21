package ch.simas.beandiff;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BeanDiff {

    private final Map<String, Difference> differences = new HashMap<String, Difference>();
    private Set<Object> visitedObjects = new HashSet<>();

    public void diff(Object o1, Object o2) {
        visitedObjects = new HashSet<>();
        diff("", o1, o2);
    }

    private void diff(String path, Object o1, Object o2) {
        try {
            if (o1 == null) {
                if (o2 != null) {
                    differences.put(path, new Difference(o1, o2, path, "", o2));
                }
            } else {
                if (!visitedObjects.contains(o1)) {
                    visitedObjects.add(o1);
                    Field[] fields = o1.getClass().getDeclaredFields();
                    for (Field field1 : fields) {
                        if (field1.getAnnotation(NoFollow.class) == null
                                && !field1.getName().equals("serialVersionUID")) {
                            field1.setAccessible(true);
                            Object value1 = field1.get(o1);
                            if (o2 == null) {
                                String p = path + "/" + field1.getName();
                                differences.put(p, new Difference(o1, o2, p, value1, ""));
                            } else {
                                Field f2 = o2.getClass().getDeclaredField(field1.getName());
                                f2.setAccessible(true);
                                Object value2 = f2.get(o2);

                                if (value1 == null) {
                                    if (value2 != null) {
                                        String p = path + "/" + f2.getName();
                                        differences.put(p, new Difference(o1, o2, p, "", value2));
                                    }
                                } 
                                else if (value1 instanceof Map) {
                                    Map col1 = (Map) value1;
                                    Map col2 = (Map) value2;
                                    if (!col1.isEmpty() && col2 != null && !col2.isEmpty()) {
                                        if (col1.size() == col2.size()) {
                                            for (Object key : col1.keySet()) {
                                                Object value1FromMap = col1.get(key);
                                                Object value2FromMap = col2.get(key);
                                                diff(path + "/" + field1.getName() + "/"
                                                        + key.toString(), value1FromMap, value2FromMap);
                                            }
                                        } else {
                                            String p = path + "/" + field1.getName();
                                            differences.put(p, new Difference(o1, o2, p, col1, col2));
                                        }
                                    }
                                } else if (value1 instanceof Collection) {
                                    Collection col1 = (Collection) value1;
                                    Collection col2 = (Collection) value2;
                                    if (!col1.isEmpty() && col2 != null && !col2.isEmpty()) {
                                        if (col2 != null && col1.size() == col2.size()) {
                                            for (int i = 0; i < col1.size(); i++) {
                                                diff(path + "/" + field1.getName() + "/" + i, col1
                                                        .iterator().next(), col2.iterator().next());
                                            }
                                        } else {
                                            String p = path + "/" + field1.getName();
                                            differences.put(p, new Difference(o1, o2, p, col1, col2));
                                        }
                                    }
                                } else if (ReflectionHelper
                                        .isPrimitiveOrStringOrWrapperOrBigDecimalOrDate(value1)) {
                                    if (!value1.equals(value2)) {
                                        String p = path + "/" + field1.getName();
                                        differences.put(p, new Difference(o1, o2, p, value1, value2));
                                    }
                                } else {
                                	if (value2 == null){
                                		String p = path + "/" + field1.getName();
                                		differences.put(p, new Difference(o1, o2, p, value1, value2));
                                	}
                                    diff(path + "/" + field1.getName(), value1, value2);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        }
    }

    public List<Difference> getDifferences() {
        ArrayList<Difference> valueList = new ArrayList<Difference>(differences.values());
		Collections.sort(valueList, new Comparator<Difference>() {
            public int compare(Difference c1, Difference c2) {
                return c1.getPath().compareTo(c2.getPath()); 
            }
        });
		return valueList;
    }

    public boolean hasDifferenceWithPath(String path) {
        return differences.containsKey(path);
    }

    public boolean hasDifferenceWithPathUsingRegex(String regex) {
        for (Difference difference : differences.values()) {
            if (difference.getPath().matches(regex)) {
                return true;
            }
        }
        return false;
    }

    public Difference getDifference(String path) {
        return differences.get(path);
    }

    public boolean hasDifferences() {
        return !differences.isEmpty();
    }

	public void patch() {
		for (Difference diff : this.differences.values()){
			diff.patch();
		}
	}

}
