import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Test {
    @org.junit.Test
    public void testDemo() throws IllegalAccessException, IOException, InvocationTargetException {
        Application app=new Application();
        app.test();
    }

}
