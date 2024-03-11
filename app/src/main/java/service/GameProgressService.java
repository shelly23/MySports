package service;

import persistence.dtos.Game_Progress;

public interface GameProgressService {

    Game_Progress getGame_progress(long userId) throws InterruptedException;

    void createGame_Progress(long userId) throws InterruptedException;

    void update(Game_Progress gameProgress) throws InterruptedException;
}
