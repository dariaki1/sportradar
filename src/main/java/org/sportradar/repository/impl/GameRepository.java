package org.sportradar.repository.impl;

import org.sportradar.model.GameStatusEnum;
import org.sportradar.model.IGame;
import org.sportradar.repository.IGameRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Represents main implementation of Game repository
 */
public class GameRepository implements IGameRepository {

    //Collection which imitates real database
    private final List<IGame> gameDatabase = new ArrayList<>();

    @Override
    public Optional<IGame> getGameById(long id) {
        return gameDatabase.stream()
                .filter(g -> g.getId() == id)
                .findFirst();
    }

    @Override
    public long saveOrUpdateGame(IGame game) {
        int indexOpt = IntStream.range(0, gameDatabase.size())
                .filter(i -> game.getId() == (gameDatabase.get(i).getId()))
                .findFirst().orElse(-1);

        if (indexOpt >= 0) {
            gameDatabase.set(indexOpt, game);
        } else {
            gameDatabase.add(game);
        }

        return game.getId();
    }

    @Override
    public List<IGame> getAllStartedGames() {
        return gameDatabase.stream()
                .filter(game -> game.getStatus() == GameStatusEnum.STARTED)
                .collect(Collectors.toList());
    }

    @Override
    public void flush() {
        gameDatabase.clear();
    }


}
