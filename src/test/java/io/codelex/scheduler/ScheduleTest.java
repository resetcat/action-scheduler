package io.codelex.scheduler;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static io.codelex.scheduler.SchedulerService.CSV_PATH;
import static org.junit.jupiter.api.Assertions.*;


public class ScheduleTest {

    private final SchedulerService schedulerService = new SchedulerService();
    private static List<Schedule> schedules = new ArrayList<>();

    @org.junit.Test
    @Before
    public void testGetSchedulesFromCsv() throws IOException {
        try (FileWriter writer = new FileWriter(CSV_PATH)) {
            writer.write("09:00,5\n10:00,6\n11:00,7");
        }
        schedules = schedulerService.getSchedulesFromCsv();
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
        assertTrue(schedulerService.isDayValid(schedules.get(0).bitmask(), DayOfWeek.WEDNESDAY));
        assertTrue(schedulerService.isDayValid(schedules.get(0).bitmask(), DayOfWeek.MONDAY));
        assertFalse(schedulerService.isDayValid(schedules.get(0).bitmask(), DayOfWeek.TUESDAY));
        assertFalse(schedulerService.isDayValid(schedules.get(0).bitmask(), DayOfWeek.SUNDAY));

        assertTrue(schedulerService.isDayValid(schedules.get(1).bitmask(), DayOfWeek.WEDNESDAY));
        assertTrue(schedulerService.isDayValid(schedules.get(1).bitmask(), DayOfWeek.TUESDAY));

        assertTrue(schedulerService.isDayValid(schedules.get(2).bitmask(), DayOfWeek.TUESDAY));
        assertTrue(schedulerService.isDayValid(schedules.get(2).bitmask(), DayOfWeek.WEDNESDAY));
        assertFalse(schedulerService.isDayValid(schedules.get(2).bitmask(), DayOfWeek.FRIDAY));
        assertFalse(schedulerService.isDayValid(schedules.get(2).bitmask(), DayOfWeek.SATURDAY));
    }


}
