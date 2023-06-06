package ru.rut.telegram.Model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "WORK")
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "WORK_DATE")
    private Date workDate;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "END_DATE")
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

    @ManyToMany
    @JoinTable(
            name = "WORK_REGION",
            joinColumns = @JoinColumn(name = "WORK_ID"),
            inverseJoinColumns = @JoinColumn(name = "REGION_ID")
    )
    private Set<Region> regions = new HashSet<>();
    //date->LocalDate->Instant


    public Work() {
    }

    @Override
    public String toString() {
        return  "Работа:" + "\n" +
                "id=" + id + "\n" +
                "Дата работы=" + workDate + "\n" +
                "Начало смены=" + startDate + "\n" +
                "Конец смены=" + endDate + "\n" +
                employee + "\n" +
                regions.toString().replaceAll("^\\[|\\]$", "");
    }
}
