package com.tim.one.service;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.tim.one.bean.ProjectStatusType;
import com.tim.one.service.impl.StatusServiceImpl;

public class TestStatusService {

  private StatusService statusService = new StatusServiceImpl();

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void shouldListProjectStatus() throws Exception {
    List<Map<Integer, String>> statuses = statusService.getProjectStatus();
    assertEquals(statuses.get(0).get("name"), ProjectStatusType.DEVELOPMENT.getName());
    assertEquals(statuses.get(1).get("name"), ProjectStatusType.REVIEWING.getName());
    assertEquals(statuses.get(2).get("name"), ProjectStatusType.CORRECTIONS.getName());
    assertEquals(statuses.get(3).get("name"), ProjectStatusType.CORRECTED.getName());
    assertEquals(statuses.get(4).get("name"), ProjectStatusType.REJECTED.getName());
    assertEquals(statuses.get(5).get("name"), ProjectStatusType.AUTORIZED.getName());
    assertEquals(statuses.get(6).get("name"), ProjectStatusType.PRODUCTION.getName());
    assertEquals(statuses.get(7).get("name"), ProjectStatusType.PRESENTATION.getName());
    assertEquals(statuses.get(8).get("name"), ProjectStatusType.CLOSED.getName());
    assertEquals(statuses.get(9).get("name"), ProjectStatusType.REVIEW.getName());
    assertEquals(statuses.get(10).get("name"), ProjectStatusType.PENDING.getName());
    assertEquals(statuses.get(11).get("name"), ProjectStatusType.CLOSING.getName());
  }

}
