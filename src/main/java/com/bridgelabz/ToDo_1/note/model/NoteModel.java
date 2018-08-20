package com.bridgelabz.ToDo_1.note.model;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Chaitra Ankolekar
 * Purpose :Entity class
 */
//@Document(collection = "note")
@Document(indexName="note",type="Note")
@JsonIgnoreProperties(value = { "createdAt", "editedAt" }, allowGetters = true)
public class NoteModel {

	@Id
	private String noteId;

	@NotBlank
	private String emailId;


	@ApiModelProperty(hidden=true)
	private boolean trash;
	
	@NotBlank
	private Description description;

	@NotBlank
	private String title;
		
	@CreatedDate
	@ApiModelProperty(hidden=true)
	private String createdAt;
	
	@LastModifiedDate
	@ApiModelProperty(hidden=true)
	private String editedAt;
	
	private boolean pin;
	
	@ApiModelProperty(hidden=true)
	private List<Label> labelname;
	
	@ApiModelProperty(hidden=true)
	private boolean archive;
	
	/**
	 * @return String
	 */
	public boolean getPin() {
		return pin;
	}

	/**
	 * @param pin
	 */
	public void setpin(boolean pin) {
		this.pin = pin;
	}
	/**
	 * @return description
	 */
	public Description getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(Description description) {
		this.description = description;
	}
	/**
	 * @return String
	 */
	public List<Label> getLabelName() {
		return labelname;
	}

	/**
	 * @param label
	 */
	public void setLabelName(List<Label> label) {
		this.labelname = label;
	}

	/**
	 * @return String
	 */
	public boolean getArchive() {
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
	public String getId() {
		return noteId;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.noteId = id;
	}

	/**
	 * @return String
	 */
	public String getCreatedAt() {
		return createdAt;
	}
	/**
	 * @return trash
	 */
	public boolean isTrash() {
		return trash;
	}

	/**
	 * @param trash
	 */
	public void setTrash(boolean trash) {
		this.trash = trash;
	}
	/**
	 * @param createdAt
	 */
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return String
	 */	
	public String getEditedAt() {
		return editedAt;
	}

	/**
	 * @param editedAt
	 */
	public void setEditedAt(String editedAt) {
		this.editedAt = editedAt;
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