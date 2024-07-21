package example;

import com.example.ApplicationContext;
import com.example.SupportManager;
import com.example.SupportManagerImpl;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ApplicationContextTest {
    @Test
    public void application_context_should_return_instance_by_class() throws InvocationTargetException, IllegalAccessException {
        final var applicationContext = new ApplicationContext("com.example.configuration");
        assertNotNull(applicationContext.getInstance(SupportManagerImpl.class));
        assertEquals(SupportManagerImpl.class, applicationContext.getInstance(SupportManager.class).getClass());
    }
}

