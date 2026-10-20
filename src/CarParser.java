public class CarParser implements Parser<Car> {
    @Override 
    public Car parse(String str) {
        return Car.builder()
            .brand("bmv")
            .enginePower(100)
            .year(1999)
            .build();
    }
}
