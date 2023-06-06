package ru.rut.telegram.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rut.telegram.Model.Employee;
import ru.rut.telegram.Model.Work;
import ru.rut.telegram.Model.WorkRegion;
import java.util.List;

@Repository
public interface WorkRegionRepo extends JpaRepository<WorkRegion, Integer> {

    List<WorkRegion> findByWork(Work work);
}
