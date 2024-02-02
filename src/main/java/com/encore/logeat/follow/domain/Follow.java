package com.encore.logeat.follow.domain;


import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Follow {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	private Long follow; // -> 내가 팔로우 하는 사람

	@Column(name = "follow_to_me")
	private Long followToMe; // -> 나를 팔로우 하는 사람
}