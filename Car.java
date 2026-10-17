package aston_java_team2_custom_class_sort;

import java.util.Objects;

public class Car {
    private final String brand;
    private final int enginePower;
    private final int year;

    private Car(Builder b) {
        brand = b.brand;
        enginePower = b.enginePower;
        year = b.year;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String brand;
        private int enginePower;
        private int year;

        private Builder() {
        }

        public Builder brand(String b) {
            brand = b;
            return this;
        }

        public Builder enginePower(int ep) {
            enginePower = ep;
            return this;
        }

        public Builder year(int y) {
            year = y;
            return this;
        }

        public Car build() {
            validate();
            return new Car(this);
        }

        private void validate() {
            if (brand == null) {
                throw new IllegalArgumentException("Бренд автомобиля не указан");
            }

            if (enginePower <= 0) {
                throw new IllegalArgumentException("Мощность двигателя должна быть положительной");
            }
            if (year < 1885) {
                throw new IllegalArgumentException("Год выпуска не может быть меньше 1885");
            }
        }
    }
}
