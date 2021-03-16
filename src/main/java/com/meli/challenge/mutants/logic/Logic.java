package com.meli.challenge.mutants.logic;


import com.meli.challenge.mutants.algorithms.HorizontalAlgorithm;
import com.meli.challenge.mutants.algorithms.ObliqueAlgorithm;
import com.meli.challenge.mutants.algorithms.VerticalAlgorithm;
import com.meli.challenge.mutants.enums.Direction;
import com.meli.challenge.mutants.interfaces.Algorithm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Slf4j
public class Logic extends Thread {

    private Algorithm algorithm;
    private Direction direction;
    private String[][] board;
    private boolean isMutant;

    private int repeatedSequence = 0;

    public Logic(Direction direction, String[][] board) {
        this.direction = direction;
        this.board = board;
    }

    @Override
    public void run() {
        if (direction.equals(Direction.VERTICAL)) {
            algorithm = new VerticalAlgorithm(this);
        }
        if (direction.equals(Direction.HORIZONTAL)) {
            algorithm = new HorizontalAlgorithm(this);
        }
        if (direction.equals(Direction.OBLIQUE)) {
            algorithm = new ObliqueAlgorithm(this);
        }
        algorithm.algorithm(board);
    }

    public void setRepeatedSequence() {
        this.repeatedSequence += 1;
    }

}
