public class CarParser implements Parser<Car> {
    private static final int EXPECTED_FIELDS = 3;

    @Override 
    public Car parse(String line) {
        if (line == null || line.isBlank()) {
            throw new ValidationException("Получена пустая строка");
        }

        String dataPart = line.trim();

        // Если в строке есть префикс "Car: ", отрезаем его
        if (dataPart.startsWith("Car:")) {
            dataPart = dataPart.substring(4).trim();
        }

        // Разделяем по запятой, игнорируя любые пробелы вокруг запятой
        String[] fields = dataPart.split("\\s*,\\s*");

        if (fields.length != EXPECTED_FIELDS) {
            throw new ValidationException("Ожидается 3 поля, но получено: " + fields.length);
        }

        try {
            return Car.builder()
                .brand(fields[0])
                .enginePower(Integer.parseInt(fields[1]))
                .year(Integer.parseInt(fields[2]))
                .build();
        } catch (NumberFormatException e) {
            throw new ValidationException("Поля engine_power и year должны быть целочисленными", e);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Ошибка при валидации: " + e.getMessage(), e);
        }
    }
}