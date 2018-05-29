package com.postgredemo.dao.impl;

import com.postgredemo.dao.UserDao;
import com.postgredemo.entity.User;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class UserDaoImpl implements UserDao {

  @Autowired
  private SessionFactory sessionFactory;

  @Override
  public void add(User user) {
    sessionFactory.getCurrentSession().save(user);
  }

  @Override
  public List<User> listUsers() {
    @SuppressWarnings("unchecked")
    TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
    return query.getResultList();
  }

  @Override
  public List<String> getUsersByESP(String name) {
    @SuppressWarnings("unchecked")
    String query = "select jsonb_pretty(u.userinfo) " +
        " from users u " +
        " where u.userinfo ->> 'email' like '%" + name + "'";
    Session session = sessionFactory.openSession();
    List<String> resultlist = session.createNativeQuery(query).getResultList();
    session.close();
    return resultlist;
  }

  @Override
  public List<String> getAdults() {
    @SuppressWarnings("unchecked")
    String query = "select jsonb_pretty(u.userinfo) " +
        " from users u " +
        " where u.userinfo ->> 'age' >= '18' ";
    Session session = sessionFactory.openSession();
    List<String> resultlist = session.createNativeQuery(query).getResultList();
    session.close();
    return resultlist;
  }
}