package ca.ulaval.glo2003.bookings.services;

import ca.ulaval.glo2003.bookings.domain.Booking;
import ca.ulaval.glo2003.bookings.domain.BookingDate;
import ca.ulaval.glo2003.bookings.domain.CancelationRefundCalculator;
import ca.ulaval.glo2003.bookings.exceptions.BookingAlreadyCanceledException;
import ca.ulaval.glo2003.bookings.exceptions.CancelationNotAllowedException;
import ca.ulaval.glo2003.bookings.mappers.CancelationMapper;
import ca.ulaval.glo2003.bookings.rest.CancelationResponse;
import ca.ulaval.glo2003.transactions.domain.Price;
import ca.ulaval.glo2003.transactions.services.TransactionService;

public class CancelationService {

  static final int MINIMUM_DAYS_FOR_CANCELATION = 1;
  static final int MINIMUM_DAYS_FOR_FULL_REFUND = 7;

  private final CancelationRefundCalculator cancelationRefundCalculator;
  private final CancelationMapper cancelationMapper;
  private final TransactionService transactionService;

  public CancelationService(
      CancelationRefundCalculator cancelationRefundCalculator,
      CancelationMapper cancelationMapper,
      TransactionService transactionService) {
    this.cancelationRefundCalculator = cancelationRefundCalculator;
    this.cancelationMapper = cancelationMapper;
    this.transactionService = transactionService;
  }

  public CancelationResponse cancel(Booking booking, String bedOwner) {
    if (booking.isCanceled()) throw new BookingAlreadyCanceledException();

    Price refund;
    BookingDate now = BookingDate.now();

    if (now.plusDays(MINIMUM_DAYS_FOR_CANCELATION).isAfter(booking.getArrivalDate())) {
      throw new CancelationNotAllowedException();
    } else {
      refund =
          now.plusDays(MINIMUM_DAYS_FOR_FULL_REFUND).isAfter(booking.getArrivalDate())
              ? refundHalfTotal(booking, bedOwner)
              : refundFullTotal(booking, bedOwner);
    }

    booking.cancel();

    return cancelationMapper.toResponse(refund);
  }

  private Price refundHalfTotal(Booking booking, String bedOwner) {
    Price tenantRefund = cancelationRefundCalculator.calculateTenantRefund(booking.getTotal());
    Price ownerRefund = cancelationRefundCalculator.calculateOwnerRefund(booking.getTotal());
    transactionService.addStayCanceledHalfRefund(
        booking.getTenantPublicKey().getValue(),
        bedOwner,
        tenantRefund,
        ownerRefund,
        booking.getTotal(),
        booking.getNumberOfNights());
    return tenantRefund;
  }

  private Price refundFullTotal(Booking booking, String bedOwner) {
    transactionService.addStayCanceledFullRefund(
        booking.getTenantPublicKey().getValue(),
        bedOwner,
        booking.getTotal(),
        booking.getNumberOfNights());
    return booking.getTotal();
  }
}
