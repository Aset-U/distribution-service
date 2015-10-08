package dao;


import models.Car;

import java.util.List;

public interface CarDao {

    public Car getCarByNumber(String number) throws PersistException;

    public Car getCarByModel(String model) throws PersistException;

    public List<Car> getCarsByForwarders(int forwarderId) throws PersistException;
}
