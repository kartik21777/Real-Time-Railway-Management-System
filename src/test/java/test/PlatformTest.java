package test;

import application.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class PlatformTest {
    private Platform platform;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    @BeforeEach
    public void setUp() {
        // initialize with id=1, name="A"
        platform = new Platform(1, "A");
    }

    @Test
    public void testGetId() {
        assertEquals(1, platform.getId(), "getId should return initial id");
    }

    @Test
    public void testSetId() {
        platform.setId(99);
        assertEquals(99, platform.getId(), "setId should update the id");
    }

    @Test
    public void testGetPlatformName() {
        assertEquals("A", platform.getPlatformName(), "getPlatformName should return initial name");
    }

    @Test
    public void testSetPlatformName() {
        platform.setPlatformName("B");
        assertEquals("B", platform.getPlatformName(), "setPlatformName should update the name");
    }

    @Test
    public void testGetNextFreeDefault() {
        // default nextFree should be "00:00"
        LocalTime expected = LocalTime.of(0, 0);
        assertEquals(expected, platform.getNextFree(), "getNextFree should return default 00:00");
    }

    @Test
    public void testSetNextFree() {
        LocalTime newTime = LocalTime.of(12, 34);
        platform.setNextFree(newTime);
        assertEquals(newTime, platform.getNextFree(), "setNextFree should update the nextFree time");
    }

    @Test
    public void testToStringFormat() {
        // use default values
        String expected = String.format("Platform{id=%d, name='%s', nextFree='%s'}",
                1, "A", platform.getNextFree().format(formatter));
        assertEquals(expected, platform.toString(), "toString should match the expected format");
    }
}
