package ru.job4j.hibernate.model.car;

import javax.persistence.*;
import java.util.Objects;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.2. Mapping
 * 1. ToMany [#301848]
 * CarModel модель данных описывает модель автомобиля.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 11.05.2022
 */
@Entity
@Table(name = "c_model")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String model;

    public static CarModel of(String name) {
        CarModel carModel = new CarModel();
        carModel.model = name;
        return carModel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String name) {
        this.model = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarModel carModel = (CarModel) o;
        return id == carModel.id && Objects.equals(model, carModel.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model);
    }
}
