package factory.infrastructure.db.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "accessories")
@Getter @Setter
@NoArgsConstructor
public class AccessoryData {
    @Id
    private int id;
}
