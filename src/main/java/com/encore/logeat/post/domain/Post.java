package com.encore.logeat.post.domain;

import com.encore.logeat.common.entity.BaseTimeEntity;
import com.encore.logeat.like.domain.Like;
import com.encore.logeat.user.domain.User;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 31)
	private String title;
	@Column(nullable = false)
	private String contents;
	@Column(name = "image_path")
	private String imagePath;
	private String location;
	private String category;
	private int viewCount;
	@Column(name = "secret_y_or_n")
	private String secretYorN; // 공개 여부
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
	private List<Like> likes_list;
	public void setImagePath(String imagePath){
		this.imagePath = imagePath;
	}
}