package web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@Entity
@Table(name = "avatars")
@AllArgsConstructor
@NoArgsConstructor
public class Avatar {
    @Id
    private UUID id;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}