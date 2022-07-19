package com.example.project_myvntour.Database;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connect {
    Connection connection;
    public Connection getCollection(){
        String ip = "192.168.42.252", port = "1433", user = "longduy", pass = "longduy", db = "MYVNTOUR";
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String connectUrl = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";databasename=" + db +";";
            this.connection = DriverManager.getConnection(connectUrl,user,pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
