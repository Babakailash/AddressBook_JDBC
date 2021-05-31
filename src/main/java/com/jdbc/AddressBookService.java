package com.jdbc;

import java.time.LocalDate;
import java.util.List;

public class AddressBookService {
    public enum IOService {FILE_IO,DB_IO}
    private List<AddressBookData> addressBookDataList;
    private AddressBookDBService addressBookDBService;

    public AddressBookService() {
        addressBookDBService = AddressBookDBService.getInstance();
    }

    public List<AddressBookData> readAddressBookData(IOService iOservice) {
        if (iOservice.equals(IOService.DB_IO))
            this.addressBookDataList = new AddressBookDBService().readData();
        return this.addressBookDataList;
    }

    public void updateAddressBookLastName(String firstName, String lastName) {
        int result = addressBookDBService.updateAddressBookData(firstName,lastName);
        if (result == 0) return;
        AddressBookData addressBookData = this.getAddressBookData(firstName);
        if (addressBookData != null) addressBookData.lastName = lastName;
    }

    public boolean checkAddressBookInSyncWithDB(String firstName) {
        List<AddressBookData> addressBookDataList = addressBookDBService.getAddressBookData(firstName);
        return addressBookDataList.get(0).equals(getAddressBookData(firstName));
    }

    private AddressBookData getAddressBookData(String firstName) {
        return this.addressBookDataList.stream()
                .filter(addressBookDataItem -> addressBookDataItem.firstName.equals(firstName))
                .findFirst()
                .orElse(null);
    }

    public List<AddressBookData> readAddressBookForDateRange(IOService ioService, LocalDate startDate, LocalDate endDate) {
        if (ioService.equals(IOService.DB_IO))
            return addressBookDBService.getAddressBookForDateRange(startDate, endDate);
        return null;
    }
}
