public class CarParser implements Parser<Car> {
    private static final int EXPECTED_FIELDS = 3;

    @Override 
    public Car parse(String line) {
        if (line == null || line.isBlank()) {
            throw new ValidationException("Получена пустая строка");
        }

        String[] parts = line.trim().split(": ");
        
        if (parts.length < 2 || !"Car".equals(parts[0])) {
            throw new ValidationException("Ожидается 'Car: <brand_name>, <engine_power>, <year>', но получено: " + line);
        }

        String[] fields = parts[1].split(", ");

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
