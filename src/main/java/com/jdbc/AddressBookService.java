package com.jdbc;

import java.util.List;

public class AddressBookService {
    public enum IOService {FILE_IO,DB_IO}

    private List<AddressBookData> addressBookDataList;

    public AddressBookService() {}

    public List<AddressBookData> readAddressBookData(IOService iOservice) {
        if (iOservice.equals(IOService.DB_IO))
            this.addressBookDataList = new AddressBookDBService().readData();
        return this.addressBookDataList;
    }
}

