package com.jdbc.test;

import com.jdbc.AddressBookData;
import com.jdbc.AddressBookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

public class AddressBookDBServiceTest {
        @Test
        void givenAddressBookInDB_WhenRetrieved_ShouldMatchAddress_Count() {
            AddressBookService addressBookService = new AddressBookService();
            List<AddressBookData> addressBookData = addressBookService.readAddressBookData(AddressBookService.IOService.DB_IO);
            Assertions.assertEquals(5,addressBookData.size());
        }

    @Test
    void givenNameForAddressBook_WhenUpdated_ShouldSyncWithDatabase() {
        AddressBookService addressBookService = new AddressBookService();
        List<AddressBookData> addressBookData = addressBookService.readAddressBookData(AddressBookService.IOService.DB_IO);
        addressBookService.updateAddressBookLastName("Gaurav","Bodh");
        boolean result = addressBookService.checkAddressBookInSyncWithDB("Gaurav");
        Assertions.assertTrue(result);
    }
    }

