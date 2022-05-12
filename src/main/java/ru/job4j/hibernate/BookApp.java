package ru.job4j.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.model.book.Author;
import ru.job4j.hibernate.model.book.Book;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.2. Mapping
 * 2. ManyToMany [#331986]
 * BookApp демонстрация работы ManyToMany.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 12.05.2022
 */
public class BookApp {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry)
                    .buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            Transaction tr = session.beginTransaction();
            Book book1 = Book.of("Идиот");
            Book book2 = Book.of("Двойник");
            Book book3 = Book.of("Земля греха");

            Author aDost = Author.of("Ф.М. Достоевский");
            Author aSara = Author.of("Ж. Сарамаго");

            aDost.addBook(book1);
            aDost.addBook(book2);
            aSara.addBook(book3);
            aSara.addBook(book2);

            session.persist(aDost);
            session.persist(aSara);

            Author author = session.get(Author.class, 2);
            session.remove(author);

            tr.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
