
import java.util.ArrayList;
import java.util.Scanner;

class Passenger {
    private final int ticketNumber;
    private final String name;
    private final String destination;

    public Passenger(int ticketNumber, String name, String destination) {
        this.ticketNumber = ticketNumber;
        this.name = name;
        this.destination = destination;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public String getName() {
        return name;
    }

    public String getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "Ticket: " + ticketNumber
                + " | Name: " + name
                + " | Destination: " + destination;
    }
}

class CircularPassengerQueue {
    private final Passenger[] queue;
    private final int capacity;

    private int front;
    private int rear;
    private int size;

    // Stores passengers who have already boarded.
    private final ArrayList<Passenger> boardingHistory;

    public CircularPassengerQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException(
                    "Capacity must be greater than zero."
            );
        }

        this.capacity = capacity;
        this.queue = new Passenger[capacity];

        front = 0;
        rear = -1;
        size = 0;

        boardingHistory = new ArrayList<>();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFrontIndex() {
        return front;
    }

    public int getRearIndex() {
        return rear;
    }

    // ENQUEUE: Add a passenger at the rear.
    public boolean enqueue(Passenger passenger) {
        if (isFull()) {
            System.out.println(
                    "Queue is full! No more passengers can join."
            );
            return false;
        }

        rear = (rear + 1) % capacity;
        queue[rear] = passenger;
        size++;

        System.out.println("Passenger added successfully:");
        System.out.println(passenger);

        return true;
    }

    // DEQUEUE: Board and remove the front passenger.
    public Passenger dequeue() {
        if (isEmpty()) {
            System.out.println("No passengers are waiting.");
            return null;
        }

        Passenger boarded = queue[front];

        queue[front] = null;
        front = (front + 1) % capacity;
        size--;

        boardingHistory.add(boarded);

        System.out.println("Passenger boarded successfully:");
        System.out.println(boarded);

        return boarded;
    }

    // PEEK: View the next passenger without removing them.
    public Passenger peek() {
        if (isEmpty()) {
            System.out.println("The queue is empty.");
            return null;
        }

        return queue[front];
    }

    // Display passengers in their logical FIFO order.
    public void displayQueue() {
        if (isEmpty()) {
            System.out.println("No passengers are waiting.");
            return;
        }

        System.out.println("\n--- Current Boarding Queue ---");

        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;

            System.out.println(
                    (i + 1) + ". " + queue[index]
                    + " [Array index: " + index + "]"
            );
        }

        System.out.println("Waiting passengers: " + size);
        System.out.println("Queue capacity: " + capacity);
    }

    // Search only among passengers who are still waiting.
    public void searchPassenger(int ticketNumber) {
        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;
            Passenger passenger = queue[index];

            if (passenger.getTicketNumber() == ticketNumber) {
                System.out.println("Passenger found in waiting queue:");
                System.out.println(passenger);
                System.out.println("Array index: " + index);
                return;
            }
        }

        System.out.println(
                "No waiting passenger found with ticket number "
                + ticketNumber + "."
        );
    }

    // Show the internal state of the circular array.
    public void displayStatus() {
        System.out.println("\n--- Circular Queue Status ---");
        System.out.println("Capacity : " + capacity);
        System.out.println("Size     : " + size);
        System.out.println("Front    : " + front);
        System.out.println("Rear     : " + rear);
        System.out.println("Empty    : " + isEmpty());
        System.out.println("Full     : " + isFull());

        System.out.println("\nPhysical array:");

        for (int i = 0; i < capacity; i++) {
            String label = "";

            if (size > 0 && i == front) {
                label += " FRONT";
            }

            if (size > 0 && i == rear) {
                label += " REAR";
            }

            if (queue[i] == null) {
                System.out.println(
                        "[" + i + "] EMPTY" + label
                );
            } else {
                System.out.println(
                        "[" + i + "] "
                        + queue[i].getTicketNumber()
                        + " - " + queue[i].getName()
                        + label
                );
            }
        }
    }

    // Display passengers who have already boarded.
    public void displayBoardingHistory() {
        System.out.println("\n--- Boarding History ---");

        if (boardingHistory.isEmpty()) {
            System.out.println("No passengers have boarded yet.");
            return;
        }

        for (int i = 0; i < boardingHistory.size(); i++) {
            System.out.println(
                    (i + 1) + ". " + boardingHistory.get(i)
            );
        }

        System.out.println(
                "Total passengers boarded: " + boardingHistory.size()
        );
    }
}

public class TransportQueueSystem {
    private static final Scanner scanner = new Scanner(System.in);

    private static int nextTicketNumber = 104;

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);

            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("Input cannot be empty.");
        }
    }

    private static void showMenu() {
        System.out.println("\n==================================");
        System.out.println(" CIRCULAR TRANSPORT QUEUE SYSTEM");
        System.out.println("==================================");
        System.out.println("1. Add passenger to queue");
        System.out.println("2. Board next passenger");
        System.out.println("3. View next passenger");
        System.out.println("4. Display waiting queue");
        System.out.println("5. Search waiting passenger");
        System.out.println("6. Display circular queue status");
        System.out.println("7. Display boarding history");
        System.out.println("8. Exit");
        System.out.println("==================================");
    }

    public static void main(String[] args) {
        CircularPassengerQueue queue =
                new CircularPassengerQueue(5);

        // Sample records for the demonstration.
        queue.enqueue(new Passenger(101, "Aarav", "Thane"));
        queue.enqueue(new Passenger(102, "Meera", "Dadar"));
        queue.enqueue(new Passenger(103, "Rohan", "Panvel"));

        boolean running = true;

        while (running) {
            showMenu();

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1: {
                    if (queue.isFull()) {
                        System.out.println(
                                "Queue is full. Board someone first."
                        );
                        break;
                    }

                    String name = readText("Enter passenger name: ");
                    String destination =
                            readText("Enter destination: ");

                    Passenger passenger = new Passenger(
                            nextTicketNumber, name, destination
                    );

                    if (queue.enqueue(passenger)) {
                        nextTicketNumber++;
                    }

                    break;
                }

                case 2:
                    queue.dequeue();
                    break;

                case 3: {
                    Passenger passenger = queue.peek();

                    if (passenger != null) {
                        System.out.println(
                                "Next passenger to board:"
                        );
                        System.out.println(passenger);
                    }

                    break;
                }

                case 4:
                    queue.displayQueue();
                    break;

                case 5: {
                    int ticket = readInt(
                            "Enter ticket number to search: "
                    );
                    queue.searchPassenger(ticket);
                    break;
                }

                case 6:
                    queue.displayStatus();
                    break;

                case 7:
                    queue.displayBoardingHistory();
                    break;

                case 8:
                    running = false;
                    System.out.println(
                            "Transport Queue System closed."
                    );
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Select 1 to 8."
                    );
            }
        }

        scanner.close();
    }
}
