package com.NetworkProject.NetworkProject.entities;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import org.springframework.data.repository.cdi.Eager;

import com.NetworkProject.NetworkProject.enums.SharePostWithCondition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String context;
	private String time;
	private String firstName;
	private String lastName;
	private int userId;
	private SharePostWithCondition sharePostWithCondition;
	private boolean ifFileExist;

	@Lob
	@Basic( fetch = FetchType.LAZY )
	private String file0Value;

	@Lob
	@Basic( fetch = FetchType.LAZY )
	private String file1Value;

	@Lob
	@Basic( fetch = FetchType.LAZY )
	private String file2Value;

	@Lob
	@Basic( fetch = FetchType.LAZY )
	private String file3Value;

	@Lob
	@Basic( fetch = FetchType.LAZY )
	private String file4Value;

	@Lob
	@Basic( fetch = FetchType.LAZY )
	private String file5Value;



	@OneToMany(fetch = FetchType.EAGER)
	private List<UserLike> userLikes = new ArrayList<>();


	@OneToMany
	private List<UserComment> userComments = new ArrayList<>();



	
	public List<UserLike> getUserLikes() {
		return userLikes;
	}
	
	public void deleteUserLikesByUserId(int userId){
//		return userLikes.stream().map((u) -> u.getUserId() == userId ?userLikes.remove(u):"");
//		userLikes.forEach((index,u) -> userLikes.remove(index));
	}



	public void setUserLikes(List<UserLike> userLikes) {
		this.userLikes = userLikes;
	}

	
	
	public void addUserLike(UserLike userLike) {
		this.userLikes.add(userLike);
	}

	public void RemoveUserLike(UserLike userLikeId) {
		this.userLikes.remove(userLikeId);
	}



	public List<UserComment> getUserComments() {
		return userComments;
	}


	public void setUserComments(List<UserComment> userComments) {
		this.userComments = userComments;
	}


	public void addUserComment(UserComment userComment) {
		this.userComments.add(userComment);
	}

	public void RemoveUserComment(UserComment userComment) {
		this.userComments.remove(userComment);
	}


	public void RemoveAllCommentPost(List<UserComment> userComments) {
		this.userComments.forEach((u) -> userComments.remove(u));
	}

}
