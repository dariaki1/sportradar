package org.sportradar.service.impl;

import org.sportradar.exception.IncorrectGameParameterException;
import org.sportradar.model.GameStatusEnum;
import org.sportradar.model.GameTypeEnum;
import org.sportradar.model.IGame;
import org.sportradar.model.ITeam;
import org.sportradar.model.impl.Game;
import org.sportradar.repository.IGameRepository;
import org.sportradar.service.IGameService;

import java.util.Optional;
import java.util.Random;

public class GameService implements IGameService {

    private IGameRepository gameRepository;

    public GameService(IGameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public long createGame(ITeam homeTeam, ITeam awayTeam, String gameType) {
        if (homeTeam == null) {
            throw new IncorrectGameParameterException("home team is null");
        }
        if (awayTeam == null) {
            throw new IncorrectGameParameterException("away team is null");
        }

        IGame newGame = Game.newBuilder(new Random().nextLong(), GameTypeEnum.valueOf(gameType))
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .homeTeamScore(0)
                .awayTeamScore(0)
                .build();
        return gameRepository.saveOrUpdateGame(newGame);
    }

    @Override
    public long updateGame(long gameId, Integer homeTeamScore, Integer awayTeamScore) {
        Optional<IGame> game = gameRepository.getGameById(gameId);

        return game.map(foundGame -> {
                    foundGame.setAwayTeamScore(awayTeamScore);
                    foundGame.setHomeTeamScore(homeTeamScore);
                    foundGame.setStatus(GameStatusEnum.STARTED);
                    return gameRepository.saveOrUpdateGame(foundGame);
                })
                .orElseThrow(() -> new IncorrectGameParameterException(String.format("Game with id %d not found", gameId)));
    }

    @Override
    public long finishGame(long gameId) {
        return 0;
    }


}
