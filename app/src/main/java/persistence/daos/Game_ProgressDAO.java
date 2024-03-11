package persistence.daos;

import persistence.dtos.Game_Progress;

public interface Game_ProgressDAO {

    Game_Progress getGameProgress(long userId) throws InterruptedException;

    void createGameProgress(long UserId) throws InterruptedException;

    void update(Game_Progress gameProgress);
}
