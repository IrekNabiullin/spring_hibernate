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
//        User userHasCar = sessionFactory.getCurrentSession().get(User.class, id); // вытаскивает юзера по id
        String hqlUser = "select u from User u where name = 'user1'";
        TypedQuery<User> queryUser = sessionFactory.getCurrentSession().createQuery(hqlUser);
        User userHasCar = queryUser.getSingleResult();

        System.out.print("User " + userHasCar + " has car + ");
        Car carByUser = userHasCar.getCar();
        System.out.println(carByUser);
//        return userHasCar;

        String hqlCar = "select c from Car c where name = 'toyota'";
        TypedQuery<Car> queryCar = sessionFactory.getCurrentSession().createQuery(hqlCar);
        Car carOfUser = queryCar.getSingleResult();

        System.out.print("Car " + carOfUser + " belongs to user + ");
        User userGetByUser = carOfUser.getUser();
        System.out.println(userGetByUser);
        return userGetByUser;
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
