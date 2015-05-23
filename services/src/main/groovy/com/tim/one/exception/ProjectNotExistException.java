package com.tim.one.exception;

/**
 * @author josdem
 * @understands An exception class threw when client attempts to change status
 *              from a project that actually does not exist
 * 
 */

public class ProjectNotExistException extends RuntimeException {

	private static final long serialVersionUID = -4553330242539113949L;

	private Integer projectId;

	public ProjectNotExistException(Integer projectId) {
		this.projectId = projectId;
	}

	@Override
	public String getMessage() {
		return "I CAN NOT CHANGE STATUS FROM PROJECT WITH ID: " + projectId + " SINCE IT DOES NOT EXIST";
	}

}
