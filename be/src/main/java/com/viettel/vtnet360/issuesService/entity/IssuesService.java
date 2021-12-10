package com.viettel.vtnet360.issuesService.entity;

import java.util.List;

public class IssuesService {
  private List<IssuesServiceEntity> listIssuesService;
  
  private int totalIssuesService;

  public List<IssuesServiceEntity> getListIssuesService() {
    return listIssuesService;
  }

  public void setListIssuesService(List<IssuesServiceEntity> listIssuesService) {
    this.listIssuesService = listIssuesService;
  }

  public int getTotalIssuesService() {
    return totalIssuesService;
  }

  public void setTotalIssuesService(int totalIssuesService) {
    this.totalIssuesService = totalIssuesService;
  }
  
  
}
