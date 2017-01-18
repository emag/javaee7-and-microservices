package javaee7.api;

public class CalculationResponse {

  private final String result;
  private final String elapsedTime;

  public CalculationResponse(String result, String elapsedTime) {
    this.result = result;
    this.elapsedTime = elapsedTime;
  }

  public String getResult() {
    return result;
  }

  public String getElapsedTime() {
    return elapsedTime;
  }

}
