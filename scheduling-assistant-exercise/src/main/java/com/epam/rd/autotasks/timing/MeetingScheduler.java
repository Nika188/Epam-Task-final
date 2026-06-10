package com.epam.rd.autotasks.timing;


import java.time.*;
import java.util.*;

public class MeetingScheduler implements SchedulingAssistant {

    private final Collection<Developer> team;
    private final LocalDate today;

    private static final Map<String, String> CITY_TO_ZONE = Map.of(
            "Los Angeles", "America/Los_Angeles",
            "New York", "America/New_York",
            "Berlin", "Europe/Berlin",
            "London", "Europe/London",
            "Tokyo", "Asia/Tokyo",
            "Paris", "Europe/Paris",
            "Prague", "Europe/Prague",
            "Samara", "Europe/Samara",
            "Tbilisi", "Asia/Tbilisi"

    );

    public MeetingScheduler(Collection<Developer> team, LocalDate today) {
        this.team = team;
        this.today = today;
    }

    @Override
    public LocalDateTime schedule(long meetingDurationMinutes, MeetingTimingPreferences preferences) {
        List<LocalDate> candidateDates = getCandidateDates(preferences.period);
        boolean earliest = preferences.inPeriod == MeetingTimingPreferences.InPeriodPreference.EARLIEST;
        if (!earliest){
            Collections.reverse(candidateDates);
        }

        for (LocalDate date : candidateDates) {
            LocalDateTime startDateTime = trySchedule(date, meetingDurationMinutes, earliest);
            if (startDateTime != null) {
                return startDateTime;
            }
        }
        return null;
    }

    private List<LocalDate> getCandidateDates(MeetingTimingPreferences.PeriodPreference period) {
        List<LocalDate> dates = new ArrayList<>();
        switch (period) {
            case TODAY -> dates.add(today);
            case TOMORROW -> dates.add(today.plusDays(1));
            case THIS_WEEK -> {
                LocalDate start = today;
                LocalDate end;
                if (today.getDayOfWeek()==(DayOfWeek.SUNDAY)){
                    end = today.plusDays(6);
                }else{
                    end = today.with(DayOfWeek.SATURDAY);
                }
                while (!start.isAfter(end)) {
                    dates.add(start);
                    start = start.plusDays(1);
                }
            }
        }
        return dates;
    }

    private LocalDateTime trySchedule(LocalDate date, long meetingDurationMinutes, boolean earliest) {
        if (earliest){
            for (int hour = 0; hour < 24; hour++) {
                for (int min = 0; min < 60; min ++) {
                    LocalTime candidateTime =LocalTime.of(hour, min);
                    LocalDateTime candidate = LocalDateTime.of(date, candidateTime);
                    if (isValidForAllDevelopers(candidate, meetingDurationMinutes)) {
                        return candidate;
                    }
                }
            }
        }else{
            LocalTime candidateTime =LocalTime.of(0, 0);
            LocalDateTime candidate = LocalDateTime.of(date, candidateTime);
            if (isValidForAllDevelopers(candidate, meetingDurationMinutes)) {
                return candidate;
            }
            for (int hour = 23; hour >= 0; hour--) {
                for (int min = 59; min >=0; min --) {
                    candidateTime =LocalTime.of(hour, min);
                    candidate = LocalDateTime.of(date, candidateTime);
                    if (isValidForAllDevelopers(candidate, meetingDurationMinutes)) {
                        return candidate;
                    }
                }
            }
        }
        return null;
    }

    private boolean isValidForAllDevelopers(LocalDateTime start, long durationMinutes) {
        for (Developer dev : team) {
            ZoneId zone = getZoneId(dev);
            ZonedDateTime localStart = start.atZone(ZoneOffset.UTC).withZoneSameInstant(zone);
            LocalTime localTime = localStart.toLocalTime();
            if (localTime.isBefore(dev.workDayStartTime) ||
                    localTime.isAfter(dev.workDayStartTime.plusHours(8)) ||
                    localTime.plusMinutes(durationMinutes).isBefore(dev.workDayStartTime) ||
                    localTime.plusMinutes(durationMinutes).isAfter(dev.workDayStartTime.plusHours(8))) {
                return false;
            }
        }
        return true;
    }

    private ZoneId getZoneId(Developer dev) {
        String zoneId = CITY_TO_ZONE.get(dev.city);
        if (zoneId == null) {
            throw new IllegalArgumentException("Unknown city: " + dev.city);
        }
        return ZoneId.of(zoneId);
    }


}