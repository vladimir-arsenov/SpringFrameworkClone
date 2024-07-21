package example;

import com.example.ApplicationContext;
import com.example.SupportManager;
import com.example.SupportPhrase;
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
