package address;

import java.util.ArrayList;

public final class AddressBook{

    private final class Address{
        private String name;

        private String street;

        private String building;

        private String apartment;
    }

    private final ArrayList<Address> list = new ArrayList<>();

    public void addAddress(String name, String street, String building, String apartment){
        Address newAddress = new Address();
        newAddress.name = name;
        newAddress.street = street;
        newAddress.building = building;
        newAddress.apartment = apartment;
        list.add(newAddress);
    }

    public void deleteAddress(String name){
        for (Address element: list){
            if (element.name.equals(name)) list.remove(element);
        }
    }

    public void changeAddress(String name, String street, String building, String apartment){
        for (Address element: list){
            if (element.name.equals(name)){
                element.street = street;
                element.building = building;
                element.apartment = apartment;
            }
        }
    }

    public String findAddress(String name){
        for (Address element: list){
            if (element.name.equals(name)) return element.street + ", " + element.building + ", " + element.apartment;
        }
        return "Такой человек в нашей базе не числится / We haven't got such person in our list";
    }

    public ArrayList<String> buildingList(String street, String building){
        ArrayList<String> people = new ArrayList<>();
        for (Address element: list){
            if (element.street.equals(street) && element.building.equals(building)) people.add(element.name);
        }
        return people;
    }

    public ArrayList<String> streetList(String street){
        ArrayList<String> people = new ArrayList<>();
        for (Address element: list){
            if (element.street.equals(street)) people.add(element.name);
        }
        return people;
    }
}