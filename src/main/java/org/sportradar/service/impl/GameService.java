package org.sportradar.service.impl;

import org.sportradar.exception.IncorrectGameParameterException;
import org.sportradar.model.IGame;
import org.sportradar.model.ITeam;
import org.sportradar.model.impl.FootballGame;
import org.sportradar.repository.impl.GameRepository;
import org.sportradar.service.IGameService;

import java.util.Random;

public class GameService implements IGameService {

    private GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public long createGame(ITeam homeTeam, ITeam awayTeam) {
        if (homeTeam == null) {
            throw new IncorrectGameParameterException("home team is null");
        }
        if (awayTeam == null) {
            throw new IncorrectGameParameterException("away team is null");
        }

        IGame newGame = FootballGame.newBuilder(new Random().nextLong())
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .homeTeamScore(0)
                .awayTeamScore(0)
                .build();
        return gameRepository.saveOrUpdateGame(newGame);
    }
}
