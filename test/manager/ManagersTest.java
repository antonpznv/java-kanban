package manager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ManagersTest {

    //5. Убедитесь, что утилитарный класс всегда возвращает проинициализированные и готовые
    // к работе экземпляры менеджеров;

    @Test
    void managersShouldReturnInitializedInstances() {
        assertNotNull(Managers.getDefault(), "Должен возвращаться проинициализированный TaskManager");
        assertNotNull(Managers.getDefaultHistory(), "Должен возвращаться проинициализированный HistoryManager");
    }
}