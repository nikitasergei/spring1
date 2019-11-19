package by.nikita.sergei.service;

import by.nikita.sergei.entity.Roles;
import by.nikita.sergei.entity.User;
import by.nikita.sergei.entity.WishList;
import by.nikita.sergei.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    MailSenderService mailSenderService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    WishListService wishListService;

    /**
     * This method check is user with @param username already exists and load them.
     *
     * @param username - name of User which we try to load
     * @return user with @param username
     * @throws UsernameNotFoundException - if user with @param username dose not exist
     */
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        return user;
    }

    /**
     * This method check is @param user exists as record in table add @param user if not
     *
     * @param user - user which want to add
     * @return false if @param user already exists and true if was added @param user
     */

    public boolean addUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return false;
        } else {
            user.setActive(false);
            user.setRoles(Collections.singleton(Roles.USER));
            user.setActivationCode(UUID.randomUUID().toString());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setWishList(new WishList());
            user.getWishList().setUser(user);
            wishListService.saveWishList(user.getWishList());
            sendMessage(user);
            return true;
        }
    }

    /**
     * Method send @param user mail with activation link
     *
     * @param user - message recipient
     */
    private void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Baller's Store. Please, visit next link: " + "http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            mailSenderService.send(user.getEmail(), "Activation Code!", message);
        }
    }

    /**
     * Method try to find @param code in table and if @param code was found activate user
     *
     * @param code - code for activate user from his email
     * @return false if records in table hasn't @param code and true if user was activated
     */
    public boolean activateUser(String code) {
        User userByCode = userRepo.findByActivationCode(code);
        if (userByCode == null) {
            return false;
        } else {
            userByCode.setActivationCode(null);
            userByCode.setActive(true);
            userRepo.save(userByCode);
            return true;
        }
    }

    /**
     * Method @return all users as list
     *
     * @return sorted list of users
     */
    public List<User> findAllUsers() {
        return userRepo.findAll(Sort.by("username"));
    }

    /**
     * @param user     - user to save
     * @param username - username for set to @param user
     * @param form     map of user's roles
     */
    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);
        Set<String> roles = Arrays.stream(Roles.values())
                .map(Roles::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Roles.valueOf(key));
            }
        }
        userRepo.save(user);
    }

    public void updateUserWishList(User user) {
        userRepo.save(user);
    }

    /**
     * Method update current user if some @param was changed, and send new mail if @param email was changed
     *
     * @param user     - user to update
     * @param password - user password
     * @param email    - user email
     * @param filename - name of user's profile picture
     */
    public void updateProfile(User user, String password, String email, String filename) {
        String userEmail = user.getEmail();
        String userFilename = user.getFilename();
        boolean isEmailChanged = (email != null && email.equals(userEmail) || userEmail != null && userEmail.equals(email));

        boolean isFilenameChanged = (filename != null && filename.equals(userFilename) || userFilename != null && userFilename.equals(filename));

        if (isEmailChanged) {
            user.setEmail(email);

            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if (!StringUtils.isEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }

        System.out.println(isFilenameChanged);
        if (!isFilenameChanged) {
            user.setFilename(filename);
        }

        userRepo.save(user);

        if (isEmailChanged) {
            sendMessage(user);
        }
    }
}
