package com.NetworkProject.NetworkProject.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NetworkProject.NetworkProject.entities.Message;
import com.NetworkProject.NetworkProject.entities.Post;
import com.NetworkProject.NetworkProject.entities.User;
import com.NetworkProject.NetworkProject.entities.UserComment;
import com.NetworkProject.NetworkProject.entities.WritingSocket;
import com.NetworkProject.NetworkProject.enums.ClientType;
import com.NetworkProject.NetworkProject.execptions.AppMExeption;
import com.NetworkProject.NetworkProject.execptions.ErrorMessages;
import com.NetworkProject.NetworkProject.requstEntities.RequestsAcceptFriendship;
import com.NetworkProject.NetworkProject.requstEntities.RequestsCommentOnPost;
import com.NetworkProject.NetworkProject.requstEntities.RequestsDeleteCommentPost;
import com.NetworkProject.NetworkProject.requstEntities.RequestsDeletePost;
import com.NetworkProject.NetworkProject.requstEntities.RequestsIfFriends;
import com.NetworkProject.NetworkProject.requstEntities.RequestsLikePost;
import com.NetworkProject.NetworkProject.requstEntities.RequestsNewPost;
import com.NetworkProject.NetworkProject.requstEntities.RequestsNotifications;
import com.NetworkProject.NetworkProject.requstEntities.RequestsSharePost;
import com.NetworkProject.NetworkProject.requstEntities.RequestsUnLikePosts;
import com.NetworkProject.NetworkProject.requstEntities.TestRequestPostWithFile;
import com.NetworkProject.NetworkProject.security.LoginRequest;
import com.NetworkProject.NetworkProject.security.LoginResponse;
import com.NetworkProject.NetworkProject.security.TokenManager;
import com.NetworkProject.NetworkProject.services.PostService;
import com.NetworkProject.NetworkProject.services.UserService;



@RestController
@RequestMapping("user")
@CrossOrigin(origins = "*", maxAge = 3600,allowedHeaders = "*")
public class UserController {


	@Autowired
	private TokenManager tokenManager;
	
	@Autowired
	private UserService userService;

	@Autowired
	private PostService postService;


	@PostMapping("login")
	public ResponseEntity<?> userLogin(@RequestBody LoginRequest loginRequest) throws Exception{
		User userCheck = userService.Login(loginRequest.getUserName(), loginRequest.getPassword());
		String token = tokenManager.createToken(ClientType.USER, userCheck.getId());
		return new ResponseEntity<>(new LoginResponse(userCheck.getId(), userCheck.getFirstName(), userCheck.getLastName(), userCheck.getUserName(), token, ClientType.USER), HttpStatus.OK);
	}


	@PostMapping("logout")
	public ResponseEntity<?> userLogout(@RequestBody User user ,  @RequestHeader("authorization") String token){
		tokenManager.removeToken(token);
		System.out.println("user logout: " + user);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

	@GetMapping("get-user/{userId}")
	public ResponseEntity<?> getUserById(@PathVariable int userId, @RequestHeader("authorization") String token){
		return new ResponseEntity<>(userService.GetUserById(userId),HttpStatus.OK);
	}


	@GetMapping("posts/{id}")
	public ResponseEntity<?> getUserPosts(@PathVariable int id ,@RequestHeader("authorization") String token) throws AppMExeption{
			return new ResponseEntity<>(userService.getUserPosts(id),HttpStatus.OK);
	}


	@GetMapping("post/{id}")
	public ResponseEntity<?> getOnePost(@PathVariable int id,@RequestHeader("authorization") String token){
		return new ResponseEntity<>(postService.getPostById(id),HttpStatus.OK);
	}
	


	@GetMapping("home-posts/{id}")
	public ResponseEntity<?> getMainPagePosts(@PathVariable int id,@RequestHeader("authorization") String token) throws AppMExeption{
		List<Post> reversePost = postService.getPostByUserId(id);
		return new ResponseEntity<>(reversePost,HttpStatus.OK);
	}

	@GetMapping("get-other-user/{otherUserId}")
	public ResponseEntity<?> getOtherUser(@PathVariable int otherUserId, @RequestHeader("authorization") String token) throws AppMExeption{
		User otherUser = userService.getOtherUser(otherUserId);
		return new ResponseEntity<>(otherUser,HttpStatus.OK);

	}


	@GetMapping("get-notifications/{userId}")
	public ResponseEntity<?> getNotifications(@PathVariable int userId, @RequestHeader("authorization") String token) throws AppMExeption{
		return new ResponseEntity<>(userService.getNotifictionForUser(userId),HttpStatus.OK);
	}

	@PutMapping("mark-notifications-as-read/{userId}")
	public ResponseEntity<?> markAsRead(@PathVariable int userId, @RequestHeader("authorization") String token){
		userService.markNotificationsAsRead(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@GetMapping("messages/{senderId}/{receiverId}")
	public ResponseEntity<?> getMessages(@PathVariable int senderId, @PathVariable int receiverId, @RequestHeader("authorization") String token) throws AppMExeption{
		return new ResponseEntity<>(userService.getFirstTimeMessages(senderId, receiverId),HttpStatus.OK);
	}

	@GetMapping("chat-userFriends-online/{userId}")
	public ResponseEntity<?> getUserFriendMessages(@PathVariable int userId, @RequestHeader("authorization") String token) throws AppMExeption{
		return new ResponseEntity<>(userService.getChatUserFriendOnline(userId),HttpStatus.OK);
	}

	@GetMapping("first-messages-notification/{userId}")
	public ResponseEntity<?> getFirstTimeMessageNotif(@PathVariable int userId, @RequestHeader("authorization") String token) throws AppMExeption{
		return new ResponseEntity<>(userService.getFirstTimeMessagesNotif(userId),HttpStatus.OK);
	}
	
	@GetMapping("mutualFriends/{userId}")
	public ResponseEntity<?> getMutualFriends(@PathVariable int userId,@RequestHeader("authorization") String token ) throws AppMExeption{
		return new ResponseEntity<>(userService.getUserMutualFriends(userId), HttpStatus.OK);
	}

	@MessageMapping("/posts-websocket")
	@SendTo("/topic/posts")
	public ResponseEntity<?> createNewPostWebSocket( @RequestBody RequestsNewPost requestsNewPost) throws AppMExeption{
		return new ResponseEntity<>(postService.createNewPost(requestsNewPost), HttpStatus.OK);
	}

	@MessageMapping("/delete-posts-websocket")
	@SendTo("/topic/deletePosts")
	public ResponseEntity<?> deletePost(@RequestBody RequestsDeletePost requestsDeletePost) throws AppMExeption{
		return new ResponseEntity<>(postService.deletePost(requestsDeletePost.getUserId(), requestsDeletePost.getPostId()), HttpStatus.OK);
	}

	@MessageMapping("/add-friend-notif-websocket")
	@SendTo("/topic/notifications")
	public ResponseEntity<?> addFreindWithWebSocket(@RequestBody RequestsIfFriends requestsIfFriends) throws AppMExeption{
		return new ResponseEntity<>(userService.addFriendWithWebSocket(requestsIfFriends),HttpStatus.OK);
	}
	
	@MessageMapping("/remove-friend-notif-websocket")
	@SendTo("/topic/notifications")
	public ResponseEntity<?> removeFreindWithWebSocket(@RequestBody RequestsIfFriends requestsIfFriends) throws AppMExeption{
		return new ResponseEntity<>(userService.removeFriendWithWebSocket(requestsIfFriends),HttpStatus.OK);
	}

	@MessageMapping("/messages-websocket")
	@SendTo("/topic/messages")
	public ResponseEntity<?> getMessages(@RequestBody Message message) throws AppMExeption{
		return new ResponseEntity<>(userService.getMessages(message), HttpStatus.OK);
	}

	@MessageMapping("ifFriends-webSocket")
	@SendTo("/topic/ifFriends")
	public ResponseEntity<?> checkIfFriendsWebSocket(@RequestBody RequestsIfFriends requestsIfFriends) throws AppMExeption{
		return new ResponseEntity<>(userService.ifFriendShipWebSocket(requestsIfFriends.getUserId(), requestsIfFriends.getFriendId()), HttpStatus.OK);
	}
	
	@MessageMapping("like-post-websocket")
	@SendTo("/topic/likePost")
	@Transactional
	public ResponseEntity<?> likePostWebSocket(@RequestBody RequestsLikePost requestsLikePost) throws AppMExeption {
		return new ResponseEntity<>(postService.likePostWebSocket(requestsLikePost.getUserId(), requestsLikePost.getPostId()), HttpStatus.OK);
	}

	@MessageMapping("unLike-post-websocket")
	@SendTo("/topic/UnLikePost")
	public ResponseEntity<?> unLikePostWebSocket(@RequestBody RequestsUnLikePosts requestsUnLikePosts) throws AppMExeption {
		return new ResponseEntity<>(postService.unLikePostWebSocket(requestsUnLikePosts.getUserId(),requestsUnLikePosts.getPostId(),requestsUnLikePosts.getUserLikeId()),HttpStatus.OK);
	}


	@MessageMapping("comment-on-post-websocket")
	@SendTo("/topic/CommentOnPost")
	public ResponseEntity<?> commentOnPostWebSocket(@RequestBody RequestsCommentOnPost requestsCommentOnPost) throws AppMExeption {
		return new ResponseEntity<>(postService.commentOnPostWebSocket(requestsCommentOnPost.getUserId(), requestsCommentOnPost.getPostId(), requestsCommentOnPost.getContext()),HttpStatus.OK);
	}

	@MessageMapping("delete-comment-on-post-websocket")
	@SendTo("/topic/DeleteCommentOnPost")
	public ResponseEntity<?> deleteCommentOnPost(@RequestBody RequestsDeleteCommentPost requestsDeleteCommentPost) throws AppMExeption{
		return new ResponseEntity<>(postService.deleteCommentPostWebSocket(requestsDeleteCommentPost.getPostId(), requestsDeleteCommentPost.getCommentId(),requestsDeleteCommentPost.getUserId()),HttpStatus.OK);
	}

	@GetMapping("shareList-userFriends/{userId}")
	public ResponseEntity<?> getShareUserFriend(@PathVariable int userId, @RequestHeader("authorization") String token) throws AppMExeption{
		return new ResponseEntity<>(userService.getShareUserFriend(userId),HttpStatus.OK);
	}
	
	@PostMapping("test-file")
	public ResponseEntity<?> testSavePostWithFile(@RequestBody TestRequestPostWithFile testRequestPostWithFile,@RequestHeader("authorization") String token){
		System.out.println(testRequestPostWithFile);
		return new ResponseEntity<>("",HttpStatus.OK);
	}


	@GetMapping("tag-friends-list")
	public ResponseEntity<?> getTagFriendsList(@RequestHeader("authorization") String token){
		return new ResponseEntity<>(userService.getTagFriendsList(),HttpStatus.OK);
	}

}
