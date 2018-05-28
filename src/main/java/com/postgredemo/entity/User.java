package com.postgredemo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.Type;


@Entity(name = "User")
@Table(name = "USERS")
public class User extends BaseEntity {

  @Type(type = "jsonb")
  @Column(columnDefinition = "jsonb")
  private UserInfo userInfo;

  public User(UserInfo userInfo) {
    this.userInfo = userInfo;
  }

  public User() {
  }

  public UserInfo getUserInfo() {
    return userInfo;
  }

  public void setUserInfo(UserInfo userInfo) {
    this.userInfo = userInfo;
  }
}