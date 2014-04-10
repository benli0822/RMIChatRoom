package rmi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * RMI client terminal
 * <p/>
 *
 * @author JIN Benli and ZHAO Xuening
 */

public class SiteClient {
    /**
     * Client's name for seeking method on the naming server
     */
    private String name;
    /**
     * If the client is already registered
     */
    private boolean add = false;

    /**
     * Client main method interact with client for adding node, build relation and send message
     *
     * @param args
     */
    public static void main(String args[]) {
        SiteClient s = new SiteClient();
        System.out.println("RMI Client started!");
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        try {
            SiteItf consult = (SiteItf) Naming
                    .lookup("rmi://localhost:8888/SiteImpl");
            String input = "";

            while (true) {
                System.out.print("->");
                try {
                    if ((input = cin.readLine()) == null)
                        break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String[] arg = input.split(" ");
                int option = parser(arg[0]);
                switch (option) {
                    case 1:
                        if (!s.add) {
                            if (arg.length < 2) {
                                System.out.println("Argument non valid, please type again.");
                            } else if (arg.length == 3 && arg[1].equals("root")) {
                                String name = arg[2];
                                s.name = name;
                                if (consult.addRoot(name))
                                    System.out.println("Success add node " + name);
                                else
                                    System.out.println("Failure add node " + name +
                                            ", please contact the server administrator for more information");
                            } else {
                                s.name = arg[1];
                                if (consult.add(arg[1]))
                                    System.out.println("Success add node " + arg[1]);
                                else
                                    System.out.println("Failure add node " + arg[1] +
                                            ", please contact the server administrator for more information");
                            }
                            s.add = true;
                        } else {
                            System.out.println("You have already registered yourself");
                        }

                        break;
                    case 2:
                        if (arg.length < 2) {
                            System.out.println("Argument non valid, please type again.");
                        } else {
                            if (consult.remove(arg[1]))
                                System.out.println("Success remove node " + arg[1]);
                            else
                                System.out.println("Failure remove node " + arg[1] +
                                        ", please contact the server administrator for more information");
                        }
                        s.add = false;
                        break;
                    case 3:
                        if (arg.length < 3) {
                            System.out.println("Argument non valid, please type again.");
                        } else {
                            if (consult.addRelation(arg[1], arg[2])) {
                                System.out.println("Success add relation between Father: [" + arg[1]
                                        + "] and Son: [" + arg[2] + "]");
                            } else {
                                System.out.println("Failure add relation between Father: [" + arg[1]
                                        + "] and Son: [" + arg[2] + "]");
                            }
                        }
                        break;
                    case 4:
                        if (arg.length < 2) {
                            System.out.println("Argument non valid, please type again.");
                        } else {
                            String msg = arg[1];
                            consult.send(msg, s.name);
                        }
                        break;
                    case 5:
                        System.out.println("SiteClient help:\n" +
                                "add [Name] Register yourself on the remote server, you can pick up a name as you wish.\n" +
                                "admin add [Name] Register as a administrator, in this option, you are not limited. :)\n" +
                                "remove [Name] Redo the registration, as you can change your registered name\n" +
                                "build [Name] [Name] Build a relationship with two names registered on the serve\n" +
                                "login [Name] Login with your registered name\n" +
                                "send [Message] Send your message\n" +
                                "exit Exit");
                        break;
                    case 6:
                        System.exit(0);
                        break;
                    case 7:
                        if (arg.length < 2) {
                            System.out.println("Argument non valid, please type again.");
                        } else {
                            if (consult.exist(arg[1])) {
                                s.name = arg[1];
                                System.out.println("Welcome [" + s.name + "], now you can send your message");
                            } else {
                                System.out.println("Sorry, invalid identification, please register yourself");
                            }
                        }
                        break;
                    case 8:
                        System.out.println("Welcome administrator, please create the graph");
                        if (arg.length < 3) {
                            System.out.println("Argument non valid, please type again.");
                        } else if (arg.length == 4 && arg[2].equals("root")) {
                            String name = arg[3];
                            s.name = name;
                            if (consult.addRoot(name))
                                System.out.println("Success add node " + name);
                            else
                                System.out.println("Failure add node " + name +
                                        ", please contact the server administrator for more information");
                        } else {
                            s.name = arg[2];
                            if (consult.add(arg[2]))
                                System.out.println("Success add node " + arg[2]);
                            else
                                System.out.println("Failure add node " + arg[2] +
                                        ", please contact the server administrator for more information");
                        }
                        break;
                    case 9:
                        System.out.println("[" + s.name + "] has received message: " + consult.receiveMsg(s.name));
                        break;
                    default:
                        System.out.println("Command non valid, please type again.");
                        System.out.println("Type help for further information: ./SiteClient help");
                        break;
                }
            }

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (RemoteException e) {

            e.printStackTrace();

        } catch (NotBoundException e) {

            e.printStackTrace();

        }
    }

    /**
     * Parser the input stream as defined option
     *
     * @param args The first argument presented in each input line
     * @return Defined option
     */
    public static int parser(String args) {
        if (args.equals("add"))
            return 1;
        else if (args.equals("remove"))
            return 2;
        else if (args.equals("build"))
            return 3;
        else if (args.equals("send"))
            return 4;
        else if (args.equals("help"))
            return 5;
        else if (args.equals("exit"))
            return 6;
        else if (args.equals("login"))
            return 7;
        else if (args.equals("admin"))
            return 8;
        else if (args.equals("get"))
            return 9;
        else
            return -1;
    }
}
