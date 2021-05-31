package com.jdbc.test;

import com.jdbc.AddressBookData;
import com.jdbc.AddressBookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class AddressBookDBServiceTest {
        @Test
        void givenAddressBookInDB_WhenRetrieved_ShouldMatchAddress_Count() {
            AddressBookService addressBookService = new AddressBookService();
            List<AddressBookData> addressBookData = addressBookService.readAddressBookData(AddressBookService.IOService.DB_IO);
            System.out.println(addressBookData);
            Assertions.assertEquals(5,addressBookData.size());
        }

        @Test
        void givenNameForAddressBook_WhenUpdated_ShouldSyncWithDatabase() {
            AddressBookService addressBookService = new AddressBookService();
            List<AddressBookData> addressBookData = addressBookService.readAddressBookData(AddressBookService.IOService.DB_IO);
            addressBookService.updateAddressBookLastName("Gaurav","Bodh");
            boolean result = addressBookService.checkAddressBookInSyncWithDB("Gaurav");
            System.out.println(addressBookData);
            Assertions.assertTrue(result);
        }

        @Test
        void givenDateRange_WhenRetrieved_ShouldMatchAddressBook_Count() {
            AddressBookService addressBookService = new AddressBookService();
            addressBookService.readAddressBookData(AddressBookService.IOService.DB_IO);
            LocalDate startDate = LocalDate.of(2002, 05, 05);
            LocalDate endDate = LocalDate.now();
            List<AddressBookData> addressBookData = addressBookService.readAddressBookForDateRange(AddressBookService
                    .IOService.DB_IO,startDate, endDate);
            System.out.println(addressBookData);
            Assertions.assertEquals(4, addressBookData.size());
        }

        @Test
        void givenCity_WhenRetrived_ShouldMatchAddressBook_Count() {
                AddressBookService addressBookService = new AddressBookService();
                List<AddressBookData> addressBookData = addressBookService.readAddressBookDataByCity ("Ayodhya");
                System.out.println(addressBookData);
                Assertions.assertEquals(2,addressBookData.size());
        }
}

