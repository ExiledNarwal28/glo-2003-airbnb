package ca.ulaval.glo2003.beds.domain;

import java.util.List;

public class BedMatcher {

  private BedTypes bedType;
  private CleaningFrequencies cleaningFrequency;
  private List<BloodTypes> bloodTypes;
  private int capacity;
  private List<Package> packages;

  public BedMatcher(
      BedTypes bedType,
      CleaningFrequencies cleaningFrequency,
      List<BloodTypes> bloodTypes,
      int capacity,
      List<Package> packages) {
    this.bedType = bedType;
    this.cleaningFrequency = cleaningFrequency;
    this.bloodTypes = bloodTypes;
    this.capacity = capacity;
    this.packages = packages;
  }

  public boolean matches(Bed bed) {
    if (bedType != null && bedType.equals(bed.getBedType())) return false;

    if (cleaningFrequency != null && cleaningFrequency.equals(bed.getCleaningFrequency()))
      return false;

    // TODO : Change test so it checks that the bed contains AT LEAST the blood types
    if (bloodTypes != null && bloodTypes.equals(bed.getBloodTypes())) return false;

    // TODO : Change tests so it checks the bed has the MINIMUM of the requested capacity
    if (capacity > 0 && capacity != bed.getCapacity()) return false;

    // TODO : Change test so it checks that the bed CONTAINS the package
    if (packages != null && !packages.equals(bed.getPackages())) return false;

    return true;
  }
}
