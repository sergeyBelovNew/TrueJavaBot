package ru.rut.telegram.Service;

import ru.rut.telegram.Model.Employee;

public interface EmployeeService {

    void addEmployee(Employee employee);

    Employee getEmployee(Integer id)
            ;
    Employee getEmployeeByLogin(String login);

    boolean checkEmployee(String login);

    void create (Employee employee);
    int get_emploee_id_by_login(String login);
}
