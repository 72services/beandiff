package ch.simas.beandiff;

import ch.simas.beandiff.model.B;
import ch.simas.beandiff.model.AChild;
import ch.simas.beandiff.model.BChild;
import ch.simas.beandiff.model.A;
import org.junit.Test;
import static org.junit.Assert.*;

public class BeanDiffTest {

    @Test
    public void noDiff() {
        System.out.println("noDiff");

        A a = new A();
        a.setName("Name");
        AChild aChild = new AChild();
        aChild.setName("Child");
        a.setChild(aChild);
        a.getChilds().add(aChild);

        B b = new B();
        b.setName("Name");
        BChild bChild = new BChild();
        bChild.setName("Child");
        b.setChild(bChild);
        b.getChilds().add(bChild);

        BeanDiff diff = new BeanDiff();
        diff.diff("", a, b);

        assertTrue(diff.getDifferences().isEmpty());
    }

    @Test
    public void valuesDiff() {
        System.out.println("valuesDiff");

        A a = new A();
        a.setName("Hallo");
        AChild aChild = new AChild();
        aChild.setName("Child1");
        a.setChild(aChild);
        a.getChilds().add(aChild);

        B b = new B();
        b.setName("Welt");
        BChild bChild = new BChild();
        bChild.setName("Child2");
        b.setChild(bChild);
        b.getChilds().add(bChild);

        BeanDiff diff = new BeanDiff();
        diff.diff("", a, b);

        assertFalse(diff.getDifferences().isEmpty());

        for (BeanDiff.Difference difference : diff.getDifferences()) {
            System.out.println(difference);
        }
    }

    @Test
    public void diffCollection() {
        System.out.println("diffCollection");

        A a = new A();
        a.setName("Hallo");
        AChild aChild = new AChild();
        aChild.setName("Child1");
        a.setChild(aChild);
        a.getChilds().add(aChild);

        B b = new B();
        b.setName("Welt");
        BChild bChild = new BChild();
        bChild.setName("Child2");
        b.setChild(bChild);

        BeanDiff diff = new BeanDiff();
        diff.diff("", a, b);

        assertFalse(diff.getDifferences().isEmpty());

        for (BeanDiff.Difference difference : diff.getDifferences()) {
            System.out.println(difference);
        }
    }

    @Test
    public void diffObject() {
        System.out.println("diffObject");

        A a = new A();
        a.setName("Hallo");
        AChild aChild = new AChild();
        aChild.setName("Child1");
        a.setChild(aChild);

        B b = new B();
        b.setName("Welt");

        BeanDiff diff = new BeanDiff();
        diff.diff("", a, b);

        assertFalse(diff.getDifferences().isEmpty());

        for (BeanDiff.Difference difference : diff.getDifferences()) {
            System.out.println(difference);
        }
    }

    @Test
    public void firstValueNull() {
        System.out.println("firstValueNull");

        A a = new A();

        B b = new B();
        b.setName("Welt");

        BeanDiff diff = new BeanDiff();
        diff.diff("", a, b);

        assertFalse(diff.getDifferences().isEmpty());

        for (BeanDiff.Difference difference : diff.getDifferences()) {
            System.out.println(difference);
        }
    }

    @Test
    public void bothValuesNull() {
        System.out.println("bothValuesNull");

        BeanDiff diff = new BeanDiff();
        diff.diff("", null, null);

        assertTrue(diff.getDifferences().isEmpty());

        for (BeanDiff.Difference difference : diff.getDifferences()) {
            System.out.println(difference);
        }
    }
}
