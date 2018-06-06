package st.malike.auth.server.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by fengdaqing on 2018/4/20.
 */
public class ClientAuthenticationToken  extends AbstractAuthenticationToken {

    private final Object principal;
    private final Object credentials;
    private final boolean client;

    public ClientAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.client = true;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public boolean isClient() {
        return client;
    }

}
