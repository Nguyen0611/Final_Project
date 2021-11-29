package fpt.toeic.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FunctionCommon {
    private FunctionCommon(){}
	public static LocalDate getDate(String value, String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return LocalDate.parse(value, formatter);
	}

	public static ZonedDateTime getDateTime(String value, String format) {
		if (value.length() <= 10) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
			LocalDate localDate = LocalDate.parse(value, formatter);
			return localDate.atStartOfDay(ZoneId.systemDefault());
		}
        if (format.equals("yyyyMMddHHmm") || StringUtils.isBlank(format)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
            return ZonedDateTime.parse(value, formatter.withZone(ZoneId.systemDefault()));
        }
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		LocalDateTime dateTime = LocalDateTime.parse(value, formatter);
		return ZonedDateTime.of(dateTime, ZoneId.of("GMT+7"));
	}

	public static List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {

		long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
		return IntStream.iterate(0, i -> i + 1).limit(numOfDaysBetween).mapToObj(i -> startDate.plusDays(i))
				.collect(Collectors.toList());
	}
}
