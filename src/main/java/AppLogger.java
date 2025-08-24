import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppLogger
{
    // Create a single shared logger instance
    private static final Logger logger = LoggerFactory.getLogger("ApplicationLogger");

    private AppLogger()
    {
        // private constructor to prevent instantiation
    }
    // Info log
    public static void info(String message, Object... args)
    {
        logger.info(message, args);
    }
    // Debug log
    public static void debug(String message, Object... args)
    {
        logger.debug(message, args);
    }
    // Warn log
    public static void warn(String message, Object... args)
    {
        logger.warn(message, args);
    }
    // Error log
    public static void error(String message, Object... args)
    {
        logger.error(message, args);
    }
    // Error log with exception
    public static void error(String message, Throwable t)
    {
        logger.error(message, t);
    }
    // Trace log
    public static void trace(String message, String simpleName)
    {
        logger.trace(message);
    }
}
