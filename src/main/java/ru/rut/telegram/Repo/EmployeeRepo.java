package ru.rut.telegram.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rut.telegram.Model.Employee;
import java.util.List;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

    Employee findByLogin(String login);

    List<Employee> findAllByIsAdmin(boolean isAdmin);

}
