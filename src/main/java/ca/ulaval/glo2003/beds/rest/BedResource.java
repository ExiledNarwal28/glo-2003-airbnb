package ca.ulaval.glo2003.beds.rest;

import static spark.Spark.get;
import static spark.Spark.post;

import ca.ulaval.glo2003.beds.services.BedService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;
import spark.RouteGroup;

public class BedResource implements RouteGroup {

  public static final String BED_PATH = "/beds";

  private final BedService bedService;

  public BedResource(BedService bedService) {
    this.bedService = bedService;
  }

  @Override
  public void addRoutes() {
    post("", this::add, new ObjectMapper()::writeValueAsString);
    get("", this::getAll, new ObjectMapper()::writeValueAsString);
    get("/:number", this::getByNumber, new ObjectMapper()::writeValueAsString);
  }

  public Object add(Request request, Response response) {
    try {
      BedRequest bedRequest = new ObjectMapper().readValue(request.body(), BedRequest.class);

      String bedNumber = bedService.add(bedRequest);

      response.status(HttpStatus.CREATED_201);
      response.header(HttpHeader.LOCATION.asString(), BED_PATH + "/" + bedNumber);
    } catch (JsonProcessingException e) {
      response.status(HttpStatus.BAD_REQUEST_400);
    }

    return "";
  }

  public Object getAll(Request request, Response response) {
    List<BedResponse> bedResponses = bedService.getAll();

    response.status(HttpStatus.OK_200);
    return bedResponses;
  }

  public Object getByNumber(Request request, Response response) {
    String bedNumber = request.params("number");
    BedResponse bedResponse = bedService.getByNumber(bedNumber);

    response.status(HttpStatus.OK_200);
    return bedResponse;
  }
}