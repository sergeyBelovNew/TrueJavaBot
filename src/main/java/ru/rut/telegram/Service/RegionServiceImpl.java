package ru.rut.telegram.Service;

import org.springframework.stereotype.Service;
import ru.rut.telegram.Model.Employee;
import ru.rut.telegram.Model.Region;
import ru.rut.telegram.Model.Work;
import ru.rut.telegram.Repo.RegionRepo;

import java.util.List;

@Service
public class RegionServiceImpl implements RegionService{

    private final RegionRepo regionRepo;

    public RegionServiceImpl(RegionRepo regionRepo) {
        this.regionRepo = regionRepo;
    }

    @Override
    public void create() {
        Region region = new Region();
        regionRepo.save(region);
    }

    @Override
    public List<Region> getAllRegions() {
        return regionRepo.findAll();
    }

    @Override
    public List<Region> getByNumbers(List<Integer> numbers) {
        return regionRepo.findAllByNumberIn(numbers);
    }
}