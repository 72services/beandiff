package ch.simas.beandiff.model;

import java.util.ArrayList;
import java.util.List;

public class A {

    private String name;
    private AChild child;

    private List<AChild> childs = new ArrayList<AChild>();

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

    public List<AChild> getChilds() {
        return childs;
    }

    public void setChilds(List<AChild> childs) {
        this.childs = childs;
    }

}
