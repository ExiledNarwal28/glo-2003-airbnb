package ca.ulaval.glo2003.beds.rest.mappers;

import ca.ulaval.glo2003.beds.domain.Bed;
import ca.ulaval.glo2003.beds.domain.BedTypes;
import ca.ulaval.glo2003.beds.domain.BloodTypes;
import ca.ulaval.glo2003.beds.domain.CleaningFrequencies;
import ca.ulaval.glo2003.beds.rest.BedRequest;
import ca.ulaval.glo2003.beds.rest.BedResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BedMapper {

  public static final String BED_TYPE_PARAM = "bedType";
  public static final String CLEANING_FREQUENCY_PARAM = "cleaningFrequency";
  public static final String BLOOD_TYPES_PARAM = "bloodTypes";

  public Bed fromRequest(BedRequest request) {
    // TODO
    return new Bed();
  }

  public Bed fromRequestParams(Map<String, String> params) {
    Bed bed = new Bed();

    if (params.get(BED_TYPE_PARAM) != null) {
      bed.setBedType(BedTypes.get(params.get(BED_TYPE_PARAM)));
    }
    if (params.get(CLEANING_FREQUENCY_PARAM) != null) {
      bed.setCleaningFrequency(CleaningFrequencies.get(params.get(CLEANING_FREQUENCY_PARAM)));
    }
    if (params.get(BLOOD_TYPES_PARAM) != null){
      List<BloodTypes> bloodTypes = fromBloodTypeString(params.get(BLOOD_TYPES_PARAM));
     bed.setBloodTypes(bloodTypes);
    }

    return bed;
  }

  public BedResponse toResponse(Bed bed) {
    // TODO
    return new BedResponse();
  }

  private List<BloodTypes> fromBloodTypeString(String bloodTypesInParams) {
    List<String> bloodTypeStrings = Arrays.asList(bloodTypesInParams.split(","));
    List<BloodTypes> bloodTypes = bloodTypeStrings.stream().map(bloodType -> BloodTypes.get(bloodType)).collect(Collectors.toList());
    return bloodTypes;
  }
}
