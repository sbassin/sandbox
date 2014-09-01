package org.sbassin.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "tasks")
@XmlRootElement
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "amount", precision = 12, scale = 0)
    private Float amount;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", length = 19)
    private Date createdAt;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @Column(name = "task_type")
    private String taskType;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", length = 19)
    private Date updatedAt;

    @Column(name = "url")
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

    public Offer getOffer() {
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

    public void setOffer(final Offer offer) {
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
