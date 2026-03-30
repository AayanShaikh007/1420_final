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

---

## JUnit Tests

**File**: `src/test/java/AppTest.java`

---

## What is Being Tested

| Test | Class | What it checks |
|------|-------|----------------|
| `testBookingConstructor` | `Booking` | All fields (ID, user, event, status) are stored correctly |
| `testStudentBookingSlots` | `Student` | A Student gets exactly 3 booking slots |
| `testUserIsEmptyAtStart` | `User` | A brand new user has no bookings |
| `testCreateUserBadEmail` | `UserManagement` | A bad email returns an error message |
| `testCreateEventDuplicateId` | `EventManagement` | A duplicate event ID returns an error |


## How to Run the Tests in IntelliJ

1. Open the project in IntelliJ
2. In the left panel go to `src` → `test` → `java`
3. Open `AppTest.java`
4. Right-click anywhere inside the file → click **Run 'AppTest'**
5. The results appear at the bottom — green means passed, red means failed

## How to View This on GitHub

1. Go to the GitHub repository
2. Click the branch dropdown (where it says `main`)
3. Select the **`Mani`** branch
4. Navigate to `src/test/java/AppTest.java` to see the test file


## How to Pull This Branch into Your Own IntelliJ

1. Open IntelliJ and go to **Git → Fetch** to get the latest branches
2. At the bottom of IntelliJ click the branch name → select **`origin/Mani`** → **Checkout**
3. The `AppTest.java` file will now be in your project
4. Right-click it → **Run 'AppTest'**


## Notes

- Tests use **JUnit 5** which is already included in `pom.xml`
- Only `@Test` and assert methods are used (`assertEquals`, `assertTrue`)

---
