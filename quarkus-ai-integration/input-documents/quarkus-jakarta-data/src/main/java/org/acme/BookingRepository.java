package org.acme;

import jakarta.data.Sort;
import jakarta.data.repository.By;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.OrderBy;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {

    @Find
    Optional<Booking> findByBookingNumberAndCustomerNameAndCustomerSurname(
            String bookingNumber,
            @By("customer.name") String customerName,
            @By("customer.surname") String customerSurname);

    @Find
    @OrderBy(Booking_.DATE_FROM)
    List<Booking> findByCustomerNameAndCustomerSurname(@By("customer.name") String customerName,
                                                       @By("customer.surname") String customerSurname);

    @Find
    List<Booking> find(Sort<Booking> sort);

    @Query("DELETE FROM Booking WHERE " + Booking_.DATE_TO + " < ?1")
    void deletePastBookings(LocalDate dateTo);

}
