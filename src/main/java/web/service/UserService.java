package web.service;

import web.model.User;

import javax.transaction.Transactional;
import java.util.List;

public interface UserService {

    void addUser(User user);
    List<User> listUsers();
    void updateUser(User user);
    void removeUser(int id);
    User getById(int id);

}
