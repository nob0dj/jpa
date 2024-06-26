package com.sh.app.user.service;

import com.sh.app.user.entity.User;
import com.sh.app.user.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.lang.reflect.Field;
import java.util.List;

public class UserServiceProxy implements UserService{
    final UserService target;
    final SessionFactory sessionFactory;

    public UserServiceProxy(UserService target, SessionFactory sessionFactory) {
        this.target = target;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(User user) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            setSessionInUserRepository(session);
            target.save(user);
            tx.commit(); // commit할때 실제쓰기 @CreationTimestamp 작동
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    /**
     * reflection api를 이용해서 UserRepository#session필드에 생성한 세션을 전달한다.
     * @param session
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void setSessionInUserRepository(Session session) {
        try {
            // UserServiceImple#userRepository
            // UserRepository#session
            Field userRepositoryField = target.getClass().getDeclaredField("userRepository");
            UserRepository userRepositoryObject = (UserRepository) userRepositoryField.get(target);
            Field sessionField = userRepositoryObject.getClass().getDeclaredField("session");
            sessionField.setAccessible(true); // private필드 접근 허용
            sessionField.set(userRepositoryObject, session);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findById(Long id) {
        Session session = sessionFactory.openSession();
        setSessionInUserRepository(session);
        User user = target.findById(id);
        session.close();
        return user;
    }

    @Override
    public List<User> findAll() {
        Session session = sessionFactory.openSession();
        setSessionInUserRepository(session);
        List<User> users = target.findAll();
        session.close();
        return users;
    }

    @Override
    public void update(User user) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            setSessionInUserRepository(session);
            target.update(user);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public void updatePoint(Long id, int point) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            setSessionInUserRepository(session);
            target.updatePoint(id, point);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(Long id) {
       Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            setSessionInUserRepository(session);
            target.delete(id);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }
}
