package ca.ulaval.glo2003.beds.rest;

import java.util.List;
import java.util.UUID;

public class BedResponse {

  private UUID bedNumber;
  private String zipCode;
  private String bedType;
  private String cleaningFrequency;
  private List<String> bloodTypes;
  private int capacity;
  private List<PackageResponse> packages;
  private int stars;

  public UUID getBedNumber() {
    return bedNumber;
  }

  public void setBedNumber(UUID bedNumber) {
    this.bedNumber = bedNumber;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getBedType() {
    return bedType;
  }

  public void setBedType(String bedType) {
    this.bedType = bedType;
  }

  public String getCleaningFrequency() {
    return cleaningFrequency;
  }

  public void setCleaningFrequency(String cleaningFrequency) {
    this.cleaningFrequency = cleaningFrequency;
  }

  public List<String> getBloodTypes() {
    return bloodTypes;
  }

  public void setBloodTypes(List<String> bloodTypes) {
    this.bloodTypes = bloodTypes;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  public List<PackageResponse> getPackages() {
    return packages;
  }

  public void setPackages(List<PackageResponse> packages) {
    this.packages = packages;
  }

  public int getStars() {
    return stars;
  }

  public void setStars(int stars) {
    this.stars = stars;
  }
}