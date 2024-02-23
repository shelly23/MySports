package service;

import android.net.Uri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import persistence.daos.Training_VideoDAO;
import persistence.dtos.Connection;
import persistence.dtos.Training_Video;
import persistence.dtos.Type;

public class Training_VideoServiceImpl implements Training_VideoService {


    private static final Logger LOG = LoggerFactory.getLogger(Training_VideoServiceImpl.class);

    private final Training_VideoDAO FBpersistenceTraining_Video;

    public Training_VideoServiceImpl(Training_VideoDAO FBpersistenceTraining_Video) {
        this.FBpersistenceTraining_Video = FBpersistenceTraining_Video;
    }

    @Override
    public List<Training_Video> getAlLTraining_Videos() throws InterruptedException {
        LOG.debug("Read all training videos");
        return FBpersistenceTraining_Video.read();
    }

    @Override
    public Training_Video getTrainingVideo(Connection connection, Type type) throws InterruptedException {
        LOG.debug("Read trainingsvideo for " + connection.getId() + type);
        return FBpersistenceTraining_Video.getTraining_Video(connection, type);
    }

    public Training_Video getPersonalized_Video(Connection connection, long level) throws InterruptedException {
        LOG.debug("Read personalized trainingsvideo for " + connection.getId() + level);
        return FBpersistenceTraining_Video.getPersonalized_Video(connection, level);
    }

    @Override
    public Uri getUrl(Training_Video trainingVideo) throws InterruptedException {
        LOG.debug("Get uri for trainingsvideo " + trainingVideo.getId());
        return FBpersistenceTraining_Video.getUrl(trainingVideo);
    }
}
