package com.meli.challenge.mutants.model;


import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "sequence")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "sequence",
        indexes = {
                @Index(name = "hashSequenceValue", columnList = "hashSequenceValue")
        })
public class Sequence {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, nullable = false)
    private long hashSequenceValue;

    @Column(name = "isMutant")
    private boolean isMutant;

    @Column(name = "createdDate", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdDate;
}
