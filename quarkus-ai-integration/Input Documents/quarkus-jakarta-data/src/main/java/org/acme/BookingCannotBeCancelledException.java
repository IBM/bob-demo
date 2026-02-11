package org.acme;

public class BookingCannotBeCancelledException extends RuntimeException {

    public BookingCannotBeCancelledException(String bookingNumber) {
        super("Booking " + bookingNumber + " cannot be canceled");
    }
}