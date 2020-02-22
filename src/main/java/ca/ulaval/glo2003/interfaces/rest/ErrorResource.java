package ca.ulaval.glo2003.interfaces.rest;

import static spark.Spark.exception;

import ca.ulaval.glo2003.beds.rest.exceptions.*;
import ca.ulaval.glo2003.beds.rest.handlers.*;
import ca.ulaval.glo2003.interfaces.rest.handlers.CatchallExceptionHandler;
import ca.ulaval.glo2003.interfaces.rest.handlers.JsonProcessingExceptionHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import spark.RouteGroup;

public class ErrorResource implements RouteGroup {

  public static final String ERROR_PATH = "*";

  @Override
  public void addRoutes() {
    exception(Exception.class, new CatchallExceptionHandler());
    exception(JsonProcessingException.class, new JsonProcessingExceptionHandler());
    exception(InvalidBedTypeException.class, new InvalidBedTypeExceptionHandler());
    exception(BedNotFoundException.class, new BedNotFoundExceptionHandler());
    exception(
        InvalidCleaningFrequencyException.class, new InvalidCleaningFrequencyExceptionHandler());
    exception(InvalidBloodTypesException.class, new InvalidBloodTypesExceptionHandler());
    exception(InvalidMinimalCapacityException.class, new InvalidMinimalCapacityExceptionHandler());
    exception(InvalidPackageException.class, new InvalidPackageExceptionHandler());
  }
}
