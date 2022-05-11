package ru.job4j.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.model.car.CarMark;
import ru.job4j.hibernate.model.car.CarModel;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.2. Mapping
 * 1. ToMany [#301848]
 * HibernateApp класс содержит метод main.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 11.05.2022
 */
public class CarApp {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry)
                    .buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            Transaction tr = session.beginTransaction();

            CarModel nivaModel = CarModel.of("Niva");
            CarModel vestaModel = CarModel.of("Vesta");
            CarModel largusModel = CarModel.of("Largus");
            CarModel grantaModel = CarModel.of("Granta");
            CarModel xRayModel = CarModel.of("xRay");

            session.save(nivaModel);
            session.save(vestaModel);
            session.save(largusModel);
            session.save(grantaModel);
            session.save(xRayModel);

            CarMark ladaMark = CarMark.of("Lada");
            ladaMark.addCModel(nivaModel);
            ladaMark.addCModel(vestaModel);
            ladaMark.addCModel(largusModel);
            ladaMark.addCModel(grantaModel);
            ladaMark.addCModel(xRayModel);
            session.save(ladaMark);

            tr.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }

    }
}
