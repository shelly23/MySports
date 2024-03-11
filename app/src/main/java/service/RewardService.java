package service;

import android.net.Uri;

import java.util.List;

import persistence.dtos.Connection;
import persistence.dtos.Reward;

public interface RewardService {

    List<Reward> getAllRewards() throws InterruptedException;

    Reward getReward(Connection connection) throws InterruptedException;

    Uri getUrl(Reward reward) throws InterruptedException;
}
