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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class SequenceServiceTest {

    @InjectMocks
    private SequenceService sequenceService;

    @Mock
    private HashService hashService;

    @Mock
    private SequenceRepository sequenceRepository;

    private static final long hash = 27571L;

    @Test
    public void itShouldReturnOptionalWithValueForExistedSequence() {
        when(hashService.getHash(any())).thenReturn(hash);
        when(sequenceRepository.findByHashSequenceValue(hash)).thenReturn(Optional.of(new Sequence()));

        Optional<Sequence> sequenceOptional = sequenceService.sequenceAlreadyExist(new Request());

        assertTrue(sequenceOptional.isPresent());
    }

}