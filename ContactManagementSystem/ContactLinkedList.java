public class ContactLinkedList {

    private ContactNode head;
    private ContactNode tail;
    private int size;

    public ContactLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    // 1. Add a contact at the end
    public void addContact(Contact contact) {
        ContactNode newNode = new ContactNode(contact);

        if (head == null) {
            // First node in the list
            head = newNode;
            tail = newNode;
        } else {
            // Link the current last node to the new node
            tail.next = newNode;
            tail = newNode;
        }

        size++;
        System.out.println("Contact added successfully.");
    }

    // 2. Search for a contact using its ID
    public Contact searchContact(int id) {
        ContactNode current = head;

        while (current != null) {
            if (current.data.getId() == id) {
                return current.data;
            }

            current = current.next;
        }

        return null;
    }

    // 3. Update an existing contact
    public boolean updateContact(
            int id, String name, String phone, String email) {

        Contact contact = searchContact(id);

        if (contact == null) {
            return false;
        }

        contact.setName(name);
        contact.setPhone(phone);
        contact.setEmail(email);

        return true;
    }

    // 4. Delete a contact using its ID
    public boolean deleteContact(int id) {

        if (head == null) {
            return false;
        }

        // Case 1: The first node must be deleted
        if (head.data.getId() == id) {
            head = head.next;
            size--;

            // If the list becomes empty
            if (head == null) {
                tail = null;
            }

            return true;
        }

        // Find the node preceding the target
        ContactNode previous = head;
        ContactNode current = head.next;

        while (current != null) {

            if (current.data.getId() == id) {

                // Bypass the target node
                previous.next = current.next;

                // If the last node was deleted
                if (current == tail) {
                    tail = previous;
                }

                size--;
                return true;
            }

            previous = current;
            current = current.next;
        }

        // No matching ID exists
        return false;
    }

    // 5. Display all contacts
    public void displayContacts() {

        if (head == null) {
            System.out.println("No contacts available.");
            return;
        }

        ContactNode current = head;

        System.out.println("\n========== CONTACT LIST ==========");

        while (current != null) {
            current.data.display();
            System.out.println("----------------------------------");

            current = current.next;
        }

        System.out.println("Total contacts: " + size);
    }

    // 6. Count contacts in O(1) time
    public int getCount() {
        return size;
    }

    // Check whether the list is empty
    public boolean isEmpty() {
        return head == null;
    }
}