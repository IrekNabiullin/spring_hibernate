package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

import static java.util.Objects.nonNull;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        String hql = "select u from User u";
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.getResultList();
    }

    @Override
    public User getUserByCar(String name, int series) {
        String hqlCar = "select c from Car c where name = '" + name + "' and series = '" + series + "'";
        TypedQuery<Car> queryCar = sessionFactory.getCurrentSession().createQuery(hqlCar);
        Car carOfUser = queryCar.getSingleResult();
        User userGotByCar = carOfUser.getUser();
        System.out.print("User " + userGotByCar.getFirstName() + " " + userGotByCar.getLastName());
        System.out.println(" has a car " + userGotByCar.getCar().getName() + " series " + userGotByCar.getCar().getSeries());
        return userGotByCar;
    }

    @Override
    public void deleteAllUsersFromTable() {
        String hql = "select u from User u";
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(hql);
        List<User> users = query.getResultList();
        for (User user : users) {
            sessionFactory.getCurrentSession().delete(user);
        }
    }
}
