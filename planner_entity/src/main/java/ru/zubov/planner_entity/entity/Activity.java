package ru.zubov.planner_entity.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Objects;

@Entity
@Table(name = "activity", schema = "tasks", catalog = "planner_task")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "activatied", nullable = false)
    @Convert(converter = org.hibernate.type.NumericBooleanConverter.class)
    // для автоматической конвертации числа в true/false
    private Boolean activatied;

//    @OneToOne(fetch = FetchType.LAZY)
//    @MapsId     //используется для ленивой загрузки для связи @OneToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User user;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "uuid", nullable = false, length = Integer.MAX_VALUE)
    private String uuid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return id.equals(activity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
