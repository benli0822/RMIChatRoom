package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * RMI interface implement
 * <p/>
 *
 * @author JIN Benli and ZHAO Xuening
 */

public class SiteImpl extends UnicastRemoteObject implements SiteItf {

    /**
     * Node registration list
     */
    private List<Noeud> nameList;

    /**
     * Inherit from superclass and initialise node list
     *
     * @throws RemoteException
     */
    public SiteImpl() throws RemoteException {
        super();
        this.nameList = new ArrayList<Noeud>();
    }

    /**
     * Add a node with its given name
     *
     * @param name node id
     * @return add status
     * @throws RemoteException
     */
    @Override
    public boolean add(String name) throws RemoteException {
        if (this.exist(name))
            return false;
        else {
            Noeud temp = new Noeud(name);
            this.nameList.add(temp);
            return true;
        }
    }

    /**
     * Add a node as root with its given name
     *
     * @param name node id
     * @return add root status
     * @throws RemoteException
     */
    public boolean addRoot(String name) throws RemoteException {
        if (this.exist(name))
            return false;
        else {
            Noeud temp = new Noeud(name);
            temp.setRoot(true);
            this.nameList.add(temp);
            return true;
        }
    }

    /**
     * Remove a node with its given name
     *
     * @param name node id
     * @return remove status
     * @throws RemoteException
     */
    @Override
    public boolean remove(String name) throws RemoteException {
        if (this.exist(name)) {
            Noeud temp = null;
            for (Noeud n : this.nameList) {
                if (n.getId().equals(name))
                    temp = n;
            }
            assert (temp != null);
            ArrayList<Noeud> childs = temp.getFils();
            for (Noeud c : childs) {
                c.setPere(null);
            }
            Noeud pere = temp.getPere();
            pere.removeFils(temp);
            this.nameList.remove(temp);
            return true;
        } else
            return false;
    }

    /**
     * Get list of all node
     *
     * @return Node list
     * @throws RemoteException
     */
    @Override
    public List<Noeud> getNodeList() throws RemoteException {
        return this.nameList;
    }

    /**
     * Figure out the existence of a node given by its name
     *
     * @param name Node name or id name
     * @return True if exist, false inverse
     * @throws RemoteException
     */
    @Override
    public boolean exist(String name) throws RemoteException {
        for (Noeud n : this.nameList) {
            if (n.getId().equals(name))
                return true;
        }
        return false;
    }

    /**
     * Send a message from a node given by its name
     *
     * @param msg  Message to send
     * @param name Node name
     * @return Send status
     * @throws RemoteException
     */
    @Override
    public boolean send(String msg, String name) throws RemoteException {
        for (Noeud n : this.nameList) {
            n.setDoReceive(false);
        }
        if (exist(name)) {
            Noeud n = getByName(name);
            n.setData(msg.toCharArray());
            n.sendMsg();
            return true;
        } else
            return false;
    }

    /**
     * Add relation as father and child between two nodes given by their names
     *
     * @param father Node father name
     * @param son    Node son name
     * @return Function execution status
     * @throws RemoteException
     */
    @Override
    public boolean addRelation(String father, String son) throws RemoteException {
        if (exist(father) && exist(son)) {
            Noeud f = getByName(father);
            Noeud s = getByName(son);
            for (Noeud n : this.nameList) {
                if (n.equals(f)) {
                    n.addFils(s);
                }
                if (n.equals(s)) {
                    n.setPere(f);
                }
            }
            return true;
        } else
            return false;
    }

    /**
     * Get a node by its name
     *
     * @param name Node name
     * @return Node requested
     * @throws RemoteException
     */
    public Noeud getByName(String name) throws RemoteException {
        if (exist(name)) {
            for (Noeud n : this.nameList) {
                if (n.getId().equals(name))
                    return n;
            }
        }
        return null;
    }

    /**
     * Get the message thar a node given by its name has received
     *
     * @param name Node name
     * @return Message received
     * @throws RemoteException
     */
    public String receiveMsg(String name) throws RemoteException {
        Noeud temp = getByName(name);
        if (temp.hasReceive())
            return temp.getMsg();
        return null;
    }
}
