package com.meli.challenge.mutants.services;

import com.meli.challenge.mutants.repositories.SequenceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class StatsServiceTest {

    @InjectMocks
    private StatsService statsService;

    @Mock
    private SequenceRepository sequenceRepository;

    private static final int COUNT_MUTANT_VALUE = 40;
    private static final int COUNT_HUMAN_VALUE = 100;


    @Test
    public void itShouldReturnCountMutantDna() {
        when(sequenceRepository.getCountMutantDna()).thenReturn(COUNT_MUTANT_VALUE);

        int countMutant = statsService.getCountMutantDna();

        assertEquals(COUNT_MUTANT_VALUE, countMutant);
    }

    @Test
    public void itShouldReturnCountHumanDna() {
        when(sequenceRepository.getCountHumanDna()).thenReturn(COUNT_HUMAN_VALUE);

        int countHuman = statsService.getCountHumanDna();

        assertEquals(COUNT_HUMAN_VALUE, countHuman);
    }

}