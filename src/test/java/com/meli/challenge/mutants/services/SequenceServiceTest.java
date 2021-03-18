package com.meli.challenge.mutants.services;

import com.meli.challenge.mutants.model.Request;
import com.meli.challenge.mutants.model.Sequence;
import com.meli.challenge.mutants.repositories.SequenceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class SequenceServiceTest {

    @InjectMocks
    private SequenceService sequenceService;

    @Mock
    private HashService hashService;

    @Mock
    private SequenceRepository sequenceRepository;

    private static final long hash = 27571L;
    private static final String[] dnaMutant = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
    private static final String[] dnaHuman = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};

    @Test
    public void itShouldReturnOptionalWithValueForExistedSequence() {
        when(hashService.getHash(any())).thenReturn(hash);
        when(sequenceRepository.findByHashSequenceValue(hash)).thenReturn(Optional.of(new Sequence()));

        Optional<Sequence> sequenceOptional = sequenceService.sequenceAlreadyExist(new Request());

        assertTrue(sequenceOptional.isPresent());
    }

    @Test
    public void itShouldReturnMatrixBasedOnDnaSequence() {
        String[][] board = sequenceService.transformToBoard(dnaMutant);

        assertEquals(dnaMutant.length, board.length);
    }

    @Test
    public void itShouldReturnTrueForSequenceMutant() {

        boolean isMutant = sequenceService.isMutant(dnaMutant);

        assertTrue(isMutant);
    }

    @Test
    public void itShouldReturnFalseForSequenceMutant() {

        boolean isMutant = sequenceService.isMutant(dnaHuman);

        assertFalse(isMutant);
    }

    @Test
    public void itShouldSaveMutantSequence() {
        when(sequenceRepository.save(any())).thenReturn(getMockSequence());

        sequenceService.saveMutantSequence(new Request(), Boolean.TRUE);

        verify(sequenceRepository, times(1)).save(any());
    }

    private Sequence getMockSequence() {
        return Sequence.builder()
                .hashSequenceValue(hash)
                .isMutant(Boolean.TRUE)
                .build();
    }

}