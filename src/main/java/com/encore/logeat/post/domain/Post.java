package com.encore.logeat.post.domain;

import com.encore.logeat.common.entity.BaseTimeEntity;
import com.encore.logeat.like.domain.Like;
import com.encore.logeat.user.domain.User;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
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
	@Column(length = 4000)
	private String location;
	private String category;
	@Builder.Default
	private int viewCount = 0;
	@Builder.Default
	@Column(name = "secret_y_or_n")
	private String secretYorN = "N"; // 공개 여부
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
	private List<PostLikeReport> likeReports;
	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
	private List<Like> likes;


	@Builder.Default
	private int likeCount=0;

	public void addLikeCount(){this.likeCount+=1;}
	public void reduceLikeCount(){this.likeCount-=1;}
	public void updatePost(String title, String contents, String location, String category, String SecretYn){
        this.title = title;
        this.contents = contents;
        this.location = location;
        this.category = category;
		if(SecretYn.equals("N"))
			this.secretYorN = "N";
		else this.secretYorN = "Y";
    }
	public void setSecret(String secretYorN){
		this.secretYorN = secretYorN;
	}
}