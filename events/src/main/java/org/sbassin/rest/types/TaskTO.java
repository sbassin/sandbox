package org.sbassin.rest.types;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TaskTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Float amount;

	private String content;

	private Date createdAt;

	private Integer id;

	private OfferTO offer;

	private String taskType;

	private String thumbnailUrl;

	private Date updatedAt;

	private String url;

	public Float getAmount() {
		return amount;
	}

	public String getContent() {
		return content;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Integer getId() {
		return id;
	}

	public OfferTO getOffer() {
		return offer;
	}

	public String getTaskType() {
		return taskType;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public String getUrl() {
		return url;
	}

	public void setAmount(final Float amount) {
		this.amount = amount;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	public void setCreatedAt(final Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public void setOffer(final OfferTO offer) {
		this.offer = offer;
	}

	public void setTaskType(final String taskType) {
		this.taskType = taskType;
	}

	public void setThumbnailUrl(final String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public void setUpdatedAt(final Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public void setUrl(final String url) {
		this.url = url;
	}
}
