package ru.job4j.hibernate.model.hql;

import javax.persistence.*;
import java.util.Objects;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.1. Конфигурирование
 * 3. HQL [#6874]
 * 3.3.3. HQL, Criteria
 * 1. select fetch [#331990]
 * Candidate модель данных.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 13.05.2022
 */
@Entity
@Table(name = "candidates")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String experience;
    @Column
    private double salary;
    @OneToOne(fetch = FetchType.LAZY)
    private DataV dataV;

    public static Candidate of(String name, String experience, Double salary, DataV dataV) {
        Candidate candidate = new Candidate();
        candidate.name = name;
        candidate.experience = experience;
        candidate.salary = salary;
        candidate.dataV = dataV;
        return candidate;
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

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public DataV getDataVacancies() {
        return dataV;
    }

    public void setDataVacancies(DataV dataV) {
        this.dataV = dataV;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Candidate{id=" + id + ", name='" + name + '\''
                + ", experience='" + experience + '\'' + ", salary=" + salary
                + ", dataVacancies=" + dataV + '}';
    }
}
