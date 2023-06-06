package ru.rut.telegram.Service;

import org.springframework.stereotype.Service;
import ru.rut.telegram.Repo.EmployeeRepo;
import ru.rut.telegram.Model.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepo employeeRepo;

    public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public void addEmployee(Employee employee) {
        employeeRepo.save(employee);
    }

    @Override
    public Employee getEmployee(Integer id) {
        return employeeRepo.getReferenceById(id);
    }

    @Override
    public Employee getEmployeeByLogin(String login) {
        return employeeRepo.findByLogin(login);
    }

    @Override
    public boolean checkEmployee(String login) {
        Employee employee = employeeRepo.findByLogin(login);
        if (employee == null){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void create(Employee employee) {
        employeeRepo.save(employee);
    }

    @Override
    public int get_emploee_id_by_login(String login){
        Employee employee = employeeRepo.findByLogin(login);
        return employee.getId();
    }
}