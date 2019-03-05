/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.malike.auth.server.service;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import st.malike.auth.server.AuthServerMain;
import st.malike.auth.server.model.ClientDetail;
import st.malike.auth.server.model.User;
import st.malike.auth.server.service.security.CustomClientDetailsService;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @author malike_st
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AuthServerMain.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomClientDetailsService clientDetailService;


    private ClientDetail authClient;

    @Value("${app.client.id}")
    private String authClientId;

    @Value("${app.client.secret}")
    private String authClientSecret;

    @Autowired
    private PasswordEncoder passwordEncoder;

    static PodamFactory podamFactory;

    @Before
    public void setUp() {

        podamFactory = new PodamFactoryImpl();

        //userService.deleteAll();
        clientDetailService.deleteAll();

        User awesomeUser = new User();
        awesomeUser.setEmail("user@awesome.com");
        awesomeUser.setPassword(passwordEncoder.encode("123456"));
        awesomeUser.setId("thisis-awesome-1");
        List<String> right=new ArrayList<>();
        right.add("Admin");
        right.add("System");
        awesomeUser.setRights(right);

        userService.save(awesomeUser);

        authClient = new ClientDetail();
        authClient.setId(RandomStringUtils.randomAlphanumeric(10));
        authClient.setClientId(authClientId);
        authClient.setResourceIds(new HashSet<>(Arrays.asList("rest_api")));
        authClient.setClientSecret(passwordEncoder.encode(authClientSecret));
        authClient.setRefreshTokenValiditySeconds(4500);
        authClient.setAccessTokenValiditySeconds(4500);
        authClient.setAuthorities(new HashSet<>(Arrays.asList("trust", "read", "write")));
        authClient.setAuthorizedGrantTypes(new HashSet<>(Arrays.asList("client_credentials", "authorization_code", "implicit", "password", "refresh_token")));
        authClient.setScope(new HashSet<>(Arrays.asList("trust", "read", "write")));
        authClient.setSecretRequired(true);

        clientDetailService.save(authClient);
    }

    @Test
    public void checkingAWESOME() {
        System.out.print("Checking");
    }

    @Test
    public void EncodingPassword() {
     String password=   passwordEncoder.encode("123456");
    }

}
