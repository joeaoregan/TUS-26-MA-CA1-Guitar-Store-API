package edu.tus.guitarstore.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Guitar extends  BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="model_name") // must match database schema
	private String modelName;
    
    private double price;

    @Column(name="manufacture_date")
    private LocalDate manufactureDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id") // Match FK column schema.sql
    private Brand brand;
    
}
