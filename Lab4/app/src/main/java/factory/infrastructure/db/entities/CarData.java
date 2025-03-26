package factory.infrastructure.db.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cars")
@Getter @Setter
@NoArgsConstructor
public class CarData {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "body_id")
    private int bodyId;

    @Column(name = "motor_id")
    private int motorid;

    @Column(name = "accessory_id")
    private int accessoryId;
}
