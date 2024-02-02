package com.encore.logeat.like.domain;

import com.encore.logeat.post.domain.Post;
import com.encore.logeat.user.domain.User;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name = "likes")
public class Like {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int count;

	@OneToOne(fetch = FetchType.LAZY)
	private Post post;

	@OneToMany(fetch = FetchType.LAZY)
	private List<User> userList;

}