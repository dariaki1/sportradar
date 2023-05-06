package org.sportradar.repository;

import org.sportradar.model.IGame;

import java.util.List;
import java.util.Optional;

/**
 *  Initial interface to represent Game repository
 */
public interface IGameRepository {

    //TODO add javadoc
    Optional<IGame> getGameById(long id);

    //TODO add javadoc
    long saveOrUpdateGame(IGame game);

    List<IGame> getAllStartedGames();

    void flush();
}
