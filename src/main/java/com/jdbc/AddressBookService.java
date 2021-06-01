package com.jdbc;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    //UC19-count city
    public List<AddressBookData> readAddressBookDataByCity(String city) {
        List<AddressBookData> addressBookDataList = addressBookDBService.getAddressBookData(city);
        this.addressBookDataList = new AddressBookDBService().readAddressBookDataByCity(city);
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

    public List<AddressBookData> addPerson(String firstName, String lastName, String address, String city, String state,
                                           String phoneNumber, int zipCode, String email, String start) throws SQLException {
        addressBookDataList.add(addressBookDBService.addPerson(firstName, lastName, address, city, state, phoneNumber, zipCode,
                                                               email, start));
        return addressBookDataList;
    }

    public boolean UpdatedRecordSyncWithDB(String firstName) {
        List<AddressBookData> addressBookData = addressBookDBService.getAddressBookData(firstName);
        return addressBookData.get(0).equals(getAddressBookData(firstName));
    }

    public void addMultiplePersonContactsToDBUsingThreads(List<AddressBookData> asList) throws InterruptedException {
        Map<Integer, Boolean> addressAdditionStatus = new HashMap<>();
        asList.forEach(addressbookdata -> {
            Runnable task = () -> { addressAdditionStatus.put(addressbookdata.hashCode(), false);
                System.out.println("Person_Contact Multiple Added:" + Thread.currentThread().getName());
                try {
                    this.addPerson(addressbookdata.getFirstName(), addressbookdata.getLastName(),
                                addressbookdata.getAddress(), addressbookdata.getCity(), addressbookdata.getState(),
                                addressbookdata.getPhoneNumber(), addressbookdata.getZipCode(), addressbookdata.getEmail(),
                                addressbookdata.getStart());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                addressAdditionStatus.put(addressbookdata.hashCode(), true);
                System.out.println("Multiple Contact Added:" + Thread.currentThread().getName()); };
            Thread thread = new Thread(task, addressbookdata.getFirstName());
            thread.start();
        });
        while (addressAdditionStatus.containsValue(false))
                Thread.sleep(50);
        }
}
