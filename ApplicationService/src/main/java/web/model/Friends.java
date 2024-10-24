package web.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
@Entity
@Table(name = "friends")
@AllArgsConstructor
@NoArgsConstructor
public class Friends {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_to_id", nullable = false)
    private User userTo;

    @ManyToOne
    @JoinColumn(name = "user_from_id", nullable = false)
    private User userFrom;

    private Boolean isAccepted;

    private Timestamp time;

}
