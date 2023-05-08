package org.sportradar.repository;

import org.sportradar.model.IGame;

import java.util.List;
import java.util.Optional;

/**
 * Initial interface to represent Game repository
 */
public interface IGameRepository {

    /**
     * Return Optional of game which either contain exact game or is empty
     *
     * @param id
     * @return Optional of
     */
    Optional<IGame> getGameById(long id);

    /**
     * This method either save new game of update existing one basing on game id
     *
     * @param game
     * @return updated game id
     */
    long saveOrUpdateGame(IGame game);

    /**
     * Get all games which have STARTED status
     *
     * @return
     */
    List<IGame> getAllStartedGames();

    /**
     * Remove all games from database
     */
    void flush();
}
