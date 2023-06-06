package ru.rut.telegram.Service;

import org.springframework.stereotype.Service;
import ru.rut.telegram.Model.Employee;
import ru.rut.telegram.Model.Region;
import ru.rut.telegram.Model.Work;
import ru.rut.telegram.Model.WorkRegion;
import ru.rut.telegram.Repo.WorkRegionRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkRegionServiceImpl implements WorkRegionService{

    private final WorkRegionRepo workRegionRepo;

    public WorkRegionServiceImpl(WorkRegionRepo workRegionRepo) {
        this.workRegionRepo = workRegionRepo;
    }

    @Override
    public void save(WorkRegion workRegion) {
        workRegionRepo.save(workRegion);
    }

    @Override
    public List<WorkRegion> getByWork(Work work) {
        return workRegionRepo.findByWork(work);
    }

    @Override
    public List<WorkRegion> getById(Work work) {
        return workRegionRepo.findByWork(work);
    }
}
