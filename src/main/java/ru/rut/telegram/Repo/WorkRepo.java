package ru.rut.telegram.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rut.telegram.Model.Work;

import java.util.List;

@Repository
public interface WorkRepo extends JpaRepository<Work, Integer> {

    List<Work> findAllByEmployeeLogin(String login);

}