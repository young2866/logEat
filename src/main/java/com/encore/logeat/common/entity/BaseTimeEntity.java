package com.encore.logeat.common.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass
public class BaseTimeEntity {

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdTime;

	@LastModifiedDate
	private LocalDateTime modifiedTime;

	@PrePersist
	public void prePersist() {
		this.createdTime = LocalDateTime.now();
		this.modifiedTime = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
		this.modifiedTime = LocalDateTime.now();
	}

}