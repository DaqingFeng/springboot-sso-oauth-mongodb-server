package st.malike.auth.server.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import st.malike.auth.server.model.User;
import st.malike.auth.server.service.UserService;

/**
 * Created by fengdaqing on 2018/4/19.
 */
@Service
public class DomainUserDetailService implements UserDetailsService {

    @Autowired
    private UserAuthConfigService authConfigService;

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findByEmail(s);
        org.springframework.security.core.userdetails.User userDetail = null;
        if (user != null) {
            userDetail = new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    authConfigService.getRights(user));
        } else {
            throw new UsernameNotFoundException(s);
        }
        return userDetail;
    }
}
