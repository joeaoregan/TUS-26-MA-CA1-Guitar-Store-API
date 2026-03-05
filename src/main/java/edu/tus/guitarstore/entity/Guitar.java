package edu.tus.guitarstore.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Guitar extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "model_name", unique = true) // must match database schema, and be unique
	private String modelName;

	private double price;

	@Column(name = "manufacture_date", columnDefinition = "DATE")
	private LocalDate manufactureDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "brand_id") // Match FK column schema.sql
	private Brand brand;
}
