package com.sh.app.user.repository;

import com.sh.app.user.entity.User;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserRepository {

    @Setter
    @Getter
    Session session;

    /**
     * - org.hibernate.Session.save(Object):Serializable 직렬화가능한 PK컬럼 반환
     * - org.hibernate.Session.persist(T):void 반환값 없음. 전달한 객체에 새 데이터 할당되어 있음.
     *
     * @param user
     * @return
     */
    public void save(User user) {
        Long id = (Long) session.save(user);
//        session.persist(user);
    }

    public User findById(Long id) {
        return session.get(User.class, id);
    }

    /**
     * jpql(Query)
     * @return
     */
    public List<User> findAll() {
        Query query = session.createQuery("SELECT u FROM user u ORDER BY u.id desc", User.class);
        return query.getResultList();
    }


    /**
     * Criteria API(TypedQuery)
     * @return
     */
    public List<User> findAll2() {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> rootEntry = cq.from(User.class);
        CriteriaQuery<User> all = cq.select(rootEntry);
        TypedQuery<User> typedQuery = session.createQuery(all);
        List<User> users = typedQuery.getResultList();
        return users;
    }

    public void update(User user) {
        session.update(user);
    }

    public void delete(Long id) {
        User user = findById(id);
        session.delete(user);
    }
}
