package epaw.lab4.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import epaw.lab4.model.User;
import epaw.lab4.repository.UserRepository;
import jakarta.servlet.http.Part;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class UserService {

    private static UserService instance;
    private UserRepository userRepository;

    private UserService() {
        this.userRepository = UserRepository.getInstance();
    }

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,}$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private static final String ROLE_REGULAR = "regular";
    private static final String ROLE_PROFESSIONAL = "professional";
    private static final String ROLE_ADMIN = "admin";

    public Map<String, String> validate(User user) {
        Map<String, String> errors = new HashMap<>();

        String name = user.getName();
        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "Username cannot be empty.");
        } else if (name.length() < 5 || name.length() > 20) {
            errors.put("name", "Username must be between 5 and 20 characters.");
        } else if (userRepository.existsByUsername(name)) {
            errors.put("name", "Username already exists.");
        }

        String password = user.getPassword();
        if (password == null || !password.matches(PASSWORD_REGEX)) {
            errors.put("password",
                    "Minimum 8 characters, including uppercase, numbers, and a special character (@#$%^&*).");
        }

        Integer age = user.getAge();
        if (age == null) {
            errors.put("age", "Age is required.");
        } else if (age < 13 || age > 120) {
            errors.put("age", "Age must be between 13 and 120.");
        }

        String email = user.getEmail();
        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "Email cannot be empty.");
        } else if (!email.matches(EMAIL_REGEX)) {
            errors.put("email", "Email format is not valid.");
        }

        String role = user.getRole();
        if (role == null || role.trim().isEmpty()) {
            errors.put("role", "Role is required.");
        } else if (!ROLE_REGULAR.equals(role) && !ROLE_PROFESSIONAL.equals(role) && !ROLE_ADMIN.equals(role)) {
            errors.put("role", "Role must be regular, professional, or admin.");
        }

        String biography = user.getBiography();
        if (biography == null || biography.trim().isEmpty()) {
            errors.put("biography", "Biography cannot be empty.");
        } else if (biography.trim().length() < 10 || biography.trim().length() > 500) {
            errors.put("biography", "Biography must be between 10 and 500 characters.");
        }

        return errors;
    }

    public Map<String, String> register(User user) {
        Map<String, String> errors = validate(user);
        if (errors.isEmpty()) {
            userRepository.save(user);
        }
        return errors;
    }

    public Map<String, String> validateProfile(User user) {
        Map<String, String> errors = new HashMap<>();

        String name = user.getName();
        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "Username cannot be empty.");
        } else if (name.length() < 5 || name.length() > 20) {
            errors.put("name", "Username must be between 5 and 20 characters.");
        } else if (userRepository.findByNameExcludeId(name, user.getId()).isPresent()) {
            errors.put("name", "Username already exists.");
        }

        Integer age = user.getAge();
        if (age == null) {
            errors.put("age", "Age is required.");
        } else if (age < 13 || age > 120) {
            errors.put("age", "Age must be between 13 and 120.");
        }

        String email = user.getEmail();
        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "Email cannot be empty.");
        } else if (!email.matches(EMAIL_REGEX)) {
            errors.put("email", "Email format is not valid.");
        }

        String role = user.getRole();
        if (role == null || role.trim().isEmpty()) {
            errors.put("role", "Role is required.");
        } else if (!ROLE_REGULAR.equals(role) && !ROLE_PROFESSIONAL.equals(role) && !ROLE_ADMIN.equals(role)) {
            errors.put("role", "Role must be regular, professional, or admin.");
        }

        String biography = user.getBiography();
        if (biography == null || biography.trim().isEmpty()) {
            errors.put("biography", "Biography cannot be empty.");
        } else if (biography.trim().length() < 10 || biography.trim().length() > 500) {
            errors.put("biography", "Biography must be between 10 and 500 characters.");
        }

        return errors;
    }

    public Map<String, String> updateProfile(User user) {
        Map<String, String> errors = validateProfile(user);
        if (errors.isEmpty()) {
            userRepository.update(user);
        }
        return errors;
    }

    public Map<String, String> login(User user) {
        Map<String, String> errors = new HashMap<>();
        if (!userRepository.checkLogin(user)) {
            errors.put("password", "The combination of name and password does not match in our dataabase");
        }
        return errors;
    }

        // Get all users
    public List<User> getAllUsers() {
    	Optional<List<User>> users = userRepository.findAll();
    	if (users.isPresent())
    	    return users.get();
        return null;
    }
    
    // Get followed users
    public List<User> getFollowedUsers(Integer id, Integer start, Integer end) {
    	Optional<List<User>> users = userRepository.findFollowed(id,start,end);
    	if (users.isPresent())
    	    return users.get();
        return null;
    }
    
    // Get unfollowed users
    public List<User> getNotFollowedUsers(Integer id, Integer start, Integer end) {
    	Optional<List<User>> users = userRepository.findNotFollowed(id,start,end);
    	if (users.isPresent())
    	    return users.get();
        return null;
    }
    
    // Follow User
    public void follow(Integer uid,Integer fid) {
    	userRepository.followUser(uid, fid);
    }
    
    // Unfollow User
    public void unfollow(Integer uid,Integer fid) {
    	userRepository.unfollowUser(uid, fid);
    }

    public void setFeaturedTweet(Integer userId, Integer tweetId) {
        userRepository.setFeaturedTweet(userId, tweetId);
    }

	public User getUserById(Integer id) {
		Optional<User> user = userRepository.findById(id);
		return user.orElse(null);
	}

	public boolean isFollowing(Integer uid, Integer fid) {
		return userRepository.isFollowing(uid, fid);
	}

    public String saveProfilePicture(Part filePart, String username) {
        if (filePart == null || filePart.getSize() <= 0) {
            return null;
        }

        try {
            String fileName = filePart.getSubmittedFileName();
            String extension = fileName.substring(fileName.lastIndexOf("."));
            String newFileName = username + extension;

            String resourcesDir = "EXTERNAL_RESOURCES";
            Files.createDirectories(Paths.get(resourcesDir));

            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, Paths.get(resourcesDir, newFileName), StandardCopyOption.REPLACE_EXISTING);
            }
            return newFileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}