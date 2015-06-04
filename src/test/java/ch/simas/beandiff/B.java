package ch.simas.beandiff;

public class B extends IdEntity {

    private String name;
    private BChild child;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BChild getChild() {
        return child;
    }

    public void setChild(BChild child) {
        this.child = child;
    }

}
