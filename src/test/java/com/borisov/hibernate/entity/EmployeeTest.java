package com.borisov.hibernate.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.assertNotNull;

public class EmployeeTest {

    private static final SessionFactory FACTORY = new Configuration().configure().buildSessionFactory();

    @AfterClass
    public static void close() {
        FACTORY.close();
    }

    @Before
    public void cleanTable() {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            int count = session.createQuery("delete from Employee e").executeUpdate();
            System.out.println("Delete " + count);
            session.getTransaction().commit();
        }
    }

    @Test
    public void checkSaveEntity() {
        try (Session session = FACTORY.openSession()) {
            Employee petr = new Employee("Petr");
            Serializable id = session.save(petr);
            assertNotNull(id);
        }
    }

    @Test
    public void checkGetEntity() {
        try (Session session = FACTORY.openSession()) {
            Employee petr = new Employee("Sveta");
            Serializable id = session.save(petr);
            assertNotNull(id);
            Employee employee = session.get(Employee.class, id);
            assertNotNull(employee);
            System.out.println(employee);
        }
    }
}