package ru.rut.telegram.Service;

import ru.rut.telegram.Model.Employee;
import ru.rut.telegram.Model.Region;
import ru.rut.telegram.Model.Work;

import java.util.List;

public interface RegionService {

    List<Region> getAllRegions();

    List<Region> getByNumbers(List<Integer> numbers);
    void create();
}