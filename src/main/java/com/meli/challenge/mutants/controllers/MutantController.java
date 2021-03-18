package com.meli.challenge.mutants.controllers;


import com.meli.challenge.mutants.model.Request;
import com.meli.challenge.mutants.model.Sequence;
import com.meli.challenge.mutants.model.Stats;
import com.meli.challenge.mutants.services.StatsService;
import com.meli.challenge.mutants.services.SequenceService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController("")
@AllArgsConstructor
@Slf4j
public class MutantController {

    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private StatsService statsService;

    /**
     * POST method requires in the requirements
     *
     * @param request Request model as a RequestBody
     * @return ResponseEntity with a defined status
     */
    @ApiOperation(value = "Validate DNA", response = ResponseEntity.class)
    @PostMapping(value = "/mutant/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity validateMutantDNA(@RequestBody Request request) {
        log.info("Post mutant endpoint consulted");
        Optional<Sequence> optional = sequenceService.sequenceAlreadyExist(request);
        if (optional.isPresent()) {
            if (optional.get().isMutant()) {
                log.info("Sequence repeated with isMutant value: {}", Boolean.TRUE);
                return new ResponseEntity(HttpStatus.OK);
            }
            log.info("Sequence repeated with isMutant value: {}", Boolean.FALSE);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        if (sequenceService.isMutant(request.getDna())) {
            sequenceService.saveMutantSequence(request, true);
            return new ResponseEntity(HttpStatus.OK);
        }
        sequenceService.saveMutantSequence(request, false);
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    /**
     * GET method to obtain the stats
     * @return ReponseEntity with Stats stereotype with the json properties needed.
     */
    @ApiOperation(value = "Get stats from the database", response = ResponseEntity.class)
    @GetMapping(value = "/stats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Stats> getStats() {
        log.info("Get Stats endpoint consulted");
        int countMutantDna = statsService.getCountMutantDna();
        int countHumanDna = statsService.getCountHumanDna();
        double ratio = 0.0;

        if (countHumanDna != 0) {
            ratio = (double) countMutantDna / countHumanDna;
        }

        Stats stats = Stats.builder()
                .countMutantDna(countMutantDna)
                .countHumanDna(countHumanDna)
                .ratio(ratio)
                .build();

        log.info("Stats object built ratio: {}, count_mutant_dna: {}," +
                        "and count_human_dna: {}", stats.getRatio(), stats.getCountMutantDna(),
                stats.getCountHumanDna());

        return new ResponseEntity(stats, HttpStatus.OK);
    }
}
