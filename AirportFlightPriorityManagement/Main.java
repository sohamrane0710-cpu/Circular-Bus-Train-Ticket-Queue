
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("Input cannot be empty.");
        }
    }

    private static void addFlight(FlightPriorityQueue queue) {
        System.out.println("\n--- Add Flight ---");

        String id = readText("Enter flight ID: ");
        String airline = readText("Enter airline: ");
        String destination = readText("Enter destination: ");
        int priority = readInt("Enter priority (1-5): ");
        String reason = readText("Enter priority reason: ");

        queue.addFlight(id, airline, destination, priority, reason);
    }

    private static void searchFlight(FlightPriorityQueue queue) {
        String id = readText("Enter flight ID to search: ");
        Flight flight = queue.searchFlight(id);

        if (flight == null) {
            System.out.println("Flight not found.");
        } else {
            System.out.println("Flight found:");
            System.out.println(flight);
        }
    }

    private static void processNextFlight(FlightPriorityQueue queue) {
        Flight flight = queue.processNextFlight();

        if (flight == null) {
            System.out.println("No flights available to process.");
        } else {
            System.out.println("\nProcessing flight:");
            System.out.println(flight);
            System.out.println("Flight processed successfully.");
        }
    }

    private static void cancelFlight(FlightPriorityQueue queue) {
        String id = readText("Enter flight ID to cancel: ");

        if (queue.cancelFlight(id)) {
            System.out.println("Flight cancelled successfully.");
        } else {
            System.out.println("Flight not found.");
        }
    }

    private static void updatePriority(FlightPriorityQueue queue) {
        String id = readText("Enter flight ID: ");
        int priority = readInt("Enter new priority (1-5): ");

        if (queue.updatePriority(id, priority)) {
            System.out.println("Priority updated successfully.");
        } else {
            System.out.println("Flight not found or priority invalid.");
        }
    }

    public static void main(String[] args) {
        FlightPriorityQueue queue = new FlightPriorityQueue();

        while (true) {
            System.out.println("\n======================================");
            System.out.println(" AIRPORT FLIGHT PRIORITY MANAGEMENT");
            System.out.println("======================================");
            System.out.println("1. Add Flight");
            System.out.println("2. Display Flights in Processing Order");
            System.out.println("3. Search Flight");
            System.out.println("4. Process Next Flight");
            System.out.println("5. Cancel Flight");
            System.out.println("6. Update Flight Priority");
            System.out.println("7. Display Waiting Flight Count");
            System.out.println("8. Display Internal Heap Structure");
            System.out.println("0. Exit");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addFlight(queue);
                    break;

                case 2:
                    queue.displayFlights();
                    break;

                case 3:
                    searchFlight(queue);
                    break;

                case 4:
                    processNextFlight(queue);
                    break;

                case 5:
                    cancelFlight(queue);
                    break;

                case 6:
                    updatePriority(queue);
                    break;

                case 7:
                    System.out.println(
                        "Waiting flights: " + queue.getSize()
                    );
                    break;

                case 8:
                    queue.displayHeapStructure();
                    break;

                case 0:
                    System.out.println("Exiting application.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid menu choice.");
            }
        }
    }
}
