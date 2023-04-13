package web.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(User user) {
        entityManager.unwrap(Session.class).saveOrUpdate(user);
    }


    @Override
    public List<User> listUsers() {
        return entityManager.unwrap(Session.class).createQuery("FROM User").getResultList();
    }

    @Override
    public User getUserById(int id) {
        return entityManager.unwrap(Session.class).createQuery("from User where id = '" + id + "'", User.class).getSingleResult();
    }

    @Override
    public void updateUser(User user) {
        entityManager.unwrap(Session.class).saveOrUpdate(user);
    }

    @Override
    public void removeUser(int id) {
        User user = getUserById(id);
        entityManager.unwrap(Session.class).delete(user);
    }
}
