package com.meli.challenge.mutants.services;


import com.meli.challenge.mutants.enums.Direction;
import com.meli.challenge.mutants.logic.Logic;
import com.meli.challenge.mutants.model.Request;
import com.meli.challenge.mutants.model.Sequence;
import com.meli.challenge.mutants.repositories.SequenceRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class SequenceService {

    @Autowired
    private SequenceRepository sequenceRepository;
    @Autowired
    private HashService hashService;

    private Thread logicVertical;
    private Thread logicHorizontal;
    private Thread logicOblique;

    private static final int MAX_SEQUENCE_VALID = 2;


    public Optional<Sequence> sequenceAlreadyExist(Request request) {
        long hash = hashService.getHash(request);
        log.info("Hash calculated: {}", hash);
        Optional<Sequence> sequenceOptional = sequenceRepository.findByHashSequenceValue(hash);

        return sequenceOptional;
    }

    public boolean isMutant(String[] dna) {
        String[][] board = transformToBoard(dna);
        logicVertical = new Logic(Direction.VERTICAL, board);
        logicHorizontal = new Logic(Direction.HORIZONTAL, board);
        logicOblique = new Logic(Direction.OBLIQUE, board);

        log.info("Creation of Logic objects");

        logicVertical.start();
        logicHorizontal.start();
        logicOblique.start();

        log.info("Threads started");

        while ((logicVertical.isAlive() || logicHorizontal.isAlive() || logicOblique.isAlive())) {
            if (isMutantThread((Logic) logicVertical, (Logic) logicHorizontal, (Logic) logicOblique)) {
                logicVertical.interrupt();
                logicHorizontal.interrupt();
                logicOblique.interrupt();
                return true;
            }
        }

        return isMutantThread((Logic) logicVertical, (Logic) logicHorizontal, (Logic) logicOblique);
    }

    public String[][] transformToBoard(String[] dna) {
        int n = dna.length;
        log.info("Getting board with length of: {}", n);
        String[][] board = new String[n][n];
        for (int i = 0; i < board.length; i++) {
            String[] row = getRowAsArray(dna[i]);
            for (int j = 0; j < board.length; j++) {
                board[i][j] = row[j];
            }
        }
        return board;
    }

    private String[] getRowAsArray(String s) {
        return s.split("");
    }

    public void saveMutantSequence(Request request, boolean isMutant) {
        Sequence sequence = sequenceRepository.save(createSequence(request, isMutant));
        log.info("New sequence created with hash value: {} and isMutant value of: {}", sequence.getHashSequenceValue(), sequence.isMutant());
    }

    private boolean isMutantThread(Logic logicVertical, Logic logicHorizontal, Logic logicOblique) {
        int totalRepeatedSequence = logicVertical.getRepeatedSequence() + logicHorizontal.getRepeatedSequence() + logicOblique.getRepeatedSequence();
        log.info("Total repeated sequences: {}", totalRepeatedSequence);
        return totalRepeatedSequence >= MAX_SEQUENCE_VALID;
    }

    private Sequence createSequence(Request request, boolean isMutant) {
        return Sequence.builder()
                .hashSequenceValue(hashService.getHash(request))
                .isMutant(isMutant)
                .build();
    }
}
