package service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import persistence.dtos.Therapist;
import persistence.dtos.User;
import persistence.exceptions.InvalidValueException;
import persistence.exceptions.MandatoryValueException;
import persistence.exceptions.PersistenceException;

public interface TherapistService {

    List<Therapist> getAlLTherapists() throws InterruptedException;

    Therapist getTherapist(long id) throws InterruptedException;

}
