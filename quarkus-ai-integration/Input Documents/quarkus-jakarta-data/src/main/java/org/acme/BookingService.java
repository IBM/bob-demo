package org.acme;


import jakarta.data.Order;
import jakarta.data.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class BookingService {

    @Inject
    BookingRepository bookingRepo;

    public Booking getBookingDetails(String bookingNumber, String name, String surname) {
        return bookingRepo
                .findByBookingNumberAndCustomerNameAndCustomerSurname(bookingNumber, name, surname)
                .orElseThrow(() -> new BookingNotFoundException(bookingNumber));
    }

    public List<Booking> findAllBookingsByCustomer(String name, String surname) {
        return bookingRepo.findByCustomerNameAndCustomerSurname(name, surname);
    }

    public List<Booking> findAllBookings() {
        return bookingRepo.find(Sort.asc(Booking_.DATE_FROM));
    }

    @Transactional
    public void cancelBooking(String bookingNumber, String name, String surname) {
        Booking bookingDetails = getBookingDetails(bookingNumber, name, surname);

        LocalDate dateFrom = bookingDetails.dateFrom;
        LocalDate limit = LocalDate.now().plusDays(2);

        if (dateFrom.isAfter(limit)) {
            bookingDetails.cancelled = true;
            bookingRepo.update(bookingDetails);
        } else {
            throw new BookingCannotBeCancelledException(bookingNumber);
        }

    }

    @Transactional
    public void cleanPastBookings() {
        this.bookingRepo.deletePastBookings(LocalDate.now());
    }

}
