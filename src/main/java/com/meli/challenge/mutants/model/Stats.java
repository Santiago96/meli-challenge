package com.meli.challenge.mutants.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Stats {
    @JsonProperty("count_mutant_dna")
    private int countMutantDna;
    @JsonProperty("count_human_dna")
    private int countHumanDna;
    @JsonProperty("ratio")
    private double ratio;
}
