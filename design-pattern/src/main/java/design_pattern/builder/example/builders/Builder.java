package design_pattern.builder.example.builders;

import design_pattern.builder.example.cars.Type;
import design_pattern.builder.example.components.Engine;
import design_pattern.builder.example.components.GPSNavigator;
import design_pattern.builder.example.components.Transmission;
import design_pattern.builder.example.components.TripComputer;

/**
 * EN: Builder interface defines all possible ways to configure a product.
 *
 * RU: Интерфейс Строителя объявляет все возможные этапы и шаги конфигурации
 * продукта.
 */
public interface Builder {
    public void setType(Type type);
    public void setSeats(int seats);
    public void setEngine (Engine engine);
    public void setTransmission(Transmission transmission);
    public void setTripComputer(TripComputer tripComputer);
    public void setGPSNavigator(GPSNavigator gpsNavigator);
}
