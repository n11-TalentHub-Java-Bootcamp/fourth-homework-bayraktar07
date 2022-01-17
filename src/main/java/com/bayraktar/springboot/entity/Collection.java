package com.bayraktar.springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "COLLECTION")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Collection implements BaseEntity {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDate collectionDate;
    @NumberFormat(pattern = "###.##")
    private Double collectedAmount;
    @OneToOne(orphanRemoval = true, fetch = FetchType.LAZY)
    private Debt boundDebt;
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_COLLECTION"))
    private User user;

}
