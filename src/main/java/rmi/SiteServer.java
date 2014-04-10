package rmi;

import javax.naming.NamingException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * RMI naming server
 * <p/>
 *
 * @author JIN Benli and ZHAO Xuening
 */

public class SiteServer extends Thread {

    /**
     * Server Main method, register port 8888 in local, bind the interface implement on "rmi://localhost:8888/SiteImpl"
     *
     * @param args
     * @throws RemoteException
     * @throws MalformedURLException
     * @throws NamingException
     */
    public static void main(String args[]) throws RemoteException,
            MalformedURLException, NamingException {
        SiteItf consult = new SiteImpl();
        LocateRegistry.createRegistry(8888);
        Naming.rebind("rmi://localhost:8888/SiteImpl", consult);
        System.out.println("> RMI server lance successful, wait for input");
    }
}
