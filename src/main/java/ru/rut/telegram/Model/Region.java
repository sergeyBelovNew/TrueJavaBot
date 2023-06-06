package ru.rut.telegram.Model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "REGION")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "NUMBER")
    private Integer number;

    @Column(name = "AREA")
    private Integer area;

    @Column(name = "REGION_NAME", length=512)
    @Lob
    private String regionName;

    public Region() {
    }

    @Override
    public String toString() {
        return  "Регионы:" + "\n" +
                "id=" + id + ";  " +
                "Номер=" + number + ";  " +
                "Название региона=" + regionName + ";  " +
                "Локация=" + area;
    }
}
