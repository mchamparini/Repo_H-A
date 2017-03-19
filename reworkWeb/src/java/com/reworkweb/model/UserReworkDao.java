/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reworkweb.model;

import com.reworkweb.entities.UserRework;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mchamparini
 */
@Service
public class UserReworkDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional(rollbackFor = {ServicioException.class})

    public void create(UserRework user) {
        em.persist(user);
    }

    public UserRework getUserRework(String nameUs, String passwordUs) {
        String query = "select u from UserRework u where u.nameUs=:nameUs and u.passwordUs=:passwordUs";        
        Query q = em.createQuery(query);        
        q.setParameter("nameUs", nameUs);
        q.setParameter("passwordUs", passwordUs);
        try {
             UserRework us = (UserRework) q.getSingleResult();
             return us;
        } catch (Exception e) {
            return null;
        }
       
    }
} 