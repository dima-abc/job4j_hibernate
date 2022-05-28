package ru.job4j.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.model.hql.Candidate;
import ru.job4j.hibernate.model.hql.DataV;
import ru.job4j.hibernate.model.hql.Vacancy;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.1. Конфигурирование
 * 3. HQL [#6874]
 * CandidateApp демонстрация HQL
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 13.05.2022
 */
public class CandidateApp {
    public static void main(String[] args) {
        addData();
        Candidate rsl = null;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry)
                    .buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            Transaction tr = session.beginTransaction();
            rsl = session.createQuery("select distinct c from Candidate c "
                            + "join fetch c.dataV d "
                            + "join fetch d.vacancies v "
                            + "where c.id =:cId", Candidate.class)
                    .setParameter("cId", 1)
                    .uniqueResult();
            tr.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        System.out.println(rsl);
    }

    private static void addData() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        final SessionFactory sf = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory();
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            Vacancy senor = Vacancy.of("Java senor developer");
            Vacancy midl = Vacancy.of("Java midl developer");
            Vacancy jun = Vacancy.of("Java jun developer");
            session.persist(senor);
            session.persist(midl);
            session.persist(jun);
            DataV dataV = DataV.of("Java");
            dataV.addVacancies(senor);
            dataV.addVacancies(midl);
            dataV.addVacancies(jun);
            session.persist(dataV);
            Candidate candidate = Candidate.of("Nik", "1 year", 2000.00D, dataV);
            session.persist(candidate);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            StandardServiceRegistryBuilder.destroy(registry);
        }

    }
}
