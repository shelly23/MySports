package persistence.daos;

import android.net.Uri;

import java.util.List;

import persistence.dtos.Connection;
import persistence.dtos.Reward;

public interface RewardDAO {
    List<Reward> read() throws InterruptedException;

    Reward getReward(Connection connection) throws InterruptedException;


    Uri getUrl(Reward reward) throws InterruptedException;
}
