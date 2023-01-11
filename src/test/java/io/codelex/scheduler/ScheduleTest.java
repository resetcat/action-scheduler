package io.codelex.scheduler;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ScheduleTest {

    private static final SchedulerService SCHEDULER_SERVICE = new SchedulerService();
    private static List<Schedule> schedules = new ArrayList<>();

    public static final String PATH = "src/test/java/io/codelex/scheduler/scheduleTest.csv";

    @BeforeAll
    public static void testGetSchedulesFromCsv() {
        try (FileWriter writer = new FileWriter(PATH)) {
            writer.write("09:00,5\n10:00,6\n11:00,7");
        } catch (IOException e) {
            System.err.println("Couldn't read path: " + PATH);
        }
        schedules = SCHEDULER_SERVICE.getSchedulesFromCsv(PATH);
    }

    @Test
    public void testGetSchedulesFromCsvIsEmpty() {
        assertFalse(schedules.isEmpty());
    }

    @Test
    public void testGetSchedulesFromCsvSizeIsThree() {
        assertEquals(3, schedules.size());
    }

    @Test
    public void testGetSchedulesFromCsvTime() {
        assertEquals(LocalTime.of(9, 0), schedules.get(0).time());
        assertEquals(LocalTime.of(10, 0), schedules.get(1).time());
        assertEquals(LocalTime.of(11, 0), schedules.get(2).time());
    }

    @Test
    public void testGetSchedulesFromCsvBitmask() {
        assertEquals(5, schedules.get(0).bitmask());
        assertEquals(6, schedules.get(1).bitmask());
        assertEquals(7, schedules.get(2).bitmask());
    }

    @Test
    public void testIsDayValid() {
        assertTrue(SCHEDULER_SERVICE.isDayValid(schedules.get(0).bitmask(), DayOfWeek.WEDNESDAY));
        assertTrue(SCHEDULER_SERVICE.isDayValid(schedules.get(0).bitmask(), DayOfWeek.MONDAY));
        assertFalse(SCHEDULER_SERVICE.isDayValid(schedules.get(0).bitmask(), DayOfWeek.TUESDAY));
        assertFalse(SCHEDULER_SERVICE.isDayValid(schedules.get(0).bitmask(), DayOfWeek.SUNDAY));

        assertTrue(SCHEDULER_SERVICE.isDayValid(schedules.get(1).bitmask(), DayOfWeek.WEDNESDAY));
        assertTrue(SCHEDULER_SERVICE.isDayValid(schedules.get(1).bitmask(), DayOfWeek.TUESDAY));

        assertTrue(SCHEDULER_SERVICE.isDayValid(schedules.get(2).bitmask(), DayOfWeek.TUESDAY));
        assertTrue(SCHEDULER_SERVICE.isDayValid(schedules.get(2).bitmask(), DayOfWeek.WEDNESDAY));
        assertFalse(SCHEDULER_SERVICE.isDayValid(schedules.get(2).bitmask(), DayOfWeek.FRIDAY));
        assertFalse(SCHEDULER_SERVICE.isDayValid(schedules.get(2).bitmask(), DayOfWeek.SATURDAY));
    }


}
