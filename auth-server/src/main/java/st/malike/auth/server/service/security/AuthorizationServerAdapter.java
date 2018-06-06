package st.malike.auth.server.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;

/**
 * Created by fengdaqing on 2018/4/18.
 */
@Configuration
@EnableAuthorizationServer
@Order(3)
public class AuthorizationServerAdapter extends AuthorizationServerConfigurerAdapter {

    @Value("${app.client.id}")
    private String clientId;

    @Value("${app.client.secret}")
    private String secret;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    public TokenStoreService tokenStore;

    @Autowired
    private ClientDetailService clientDetailsService;

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        endpoints.tokenStore(tokenStore)
                .tokenEnhancer(tokenEnhancerChain)
                .authenticationManager(authenticationManager);
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore);
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Bean
    public CheckTokenEndpoint checkTokenEndpoint() {
        final CheckTokenEndpoint defaultTokenEndpoint = new CheckTokenEndpoint(tokenServices());
        return defaultTokenEndpoint;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
        clients.inMemory()
                .withClient(clientId)
                .secret(secret)
                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
                .scopes("app")
                .accessTokenValiditySeconds(60 * 30) // 30min
                .refreshTokenValiditySeconds(60 * 60 * 24); // 24h
    }
}
