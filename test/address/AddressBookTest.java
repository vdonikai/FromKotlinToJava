package address;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class AddressBookTest {
    @Test

    public void findAddress() {
        AddressBook addressBook = new AddressBook();
        addressBook.addAddress("Васильев", "ул.Колотушкина", "дом 24", "кв. 34");
        addressBook.addAddress("Осипова", "ул.Ряженко", "дом 1", "кв. 1");
        assertEquals("ул.Колотушкина, дом 24, кв. 34", addressBook.findAddress("Васильев"));
        assertEquals("Такой человек в нашей базе не числится / We haven't got such person in our list", addressBook.findAddress("Остапенко"));
        assertEquals("ул.Ряженко, дом 1, кв. 1", addressBook.findAddress("Осипова"));
    }

    @Test
    public void buildingAndStreetList() {
        AddressBook addressBook = new AddressBook();
        addressBook.addAddress("Васильев", "ул.Ряженко", "дом 1", "кв. 34");
        addressBook.addAddress("Осипова", "ул.Ряженко", "дом 1", "кв. 1");
        addressBook.addAddress("Негев", "ул.Ряженко", "дом 2", "кв. 7");
        addressBook.addAddress("Васюков", "ул.Колотушкина", "дом 7", "кв. 304");
        addressBook.addAddress("Орипова", "ул.Колотушкина", "дом 31", "кв. 48");
        addressBook.addAddress("Негева", "ул.Подложкино", "дом 23", "кв. 27");
        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        ArrayList<String> list3 = new ArrayList<>();
        list1.add("Васильев"); list1.add("Осипова"); list1.add("Негев");
        list2.add("Васильев"); list2.add("Осипова");
        list3.add("Васюков"); list3.add("Орипова");
        assertEquals(list2, addressBook.buildingList("ул.Ряженко", "дом 1"));
        assertEquals(list1, addressBook.streetList("ул.Ряженко"));
        assertEquals(list3, addressBook.streetList("ул.Колотушкина"));

    }

    @Test
    public void stringBufferTest(){
        String string = "abcd\nthyfnb \n jfne";
        String[] stringArr = string.split("");
        StringBuffer sb = new StringBuffer("");
        int n = 0;
        while (n < string.length()) {
            sb.append(stringArr[n]);
            n++;
        }
        assertEquals("abcdthyfnb \n jfne", sb.toString());
        assertEquals(20, n);
    }
}
