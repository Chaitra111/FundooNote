package com.bridgelabz.ToDo_1.note.model;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Chaitra Ankolekar
 * Purpose :DTO class for user
 */

public class NoteDTO 
{
	@NotBlank
	private String title;

	@NotBlank
	private String description;

	@NotBlank
	private String emailId;
	
	@ApiModelProperty(hidden=true)
	private boolean trash;
	public boolean getTrash() {
		return trash;
	}

	public void setTrash(boolean trash) {
		this.trash = trash;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@ApiModelProperty(hidden=true)
	private boolean archive;
	
	@ApiModelProperty(hidden=true)
	private String label;
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	@ApiModelProperty(hidden=true)	
	private boolean pin;
	
	/**
	 * @return boolean
	 */
	public boolean isArchive() {
		return archive;
	}

	/**
	 * @param archive
	 */
	public void setArchive(boolean archive) {
		this.archive = archive;
	}

	/**
	 * @return String
	 */
	public boolean getPin() {
		return pin;
	}

	/**
	 * @param emailId
	 */
	public void setPin(boolean pin) {
		this.pin = pin;
	}
	/**
	 * @return String
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return String
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}
