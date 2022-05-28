package ru.job4j.hibernate.model.hql;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.2. Mapping
 * 3.3.3. HQL, Criteria
 * 1. select fetch [#331990]
 * DataVacancies модель данных база вакансий.
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 28.05.2022
 */
@Entity
@Table(name = "v_data")
public class DataV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "data_id")
    private int id;
    @Column(name = "data_name", nullable = false)
    private String name;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vacancy> vacancies = new CopyOnWriteArrayList<>();

    public static DataV of(String name) {
        DataV dataV = new DataV();
        dataV.name = name;
        return dataV;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Vacancy> getVacancies() {
        return vacancies;
    }

    public void setVacancies(List<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    public void addVacancies(Vacancy vacancy) {
        this.vacancies.add(vacancy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataV that = (DataV) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DataVacancies{id=" + id + ", name='" + name + '\''
                + ", vacancies=" + vacancies + '}';
    }
}
