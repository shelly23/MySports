package service;

import persistence.dtos.Settings;
import persistence.exceptions.PersistenceException;

public interface SettingsService {

    void createSettings(long user) throws PersistenceException, InterruptedException;

    Settings getUsersSettings(long userId) throws InterruptedException;

    void delete(long id) throws InterruptedException;

    Settings updateSettings(Settings settings) throws InterruptedException, PersistenceException;

}
