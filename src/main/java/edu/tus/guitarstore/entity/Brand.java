package edu.tus.guitarstore.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Brand extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    
    private String name;
    
    private String country;
    
    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    @ToString.Exclude // prevent infinite loops with Lombok
    private java.util.List<Guitar> guitars;
}
