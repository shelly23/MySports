package persistence.daos;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import persistence.dtos.Therapist;
import persistence.exceptions.PersistenceException;

public interface TherapistDAO {

    List<Therapist> read() throws InterruptedException;

    Therapist getTherapist(long id) throws InterruptedException;

}
