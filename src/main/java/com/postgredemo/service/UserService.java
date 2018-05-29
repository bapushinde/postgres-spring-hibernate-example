package com.postgredemo.service;

import com.postgredemo.entity.User;
import java.util.List;


public interface UserService {

  void add(User user);

  List<User> listUsers();

  List<String> getUsersByESP(String name);

  List<String> getAdults();
}