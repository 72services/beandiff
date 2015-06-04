package ch.simas.beandiff;

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
        return "Difference {" + "path=" + path + ", leftValue=" + leftValue + ", rightValue=" + rightValue + '}';
    }

}
