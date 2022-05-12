package ru.job4j.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.model.car.CarMark;
import ru.job4j.hibernate.model.car.CarModel;
import ru.job4j.hibernate.model.carlazy.Mark;
import ru.job4j.hibernate.model.carlazy.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.2. Mapping
 * 3. LazyInitializationexception [#331987]
 * CarLazyApp содержит демонстрацию возможного Hibernate – LazyInitializationException.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 12.05.2022
 */
public class CarLazyApp {
    public static void main(String[] args) {
        List<Mark> marks = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry)
                    .buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            Transaction tr = session.beginTransaction();
            loadModel(session);
            marks = session.createQuery("from Mark").list();
            for (Mark mark : marks) {
                for (Model model : mark.getModels()) {
                    System.out.println(model);
                }
            }
            tr.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    /**
     * Заполение таблиц данными.
     *
     * @param session Session.
     */
    private static void loadModel(Session session) {
        Mark ladaMark = Mark.of("Lada");
        Model ladaNiva = Model.of("Niva", ladaMark);
        Model ladaVesta = Model.of("Vesta", ladaMark);
        Model ladaLargus = Model.of("Largus", ladaMark);
        Model ladaGranta = Model.of("Granta", ladaMark);
        Model ladaXRay = Model.of("xRay", ladaMark);
        ladaMark.addModel(ladaNiva);
        ladaMark.addModel(ladaVesta);
        ladaMark.addModel(ladaLargus);
        ladaMark.addModel(ladaGranta);
        ladaMark.addModel(ladaXRay);
        Mark kiaMark = Mark.of("Kia");
        Model kiaRio = Model.of("Rio", kiaMark);
        Model kiaCeed = Model.of("Ceed", kiaMark);
        Model kiaK5 = Model.of("K5", kiaMark);
        kiaMark.addModel(kiaRio);
        kiaMark.addModel(kiaCeed);
        kiaMark.addModel(kiaK5);
        session.save(kiaMark);
        session.save(ladaMark);
    }
}
