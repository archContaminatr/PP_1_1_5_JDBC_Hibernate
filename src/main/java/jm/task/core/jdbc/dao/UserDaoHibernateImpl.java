package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaBuilder;

import java.util.Collections;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    public void sqlCommandTemplate(String sqlCommand) {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            try {
                session.beginTransaction();
                session.createSQLQuery(sqlCommand).executeUpdate();
                session.getTransaction().commit();
            }
            catch (Exception e) {
                session.getTransaction().rollback();
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createUsersTable() {
        String sqlCommand = """
                CREATE TABLE IF NOT EXISTS users (
                  `id` BIGINT(45) NOT NULL AUTO_INCREMENT,
                  `name` VARCHAR(45) NOT NULL,
                  `lastname` VARCHAR(45) NOT NULL,
                  `age` TINYINT(45) NOT NULL,
                  PRIMARY KEY (`id`));""";
        sqlCommandTemplate(sqlCommand);
    }

    @Override
    public void dropUsersTable() {
        String command = "DROP TABLE users";
        sqlCommandTemplate(command);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            User user = new User(name, lastName, age);
            try {
                session.beginTransaction();
                session.save(user);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            User user = new User();
            user.setId(id);
            try {
                session.beginTransaction();
                session.delete(user);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            try {
                session.beginTransaction();
                CriteriaBuilder builder = session.getCriteriaBuilder();
                CriteriaQuery<User> criteria = builder.createQuery(User.class);
                criteria.from(User.class);
                return session.createQuery(criteria).getResultList();
            } catch (Exception e) {
                session.getTransaction().rollback();
                e.printStackTrace();
                return Collections.emptyList();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public void cleanUsersTable() {
        String command = "DELETE FROM User";
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            try {
                session.beginTransaction();
                session.createQuery(command).executeUpdate();
                session.getTransaction().commit();
            }
            catch (Exception e) {
                session.getTransaction().rollback();
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}