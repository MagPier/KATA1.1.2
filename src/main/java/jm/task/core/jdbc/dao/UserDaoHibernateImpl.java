package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS Users (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), lastName VARCHAR(255), age TINYINT ) ";
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            session.createSQLQuery(query).executeUpdate();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void dropUsersTable() {
        String query = "DROP DATABASE IF EXISTS users";
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            session.createSQLQuery(query).executeUpdate();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.save(user);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            User user = session.get(User.class, id);
            transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {

            List<User> users = session.createCriteria(User.class).list();
            users.stream().forEach(System.out::println);
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }
}
