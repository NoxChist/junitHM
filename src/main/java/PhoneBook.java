import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PhoneBook {
    private Set<String> grSet = new HashSet<>();
    private Set<Contact> contSet = new HashSet<>();
    private Map<String, Set<Contact>> phBook = new HashMap<>();

    public boolean isGroupExist(String groupName) {
        return grSet.contains(groupName);
    }

    public boolean isContactExist(String name, String phoneNumber) {
        Contact c = new Contact(name, phoneNumber);
        return contSet.contains(c);
    }

    public void addGroup(String name) throws NullPointerException {
        if (name == null) {
            throw new NullPointerException();
        }
        if (grSet.add(name)) {
            System.out.println("Группа " + name + " успешно добавлена.");
        } else {
            System.out.println("Group " + name + " already exists.");
        }
    }

    public void addContact(String name, String phoneNumber) {
        Contact c = new Contact(name, phoneNumber);
        if (contSet.isEmpty()) {
            contSet.add(c);
            System.out.println("Контакт " + name + " успешно добавлен.");
        } else {
            Contact searchRes = findByNumber(phoneNumber);
            if (searchRes == null) {
                contSet.add(c);
                System.out.println("Контакт " + name + " успешно добавлен.");
            } else {
                System.out.println("Контакт с номером " + phoneNumber + " уже существует.");
            }
        }
    }

    public void addContactToGroup(String groupName, String phoneNumber) {
        Contact c = findByNumber(phoneNumber);
        if (c != null && grSet.contains(groupName)) {
            phBook.merge(groupName, new HashSet<>(Set.of(c)), (oldV, newV) -> {
                newV.addAll(oldV);
                return newV;
            });
            System.out.println("Контакт " + c.toString() + " успешно добавлен в группу " + groupName + ".");
        }
    }

    public Contact findByNumber(String phoneNumber) {
        if (!contSet.isEmpty()) {
            for (Contact c : contSet) {
                if (c.getPhoneNumber() == phoneNumber) {
                    return c;
                }
            }
        }
        return null;
    }

    public Set findByGroup(String groupName) {
        return phBook.get(groupName);
    }

    /*public void showAllContact() {
        if (!contSet.isEmpty()) {
            for (Contact c : contSet) {
                System.out.print(c.toString());
            }
        } else {
            System.out.println("Телефонная книга пуста");
        }
    }*/
}
