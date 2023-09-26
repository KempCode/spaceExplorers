package com.mikeandliam.spaceexplorers.randomevents;

import com.mikeandliam.spaceexplorers.GameEnvironment;
import com.mikeandliam.spaceexplorers.Util;

/**
 * A RandomEvent in which a crew member is chosen at random and is given the space plague.
 */
public class SpacePlagueRandomEvent implements RandomEvent {
    @Override
    public void performEvent(GameEnvironment environment) {
        if (environment.getCrew().size() > 0) {
            environment.getCrew().get(Util.globalRandom.nextInt(environment.getCrew().size())).setHasSpacePlague(true);
        }
    }
}
