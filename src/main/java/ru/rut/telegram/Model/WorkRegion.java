package ru.rut.telegram.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "WORK_REGION")
public class WorkRegion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "WORK_ID")
    private Work work;

    @ManyToOne
    @JoinColumn(name = "REGION_ID")
    private Region region;

    @Column(name = "STATE")
    @Enumerated(value = EnumType.STRING)
    private RegionState state;

    public WorkRegion() {
    }

    public WorkRegion(Work work, Region region) {
        this.work = work;
        this.region = region;
    }

    @Override
    public String toString() {
        return "Рабочии регионы:" + "\n" +
                "id=" + id + "\n" +
                "Работа:\nId: " + work.getId() + "\n" +
                "Начало работы=" + work.getStartDate() + "\n" +
                "Конец работы=" + work.getEndDate() + "\n" +
                region + ";  " +
                "Cостояние региона=" + state;
    }
}
