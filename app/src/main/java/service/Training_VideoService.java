package service;

import android.net.Uri;

import java.util.List;

import persistence.dtos.Connection;
import persistence.dtos.Therapist;
import persistence.dtos.Training_Video;
import persistence.dtos.Type;

public interface Training_VideoService {

    List<Training_Video> getAlLTraining_Videos() throws InterruptedException;

    Training_Video getTrainingVideo(Connection connection, Type type) throws InterruptedException;

    Uri getUrl(Training_Video trainingVideo) throws InterruptedException;

}
