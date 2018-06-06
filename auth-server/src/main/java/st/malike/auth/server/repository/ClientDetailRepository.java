/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.malike.auth.server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import st.malike.auth.server.model.ClientDetail;

import java.io.Serializable;

/**
 * @author malike_st
 */
public interface ClientDetailRepository extends MongoRepository<ClientDetail, Serializable> {

    ClientDetail findByClientId(String clientId);

}
