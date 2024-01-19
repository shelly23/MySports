package persistence.daos;

import android.net.Uri;

import java.util.List;

import persistence.dtos.Connection;
import persistence.dtos.Therapist;
import persistence.dtos.Training_Video;
import persistence.dtos.Type;

public interface Training_VideoDAO {

    List<Training_Video> read() throws InterruptedException;

    Training_Video getTraining_Video(Connection connection, Type type) throws InterruptedException;

    Uri getUrl(Training_Video trainingVideo) throws InterruptedException;

}
