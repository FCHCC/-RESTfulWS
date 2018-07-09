package me.jmll.utm.repository;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import me.jmll.utm.model.User;

	@Repository("userRepository")
	public class UserRepositoryImpl implements UserRepository {
		private static Map<String, User> userDB = new Hashtable<>();
		private static final Logger log = LogManager.getLogger();
		static {
			userDB.put("anakin", new User("anakin", "skywalker", "Anakin Skywalker"));
			userDB.put("obiwan", new User("obiwan", "kenobi", "Obi-Wan Kenobi"));
			userDB.put("mace", new User("mace", "windu", "Mace Windu"));
			userDB.put("shaak", new User("shaak", "ti", "Shaak Ti"));
		}
	
		@Override
		public List<User> getUsers() {
			List<User> userList = new ArrayList<User>();
			userList.addAll(userDB.values());
			return userList;
		}
	
		@Override
		public User getUser(String username) {
			return userDB.get(username);
		}
	
		@Override
		public User createUser(String username, String password, String fullName) {
			userDB.put(username, new User(username, password, fullName));
			return userDB.get(username);
		}
	
		@Override
		public boolean login(String username, String password) {
			log.debug("Trying to authenticate {}, {}", username, password);
			if (userDB.containsKey(username)) {
				User user = userDB.get(username);
				if (user.getPassword().equals(password)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

		@Override
		public void deleteUser(String username) {
			userDB.remove(username);
		}

		@Override
		public User updateUser(User user) {
			userDB.put(user.getUsername(), user);
			return user;
		}
	}
