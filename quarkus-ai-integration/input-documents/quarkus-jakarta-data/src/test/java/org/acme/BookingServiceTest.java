package org.acme;


import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@QuarkusTest
public class BookingServiceTest {

    @Inject
    BookingService bookingService;

    @Test
    public void shouldFindBookingsByCustomerAndBooking() {
        Booking bookingDetails = bookingService
                .getBookingDetails("123-456", "Alex", "Soto");

        assertThat(bookingDetails.bookingNumber).isEqualTo("123-456");

    }

    @Test
    public void shouldFindAllBookingsByCustomer() {
        List<Booking> allBookingsByCustomer = bookingService.findAllBookingsByCustomer("Alex", "Soto");
        assertThat(allBookingsByCustomer)
                .extracting("bookingNumber")
                .containsExactly("123-456", "456-789");
    }

    @Test
    public void shouldFindAllBookings() {
        List<Booking> allBookings = bookingService.findAllBookings();
        assertThat(allBookings)
                .extracting("bookingNumber")
                .containsExactly("123-456", "456-789");
    }

    @Inject
    Customer.Repo customerRepo;

    @Inject
    BookingRepository bookingRepo;

    @Test
    public void shouldRemovePastBookings() {
        QuarkusTransaction.begin();
        Customer customer2 = new Customer();
        customer2.name = "Aixa";
        customer2.surname = "Soto";

        Booking booking3 = new Booking();
        booking3.bookingNumber = "123-789";
        booking3.dateFrom = LocalDate.now().minusDays(3);
        booking3.dateTo = LocalDate.now().minusDays(1);

        customer2.addBooking(booking3);

        customerRepo.insert(customer2);
        bookingRepo.insert(booking3);
        QuarkusTransaction.commit();

        List<Booking> allBookings = bookingService.findAllBookings();

        bookingService.cleanPastBookings();

        assertThat(bookingService.findAllBookings().size()).isEqualTo(allBookings.size() - 1);

    }

    @Test
    public void shouldCancelABooking() {
        QuarkusTransaction.begin();
        Customer customer2 = new Customer();
        customer2.name = "John";
        customer2.surname = "Doe";

        Booking booking3 = new Booking();
        booking3.bookingNumber = "789-123";
        booking3.cancelled = false;
        booking3.dateFrom = LocalDate.now().plusDays(10);
        booking3.dateTo = LocalDate.now().plusDays(20);

        customer2.addBooking(booking3);

        customerRepo.insert(customer2);
        bookingRepo.insert(booking3);
        QuarkusTransaction.commit();

        bookingService.cancelBooking("789-123", "John", "Doe");

        Booking bookingDetails = bookingService.getBookingDetails("789-123", "John", "Doe");
        assertThat(bookingDetails.cancelled).isTrue();

    }

    @Test
    public void shouldNotCancelABooking() {
        QuarkusTransaction.begin();
        Customer customer2 = new Customer();
        customer2.name = "John";
        customer2.surname = "Doe";

        Booking booking3 = new Booking();
        booking3.bookingNumber = "789-123";
        booking3.cancelled = false;
        booking3.dateFrom = LocalDate.now().plusDays(1);
        booking3.dateTo = LocalDate.now().plusDays(2);

        customer2.addBooking(booking3);

        customerRepo.insert(customer2);
        bookingRepo.insert(booking3);
        QuarkusTransaction.commit();

        assertThatThrownBy(() -> bookingService.cancelBooking("789-123", "John", "Doe"))
                .isInstanceOf(BookingCannotBeCancelledException.class);

    }

}
