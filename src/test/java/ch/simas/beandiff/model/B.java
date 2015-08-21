package ch.simas.beandiff.model;

import java.util.ArrayList;
import java.util.List;

public class B {

    private String name;
    private BChild child;

    private List<BChild> childs = new ArrayList<BChild>();

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

    public List<BChild> getChilds() {
        return childs;
    }

    public void setChilds(List<BChild> childs) {
        this.childs = childs;
    }

}
