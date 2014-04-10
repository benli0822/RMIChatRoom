package rmi;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Node model represent every client registered on the rmi naming server
 * <p/>
 *
 * @author JIN Benli and ZHAO Xuening
 */

public class Noeud implements Serializable {
    /**
     * Serialize the node version
     */
    private static final long serialVersionUID = 1L;
    /**
     * Current node's father
     */
    private Noeud pere;
    /**
     * Current node's child list
     */
    private ArrayList<Noeud> fils;
    /**
     * Current node's name
     */
    private String id;
    /**
     * Current node's message storage
     */
    private char[] data;
    /**
     * Current node's status for root
     */
    private boolean root = false;
    /**
     * Current node's receive status
     */
    private boolean doReceive = false;

    /**
     * If defined like a tree, then possible to figure out if this node is the root
     *
     * @return True for root
     */
    public boolean isRoot() {
        return root;
    }

    /**
     * Every time the client send a message, the variable need to be reset
     *
     * @param b represent send refreshing
     */
    public void setDoReceive(boolean b) {
        this.doReceive = b;
    }

    /**
     * Set this node as root for tree structure
     *
     * @param root root
     */
    public void setRoot(boolean root) {
        this.root = root;
    }

    /**
     * Create a node with the given name, also initialize its child list
     *
     * @param id name
     */
    public Noeud(String id) {
        this.id = id;
        fils = new ArrayList<Noeud>();
    }

    /**
     * Return node's name
     *
     * @return name
     */
    public String getId() {
        return this.id;
    }

    /**
     * Set its father
     *
     * @param pere father
     */
    public void setPere(Noeud pere) {
        this.pere = pere;
    }

    /**
     * Get its father
     *
     * @return Type node father
     */
    public Noeud getPere() {
        return this.pere;
    }

    /**
     * Add a node type as child
     *
     * @param fils child node for adding
     */
    public void addFils(Noeud fils) {
        this.fils.add(fils);
    }

    /**
     * Remove a node type child
     *
     * @param fils child node for removing
     */
    public void removeFils(Noeud fils) {
        this.fils.remove(fils);
    }

    /**
     * Get child list
     *
     * @return Node type child list
     */
    public ArrayList<Noeud> getFils() {
        return this.fils;
    }

    /**
     * Set current data as message, also as receive function
     *
     * @param data The char array mode of message
     */
    public void setData(char[] data) {
        if (this.data != null) {
            // if already received msg
            if (this.data.equals(data)) {
                String da = "";
                int length = this.data.length;
                for (int i = 0; i < length; i++) {
                    da += this.data[i];
                }
                this.doReceive = true;
                System.out.println("[" + this.id + "] already received msg: " + da);
            } else {
                this.data = data;
                String da = "";
                int length = this.data.length;
                for (int i = 0; i < length; i++) {
                    da += this.data[i];
                }
                System.out.println("[" + this.id + "] receive msg: " + da);
            }
        } else {
            this.data = data;
            String da = "";
            int length = this.data.length;
            for (int i = 0; i < length; i++) {
                da += this.data[i];
            }
            System.out.println("[" + this.id + "] receive msg: " + da);
        }
    }

    /**
     * Message Printer
     *
     * @return Current message received in the node
     */
    public String getMsg() {
        if (this.data != null) {
            StringBuilder s = new StringBuilder();
            for (char c : this.data) {
                s.append(c);
            }
            return s.toString();
        } else
            return null;
    }

    /**
     * Change receive status
     *
     * @return Receive status
     */
    public boolean hasReceive() {
        return this.doReceive;
    }

    /**
     * Send message to other node, bidirectional sending, which means to its father and child
     * when comes to its father, check whether if it's null, or root; when comes to its child
     * send normally, every time every send should check the method doReceive() to retrieve the
     * current status
     *
     * @return Sending message status
     */
    public boolean sendMsg() {
        if (this.data == null)
            return false;
        if (this.fils.isEmpty()) {
            if (this.pere != null) {
                if (!this.pere.doReceive) {
                    String da = "";
                    int length = this.data.length;
                    for (int i = 0; i < length; i++) {
                        da += this.data[i];
                    }
                    System.out.println("[" + this.id + "] send msg: " + da + " to [" + this.pere.getId() + "]");
                    this.pere.setData(this.data);
                    this.pere.sendMsg();
                }
            }
            return true;
        }
        if (this.pere != null) {
            if (!this.pere.doReceive) {
                String da = "";
                int length = this.data.length;
                for (int i = 0; i < length; i++) {
                    da += this.data[i];
                }
                System.out.println("[" + this.id + "] send msg: " + da + " to [" + this.pere.getId() + "]");
                this.pere.setData(this.data);
                this.pere.sendMsg();
            }
        }
        String da = "";
        int length = this.data.length;
        for (int i = 0; i < length; i++) {
            da += this.data[i];
        }
        System.out.println("[" + this.id + "] send msg: " + da);
        for (Noeud noeud : this.fils) {
            if (!noeud.doReceive)
                noeud.setData(this.data);
        }
        for (Noeud noeud : this.fils) {
            if (!noeud.sendMsg())
                return false;
        }
        return true;
    }
}
