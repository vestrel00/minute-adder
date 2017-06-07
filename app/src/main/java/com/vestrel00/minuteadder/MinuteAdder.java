/*
 * Copyright 2017 Vandolf Estrellado
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vestrel00.minuteadder;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides a collection of utility methods for time manipulation.
 * <p>
 * <strong>NOTE 1</strong>: This class' methods could be static. We choose not to make
 * it static for the following reasons:
 * <ul>
 * <li>
 * Mocking this class would be possible without PowerMockito to mock static methods
 * </li>
 * <li>
 * If we ever need to add state (fields) here, there would be no need for a refactor because
 * it's usages are already non-static (requiring an object instance).
 * </li>
 * </ul>
 * <strong>NOTE 2</strong>: This class could be final. We choose not to make it final in order
 * for it to be mockable by Mockito. Mockito2 does allow for mocking finals but most projects
 * are still on Mockito (1).
 */
class MinuteAdder {

    /**
     * The regular expression for matching and capturing 12-hour time strings.
     * <p>
     * You may test this regex online, see https://regex101.com/r/Ikj3p1/12/tests
     */
    private static final String TIME_REGEX = "^([1-9]|1[0-2]):([0-5]\\d) (AM|PM)$";
    private static final Pattern TIME_PATTERN = Pattern.compile(TIME_REGEX);

    private static final String TIME_FORMAT = "%d:%02d %s";
    private static final String AM = "AM";
    private static final String PM = "PM";
    private static final int MINUTES_IN_DAY = 60 * 24;

    /**
     * Adds the given minutesToAdd to the given 12-hour (AM-PM) timeStr.
     * <p>
     * This uses the regular expression "{@value #TIME_REGEX}" to match the given timeStr.
     * <p>
     * Examples:
     * <ul>
     *      <li>addMinutes("1:00 AM", -60) returns "12:00 AM"</li>
     *      <li>addMinutes("1:00 AM", -61) returns "11:59 PM"</li>
     *      <li>addMinutes("11:00 PM", 59) returns "11:59 PM"</li>
     *      <li>addMinutes("11:00 PM", 60) returns "12:00 AM"</li>
     *      <li>addMinutes("9:13 AM", 200) returns "12:33 PM"</li>
     *      <li>addMinutes("9:13 AM", -200) returns "5:53 AM"</li>
     *      <li>addMinutes("9:00 AM", 1440) returns "9:00 AM"</li>
     *      <li>addMinutes("9:00 AM", -1440) returns "9:00 AM"</li>
     *      <li>addMinutes("9:00 AM", 720) returns "9:00 PM"</li>
     *      <li>addMinutes("9:00 AM", -720) returns "9:00 PM"</li>
     *      <li>addMinutes("9:00 AM", 0) returns "9:00 AM"</li>
     * </ul>
     *
     * @param timeStr      a 12-hour time string
     * @param minutesToAdd number of minutes to add to the given timeStr
     * @return a new 12-hour time string with the given minutes added in the format {@value #TIME_FORMAT}
     * @throws IllegalArgumentException if the given timeStr has an invalid format (it does not match
     *                                  "{@value #TIME_REGEX}") or if it is null or empty
     */
    String addMinutes(String timeStr, int minutesToAdd) {
        if (timeStr == null) {
            throw new IllegalArgumentException("The given timeStr must not be null");
        }

        Matcher matcher = TIME_PATTERN.matcher(timeStr);
        if (!matcher.find()) {
            throw new IllegalArgumentException("The given timeStr " + timeStr
                    + " does not match the pattern " + TIME_PATTERN);
        }

        String hourStr = matcher.group(1);
        String minuteStr = matcher.group(2);
        String ampm = matcher.group(3);

        return addMinutes(hourStr, minuteStr, ampm, minutesToAdd);
    }

    private String addMinutes(String hourStr, String minuteStr, String ampm, int minutesToAdd) {
        // first convert to minutes of day, which is a number from 0 (0:00 AM) to 1439 (11:59 PM)
        int minuteOfDay = fromTimeStringToMinuteOfDay(hourStr, minuteStr, ampm);

        // add the minutesToAdd and ensure that it is within MINUTES_IN_DAY
        minuteOfDay += minutesToAdd;
        minuteOfDay = minuteOfDay % MINUTES_IN_DAY;

        // minuteOfDay can now be a negative number so we need to normalize it to [0, 1440)
        if (minuteOfDay < 0) {
            minuteOfDay = MINUTES_IN_DAY + minuteOfDay;
        }

        // then convert back to its string representation
        return fromMinuteOfDayToTimeString(minuteOfDay);
    }

    private int fromTimeStringToMinuteOfDay(String hourOfHalfDayStr, String minuteOfHourStr, String ampm) {
        int hourOfHalDay = Integer.valueOf(hourOfHalfDayStr);
        int minuteOfHour = Integer.valueOf(minuteOfHourStr);

        int hourOfDay;
        if (hourOfHalDay == 12) {
            // normalize 12 AM/PM
            hourOfDay = AM.equals(ampm) ? 0 : 12;
        } else {
            hourOfDay = AM.equals(ampm) ? hourOfHalDay : hourOfHalDay + 12;
        }

        return (hourOfDay * 60) + minuteOfHour;
    }

    private String fromMinuteOfDayToTimeString(int minuteOfDay) {
        int minuteOfHour = minuteOfDay % 60;
        String ampm = minuteOfDay < MINUTES_IN_DAY / 2 ? AM : PM;

        int hourOfHalfDay = (minuteOfDay / 60) % 12;
        if (hourOfHalfDay == 0) {
            // check for 0:XX (AM|PM) and transform that to 12:XX (AM|PM)
            hourOfHalfDay = 12;
        }

        return String.format(Locale.US, TIME_FORMAT, hourOfHalfDay, minuteOfHour, ampm);
    }
}