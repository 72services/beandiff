package ch.simas.beandiff;

import ch.simas.beandiff.model.A;
import ch.simas.beandiff.model.AChild;
import ch.simas.beandiff.model.B;
import ch.simas.beandiff.model.BChild;
import org.junit.Test;
import static org.junit.Assert.*;

public class BeanPatchTest {

    @Test
    public void valuesDiff() {
        System.out.println("\n--> valuesDiff");

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
        diff.diff(a, b);
        diff.patch();

        assertEquals(b.getName(), a.getName());
        assertEquals(b.getChild().getName(), a.getChild().getName());

        for (Difference difference : diff.getDifferences()) {
            System.out.println(difference);
        }
    }
}
