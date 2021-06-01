package com.jdbc.test;

import com.jdbc.AddressBookData;
import com.jdbc.AddressBookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class AddressBookDBServiceTest {
    @Test
    void givenAddressBookInDB_WhenRetrieved_ShouldMatchAddress_Count() {
        AddressBookService addressBookService = new AddressBookService();
        List<AddressBookData> addressBookData = addressBookService.readAddressBookData(AddressBookService.IOService.DB_IO);
        System.out.println(addressBookData);
        Assertions.assertEquals(5, addressBookData.size());
    }

    @Test
    void givenNameForAddressBook_WhenUpdated_ShouldSyncWithDatabase() {
        AddressBookService addressBookService = new AddressBookService();
        List<AddressBookData> addressBookData = addressBookService.readAddressBookData(AddressBookService.IOService.DB_IO);
        addressBookService.updateAddressBookLastName("Gaurav", "Bodh");
        boolean result = addressBookService.checkAddressBookInSyncWithDB("Gaurav");
        Assertions.assertTrue(result);
    }

    @Test
    void givenDateRange_WhenRetrieved_ShouldMatchAddressBook_Count() {
        AddressBookService addressBookService = new AddressBookService();
        addressBookService.readAddressBookData(AddressBookService.IOService.DB_IO);
        LocalDate startDate = LocalDate.of(2002, 05, 05);
        LocalDate endDate = LocalDate.now();
        List<AddressBookData> addressBookData = addressBookService.readAddressBookForDateRange(AddressBookService
                .IOService.DB_IO, startDate, endDate);
        Assertions.assertEquals(4, addressBookData.size());
    }

    @Test
    void givenCity_WhenRetrived_ShouldMatchAddressBook_Count() {
        AddressBookService addressBookService = new AddressBookService();
        List<AddressBookData> addressBookData = addressBookService.readAddressBookDataByCity("Ayodhya");
        System.out.println(addressBookData);
        Assertions.assertEquals(2, addressBookData.size());
    }

    @Test
    void givenAddressBookDetails_WhenAdded_ShouldSyncWithDB() throws SQLException {
        AddressBookService addressBookService = new AddressBookService();
        addressBookService.readAddressBookData(AddressBookService.IOService.DB_IO);
        List<AddressBookData> addressBookData = addressBookService.addPerson("Kailash", "Vish", "New Delhi", "New Ashok Nagar", "NewDelhi",
                                                                             "9205267464",224001, "baba@gmail.com", "2012-05-20");
        boolean result = addressBookService.UpdatedRecordSyncWithDB("Kailash");
        System.out.println(addressBookData);
        Assertions.assertTrue(result);
    }

    @Test
    public void givenMultiplePersonContact_WhenAdded_ShouldSyncWithDB() throws SQLException, InterruptedException {
        AddressBookService addressBookService = new AddressBookService();
        List<AddressBookData> addressBookData = addressBookService.readAddressBookData(AddressBookService.IOService.DB_IO);
        AddressBookData[] personBook = {new AddressBookData(7,"Rohit", "Sharma", "Mumbai", "Maharashta", "MH",  "1234567890",
                                                               526341, "sharma@gmail.com", "2015-05-11"),
                                        new AddressBookData(8,"Virat", "Kohli", "Hariyana", "Hisaar", "Hariyana", "6633388256",
                                                         852963, "Virat@gmail.com", "2008-05-14"),
                                        new AddressBookData(9,"Mahi", "dhoni", "Ranchi", "Dhanbad", "Jharkhand",  "9876543210",
                                                         555889, "mahi@gmail.com", "2007-07-07"),};
        addressBookService.addMultiplePersonContactsToDBUsingThreads(Arrays.asList(personBook));
        boolean rs1 = addressBookService.checkAddressBookInSyncWithDB("Rohit");
        boolean rs2 = addressBookService.checkAddressBookInSyncWithDB("Virat");
        boolean rs3 = addressBookService.checkAddressBookInSyncWithDB("Mahi");
        System.out.println(addressBookData);
        Assertions.assertTrue(rs1);
        Assertions.assertTrue(rs2);
        Assertions.assertTrue(rs3);
    }
}


