package com.borisov.hibernate;

import com.borisov.hibernate.entity.Address;
import com.borisov.hibernate.entity.Employee;
import com.borisov.hibernate.entity.EmployeeLang;
import com.borisov.hibernate.entity.Gender;
import lombok.extern.log4j.Log4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.Serializable;
import java.time.LocalDate;

import static com.borisov.hibernate.entity.EmployeeLang.*;

@Log4j
public class HibernateRunner {

    public static void main(String[] args) {
        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            log.debug("begin transaction...");
            session.beginTransaction();
            Employee employee = Employee.builder()
                    .name("DIMA-2")
                    .gender(Gender.FEMALE)
                    .birthDay(LocalDate.now())
                    .address(Address.of("Minsk","Platonova"))
                    .workAddress(Address.of("Mogilev","Mira"))
                    .build();
            log.info("prepare for saving employee...");
            session.save(employee);
            log.info("completed");


            EmployeeLangId employeeLangId = EmployeeLangId.builder()
                    .employeeId(employee.getId())
                    .lang("EN")
                    .build();
            EmployeeLang employeeLang = new EmployeeLang(employeeLangId,"translation en");
            session.save(employeeLang);
            session.flush();
            session.evict(employeeLang);
            EmployeeLang employeeLang1 = session.get(EmployeeLang.class, employeeLangId);
            session.getTransaction().commit();
        }
        //update();
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

    private static void update(){
        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Employee employee = session.get(Employee.class, 3L);
            employee.setGender(Gender.FEMALE);
            session.update(employee);
            //Serializable id = session.save(employee);
            session.getTransaction().commit();
        }
    }
}
