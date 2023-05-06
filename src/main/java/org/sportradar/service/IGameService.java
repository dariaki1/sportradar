package org.sportradar.service;

import org.sportradar.model.ITeam;

public interface IGameService {

    long createGame(ITeam homeTeam, ITeam awayTeam);
}
