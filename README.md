# Circular Bus/Train Queue Management System

A menu-driven Java application that simulates passenger boarding for a bus or train using a **manually implemented, array-based Circular Queue**. The project demonstrates the FIFO (First In, First Out) principle, circular indexing, efficient insertion and deletion, and queue boundary conditions.

## Table of Contents

- [Overview](#overview)
- [Objectives](#objectives)
- [Features](#features)
- [Data Structure and Design](#data-structure-and-design)
- [Requirements](#requirements)
- [Compilation and Execution](#compilation-and-execution)
- [How to Use](#how-to-use)
- [Circular Queue Logic](#circular-queue-logic)
- [Example Dry Run](#example-dry-run)
- [Time and Space Complexity](#time-and-space-complexity)
- [Error Handling and Limitations](#error-handling-and-limitations)
- [Testing](#testing)
- [Project Structure](#project-structure)
- [Team Contributions](#team-contributions)
- [References](#references)

## Overview

In a bus or train boarding system, passengers generally board in the order in which they join the waiting queue. A circular queue is suitable for demonstrating this behaviour because it preserves FIFO ordering while allowing previously occupied array positions to be reused.

This application maintains passenger records containing a ticket number, name, and destination. It provides a command-line menu through which users can manage the waiting queue and inspect its current state.

**Project type:** Data Structures course project  
**Language:** Java  
**Interface:** Command-line interface (CLI)  
**Queue capacity:** 5 passengers in the demonstration configuration  
**Core data structure:** Array-based Circular Queue

## Objectives

- Implement a circular queue using an array without relying on Java's built-in queue implementation.
- Demonstrate FIFO ordering during passenger boarding.
- Support insertion, deletion, peeking, searching, and queue display.
- Reuse vacant array positions through circular indexing.
- Handle queue overflow, queue underflow, and invalid menu input.
- Analyse the time and space complexity of the supported operations.

## Features

| Feature | Description |
|---|---|
| Add passenger | Inserts a new passenger at the rear of the queue. |
| Board next passenger | Removes and returns the passenger at the front. |
| View next passenger | Displays the next passenger without removing them. |
| Display waiting queue | Shows passengers in logical FIFO order. |
| Search passenger | Searches waiting passengers by ticket number. |
| Queue status | Displays capacity, size, indices, and physical array slots. |
| Boarding history | Displays previously boarded passengers, if enabled in the implementation. |
| Input validation | Handles invalid numeric menu choices and blank text input. |
| Overflow and underflow handling | Prevents insertion into a full queue and removal from an empty queue. |

## Data Structure and Design

The queue is implemented using a fixed-size `Passenger[]` array and the following variables:

| Variable | Purpose |
|---|---|
| `queue[]` | Stores passenger objects. |
| `front` | Index of the next passenger to be boarded. |
| `rear` | Index of the most recently inserted passenger. |
| `size` | Number of passengers currently waiting. |
| `capacity` | Maximum number of passengers the queue can hold. |

The implementation uses `size` to distinguish between full and empty states.

- **Empty condition:** `size == 0`
- **Full condition:** `size == capacity`
- **Next circular index:** `(currentIndex + 1) % capacity`

The modulo operator allows an index to wrap from the last array position back to index `0`.

The core passenger queue is implemented manually. Java's built-in `Queue` or `LinkedList` is not used to perform the assessed queue operations.

## Requirements

Before running the project, ensure that you have:

- Java Development Kit (JDK) installed.
- A terminal or command-line interface.
- A text editor or Java-compatible IDE (optional).

Verify your Java installation:

```bash
java -version
javac -version
```

Both commands should display version information. The `javac` command must be available to compile the source file.

## Compilation and Execution

### 1. Clone the repository

Replace the placeholder URL with your actual GitHub repository URL.

```bash
git clone git@github.com:sohamrane0710-cpu/Circular-Bus-Train-Ticket-Queue.git
cd git@github.com:sohamrane0710-cpu/Circular-Bus-Train-Ticket-Queue.git
```

Alternatively, download the project and open a terminal in the directory containing `TransportQueueSystem.java`.

### 2. Compile the program

```bash
javac TransportQueueSystem.java
```

If compilation succeeds, Java generates the corresponding `.class` files in the current directory.

### 3. Run the program

```bash
java TransportQueueSystem
```

**Important:** Run the program using the class name `TransportQueueSystem`, without the `.java` extension.

### 4. Run again after making changes

```bash
javac TransportQueueSystem.java && java TransportQueueSystem
```

This command recompiles the program and launches it only if compilation succeeds.

## How to Use

When the program starts, it initializes a queue with three sample passengers:

| Ticket number | Passenger name | Destination |
|---:|---|---|
| 101 | Aarav | Thane |
| 102 | Meera | Dadar |
| 103 | Rohan | Panvel |

The menu provides the following operations:

1. **Add passenger:** Enter the passenger's name and destination. A ticket number is assigned by the program.
2. **Board next passenger:** Removes the passenger at the front and records the boarding operation.
3. **View next passenger:** Inspects the front passenger without changing the queue.
4. **Display waiting queue:** Lists waiting passengers in FIFO order.
5. **Search waiting passenger:** Searches for a ticket number among passengers currently waiting.
6. **Queue status:** Displays the queue's size, capacity, front and rear indices, and physical array contents.
7. **Boarding history:** Displays previously boarded passengers, if implemented.
8. **Exit:** Terminates the program.

The exact menu wording depends on the current source code.

## Circular Queue Logic

### Enqueue (insertion)

1. Check whether the queue is full.
2. Calculate the next rear index:

   ```java
   rear = (rear + 1) % capacity;
   ```

3. Store the passenger at `queue[rear]`.
4. Increment `size`.

**Time complexity:** `O(1)`

### Dequeue (boarding)

1. Check whether the queue is empty.
2. Save the passenger at `queue[front]`.
3. Clear the slot if supported by the implementation.
4. Advance the front index:

   ```java
   front = (front + 1) % capacity;
   ```

5. Decrement `size`.
6. Return the removed passenger.

**Time complexity:** `O(1)`

### Peek

Returns the passenger at `queue[front]` without removing them.

**Time complexity:** `O(1)`

### Display and search

These operations traverse the logical queue. For an element at logical position `i`, the physical array index is:

```java
(front + i) % capacity
```

This ensures traversal remains correct even when the queue wraps around the end of the array.

## Example Dry Run

Assume the queue has a capacity of five and initially contains passengers 101, 102, and 103.

| Operation | Front | Rear | Size |
|---|---:|---:|---:|
| Initial state | 0 | 2 | 3 |
| Add 104 | 0 | 3 | 4 |
| Add 105 | 0 | 4 | 5 |
| Board 101 | 1 | 4 | 4 |
| Board 102 | 2 | 4 | 3 |
| Add 106 | 2 | 0 | 4 |
| Add 107 | 2 | 1 | 5 |

Final physical array:

```text
Index:    0      1      2      3      4
        +------+------+------+------+------+
Queue:  | 106  | 107  | 103  | 104  | 105  |
        +------+------+------+------+------+
                  ^             ^
                front         rear
```

Final logical FIFO order:

```text
103 -> 104 -> 105 -> 106 -> 107
```

Although the physical array begins with 106 and 107, the next passenger to board is 103 because `front == 2`. This demonstrates circular wraparound and correct FIFO behaviour.

## Time and Space Complexity

Let:

- `n` = number of passengers currently waiting.
- `C` = queue capacity.
- `b` = number of passengers in boarding history.

| Operation | Time complexity |
|---|---|
| Enqueue | `O(1)` |
| Dequeue | `O(1)` |
| Peek | `O(1)` |
| Check empty/full | `O(1)` |
| Search passenger | `O(n)` |
| Display waiting queue | `O(n)` |
| Display physical queue status | `O(C)` |
| Display boarding history | `O(b)` |

**Space complexity:**

- Queue storage: `O(C)`
- Boarding history: `O(b)` additional space if retained.
- Overall: `O(C + b)` when boarding history is maintained.

The queue's capacity is fixed, so its array storage does not grow dynamically.

## Error Handling and Limitations

### Error handling

The program is designed to handle the following conditions:

- Attempting to add a passenger when the queue is full.
- Attempting to board a passenger when the queue is empty.
- Searching for a ticket that is not in the waiting queue.
- Entering invalid numeric menu input.
- Entering blank passenger names or destinations, if the corresponding validation is enabled.

### Limitations

- The queue has a fixed capacity.
- Passenger records are stored in memory and are not automatically saved between executions.
- The application uses a command-line interface rather than a graphical interface.
- Ticket number generation is simplified and is not connected to a real transport booking system.
- Boarding history is available only if it is maintained by the implementation.

## Testing

Test the following scenarios before submission:

| Test | Expected behaviour |
|---|---|
| Add a passenger to a non-full queue | Passenger is inserted successfully. |
| Board a passenger from a non-empty queue | The front passenger is removed. |
| View the next passenger | Front passenger is displayed without removal. |
| Search for an existing ticket | Matching waiting passenger is displayed. |
| Search for a nonexistent ticket | A suitable not-found message is displayed. |
| Insert into a full queue | Overflow is reported; queue remains unchanged. |
| Board from an empty queue | Underflow or an empty-queue message is displayed. |
| Reuse a freed array slot | Circular indexing wraps correctly. |
| Enter invalid menu input | The program handles the input without terminating unexpectedly. |

Record actual results after running the tests. Do not mark tests as passed until they have been verified.

## Project Structure

The project can be maintained as a single Java source file:

```text
circular-transport-queue/
├── TransportQueueSystem.java
└── README.md
```

The source file contains the following classes:

- **`Passenger`** — represents an individual passenger and their ticket details.
- **`CircularPassengerQueue`** — implements the circular queue operations.
- **`TransportQueueSystem`** — handles the menu, user input, initialization, and application workflow.

Java may generate `.class` files after compilation. These are build artifacts rather than source files.

## Team Contributions

Update this table to reflect the actual work completed by each team member.

| Team member | Contribution |
|---|---|
| [Member 1] | [Circular queue implementation and algorithms] |
| [Member 2] | [Menu-driven interface and input validation] |
| [Member 3] | [Testing, dry run, and complexity analysis] |
| [Member 4] | [Documentation, presentation, and demonstration] |

Adjust the number of rows and responsibilities to match your actual team.

## References

1. Oracle. *Java Documentation*. https://docs.oracle.com/en/java/
2. Course-prescribed Data Structures textbook and lecture notes.
3. Course materials covering queues, circular queues, arrays, FIFO behaviour, and algorithmic complexity.

---

**Academic note:** This project is intended to demonstrate the implementation and analysis of a circular queue as part of a Data Structures course. It is an educational simulation and is not a production passenger booking system.