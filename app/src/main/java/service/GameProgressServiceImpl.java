package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import persistence.daos.Game_ProgressDAO;
import persistence.dtos.Game_Progress;

public class GameProgressServiceImpl implements GameProgressService {

    private static final Logger LOG = LoggerFactory.getLogger(GameProgressServiceImpl.class);

    private final Game_ProgressDAO FBpersistenceGame_Progress;

    public GameProgressServiceImpl(Game_ProgressDAO FBpersistenceGame_Progress) {
        this.FBpersistenceGame_Progress = FBpersistenceGame_Progress;
    }

    @Override
    public Game_Progress getGame_progress(long userId) throws InterruptedException {
        return FBpersistenceGame_Progress.getGameProgress(userId);
    }

    @Override
    public void createGame_Progress(long userId) throws InterruptedException {
        FBpersistenceGame_Progress.createGameProgress(userId);
        LOG.debug("Saved Game_Progress for user {}", userId);
    }

    @Override
    public void update(Game_Progress gameProgress) throws InterruptedException {
        this.FBpersistenceGame_Progress.update(gameProgress);
    }


}

