package ru.job4j.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.model.hql.Candidate;

import java.util.List;

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
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry)
                    .buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            Transaction tr = session.beginTransaction();
            Candidate first = Candidate.of("Ivan", "3 years", 3000.00);
            Candidate two = Candidate.of("Nikita", "1 month", 1000.00);
            Candidate three = Candidate.of("Sasha", "1 year", 2000.00);
            session.save(first);
            session.save(two);
            session.save(three);
            List<Candidate> allCandidate = session.createQuery("from Candidate").list();
            List<Candidate> candidateFromId = session.createQuery(
                            "from Candidate where id=:id")
                    .setParameter("id", two.getId())
                    .list();
            List<Candidate> candidateFromName = session.createQuery(
                            "from Candidate where name=:name")
                    .setParameter("name", first.getName())
                    .list();
            session.createQuery(
                            "update Candidate set experience=:experience, salary=:salary where id=:id")
                    .setParameter("experience", "2 year")
                    .setParameter("salary", 2500.00)
                    .setParameter("id", three.getId())
                    .executeUpdate();
            session.createQuery("delete Candidate where id=:id")
                    .setParameter("id", first.getId())
                    .executeUpdate();
            tr.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
