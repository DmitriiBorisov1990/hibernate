package com.borisov.hibernate;

import com.borisov.hibernate.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.Serializable;

public class HibernateRunner {

    public static void main(String[] args) {
        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            Employee ivan = new Employee("Ivan");
            Serializable id = session.save(ivan);
            System.out.println("\n" + id + "<-" + "->" + ivan);
        }
    }
}
