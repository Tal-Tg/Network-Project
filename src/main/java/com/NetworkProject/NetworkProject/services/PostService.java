package com.NetworkProject.NetworkProject.services;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NetworkProject.NetworkProject.entities.Post;
import com.NetworkProject.NetworkProject.entities.User;
import com.NetworkProject.NetworkProject.entities.UserComment;
import com.NetworkProject.NetworkProject.entities.UserFriends;
import com.NetworkProject.NetworkProject.entities.UserLike;
import com.NetworkProject.NetworkProject.entities.UserVsPosts;
import com.NetworkProject.NetworkProject.execptions.AppMExeption;
import com.NetworkProject.NetworkProject.execptions.ErrorMessages;
import com.NetworkProject.NetworkProject.repository.PostRepository;
import com.NetworkProject.NetworkProject.repository.UserCommentRepository;
import com.NetworkProject.NetworkProject.repository.UserFriendRepository;
import com.NetworkProject.NetworkProject.repository.UserLikeRepository;
import com.NetworkProject.NetworkProject.repository.UserRepository;
import com.NetworkProject.NetworkProject.repository.UserVsPostsRepository;
import com.NetworkProject.NetworkProject.requstEntities.RequestsNewPost;



@Service
@Transactional
public class PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserVsPostsRepository userVsPostsRepository;

	@Autowired
	private UserLikeRepository userLikeRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserCommentRepository userCommentRepository;
	
	@Autowired
	private UserFriendRepository userFriendRepository;

	

	public Post getPostById(int postId) {
		if(postId > 0) {
			Post post = postRepository.findById(postId);
			if(post != null) {
				return post;
			}
		}
		return null;
	}

	//start here webSocker

	public List<Post> createNewPost(RequestsNewPost requestsNewPost) throws AppMExeption {
		if(requestsNewPost.getUserId() > 0) {
			User user = userRepository.getById(requestsNewPost.getUserId());
			if(user != null) {
		        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		        LocalDateTime now = LocalDateTime.now();  
				Post postSaveFixed = postRepository.save(Post.builder().
						firstName(user.getFirstName()).
						lastName(user.getLastName()).
						context(requestsNewPost.getContext()).
						time(dtf.format(now)).
						userId(user.getId()).
						file0Value(requestsNewPost.getFiles()[0].isEmpty()?"":requestsNewPost.getFiles()[0]).
						file1Value(requestsNewPost.getFiles()[1].isEmpty()?"":requestsNewPost.getFiles()[1]).
						file2Value(requestsNewPost.getFiles()[2].isEmpty()?"":requestsNewPost.getFiles()[2]).
						file3Value(requestsNewPost.getFiles()[3].isEmpty()?"":requestsNewPost.getFiles()[3]).
						file4Value(requestsNewPost.getFiles()[4].isEmpty()?"":requestsNewPost.getFiles()[4]).
						file5Value(requestsNewPost.getFiles()[4].isEmpty()?"":requestsNewPost.getFiles()[5]).
						sharePostWithCondition(requestsNewPost.getSharePostCondition()).
						build()
						);
				userVsPostsRepository.save(UserVsPosts.builder().user(user).post(postSaveFixed).build());
				return getPostByUserId(user.getId());
			}
			throw new AppMExeption(ErrorMessages.NO_USER_FOUND);
		}
		throw new AppMExeption(ErrorMessages.NO_USER_FOUND);
	}
	
	
	public List<Post> deletePost(int userId, int postId) throws AppMExeption{
		if(userId > 0 && postId > 0) {
			User user = userRepository.findById(userId);
			Post post = postRepository.findById(postId);
			if(user != null && post != null) {
				post.deleteUserLikesByUserId(userId);
				userVsPostsRepository.delete(userVsPostsRepository.findByUserIdAndPostId(user.getId(), post.getId()));
				postRepository.delete(post);
				return getPostByUserId(user.getId());
			}
			throw new AppMExeption(ErrorMessages.NO_DATA_FOUND);
		}
		throw new AppMExeption(ErrorMessages.INVALID_INPUT);
	}
	
	
	public List<Post> likePostWebSocket(int userId, int postId) throws AppMExeption {
		if(userId > 0 && postId > 0) {
			User user = userRepository.findById(userId);
			Post post = postRepository.findById(postId);
			if(user != null && post != null) {
				UserLike userLike = UserLike.builder()
						.userId(user.getId())
						.firstName(user.getFirstName())
						.lastName(user.getLastName())
						.password(user.getPassword())
						.type(user.getType())
						.userName(user.getUserName())
						.build();
				post.addUserLike(userLikeRepository.save(userLike));
				postRepository.save(post);
				return getPostByUserId(user.getId());
			}
			throw new AppMExeption(ErrorMessages.INVALID_INPUT);
		}
		throw new AppMExeption(ErrorMessages.INVALID_INPUT);
	}
	

	public List<Post> unLikePostWebSocket(int userId,int postId,int userLikeId) throws AppMExeption {
		if(userId > 0 && postId > 0 && userLikeId > 0) {
			Post post = postRepository.findById(postId);
			User user = userRepository.findById(userId);
			System.out.println(post);
			if(post != null && user != null) {
				UserLike userLikeSaved = userLikeRepository.findByUserId(userLikeId);
				System.out.println(userLikeSaved);
				post.RemoveUserLike(userLikeSaved);
				userLikeRepository.delete(userLikeSaved);
				return getPostByUserId(user.getId());
			}
			throw new AppMExeption(ErrorMessages.NO_DATA_FOUND);
		}
		throw new AppMExeption(ErrorMessages.INVALID_INPUT);
	}

	
	
	public List<Post> commentOnPostWebSocket(int userId, int postId,String commentContext) throws AppMExeption {
		if(userId > 0 && postId > 0 && commentContext != null) {
			User user = userRepository.findById(userId);
			Post post = postRepository.findById(postId);
			if(user != null && post != null) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				Date date = new Date();
				post.addUserComment(userCommentRepository.save(UserComment.builder()
						.userId(user.getId())
						.firstName(user.getFirstName())
						.lastName(user.getLastName())
						.userName(user.getUserName()).
						password(user.getPassword())
						.type(user.getType())
						.context(commentContext)
						.time(formatter.format(date))
						.build()));
				return getPostByUserId(user.getId());
			}
			throw new AppMExeption(ErrorMessages.NO_USER_FOUND);
		}
		throw new AppMExeption(ErrorMessages.INVALID_INPUT);
	}


	
	public List<Post> deleteCommentPostWebSocket(int postId,int userCommentId,int userId) throws AppMExeption {
		if(postId > 0 && userCommentId > 0) {
			Post post = postRepository.findById(postId);
			UserComment userComment = userCommentRepository.findById(userCommentId);
			if(post != null && userComment != null) {
				return getPostByUserId(userId);
			}
			throw new AppMExeption(ErrorMessages.NO_DATA_FOUND);	
		}
		throw new AppMExeption(ErrorMessages.INVALID_INPUT);
	}
	
	
	
	public List<Post> getPostByUserId(int userId) throws AppMExeption{
		if(userId > 0) {
			User user = userRepository.findById(userId);
			if(user != null) {
				List<Post> postReturn = new ArrayList<>();
				List<UserFriends> userFriends = userFriendRepository.findByUserId(userId);
				if(userFriends.size() > 0) {
					userFriends.forEach((u) -> postReturn.addAll(postRepository.findByUserId(u.getFriendId())));
				}
				postReturn.addAll(postRepository.findByUserId(user.getId()));
				Collections.sort(postReturn, new Comparator<Post>() {
					@Override
					public int compare(Post post1, Post post2) {
						return post1.getTime().compareTo(post2.getTime());
					}
				});
				Collections.reverse(postReturn);
				System.out.println("getting posts for user: " + userId +".\n" + postReturn );
				return postReturn;
			}
			throw new AppMExeption(ErrorMessages.NO_DATA_FOUND);
			}
		throw new AppMExeption(ErrorMessages.INVALID_INPUT);
	}

	
	
	public List<Post> getFriendPosts(int otherUserId){
		List<UserVsPosts> userVsPosts = userVsPostsRepository.findByUserId(otherUserId);
		List<Post> posts = new ArrayList<>();
		userVsPosts.forEach((p) -> posts.add(p.getPost()));
		System.out.println("getting other user posts: "+ posts);
		return posts;
	}


}
