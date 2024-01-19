package service;

import java.util.List;

import persistence.dtos.Connection;
import persistence.dtos.Settings;
import persistence.exceptions.PersistenceException;

public interface SettingsService {

    void createSettings(long user) throws PersistenceException, InterruptedException;

    Settings getUsersSettings(long userId) throws InterruptedException;

    void delete(long id) throws InterruptedException;


}
