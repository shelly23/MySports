package persistence.daos;

import java.util.List;

import persistence.dtos.Therapist;

public interface TherapistDAO {

    List<Therapist> read() throws InterruptedException;

    Therapist getTherapist(long id) throws InterruptedException;

}
