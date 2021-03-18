package com.meli.challenge.mutants.algorithms;

import com.meli.challenge.mutants.interfaces.Algorithm;
import com.meli.challenge.mutants.logic.Logic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;


@Slf4j
public class ObliqueAlgorithm implements Algorithm {

    private Logic logic;

    public ObliqueAlgorithm(Logic logic) {
        this.logic = logic;
    }

    /**
     * This method will search in a oblique direction one for the right and one for the left starting in the corner of the
     * board, once it identifies 4 times the same letter will increase the
     * repeated sequence value in logic class that is consulted each moment in the main thread, this thread will be
     * interrupted if the main thread identifies 2 occurrences in the board.
     * @param board
     */
    @Override
    public void algorithm(String[][] board) {
        log.info("Start searching in: {}", this.getClass().getName());

        int size = board.length;
        findRightOblique(size, board);
        findLeftOblique(size, board);
    }

    public void findRightOblique(int size, String[][] board) {
        int repeatedLetter = 0;

        for (int i = 0; i < size / 2; i++) {
            for (int j = 0; j < size / 2; j++) {
                String letterInit = null;
                for (int k = 0; k <= size / 2 && k + j < size && k + i < size; k++) {

                    String letter = board[k + i][k + j];
                    if (null == letterInit) {
                        letterInit = letter;
                        repeatedLetter = 1;
                    } else if (letterInit.equals(letter)) {
                        repeatedLetter += 1;
                    } else {
                        repeatedLetter = 1;
                        letterInit = letter;
                    }
                    if (repeatedLetter == SEQUENCE_NUMBER) {
                        letterInit = null;
                        repeatedLetter = 0;

                        setRepeatedSequence();
                    }
                }
            }
        }
    }


    public void findLeftOblique(int size, String[][] board) {
        int repeatedLetter = 0;

        for (int i = 0; i < size / 2; i++) {
            for (int j = size - 1; j > size / 2; j--) {
                String letterInit = null;
                for (int k = 0; k <= size / 2; k++) {

                    String letter = board[k + i][j - k];
                    if (null == letterInit) {
                        letterInit = letter;
                        repeatedLetter = 1;
                    } else if (letterInit.equals(letter)) {
                        repeatedLetter += 1;
                    } else {
                        repeatedLetter = 1;
                        letterInit = letter;
                    }
                    if (repeatedLetter == SEQUENCE_NUMBER) {
                        letterInit = null;
                        repeatedLetter = 0;

                        setRepeatedSequence();
                    }
                }
            }
        }
    }

    private void setRepeatedSequence() {
        log.info("Repeated Sequence found in: {}", this.getClass().getName());
        logic.setRepeatedSequence();
    }
}
