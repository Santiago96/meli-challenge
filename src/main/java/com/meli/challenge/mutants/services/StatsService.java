package com.meli.challenge.mutants.services;


import com.meli.challenge.mutants.repositories.SequenceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StatsService {

    @Autowired
    private SequenceRepository sequenceRepository;

    public int getCountMutantDna() {
        log.info("Get count_mutant_dna consulted");
        return sequenceRepository.getCountMutantDna();
    }

    public int getCountHumanDna() {
        log.info("Get count_human_dna consulted");
        return sequenceRepository.getCountHumanDna();
    }
}
