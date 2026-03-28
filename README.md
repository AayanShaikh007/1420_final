# ENGG1420 Final Project - Campus Event Booking System

This is a Java application for managing users, events, and bookings. It supports creating users (3 types- students, staff, guests), managing different types of events (3 supported events- workshops, seminars, concerts), and handling bookings with a waitlist feature.

## Features
- User Management: Create and view users.
- Event Management: Create, update, cancel, and list events.
- Booking Management: Book users into events, cancel bookings, and view event registration list.
- Waitlist: Automatic waitlisting when events are full, with automatic promotion when a spot opens up (Using Java Queue).
- GUI: A graphical user interface built with JavaFX.

## How to Run the Program

### Prerequisites
- **Java JDK 21** or higher
- **Maven**

### Option 1: Using the GUI (Recommended)
1. Open this project in your favorite IDE (like IntelliJ IDEA, Eclipse, or VS Code).
2. Find the file `src/main/java/com/example/_420_final/Launcher.java `
3. Right-click the file and select **Run 'Launcher.main()'**
4. You should now see the GUI window and may interact with it.

### Option 2: Using the Command Line
1. In your IDE, locate `src/main/java/com/example/_420_final/Main.java `
2. Run the file and monitor your terminal
3. Follow the instructions in the terminal to interact via terminal.

## Features and pages
Once the program starts, you will see several tabs:
1. Main Menu: General information.
2. User Management: Add new users or view existing ones.
3. Event Management: Create new events.
4. Booking Management: Book a user (by ID) into an event (by ID). This page also shows which users are registered for each event.
5. Waitlist Management: See who is waiting for an event and manage the queue.
