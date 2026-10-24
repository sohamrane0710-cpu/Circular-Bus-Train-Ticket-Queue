import java.util.Scanner;

public class ContactManagerApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ContactLinkedList contacts =
            new ContactLinkedList();

    private static int nextId = 1;

    public static void main(String[] args) {

        int choice;

        System.out.println("==================================");
        System.out.println("     CONTACT MANAGEMENT SYSTEM");
        System.out.println("       Using Singly Linked List");
        System.out.println("==================================");

        do {
            displayMenu();
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addContact();
                    break;

                case 2:
                    contacts.displayContacts();
                    break;

                case 3:
                    searchContact();
                    break;

                case 4:
                    updateContact();
                    break;

                case 5:
                    deleteContact();
                    break;

                case 6:
                    System.out.println(
                            "Total contacts: " + contacts.getCount());
                    break;

                case 7:
                    System.out.println(
                            "Thank you for using Contact Management System.");
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Please enter a number from 1 to 7.");
            }

            System.out.println();

        } while (choice != 7);

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("========== MAIN MENU ==========");
        System.out.println("1. Add Contact");
        System.out.println("2. Display All Contacts");
        System.out.println("3. Search Contact");
        System.out.println("4. Update Contact");
        System.out.println("5. Delete Contact");
        System.out.println("6. Count Contacts");
        System.out.println("7. Exit");
        System.out.println("===============================");
    }

    // Read an integer without crashing on invalid input
    private static int readInt(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    // Read a non-empty string
    private static String readRequiredString(String message) {
        while (true) {
            System.out.print(message);
            String value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("This field cannot be empty.");
        }
    }

    // Add a new contact
    private static void addContact() {
        System.out.println("\n--- Add Contact ---");

        String name = readRequiredString("Enter name: ");
        String phone = readRequiredString("Enter phone number: ");
        String email = readRequiredString("Enter email: ");

        Contact contact = new Contact(nextId, name, phone, email);
        contacts.addContact(contact);

        System.out.println("Assigned Contact ID: " + nextId);
        nextId++;
    }

    // Search for a contact
    private static void searchContact() {
        System.out.println("\n--- Search Contact ---");

        int id = readInt("Enter Contact ID: ");
        Contact contact = contacts.searchContact(id);

        if (contact != null) {
            System.out.println("\nContact found:");
            contact.display();
        } else {
            System.out.println("Contact not found.");
        }
    }

    // Update an existing contact
    private static void updateContact() {
        System.out.println("\n--- Update Contact ---");

        int id = readInt("Enter Contact ID to update: ");

        Contact existing = contacts.searchContact(id);

        if (existing == null) {
            System.out.println("Contact not found.");
            return;
        }

        System.out.println("Current contact details:");
        existing.display();

        System.out.println("\nEnter the new details:");

        String name = readRequiredString("Enter new name: ");
        String phone = readRequiredString("Enter new phone number: ");
        String email = readRequiredString("Enter new email: ");

        boolean updated =
                contacts.updateContact(id, name, phone, email);

        if (updated) {
            System.out.println("Contact updated successfully.");
        } else {
            System.out.println("Unable to update contact.");
        }
    }

    // Delete a contact
    private static void deleteContact() {
        System.out.println("\n--- Delete Contact ---");

        int id = readInt("Enter Contact ID to delete: ");

        boolean deleted = contacts.deleteContact(id);

        if (deleted) {
            System.out.println("Contact deleted successfully.");
        } else {
            System.out.println(
                    "Contact not found. No changes were made.");
        }
    }
}