package com.encore.logeat.user.domain;



import com.encore.logeat.common.entity.BaseTimeEntity;
import com.encore.logeat.follow.domain.Follow;
import com.encore.logeat.post.domain.Post;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true, nullable = false)
	private String email;
	@Column(unique = true, nullable = false, length = 8)
	private String nickname;
	@Column(nullable = false)
	private String password;
	@Column(name = "profile_image_path")
	private String profileImagePath;
	@Column(length = 31)
	private String introduce;

	@Enumerated(EnumType.STRING)
	private Role role;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Post> postList;

	@OneToMany(mappedBy = "following",fetch = FetchType.LAZY)
	private List<Follow> followerList; // 유저를 팔로우

	@OneToMany(mappedBy = "follower",fetch = FetchType.LAZY)
	private List<Follow> followingList; // 유저가 팔로우



	public void userUpdatedPassword(String password) {
		this.password = password;
	}

}
