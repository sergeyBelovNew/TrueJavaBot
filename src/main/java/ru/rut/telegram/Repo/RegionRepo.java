package ru.rut.telegram.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rut.telegram.Model.Employee;
import ru.rut.telegram.Model.Region;
import java.util.List;

@Repository
public interface RegionRepo extends JpaRepository<Region, Integer> {

    List<Region> findAllByNumberIn(List<Integer> numbers);
}