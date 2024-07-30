package example;

import com.example.infrastructure.ioc_container_and_beans.ApplicationContext;
import com.example.business_logic.SupportManager;
import com.example.business_logic.SupportPhrase;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SupportManagerImplTest {

    @Test
    public void support_service_should_return_support_phrase() throws InvocationTargetException, IllegalAccessException {
        final var context = new ApplicationContext("com.example.configuration");
        final var supportService = context.getInstance(SupportManager.class);
        final var supportPhrase = new SupportPhrase("Hey");
        assertEquals(supportPhrase, supportService.provideSupport());
    }
}
