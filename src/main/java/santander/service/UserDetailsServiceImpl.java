package santander.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import santander.model.user.User;
import santander.repository.UserJpaRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserJpaRepository userJpaRepository;

    public UserDetails loadUserByUsernameOrEmail(String name, String email) throws UsernameNotFoundException {

        UserDetails userDetails = null;
        if (null != email) {
            User user = (User) userJpaRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));
            userDetails = UserDetailsImpl.build(user);
        } else if (null != name) {
            userDetails =  loadUserByUsername(name);
        }
        return userDetails;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = (User) userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }
}