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

    public String getBrand() {
        return brand;
    }

    public int getEnginePower() {
        return enginePower;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Car car = (Car) obj;

        return enginePower == car.enginePower
                && year == car.year
                && Objects.equals(brand, car.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, enginePower, year);
    }

    @Override
    public String toString() {
        return "Автомобиль {" +
                "бренд:" + brand + '\'' +
                ", мощность двигателя: " + enginePower +
                ", год выпуска: " + year +
                '}';
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
