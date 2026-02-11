package org.acme;

import io.quarkus.runtime.Startup;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestQuery;

import java.time.LocalDate;
import java.util.Optional;

@Path("/booking")
public class BookingResource {

    @Inject
    Customer.Repo customerRepo;

    @Inject
    BookingRepository bookingRepo;

    @Inject
    BookingService bookingService;

    @Transactional
    @Startup
    public void populate() {

        Booking booking = new Booking();

        booking.bookingNumber = "123-456";
        booking.dateFrom = LocalDate.now().plusDays(1);
        booking.dateTo = LocalDate.now().plusDays(3);

        Booking booking2 = new Booking();

        booking2.bookingNumber = "456-789";
        booking2.dateFrom = LocalDate.now().plusDays(2);
        booking2.dateTo = LocalDate.now().plusDays(4);

        Customer customer = new Customer();
        customer.name = "Alex";
        customer.surname = "Soto";
        customer.addBooking(booking);
        customer.addBooking(booking2);

        customerRepo.insert(customer);
        bookingRepo.insert(booking);
        bookingRepo.insert(booking2);

    }

    @GET
    public Booking findBooking(@RestQuery String bookingNumber,
                                   @RestQuery String name,
                                   @RestQuery String surname) {

        return bookingService.getBookingDetails(bookingNumber, name, surname);
    }
}
