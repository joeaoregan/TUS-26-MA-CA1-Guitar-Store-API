package edu.tus.guitarstore.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Brand extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", unique=true)
	private String name;

	private String country;

	@OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true)
	@ToString.Exclude // prevent infinite loops with Lombok
	private List<Guitar> guitars;
}
