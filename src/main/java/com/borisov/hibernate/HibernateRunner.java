package com.borisov.hibernate;

import com.borisov.hibernate.entity.Employee;
import com.borisov.hibernate.entity.Gender;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.Serializable;

@Log4j
public class HibernateRunner {

    //private static Logger log = Logger.getLogger(HibernateRunner.class);

    public static void main(String[] args) {
        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            log.debug("begin transaction...");
            session.beginTransaction();
            Employee employee = Employee.builder()
                    .name("Anna")
                    .gender(Gender.MALE)
                    .build();
            log.info("prepare for saving employee...");
            session.save(employee);
            log.info("completed");
            session.getTransaction().commit();
        }
        //saveWithJPA();
        //severalSessions();
    }

    private static void test1() {
        Employee petr = new Employee("Petr");
        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            Serializable id = session.save(petr);
            session.evict(petr);
            Employee employee2 = session.get(Employee.class, id);
            System.out.println(employee2);
        }
        System.out.println(petr);
    }

    private static void saveWithJPA() {
        Employee ivan = new Employee("Ivan");
        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(ivan);
            session.getTransaction().commit();
            session.evict(ivan);
            Employee load = session.load(Employee.class, ivan.getId());
            System.out.println(load);
        }
    }

    private static void severalSessions() {
        Employee jan = new Employee("Jan");
        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory()) {
            try (Session session1 = sessionFactory.openSession()) {
                Serializable id = session1.save(jan);
                System.out.println(id);
            }
            try (Session session2 = sessionFactory.openSession()) {
                Employee employee = session2.get(Employee.class, jan.getId());
                System.out.println(employee);
            }
        }
    }
}
