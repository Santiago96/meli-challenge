package com.meli.challenge.mutants.algorithms;

import com.meli.challenge.mutants.interfaces.Algorithm;
import com.meli.challenge.mutants.logic.Logic;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class HorizontalAlgorithm implements Algorithm {

    private Logic logic;

    public HorizontalAlgorithm(Logic logic) {
        this.logic = logic;
    }


    /**
     * This method will search in a horizontal direction once it identifies 4 times the same letter will increase the
     * repeated sequence value in logic class that is consulted each moment in the main thread, this thread will be
     * interrupted if the main thread identifies 2 occurrencies in the board.
     * @param board
     */
    @Override
    public void algorithm(String[][] board) {
        log.info("Start searching in: {}", this.getClass().getName());
        int repeatedLetter = 0;

        for (int i = 0; i < board.length; i++) {
            String letterInit = null;
            for (int j = 0; j < board.length; j++) {
                String letter = board[i][j];
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
                    log.info("Repeated Sequence found in: {}",this.getClass().getName());
                    logic.setRepeatedSequence();
                }

            }
        }
    }
}
