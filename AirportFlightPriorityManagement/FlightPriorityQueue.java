
public class FlightPriorityQueue {
    private Flight[] heap;
    private int size;
    private long nextArrivalOrder;

    public FlightPriorityQueue() {
        heap = new Flight[10];
        size = 0;
        nextArrivalOrder = 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    // Returns true if flight A must appear before flight B.
    private boolean hasHigherPriority(Flight a, Flight b) {
        if (a.getPriority() != b.getPriority()) {
            return a.getPriority() > b.getPriority();
        }

        return a.getArrivalOrder() < b.getArrivalOrder();
    }

    private void swap(int i, int j) {
        Flight temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private void resizeIfNeeded() {
        if (size == heap.length) {
            Flight[] newHeap = new Flight[heap.length * 2];

            for (int i = 0; i < size; i++) {
                newHeap[i] = heap[i];
            }

            heap = newHeap;
        }
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;

            if (!hasHigherPriority(heap[index], heap[parent])) {
                break;
            }

            swap(index, parent);
            index = parent;
        }
    }

    private void heapifyDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int best = index;

            if (left < size &&
                hasHigherPriority(heap[left], heap[best])) {
                best = left;
            }

            if (right < size &&
                hasHigherPriority(heap[right], heap[best])) {
                best = right;
            }

            if (best == index) {
                break;
            }

            swap(index, best);
            index = best;
        }
    }

    private int findIndex(String flightId) {
        for (int i = 0; i < size; i++) {
            if (heap[i].getFlightId().equalsIgnoreCase(flightId)) {
                return i;
            }
        }

        return -1;
    }

    // Insert a new flight into the priority queue.
    public boolean addFlight(String flightId, String airline,
                             String destination, int priority,
                             String reason) {
        if (flightId == null || flightId.trim().isEmpty()
                || airline == null || airline.trim().isEmpty()
                || destination == null || destination.trim().isEmpty()
                || reason == null || reason.trim().isEmpty()) {
            System.out.println("Flight details cannot be empty.");
            return false;
        }

        if (priority < 1 || priority > 5) {
            System.out.println("Priority must be between 1 and 5.");
            return false;
        }

        if (findIndex(flightId.trim()) != -1) {
            System.out.println("Flight ID already exists.");
            return false;
        }

        resizeIfNeeded();

        Flight flight = new Flight(
            flightId.trim(),
            airline.trim(),
            destination.trim(),
            priority,
            reason.trim(),
            nextArrivalOrder++
        );

        heap[size] = flight;
        heapifyUp(size);
        size++;

        System.out.println("Flight added successfully.");
        return true;
    }

    // Return the highest-priority flight without removing it.
    public Flight peekNextFlight() {
        if (isEmpty()) {
            return null;
        }

        return heap[0];
    }

    // Remove and return the highest-priority flight.
    public Flight processNextFlight() {
        if (isEmpty()) {
            return null;
        }

        return removeAt(0);
    }

    private Flight removeAt(int index) {
        Flight removed = heap[index];
        int lastIndex = size - 1;

        if (index != lastIndex) {
            heap[index] = heap[lastIndex];
        }

        heap[lastIndex] = null;
        size--;

        if (index < size) {
            if (index > 0 &&
                hasHigherPriority(heap[index], heap[(index - 1) / 2])) {
                heapifyUp(index);
            } else {
                heapifyDown(index);
            }
        }

        return removed;
    }

    // Cancel a flight using its ID.
    public boolean cancelFlight(String flightId) {
        int index = findIndex(flightId);

        if (index == -1) {
            return false;
        }

        removeAt(index);
        return true;
    }

    // Change priority and restore the heap property.
    public boolean updatePriority(String flightId, int newPriority) {
        if (newPriority < 1 || newPriority > 5) {
            System.out.println("Priority must be between 1 and 5.");
            return false;
        }

        int index = findIndex(flightId);

        if (index == -1) {
            return false;
        }

        heap[index].setPriority(newPriority);

        if (index > 0 &&
            hasHigherPriority(heap[index], heap[(index - 1) / 2])) {
            heapifyUp(index);
        } else {
            heapifyDown(index);
        }

        return true;
    }

    // Search by flight ID.
    public Flight searchFlight(String flightId) {
        int index = findIndex(flightId);
        return index == -1 ? null : heap[index];
    }

    // Display flights in actual processing order.
    // Work on a copy so the original queue is unchanged.
    public void displayFlights() {
        if (isEmpty()) {
            System.out.println("No flights are waiting.");
            return;
        }

        FlightPriorityQueue copy = new FlightPriorityQueue();
        copy.heap = new Flight[Math.max(10, size)];

        for (int i = 0; i < size; i++) {
            copy.heap[i] = heap[i];
        }

        copy.size = size;
        copy.nextArrivalOrder = nextArrivalOrder;

        System.out.println("\n--- Flights in Processing Order ---");

        while (!copy.isEmpty()) {
            System.out.println(copy.processNextFlight());
        }
    }

    // Display the underlying array representation of the heap.
    public void displayHeapStructure() {
        if (isEmpty()) {
            System.out.println("Heap is empty.");
            return;
        }

        System.out.println("\n--- Internal Heap Array ---");

        for (int i = 0; i < size; i++) {
            int parent = (i - 1) / 2;

            System.out.printf(
                "Index %d: %s",
                i, heap[i].getFlightId()
            );

            if (i > 0) {
                System.out.printf(" | Parent: %s",
                    heap[parent].getFlightId());
            }

            System.out.println();
        }
    }
}
