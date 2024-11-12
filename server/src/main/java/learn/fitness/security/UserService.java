package learn.fitness.security;

import learn.fitness.data.UserRepository;
import learn.fitness.models.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repository, PasswordEncoder encoder){
        this.repository = repository;
        this.encoder = encoder;
        ensureAdmin();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = repository.getUserByUsername(username);

        if(appUser == null){
            throw new UsernameNotFoundException(username + " not found.");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if(appUser.isAdmin()){
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new User(appUser.getUsername(), appUser.getPassword(), authorities);
    }

    public AppUser add(AppUser user){
        validate(user);
        user.setPassword(encoder.encode(user.getPassword()));
        return repository.add(user);
    }

    private void ensureAdmin(){
        AppUser appUser = repository.getUserByUsername("admin");

        if(appUser == null){
            String randomPassword = UUID.randomUUID().toString();

            appUser = new AppUser();
            appUser.setUsername("admin");
            appUser.setPassword(randomPassword);
            appUser.setAdmin(true);

            try {
                repository.add(appUser);
                System.out.printf("%n%nRandom admin password: %s%n%n", randomPassword);
            } catch (ValidationException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void validate(AppUser user){

        if (user == null) {
            throw new ValidationException("user cannot be null");
        }

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new ValidationException("username is required");
        }

        if (user.getUsername().length() > 50) {
            throw new ValidationException("username must be less than 50 characters");
        }
    }

}
