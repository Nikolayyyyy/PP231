package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.dao.UserDao;
import web.model.User;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    @Transactional
    public void addUser(User user) { userDao.addUser(user);
    }


    @Override
    @Transactional
    public List<User> listUsers() {
        return userDao.listUsers();
    }


    @Override
    @Transactional
    public User getById(int id) {
        return userDao.getUserById(id);
    }



    @Override
    @Transactional
    public void updateUser(User user) {
        userDao.updateUser(user);
    }


    @Override
    @Transactional
    public void removeUser(int id) {
        userDao.removeUser(id);
    }
}
