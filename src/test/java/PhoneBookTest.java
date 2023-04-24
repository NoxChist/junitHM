import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.stream.Stream;


public class PhoneBookTest {
    PhoneBook pb;

    @BeforeEach
    public void createPhoneBook() {
        pb = new PhoneBook();
    }

    @AfterEach
    public void destroyPhoneBook() {
        pb = new PhoneBook();
    }

    @Test
    public void groupAddTest() {
        String name = "New group";
        BooleanSupplier checkGroup = () -> pb.isGroupExist(name);

        pb.addGroup(name);

        Assertions.assertTrue(checkGroup);
    }

    @Test
    public void groupAddNullTest() {
        String name = null;
        Class<NullPointerException> expected = NullPointerException.class;

        Executable executable = () -> pb.addGroup(name);

        Assertions.assertThrowsExactly(expected, executable);
    }

    @Test
    public void addContactTest() {
        String name = "Kate", phoneNumber = "+89008007766";
        BooleanSupplier checkContact = () -> pb.isContactExist(name, phoneNumber);

        pb.addContact(name, phoneNumber);

        Assertions.assertTrue(checkContact);
    }

    @Test
    public void addContactSameNumberTest() {
        String name = "Existed", name_ = "Double", phoneNumber = "+89008007766";
        pb.addContact(name, phoneNumber);
        BooleanSupplier checkContact = () -> pb.isContactExist(name_, phoneNumber);

        pb.addContact(name_, phoneNumber);

        Assertions.assertFalse(checkContact);
    }

    @ParameterizedTest
    @MethodSource("findByNumberTestArguments")
    public void findByNumberTest(PhoneBook phoneBook, String phoneNumber, Contact expected) {

        Contact result = phoneBook.findByNumber(phoneNumber);

        Assertions.assertEquals(expected, result);
    }

    private static Stream<Arguments> findByNumberTestArguments() {
        List<String> names = List.of("Bob", "Jhon", "Tom", "Jake", "Mark");
        List<String> numbers = List.of("+9990032", "+9990132", "87099900", "123456", "654321");
        List<Contact> contacts = new ArrayList<>();
        PhoneBook pb = new PhoneBook();
        for (int i = 0; i < names.size(); i++) {
            contacts.add(new Contact(names.get(i), numbers.get(i)));
            pb.addContact(names.get(i), numbers.get(i));
        }
        return Stream.of(
                Arguments.of(pb, numbers.get(0), contacts.get(0)),
                Arguments.of(pb, numbers.get(1), contacts.get(1)),
                Arguments.of(pb, numbers.get(2), contacts.get(2)),
                Arguments.of(pb, numbers.get(3), contacts.get(3)),
                Arguments.of(pb, numbers.get(4), contacts.get(4)));
    }


}
