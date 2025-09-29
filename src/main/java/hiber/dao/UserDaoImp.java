package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.TypedQuery;

import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private static final String GET_ALL_USERS_QUERY = "FROM User";
    private static final String GET_USER_BY_CAR_QUERY =
            "FROM User u WHERE u.car.model = :model AND u.car.series = :series";


    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().persist(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUser() {
        TypedQuery<User> query = sessionFactory.getCurrentSession()
                .createQuery(GET_ALL_USERS_QUERY, User.class);
        return query.getResultList();
    }

    @Override
    public User getUserByCar(String model, int series) {
        TypedQuery<User> query = sessionFactory.getCurrentSession()
                .createQuery(GET_USER_BY_CAR_QUERY, User.class);
        query.setParameter("model", model);
        query.setParameter("series", series);
        return query.getResultStream().findFirst().orElse(null);
    }

}
