package ru.rut.telegram.Service;

import ru.rut.telegram.Model.Employee;
import ru.rut.telegram.Model.Region;
import ru.rut.telegram.Model.Work;
import ru.rut.telegram.Model.WorkRegion;

import java.util.List;

public interface WorkRegionService {


    void save(WorkRegion workRegion);

    List<WorkRegion> getByWork(Work work);

    List<WorkRegion> getById(Work work);
}
