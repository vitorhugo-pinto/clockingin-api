package com.vhp.clockinginapi.dtos;

import java.util.List;

public class SummaryDTO {

  private List<CheckPointResponseDTO> checkPoints;
  private long workBalance;
  private boolean finishedShift;

  public SummaryDTO() {
    
  }
  
  public SummaryDTO(List<CheckPointResponseDTO> checkPoints, long workBalance, boolean finishedShift) {
    this.checkPoints = checkPoints;
    this.workBalance = workBalance;
    this.finishedShift = finishedShift;
  }

  public List<CheckPointResponseDTO> getCheckPoints() {
    return checkPoints;
  }

  public void setCheckPoints(List<CheckPointResponseDTO> checkPoints) {
    this.checkPoints = checkPoints;
  }

  public long getWorkBalance() {
    return workBalance;
  }

  public void setWorkBalance(long workBalance) {
    this.workBalance = workBalance;
  }

  public boolean getFinishedShift() {
    return finishedShift;
  }

  public void setFinishedShift(boolean finishedShift) {
    this.finishedShift = finishedShift;
  }
}
