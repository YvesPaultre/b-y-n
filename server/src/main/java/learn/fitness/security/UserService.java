package learn.fitness.security;

import learn.fitness.data.UserRepository;
import learn.fitness.models.AppUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repository,
                          PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
        ensureAdmin();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = repository.getUserByUsername(username);

        if (appUser == null || !appUser.isEnabled()) {
            throw new UsernameNotFoundException(username + " not found");
        }

        return appUser;
    }

    public AppUser create(String username, String email, String password) {
        validate(username);
        validatePassword(password);
        // validateEmail(email);

        password = encoder.encode(password);

        AppUser appUser = new AppUser(0, username, password, false, List.of("USER"));
        appUser.setEmail(email);

        return repository.add(appUser);
    }

    private void validate(String username) {
        if (username == null || username.isBlank()) {
            throw new ValidationException("username is required");
        }

        if (username.length() > 50) {
            throw new ValidationException("username must be less than 50 characters");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new ValidationException("password must be at least 8 characters");
        }

        int digits = 0;
        int letters = 0;
        int others = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                digits++;
            } else if (Character.isLetter(c)) {
                letters++;
            } else {
                others++;
            }
        }

        if (digits == 0 || letters == 0 || others == 0) {
            throw new ValidationException("password must contain a digit, a letter, and a non-digit/non-letter");
        }
    }

    private void ensureAdmin() {

        AppUser user = repository.getUserByUsername("admin");

        if (user == null) {
            System.out.println("Creating Admin");

            String randomPassword = UUID.randomUUID().toString();
//            String randomPassword = "testing";
            System.out.println("randomPassword: "+ randomPassword);

            user = new AppUser(0, "admin", encoder.encode(randomPassword), false, List.of("USER", "ADMIN"));
            user.setEmail("test@example.com");

            try {
                repository.add(user);
//                System.out.printf("%n%nRandom admin password: %s%n%n", randomPassword);
            } catch (ValidationException ex) {
                ex.printStackTrace();
            }
        }
    }
}
