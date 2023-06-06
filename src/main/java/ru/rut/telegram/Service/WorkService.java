package ru.rut.telegram.Service;

import ru.rut.telegram.Model.Employee;
import ru.rut.telegram.Model.Work;

import java.util.List;

public interface WorkService {

    List<Work> getEmployeeWork(String login);

    void startWork(String login);

    void create(Employee employee);

    boolean checkEmployee(int userName);
}
