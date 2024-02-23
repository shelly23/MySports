package service;

import java.util.List;

import persistence.dtos.Therapist;

public interface TherapistService {

    List<Therapist> getAlLTherapists() throws InterruptedException;

    Therapist getTherapist(long id) throws InterruptedException;

}
