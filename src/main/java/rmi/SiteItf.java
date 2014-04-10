package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * RMI interface declaration
 * <p/>
 *
 * @author JIN Benli and ZHAO Xuening
 */

public interface SiteItf extends Remote {
    /**
     * Add a node with its given name
     *
     * @param name node id
     * @return add status
     * @throws RemoteException
     */
    public boolean add(String name) throws RemoteException;

    /**
     * Add a node as root with its given name
     *
     * @param name node id
     * @return add root status
     * @throws RemoteException
     */
    public boolean addRoot(String name) throws RemoteException;

    /**
     * Remove a node with its given name
     *
     * @param name node id
     * @return remove status
     * @throws RemoteException
     */
    public boolean remove(String name) throws RemoteException;

    /**
     * Get list of all node
     *
     * @return Node list
     * @throws RemoteException
     */
    public List<Noeud> getNodeList() throws RemoteException;

    /**
     * Figure out the existence of a node given by its name
     *
     * @param name Node name or id name
     * @return True if exist, false inverse
     * @throws RemoteException
     */
    public boolean exist(String name) throws RemoteException;

    /**
     * Send a message from a node given by its name
     *
     * @param msg  Message to send
     * @param name Node name
     * @return Send status
     * @throws RemoteException
     */
    public boolean send(String msg, String name) throws RemoteException;

    /**
     * Add relation as father and child between two nodes given by their names
     *
     * @param father Node father name
     * @param son    Node son name
     * @return Function execution status
     * @throws RemoteException
     */
    public boolean addRelation(String father, String son) throws RemoteException;

    /**
     * Get a node by its name
     *
     * @param name Node name
     * @return Node requested
     * @throws RemoteException
     */
    public Noeud getByName(String name) throws RemoteException;

    /**
     * Get the message thar a node given by its name has received
     *
     * @param name Node name
     * @return Message received
     * @throws RemoteException
     */
    public String receiveMsg(String name) throws RemoteException;
}
