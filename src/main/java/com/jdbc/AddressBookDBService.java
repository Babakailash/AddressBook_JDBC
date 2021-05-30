package com.jdbc;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {
    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/addressbook?useSSL=false";
        String userName = "root";
        String password = "root";
        Connection connection;
        System.out.println("Connecting to Database:" +jdbcURL);
        connection = DriverManager.getConnection(jdbcURL,userName,password);
        System.out.println("Connection Successful:" +connection);
        return connection;
    }

    public List<AddressBookData> readData() {
        String sql = "SELECT * FROM details";
        List<AddressBookData> addressBookDataList = new ArrayList<>();
        try {
            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt("id");
                String firstName = result.getString("FirstName");
                String lastName = result.getString("LastName");
                String address = result.getString("Address");
                String city = result.getString("City");
                String state = result.getString("State");
                String phoneNumber = result.getString("PhoneNumber");
                int zipCode = result.getInt("ZipCode");
                String email = result.getString("Email");
                addressBookDataList.add(new AddressBookData(id,firstName, lastName, address, city,state,
                                            phoneNumber,zipCode,email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressBookDataList;
    }
}

