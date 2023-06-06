package ru.rut.telegram.Controller;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.rut.telegram.Model.Employee;
import ru.rut.telegram.Model.Region;
import ru.rut.telegram.Model.Work;
import ru.rut.telegram.Model.WorkRegion;
import ru.rut.telegram.Repo.EmployeeRepo;
import ru.rut.telegram.Repo.RegionRepo;
import ru.rut.telegram.Repo.WorkRegionRepo;
import ru.rut.telegram.Repo.WorkRepo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RestController
public class RestControl {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private WorkRepo workRepo;

    @Autowired
    private WorkRegionRepo workRegionRepo;

    @Autowired
    private RegionRepo regionRepo;

    @GetMapping("/get_work")
    public void work(Integer id) {
        Work work = workRepo.getReferenceById(id);
        System.out.println(work);
    }

    @GetMapping("/get_employee")
    public void employee(Integer employee_id) {
        Employee employee = employeeRepo.getReferenceById(employee_id);
        System.out.println(employee);
    }

    @PostMapping("/create_work")
    public void work() {
        Work work = new Work();
        workRepo.save(work);
        System.out.println("Сущность работа успешно создана");
    }


    @PostMapping("/create_work_shift(work_region + region)")
    public void region(@Schema(example = "1", description = "Введите id выполняемой работником работы")
                       @RequestParam Integer current_work_id,
                       @Schema(example = "1", description = "Введите regionArea")
                       @RequestParam Integer regionArea,
                       @Schema(example = "1", description = "Введите regionNumber")
                       @RequestParam Integer regionNumber,
                       @Schema(example = "Moscow", description = "Введите название локации") @RequestParam String regionName) {

        Region region1 = new Region();
        region1.setArea(regionArea);
        region1.setNumber(regionNumber);
        region1.setRegionName(regionName);
        regionRepo.save(region1);

        Work work = workRepo.getReferenceById(current_work_id);
        Set<Region> regionSet = new HashSet<>();
        regionSet.add(region1);
        work.setRegions(regionSet);

        WorkRegion workRegion = new WorkRegion(work, regionRepo.getReferenceById(region1.getId()));

        workRegionRepo.save(workRegion);
        System.out.println("Регион успешно создан");
    }

    @PutMapping("/update_work")
    public void work(@Schema(example = "1", description = "Введите work_id")
                     @RequestParam Integer work_id,
                     @Schema(example = "2023-6-23", description = "Введите started_date")
                     @RequestParam Date started_date,
                     @Schema(example = "2023-6-23", description = "Введите started_date")
                     @RequestParam Date end_date) {
        Work work = new Work();
        work.setId(work_id);
        work.setStartDate(started_date);
        work.setEndDate(end_date);
        workRepo.save(work);
        System.out.println("Сущность работа успешно обновленна");
    }

    @PutMapping("/update_work_shift(work_region + region)")
    public void region(@Schema(example = "1", description = "Введите id выполняемой работником работы")
                       @RequestParam Integer current_work_id,
                       @Schema(example = "1", description = "Введите current_region_id")
                       @RequestParam Integer current_region_id,
                       @Schema(example = "1", description = "Введите regionArea")
                       @RequestParam Integer regionArea,
                       @Schema(example = "1", description = "Введите regionNumber")
                       @RequestParam Integer regionNumber,
                       @Schema(example = "Moscow", description = "Введите название локации") @RequestParam String regionName) {

        Region region1 = new Region();
        region1.setId(current_region_id);
        region1.setArea(regionArea);
        region1.setNumber(regionNumber);
        region1.setRegionName(regionName);
        regionRepo.save(region1);

        Work work = workRepo.getReferenceById(current_work_id);
        Set<Region> regionSet = new HashSet<>();
        regionSet.add(region1);
        work.setRegions(regionSet);

        WorkRegion workRegion = new WorkRegion(work, regionRepo.getReferenceById(region1.getId()));

        workRegionRepo.save(workRegion);
        System.out.println("Регион успешно обновлен");
    }

    @DeleteMapping("/delete_work")
    public void work(@Schema(example = "1", description = "Введите work_id")
                     @RequestParam Long work_id) {
        Work work = workRepo.getReferenceById(Integer.parseInt(work_id.toString()));
        workRepo.delete(work);
        System.out.println("Сущность работа успешно удалена");
    }

    @DeleteMapping("/delete_work_shift(work_region + region)")
    public void region(@Schema(example = "1", description = "Введите id выполняемой работником работы")
                       @RequestParam Integer current_work_id,
                       @Schema(example = "1", description = "Введите current_region_id")
                       @RequestParam Integer current_region_id) {

        Region region1 = new Region();
        region1.setId(current_region_id);
        regionRepo.delete(region1);

        Work work = workRepo.getReferenceById(current_work_id);
        WorkRegion workRegion = new WorkRegion(work, regionRepo.getReferenceById(region1.getId()));

        workRegionRepo.delete(workRegion);
        System.out.println("Регион успешно удален");
    }
}
