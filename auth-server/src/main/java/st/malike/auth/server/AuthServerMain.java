/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.malike.auth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author malike_st
 */
@EnableMongoRepositories("st.malike.auth.server.repository")
@SpringBootApplication
public class AuthServerMain {
    public static void main(String[] args) {
        SpringApplication.run(AuthServerMain.class, args);
    }
}
