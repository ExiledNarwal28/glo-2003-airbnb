package ca.ulaval.glo2003.beds.bookings.rest.mappers;

import ca.ulaval.glo2003.beds.bookings.domain.Booking;
import ca.ulaval.glo2003.beds.bookings.domain.BookingDate;
import ca.ulaval.glo2003.beds.bookings.exceptions.InvalidNumberOfNights;
import ca.ulaval.glo2003.beds.bookings.rest.BookingRequest;
import ca.ulaval.glo2003.beds.bookings.rest.BookingResponse;
import ca.ulaval.glo2003.beds.domain.Packages;
import ca.ulaval.glo2003.beds.domain.PublicKey;
import ca.ulaval.glo2003.beds.rest.mappers.PriceMapper;
import ca.ulaval.glo2003.beds.rest.mappers.PublicKeyMapper;

public class BookingMapper {

  private final PublicKeyMapper publicKeyMapper;
  private final BookingDateMapper bookingDateMapper;
  private final PriceMapper priceMapper;

  public BookingMapper(
      PublicKeyMapper publicKeyMapper,
      BookingDateMapper bookingDateMapper,
      PriceMapper priceMapper) {
    this.publicKeyMapper = publicKeyMapper;
    this.bookingDateMapper = bookingDateMapper;
    this.priceMapper = priceMapper;
  }

  public Booking fromRequest(BookingRequest bookingRequest) {
    validateNumberOfNights(bookingRequest.getNumberOfNights());

    PublicKey tenantPublicKey = publicKeyMapper.fromString(bookingRequest.getTenantPublicKey());
    BookingDate arrivalDate = bookingDateMapper.fromString(bookingRequest.getArrivalDate());
    Packages bookingPackage = Packages.get(bookingRequest.getBookingPackage());

    return new Booking(
        tenantPublicKey,
        arrivalDate,
        bookingRequest.getNumberOfNights(),
        bookingRequest.getColonySize(),
        bookingPackage);
  }

  public BookingResponse toResponse(Booking booking) {
    String arrivalDate = bookingDateMapper.toString(booking.getArrivalDate());
    Double total = priceMapper.toDouble(booking.getTotal());

    return new BookingResponse(
        arrivalDate, booking.getNumberOfNights(), booking.getPackage(), total);
  }

  private void validateNumberOfNights(int numberOfNights) {
    if (numberOfNights < 1 || numberOfNights > 90) {
      throw new InvalidNumberOfNights();
    }
  }
}
