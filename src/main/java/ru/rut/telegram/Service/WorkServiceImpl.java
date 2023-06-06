package ru.rut.telegram.Service;

import org.springframework.stereotype.Service;
import ru.rut.telegram.Model.Employee;
import ru.rut.telegram.Model.Work;
import ru.rut.telegram.Model.WorkRegion;
import ru.rut.telegram.Repo.WorkRepo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class WorkServiceImpl implements WorkService{

    private final WorkRepo workRepo;

    public WorkServiceImpl(WorkRepo workRepo) {
        this.workRepo = workRepo;
    }

    @Override
    public List<Work> getEmployeeWork(String login) {
        return workRepo.findAllByEmployeeLogin(login);
    }

    @Override
    public void create(Employee employee) {
        Work work = new Work();
        work.setEmployee(employee);
        workRepo.save(work);
    }

    @Override
    public void startWork(String login) {

    }

    @Override
    public boolean checkEmployee(int id) {
        Work work = workRepo.findById(id).get();
        if (work.getId()>0){
            return true;
        } else {
            return false;
        }
    }
}
