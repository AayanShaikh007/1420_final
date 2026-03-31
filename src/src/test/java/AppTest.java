import com.example._420_final.Control.*;
import com.example._420_final.Management.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class AppTest {
    // Test 1: Check that a Booking stores its values correctly
    @Test
    void testBookingConstructor() {
        Booking booking = new Booking("B001", "U001", "E001", "2025-06-15T09:30", "Confirmed");

        assertEquals("B001", booking.getBookingId());
        assertEquals("U001", booking.getUserId());
        assertEquals("E001", booking.getEventId());
        assertEquals("Confirmed", booking.getBookingStatus());
    }

    // Test 2: Check that a Student gets exactly 3 booking slots
    @Test
    void testStudentBookingSlots() {
        Student student = new Student("S001", "Alice", "alice@test.com");

        assertEquals(3, student.getUserBook().length);
    }

    // Test 3: Check that a new user with no bookings is empty
    @Test
    void testUserIsEmptyAtStart() {
        Student student = new Student("S001", "Alice", "alice@test.com");

        assertTrue(student.isEmpty());
    }

    // Test 4: Check that createUserGui rejects a bad email
    @Test
    void testCreateUserBadEmail() {
        UserManagement um = new UserManagement();

        String result = um.createUserGui("student", "S001", "Alice", "notAnEmail");

        assertTrue(result.toLowerCase().contains("error"));
    }

    // Test 5: Check that createEventGui rejects a duplicate event ID
    @Test
    void testCreateEventDuplicateId() {
        EventManagement em = new EventManagement();
        em.createEventGui("workshop", "E001", "First Event", "2025-06-01T10:00", "Room A", "10", "Java");

        String result = em.createEventGui("seminar", "E001", "Second Event", "2025-06-02T10:00", "Room B", "20", "AI");

        assertTrue(result.toLowerCase().contains("error"));
    }

}   //testing pushing changes