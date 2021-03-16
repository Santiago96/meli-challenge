package com.meli.challenge.mutants.controllers;

import com.meli.challenge.mutants.model.Request;
import com.meli.challenge.mutants.model.Sequence;
import com.meli.challenge.mutants.model.Stats;
import com.meli.challenge.mutants.services.StatsService;
import com.meli.challenge.mutants.services.SequenceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MutantControllerTest {


    @InjectMocks
    private MutantController mutantController;

    @Mock
    private SequenceService sequenceService;

    @Mock
    private StatsService statsService;

    private static final String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};


    @Test
    public void itShouldReturnStatus200ForDNASequence() {
        when(sequenceService.sequenceAlreadyExist(any())).thenReturn(Optional.empty());
        when(sequenceService.isMutant(any())).thenReturn(true);

        ResponseEntity response = mutantController.validateMutantDNA(getMockRequest());

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    public void itShouldReturnStatus403ForDNASequence() {
        when(sequenceService.sequenceAlreadyExist(any())).thenReturn(Optional.empty());
        when(sequenceService.isMutant(any())).thenReturn(false);

        ResponseEntity response = mutantController.validateMutantDNA(getMockRequest());

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCodeValue());
    }

    @Test
    public void itShouldReturnStatus200ForDNASequenceAlreadyProcessed() {
        when(sequenceService.sequenceAlreadyExist(any())).thenReturn(Optional.of(getMockSecuence(true)));

        ResponseEntity response = mutantController.validateMutantDNA(getMockRequest());

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    public void itShouldReturnStatus403ForDNASequenceAlreadyProcessed() {
        when(sequenceService.sequenceAlreadyExist(any())).thenReturn(Optional.of(getMockSecuence(false)));

        ResponseEntity response = mutantController.validateMutantDNA(getMockRequest());

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCodeValue());
    }

    @Test
    public void itShouldReturnStats() {
        when(statsService.getCountMutantDna()).thenReturn(40);
        when(statsService.getCountHumanDna()).thenReturn(100);

        ResponseEntity responseEntity = mutantController.getStats();
        Stats stats = (Stats) responseEntity.getBody();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(0.40, stats.getRatio());
    }

    private Sequence getMockSecuence(boolean isMutant) {
        return Sequence.builder()
                .isMutant(isMutant)
                .build();
    }

    private Request getMockRequest() {
        Request request = new Request();
        request.setDna(dna);
        return request;
    }
}