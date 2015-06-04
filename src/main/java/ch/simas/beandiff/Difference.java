package ch.simas.beandiff;

public class Difference {

    private final String path;
    private final Object left;
    private final Object right;

    public Difference(String path, Object left, Object right) {
        this.path = path;
        this.left = left;
        this.right = right;
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

    private String getRightAsString() {
        return ToStringBuilder.createToString(right);
    }

    @Override
    public String toString() {
        return "Difference{" + "path=" + path + ", left=" + getLeftAsString() + ", right=" + getRightAsString() + '}';
    }

}
