package com.github.pedramrn.samplemiddleware;

import java.util.Random;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-29
 */
public class VoteRepository {

    private Random random = new Random();

    public Boolean voteUp(String id) {
        return random.nextBoolean();
    }
}
