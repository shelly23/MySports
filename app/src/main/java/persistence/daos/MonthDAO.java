package persistence.daos;

import persistence.dtos.Month;
import persistence.dtos.Type;

public interface MonthDAO {

    void createMonth(long user, String month);

    boolean existsMonth(long user_id, String month) throws InterruptedException;

    void updateMonth(long user, String month, Type type) throws InterruptedException;

    Month getMonth(long user, String month) throws InterruptedException;
}
