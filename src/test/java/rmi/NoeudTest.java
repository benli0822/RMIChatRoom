package rmi;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by JIN Benli on 30/03/14.
 */
public class NoeudTest {
    @Test
    public void testPrintHelloWorld() {
        Assert.assertEquals("Hello World", "Hello World");
    }

    @Test
    public void testRelationShipBetweenNoeuds() {
        Noeud t1 = new Noeud("1");
        Noeud t2 = new Noeud("2");

        t1.addFils(t2);
        t2.setPere(t1);

        Assert.assertEquals(t2.getPere(), t1);
        Assert.assertEquals(true, t1.getFils().contains(t2));
    }

    @Test
    public void testSendMsg() {
        Noeud t1 = new Noeud("1");
        Noeud t2 = new Noeud("2");

        String s = "Test String";
        char[] data = s.toCharArray();

        t1.setData(data);
        t1.addFils(t2);
        t1.sendMsg();

        Assert.assertEquals("Test String", t2.getMsg());
    }
}
