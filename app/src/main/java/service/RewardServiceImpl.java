package service;

import android.net.Uri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import persistence.daos.RewardDAO;
import persistence.dtos.Connection;
import persistence.dtos.Reward;

public class RewardServiceImpl implements RewardService {


    private static final Logger LOG = LoggerFactory.getLogger(RewardServiceImpl.class);

    private final RewardDAO FBpersistenceReward;

    public RewardServiceImpl(RewardDAO FBpersistenceReward) {
        this.FBpersistenceReward = FBpersistenceReward;
    }

    @Override
    public List<Reward> getAllRewards() throws InterruptedException {
        LOG.debug("Read all rewards");
        return FBpersistenceReward.read();
    }

    @Override
    public Reward getReward(Connection connection) throws InterruptedException {
        LOG.debug("Read reward for " + connection.getId());
        return FBpersistenceReward.getReward(connection);
    }

    @Override
    public Uri getUrl(Reward reward) throws InterruptedException {
        LOG.debug("Get uri for rewards " + reward.getId());
        return FBpersistenceReward.getUrl(reward);
    }
}
