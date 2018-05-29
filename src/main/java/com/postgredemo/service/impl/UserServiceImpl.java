package com.postgredemo.service.impl;

import com.postgredemo.dao.UserDao;
import com.postgredemo.entity.User;
import com.postgredemo.service.UserService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserDao userDao;

  @Transactional
  @Override
  public void add(User user) {
    userDao.add(user);
  }

  @Transactional(readOnly = true)
  @Override
  public List<User> listUsers() {
    return userDao.listUsers();
  }

  @Override
  public List<String> getUsersByESP(String name) {
    return userDao.getUsersByESP(name);
  }

  @Override
  public List<String> getAdults() {
    return userDao.getAdults();
  }
}