package com.lichao666.design_pattern.builder.example.builders;

import com.lichao666.design_pattern.builder.example.cars.Type;
import com.lichao666.design_pattern.builder.example.components.Engine;
import com.lichao666.design_pattern.builder.example.components.GPSNavigator;
import com.lichao666.design_pattern.builder.example.components.Transmission;
import com.lichao666.design_pattern.builder.example.components.TripComputer;

/**
 * EN: Builder interface defines all possible ways to configure a product.
 *
 */
public interface Builder {
    public void setType(Type type);
    public void setSeats(int seats);
    public void setEngine (Engine engine);
    public void setTransmission(Transmission transmission);
    public void setTripComputer(TripComputer tripComputer);
    public void setGPSNavigator(GPSNavigator gpsNavigator);
}
