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

    /**
     * Method to obtain an Optional and load with an element previously a hash is calculated to find in the DB.
     *
     * @param request
     * @return Optional with an element if it was found in the DB
     */
    public Optional<Sequence> sequenceAlreadyExist(Request request) {
        long hash = hashService.getHash(request);
        log.info("Hash calculated: {}", hash);
        Optional<Sequence> sequenceOptional = sequenceRepository.findByHashSequenceValue(hash);

        return sequenceOptional;
    }

    /**
     * Core method that determines if the dna sequence is mutant or human, I decided to use threads in order to
     * optimize the searching, I created a vertical searching, horizontal searching and an oblique searching in this one
     * it will search oblique to the right and oblique to the left, as all of them are threads there is a variable that
     * will increase the value if a occurrence of 4 equals letters is found.
     * <p>
     * The while block written here, will be executed if one of the threads is alive and will consult isMutantThread method
     * that will consult and sum each value for repeatedSequence in each thread in that way, once it identifies that exists
     * at least two occurencies it will interrupt all the threads and return true, avoiding go through all the board or
     * sequence
     *
     * @param dna it is the sequence sent in the http request
     * @return boolean value that determines if the dna consulted is mutant or human
     */
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


    /**
     * This method pretends to transform the sequence dna in a board, to
     *
     * @param dna
     * @return String matrix to be treat like a board
     */
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

    /**
     * This method pretends to save the sequence in the DB, the sequence is created by the request value and isMutant
     * value that results from the process
     *
     * @param request  Request model that comes in the http request
     * @param isMutant Determines if the sequence is mutant or human
     */
    public void saveMutantSequence(Request request, boolean isMutant) {
        Sequence sequence = sequenceRepository.save(createSequence(request, isMutant));
        log.info("New sequence created with hash value: {} and isMutant value of: {}", sequence.getHashSequenceValue(), sequence.isMutant());
    }

    /**
     * this method pretends to consult the respective repeatedSequence value in each thread in order to sum them an
     * return if it is mutant or human
     *
     * @param logicVertical   Thread that searches in vertical direction
     * @param logicHorizontal Thread that searches in horizontal direction
     * @param logicOblique    Thread that searches in obloque direction
     * @return return true if exists 2 or more repeated sequences
     */
    private boolean isMutantThread(Logic logicVertical, Logic logicHorizontal, Logic logicOblique) {
        int totalRepeatedSequence = logicVertical.getRepeatedSequence() + logicHorizontal.getRepeatedSequence() + logicOblique.getRepeatedSequence();
        log.info("Total repeated sequences: {}", totalRepeatedSequence);
        return totalRepeatedSequence >= MAX_SEQUENCE_VALID;
    }

    /**
     * This method pretends to create the Sequence instace based on request object and boolean value
     *
     * @param request
     * @param isMutant
     * @return Sequence object previously built
     */
    private Sequence createSequence(Request request, boolean isMutant) {
        return Sequence.builder()
                .hashSequenceValue(hashService.getHash(request))
                .isMutant(isMutant)
                .build();
    }
}
