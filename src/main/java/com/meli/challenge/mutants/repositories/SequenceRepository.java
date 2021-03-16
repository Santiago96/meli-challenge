package com.meli.challenge.mutants.repositories;


import com.meli.challenge.mutants.model.Sequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SequenceRepository extends JpaRepository<Sequence, Long> {

    Optional<Sequence> findByHashSequenceValue(long HashSequenceValue);

    @Query("SELECT COUNT(id) AS countMutantDna FROM sequence  WHERE isMutant = TRUE")
    int getCountMutantDna();

    @Query("SELECT COUNT(id) AS countHumanDna FROM sequence  WHERE isMutant = FALSE")
    int getCountHumanDna();
}
