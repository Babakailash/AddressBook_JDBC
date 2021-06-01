package com.jdbc;
import java.awt.print.Book;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {

    private PreparedStatement addressBookDataStatement;
    private static AddressBookDBService addressBookDBService;

    public AddressBookDBService() {
    }

    public static AddressBookDBService getInstance() {
        if (addressBookDBService == null)
            addressBookDBService = new AddressBookDBService();
        return addressBookDBService;
    }

    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/addressbook?useSSL=false";
        String userName = "root";
        String password = "root";
        Connection connection;
        System.out.println("Connecting to Database:" + jdbcURL);
        connection = DriverManager.getConnection(jdbcURL, userName, password);
        System.out.println("Connection Successful:" + connection);
        return connection;
    }

    //UC-20 Transaction, Add Person
    public AddressBookData addPerson(String firstName, String lastName, String address, String city, String state,
                                     String phoneNumber, int zipCode, String email, String start) throws SQLException {
        int id = 6;
        AddressBookData addressBookData = null;
        String sql = String.format("insert into details(FirstName, LastName, Address, City, State, ZipCode, PhoneNumber, Email, start) values ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s','%s')", firstName, lastName, address, city, state, zipCode, phoneNumber, email, start);
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            int rowChanged = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
            if (rowChanged == 1) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next())
                    id = resultSet.getInt(1);
            }
            addressBookData = new AddressBookData(id, firstName, lastName, address, city, state, phoneNumber, zipCode, email, start);

            return addressBookData;
        }
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
                String start = result.getString("start");
                addressBookDataList.add(new AddressBookData(id, firstName, lastName, address, city, state,
                        phoneNumber, zipCode, email,start));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressBookDataList;
    }

    public int updateAddressBookData(String firstName, String lastName) {
        String sql = String.format("update details set LastName = '%s' where FirstName = '%s';", lastName, firstName);
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<AddressBookData> getAddressBookForDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = String.format("SELECT * FROM details where start between '%s' and '%s';",
                Date.valueOf(startDate), Date.valueOf(endDate));
        return this.getAddressBookDataUsingDB(sql);
    }

    public List<AddressBookData> readAddressBookDataByCity(String city) {
        String sql = String.format("SELECT * FROM details where City = '%s';",city);
        return this.getAddressBookDataUsingDB(sql);
    }

    public List<AddressBookData> getAddressBookDataUsingDB(String sql) {
        List<AddressBookData> addressBookDataList = new ArrayList<>();
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            addressBookDataList =this.getAddressBookData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressBookDataList;
    }

    public List<AddressBookData> getAddressBookData(String firstName) {
        List<AddressBookData> addressBookDataList = null;
        if (this.addressBookDataStatement == null)
            this.prepareStatementForAddressBook();
        try {
            addressBookDataStatement.setString(1, firstName);
            ResultSet resultSet = addressBookDataStatement.executeQuery();
            addressBookDataList = this.getAddressBookData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressBookDataList;
    }

    private List<AddressBookData> getAddressBookData(ResultSet resultSet) {
        List<AddressBookData> addressBookDataList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String address = resultSet.getString("Address");
                String city = resultSet.getString("City");
                String state = resultSet.getString("State");
                String phoneNumber = resultSet.getString("PhoneNumber");
                int zipCode = resultSet.getInt("ZipCode");
                String email = resultSet.getString("Email");
                String start = resultSet.getString("start");
                addressBookDataList.add(new AddressBookData(id, firstName, lastName, address, city, state,
                        phoneNumber, zipCode, email,start));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressBookDataList;

    }

    private void prepareStatementForAddressBook() {
        try {
            Connection connection = this.getConnection();
            String sql = "select * from details where FirstName = ?";
            addressBookDataStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



