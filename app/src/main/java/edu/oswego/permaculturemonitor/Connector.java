package edu.oswego.permaculturemonitor;

/**
 * Created by chrisrk192 on 11/28/2016.
 * Description: This should be called first in the main thread and the connection established there
 *          If youre not working in the main activity, the setConnection should not be used.
 */
public class Connector {
    java.sql.Connection con;
    private static Connector ourInstance = new Connector();

    public static Connector getInstance() {
        return ourInstance;
    }
    private Connector() {
    }
    public java.sql.Connection getConnection(){
        return con;
    }
    public void setConnection(java.sql.Connection connect){
        con = connect;
    }
}
