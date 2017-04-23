package my.apps.db;

import my.apps.web.Shopping;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class ShoppingRepository {

    // 1. define connection params to db
    final static String URL = "jdbc:postgresql://54.93.65.5:5432/QA6_Oana";
    final static String USERNAME = "fasttrackit_dev";
    final static String PASSWORD = "fasttrackit_dev";


    public void insert(Shopping shopping) throws ClassNotFoundException, SQLException {
         // 1. load the driver
        Class.forName("org.postgresql.Driver");

        // 2. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        // 3. create a query statement
        PreparedStatement pSt = conn.prepareStatement("INSERT INTO ware( Product, Quantity) VALUES (?,?)");
        pSt.setString(1, shopping.getProduct());
        pSt.setInt(2, shopping.getQuantity());


        // 4. execute a prepared statement
        int rowsInserted = pSt.executeUpdate();
        System.out.println("Inserted " + rowsInserted + " rows.");

        // 5. close the objects
        pSt.close();
        conn.close();
    }

    public List<Shopping> read() throws ClassNotFoundException, SQLException {
        // 1. load the driver
        Class.forName("org.postgresql.Driver");

        // 2. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        // 3. create a query statement
        Statement st = conn.createStatement();

        // 4. execute a query
        ResultSet rs = st.executeQuery("SELECT Product, Quantity FROM ware");

        // 5. iterate the result set and print the values
        List<Shopping> shopping = new ArrayList<>();
        while (rs.next()) {
            Shopping Shopping = new Shopping(
                    rs.getString("Product"),
                    rs.getInt("Quantity")
                    );

            shopping.add(Shopping);
        }

        // 6. close the objects
        rs.close();
        st.close();
        conn.close();
        return shopping;
    }

}