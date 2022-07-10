package com.NetworkProject.NetworkProject.services;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NetworkProject.NetworkProject.entities.Message;
import com.NetworkProject.NetworkProject.entities.MessageNotif;
import com.NetworkProject.NetworkProject.entities.Post;
import com.NetworkProject.NetworkProject.entities.User;
import com.NetworkProject.NetworkProject.entities.UserComment;
import com.NetworkProject.NetworkProject.entities.UserFriends;
import com.NetworkProject.NetworkProject.entities.UserLike;
import com.NetworkProject.NetworkProject.entities.UserNotification;
import com.NetworkProject.NetworkProject.entities.UserVsPosts;
import com.NetworkProject.NetworkProject.entities.WritingSocket;
import com.NetworkProject.NetworkProject.execptions.AppMExeption;
import com.NetworkProject.NetworkProject.execptions.ErrorMessages;
import com.NetworkProject.NetworkProject.repository.MessageNotifRepository;
import com.NetworkProject.NetworkProject.repository.MessageRepository;
import com.NetworkProject.NetworkProject.repository.PostRepository;
import com.NetworkProject.NetworkProject.repository.UserCommentRepository;
import com.NetworkProject.NetworkProject.repository.UserFriendRepository;
import com.NetworkProject.NetworkProject.repository.UserLikeRepository;
import com.NetworkProject.NetworkProject.repository.UserNotificationRepository;
import com.NetworkProject.NetworkProject.repository.UserRepository;
import com.NetworkProject.NetworkProject.repository.UserVsPostsRepository;
import com.NetworkProject.NetworkProject.repository.WritingSocketRepository;
import com.NetworkProject.NetworkProject.requstEntities.RequestsIfFriends;
import com.NetworkProject.NetworkProject.requstEntities.RequestsNotifications;
import com.NetworkProject.NetworkProject.security.TokenManager;


@Service
@Transactional
public class UserService {

	

	@Autowired
	private UserRepository userRepository;


	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserFriendRepository userFriendRepository;

	@Autowired
	private UserNotificationRepository userNotificationRepository;

	@Autowired
	private UserLikeRepository userLikeRepository;

	@Autowired
	private UserCommentRepository userCommentRepository;

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private MessageNotifRepository messageNotifRepository;

	@Autowired
	private WritingSocketRepository writingSocketRepository;

	@Autowired
	private UserVsPostsRepository userVsPostsRepository;




	public User GetUserById(int id) {
		User user = userRepository.getById(id);
		return user;
	}


	public User Login(String userName, String password) throws Exception {
		if(userName != null && password != null) {
			User user = userRepository.findByUserNameAndPassword(userName, password);
			if(user != null) {
				System.out.println("user loggged: " + user);
				return user;
			}
			throw new AppMExeption(ErrorMessages.NO_USER_FOUND);
		}
		throw new AppMExeption(ErrorMessages.INVALID_INPUT);
	}


	
	public User getOtherUser(int otherUserId) throws AppMExeption {
		if(otherUserId > 0) {
			User otherUser = userRepository.findById(otherUserId);
			System.out.println("getting other user "+ otherUser);
			if(otherUser != null) {
				return otherUser;
			}
			throw new AppMExeption(ErrorMessages.NO_USER_FOUND);
		}
		throw new AppMExeption(ErrorMessages.INVALID_INPUT);
	}



	public List<UserNotification> getNotifictionForUser(int userId) throws AppMExeption {
		if(userId > 0) {
			return userNotificationRepository.getByFriendId(userId);
		}
		throw new AppMExeption(ErrorMessages.INVALID_INPUT);
	}

	public void markNotificationsAsRead(int userId) {
//		userNotificationRepository.getByFriendId(userId).forEach((i,n) -> n.setIfSeen("yes"));
	}
	


	public List<Message> getFirstTimeMessagesNotif(int userId) throws AppMExeption{
		if(userId > 0) {
			List<Message> messagesNotifReverse = messageRepository.findByReceiverId(userId);
			return messagesNotifReverse;
		}
		throw new AppMExeption(ErrorMessages.INVALID_INPUT);
	}
	

	public UserFriends addFriendWithWebSocket(RequestsIfFriends requestsIfFriends) throws AppMExeption{
		if(requestsIfFriends.getUserId() > 0 && requestsIfFriends.getFriendId() > 0) {
			User user = userRepository.findById(requestsIfFriends.getUserId());
			User friend = userRepository.findById(requestsIfFriends.getFriendId());
			if(user != null && friend != null) {
				userFriendRepository.save(UserFriends.builder().userId(user.getId()).friendId(friend.getId()).accepted("waiting").build());
				userFriendRepository.save(UserFriends.builder().userId(friend.getId()).friendId(user.getId()).accepted("waiting").build());
				UserFriends userFriendReturn = userFriendRepository.findByUserIdAndFriendId(user.getId(), friend.getId());
				System.out.println("getting notification with webScoket: " + userFriendReturn);
				return userFriendReturn;
			}
			throw new AppMExeption(ErrorMessages.NO_DATA_FOUND);
		}
		throw new AppMExeption(ErrorMessages.INVALID_INPUT);
	}
	
	
	public UserFriends removeFriendWithWebSocket(RequestsIfFriends requestsIfFriends) throws AppMExeption{
		if(requestsIfFriends.getUserId() > 0 && requestsIfFriends.getFriendId() != 0) {
			User user = userRepository.findById(requestsIfFriends.getUserId());
			User friend = userRepository.findById(requestsIfFriends.getFriendId());
				if(user != null && friend != null) {
					userFriendRepository.deleteByUserId(user.getId());
					userFriendRepository.deleteByUserId(friend.getId());
					System.out.println("removed friendship between : " + user.getId() +" : " + friend.getId());
					UserFriends userFriendReturn = new UserFriends();
					return userFriendReturn;
				}
			throw new AppMExeption(ErrorMessages.NO_DATA_FOUND);
		}
		throw new AppMExeption(ErrorMessages.INVALID_INPUT);
	}




	public List<Message> getMessages( Message message) throws AppMExeption{
		if(message.getSenderId() > 0 && message.getReceiverId() > 0 && message.getContext() != null) {
			User userSender = userRepository.findById(message.getSenderId());
			User userReceiver = userRepository.findById(message.getReceiverId());
			if(userSender != null && userReceiver != null) {
				LocalDateTime localDateTime = LocalDateTime.now();
				messageRepository.save(Message.builder().timeDate(localDateTime).senderFullName(userSender.getFirstName() +" "+ userSender.getLastName())
						.receiverFullName(userReceiver.getFirstName()+" "+ userReceiver.getLastName())
						.seen("no").receiverId(userReceiver.getId()).senderId(userSender.getId())
						.context(message.getContext()).postContext(message.getPostContext()).ifPostExist(message.isIfPostExist())
						.postValue(message.getPostValue()).build());
				List<Message> senderMessages = messageRepository.findByReceiverIdAndSenderId(message.getReceiverId(), message.getSenderId());
				List<Message> recevierMessages = messageRepository.findByReceiverIdAndSenderId(message.getSenderId(),message.getReceiverId());
				senderMessages.addAll(recevierMessages);
				return sortMessageList(senderMessages);
			}
			throw new AppMExeption(ErrorMessages.NO_DATA_FOUND);
		}
		throw new AppMExeption(ErrorMessages.INVALID_INPUT);
	}



	public List<Message> getFirstTimeMessages(int senderId, int receiverId) throws AppMExeption{
		if(senderId  > 0 && receiverId > 0) {
			List<Message> senderMessages = messageRepository.findByReceiverIdAndSenderId(receiverId, senderId);
			List<Message> recevierMessages = messageRepository.findByReceiverIdAndSenderId(senderId, receiverId);
			senderMessages.addAll(recevierMessages);
			return sortMessageList(senderMessages);
		}
		throw new AppMExeption(ErrorMessages.INVALID_INPUT);
	}
	

	
	public List<Message> sortMessageList(List<Message> messages){
		Collections.sort(messages,new Comparator<Message>() {
			@Override
			public int compare(Message msg1, Message msg2) {
				
				return msg1.getTimeDate().compareTo(msg2.getTimeDate());
			}
		});
		return messages;
	}


	
	public List<User> getChatUserFriendOnline(int userId) throws AppMExeption{
		if(userId > 0) {
			User user = userRepository.findById(userId);
			if(user != null) {
				List<UserFriends> userFriendsMessages = userFriendRepository.findByUserId(user.getId());
				List<User> returnFriends = new ArrayList<>();		   
				for(UserFriends u : userFriendsMessages) {
					returnFriends.add(userRepository.findById(u.getFriendId()));
				}
				return returnFriends;
			}
			throw new AppMExeption(ErrorMessages.NO_DATA_FOUND);
		}
		throw new AppMExeption(ErrorMessages.INVALID_INPUT);
	}

	
	public UserFriends ifFriendShipWebSocket(int userId, int friendId) throws AppMExeption {
		if(userId > 0 && friendId > 0) {
			User user = userRepository.findById(userId);
			User friend = userRepository.findById(friendId);
			if(user != null && friend != null ) {
				UserFriends ifFriends = userFriendRepository.findByUserIdAndFriendId(user.getId(), friend.getId());
				if(ifFriends != null ) {
					System.out.println("checking if friends: " +ifFriends);
					return ifFriends;
				}
				System.out.println("checking if friends: null");
				return null;
			}
			throw new AppMExeption(ErrorMessages.NO_DATA_FOUND);
		}
		throw new AppMExeption(ErrorMessages.INVALID_INPUT);
	}



	public List<User> getShareUserFriend(int userId) throws AppMExeption{
		if(userId > 0) {
			User user = userRepository.findById(userId);
			if(user != null) {
				List<User> usersReturn = new ArrayList<>();
				List<UserFriends> userFriends = userFriendRepository.findByUserId(user.getId());
				userFriends.forEach((u) -> usersReturn.add(userRepository.findById(u.getFriendId())));
				return usersReturn;
			}
			throw new AppMExeption(ErrorMessages.NO_DATA_FOUND);
		}
		throw new AppMExeption(ErrorMessages.INVALID_INPUT);
	}

	
	
	public List<Post> getUserPosts(int userId) throws AppMExeption{
		if ( userId > 0 ) {
			User user = userRepository.findById(userId);
			if (user != null) {
				List<Post> posts = new ArrayList<>();
				List<UserVsPosts> usersVSposts =  userVsPostsRepository.findByUserId(userId);
				Collections.reverse(posts);
				usersVSposts.forEach((p) -> posts.add(p.getPost()));
				return posts;
			}
			throw new AppMExeption(ErrorMessages.NO_USER_FOUND);
		}
	throw new AppMExeption(ErrorMessages.NO_USER_FOUND);
	}




	public List<User> getTagFriendsList(){
		return userRepository.findAll();
	}
	
	public List<User> getUserMutualFriends(int userId) throws AppMExeption{
		if(userId > 0) {
			User user = userRepository.findById(userId);
			if(user != null ) {
				
				
				
				
				return userRepository.findAll();
			}
			throw new AppMExeption(ErrorMessages.NO_DATA_FOUND);
			
		}
		throw new AppMExeption(ErrorMessages.INVALID_INPUT);

	}

	



}

