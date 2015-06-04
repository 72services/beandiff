package ch.simas.beandiff;

import org.junit.Test;
import static org.junit.Assert.*;

public class BeanDiffTest {

    @Test
    public void diff() {
        A a = new A();
        a.setName("Hallo");
        AChild aChild = new AChild();
        aChild.setName("Child1");
        a.setChild(aChild);

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
}
