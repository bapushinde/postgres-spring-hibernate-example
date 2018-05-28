import com.fasterxml.jackson.databind.ObjectMapper;
import com.postgredemo.config.AppConfig;
import com.postgredemo.entity.User;
import com.postgredemo.entity.UserInfo;
import com.postgredemo.service.UserService;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

  private static final Logger LOG = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) throws IOException {

    LOG.info("Launching application");

    AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(AppConfig.class);

    UserService userService = context.getBean(UserService.class);

    // Adding user with userInfo as JSON
    userService.add(new User(new UserInfo("test1", "john", "john@example.com")));
    userService.add(new User(new UserInfo("test2", "sean", "sean@example.com")));
    userService.add(new User(new UserInfo("test3", "gill", "gill@example.com")));
    userService.add(new User(new UserInfo("test4", "Smith", "Smith@example.com")));

    // Get UserInfo
    List<User> users = userService.listUsers();
    for (User user : users) {
      LOG.info("Loaded User with Id is {} , First Name is  {} , Last Name is {} , Email is {} ",
          user.getId(), user.getUserInfo().getFirstName(), user.getUserInfo().getLastName(),
          user.getUserInfo().getEmail());
    }

    List<String> result = userService.getByName(users.get(0).getUserInfo().getFirstName());
    // LOG.info("User by first Name : {} : {} ",users.get(0).getUserInfo().getFirstName() , result);

    for (String json : result) {
      ObjectMapper objectMapper = new ObjectMapper();
      UserInfo userInfo = objectMapper.readValue(json, UserInfo.class);
      LOG.info("{}", userInfo);
    }

    context.close();
  }
}