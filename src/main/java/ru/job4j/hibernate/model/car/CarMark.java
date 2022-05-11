package ru.job4j.hibernate.model.car;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.2. Mapping
 * 1. ToMany [#301848]
 * CarMark модель данных описывает марку автомобиля.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 11.05.2022
 */
@Entity
@Table(name = "c_mark")
public class CarMark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String mark;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarModel> carModels = new ArrayList<>();

    public static CarMark of(String name) {
        CarMark carMark = new CarMark();
        carMark.mark = name;
        return carMark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String name) {
        this.mark = name;
    }

    public List<CarModel> getCarModels() {
        return carModels;
    }

    public void setCarModels(List<CarModel> users) {
        this.carModels = users;
    }

    public void addCModel(CarModel carModel) {
        this.carModels.add(carModel);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarMark carMark = (CarMark) o;
        return id == carMark.id && Objects.equals(mark, carMark.mark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mark);
    }

    @Override
    public String toString() {
        return "CarMark{id=" + id
                + ", name='" + mark + "}";
    }
}
