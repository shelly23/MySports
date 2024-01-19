package persistence.daos;

import java.util.List;

import persistence.dtos.Connection;
import persistence.dtos.Settings;
import persistence.dtos.User;
import persistence.exceptions.PersistenceException;

public interface SettingsDAO {

    void create(long user) throws PersistenceException, InterruptedException;

    List<Settings> read() throws PersistenceException, InterruptedException;

    Settings getSettings(long id) throws PersistenceException, InterruptedException;

    void update(Settings settings) throws PersistenceException;

    Settings getForUser(long userId) throws InterruptedException;

    void delete(long id) throws InterruptedException;
}
