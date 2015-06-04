package ch.simas.beandiff;

public class A extends IdEntity {

    private String name;
    private AChild child;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AChild getChild() {
        return child;
    }

    public void setChild(AChild child) {
        this.child = child;
    }

}
