package io.codelex.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class SchedulerService {

    static final String CSV_PATH = "src/main/resources/schedule.csv";
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(
            "HH" + ":mm");

    @Scheduled(fixedRateString = "${fixed.delay.seconds}000")
    public void runScheduler() throws IOException {
        List<Schedule> schedules = getSchedulesFromCsv();
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("Africa/Lagos"));
        DayOfWeek currentDay = currentTime.getDayOfWeek();
        schedules.stream()
                 .filter(s -> s.time()
                               .toString()
                               .equals(currentTime.toLocalTime().format(timeFormatter)) &&
                         isDayValid(s.bitmask(), currentDay))
                 .findFirst()
                 .ifPresent(s -> successMessage(s.time()));
    }

    private void successMessage(LocalTime time) {
        System.out.println("Look at the time its already " + time);
    }

    public boolean isDayValid(int bitmask, DayOfWeek day) {
        int dayIndex = day.getValue() - 1;
        return (bitmask & (1 << dayIndex)) != 0;
    }

    public List<Schedule> getSchedulesFromCsv() throws IOException {
        List<Schedule> schedules = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                schedules.add(new Schedule(LocalTime.parse(parts[0], timeFormatter),
                                           Integer.parseInt(parts[1])));
            }
        }
        return schedules;
    }
}