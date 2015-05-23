package com.tim.one.service.impl

import org.springframework.stereotype.Service

import com.tim.one.bean.ProjectStatusType
import com.tim.one.service.StatusService

/**
 * @author josdem
 * @understands A class who knows how to delegate list ProjectStatus to the dao
 *
 */

@Service
public class StatusServiceImpl implements StatusService {

  public List<Map<Integer, String>> getProjectStatus() {
    def statuses = ProjectStatusType.values().collect { projectStatus ->
      [id : projectStatus.code, name : projectStatus.name]
    }
    statuses
  }

}
