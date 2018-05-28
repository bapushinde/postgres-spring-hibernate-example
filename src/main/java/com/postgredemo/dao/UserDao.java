package com.postgredemo.dao;

import com.postgredemo.entity.User;
import java.util.List;

public interface UserDao {

  void add(User user);

  List<User> listUsers();

  List<String> getByName(String name);
}