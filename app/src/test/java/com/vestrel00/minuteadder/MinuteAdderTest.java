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

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Test cases for {@link MinuteAdder}. Note that the tests for the {@link MinuteAdder#TIME_REGEX}
 * can be run in https://regex101.com/r/Ikj3p1/12/tests
 * <p>
 * These tests follow a set of standard testing conventions.
 * <ul>
 * <li>
 * The name of the object under test is named as a class field "testSubject".
 * This is to identify the object target that is being tested throughout all test cases.
 * </li>
 * <li>
 * Test methods names have the following format: methodName_whenThisIsTheCase_thenDoesThis
 * This keeps the test focused to a method and it's return value with the given parameters.
 * <p>
 * See http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html
 * </li>
 * <li>
 * Each test is separated into GIVEN-WHEN-THEN sections to clearly organize the test
 * variables, method being tested, and expected outputs.
 * <p>
 * See https://www.javacodegeeks.com/2015/01/given-when-then-in-java.html
 * </li>
 * <li>
 * These tests uses the JUnitParams to make it possible to provide parameters per test method.
 * See https://github.com/Pragmatists/JUnitParams
 * </li>
 * <li>
 * These are also using Hamcrest assertThat for assertions.
 * See https://objectpartners.com/2013/09/18/the-benefits-of-using-assertthat-over-other-assert-methods-in-unit-tests/
 * </li>
 * </ul>
 */
@RunWith(JUnitParamsRunner.class)
public class MinuteAdderTest {

    private MinuteAdder testSubject;

    @Before
    public void setup() throws Exception {
        testSubject = new MinuteAdder();
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters(method = "invalidParametersFor_addMinutes")
    public void addMinutes_withInvalidTime_throwsIllegalArgumentException(String timeStr) throws Exception {
        // WHEN
        testSubject.addMinutes(timeStr, 0);
    }

    @Test
    @Parameters(method = "validParametersFor_addMinutes")
    public void addMinutes_withValidTime_returnsCorrectTime(String timeStr, int minutesToAdd, String expectedTimeStr) throws Exception {
        // WHEN
        String resultTimeStr = testSubject.addMinutes(timeStr, minutesToAdd);

        // THEN
        assertThat(resultTimeStr, is(expectedTimeStr));
    }

    private Object[] invalidParametersFor_addMinutes() {
        return new Object[]{
                new Object[]{timeStr(null)},
                new Object[]{timeStr("")},
                new Object[]{timeStr("1:00")},
                new Object[]{timeStr("-1:00")},
                new Object[]{timeStr("1:00 ")},
                new Object[]{timeStr("1:00 A")},
                new Object[]{timeStr("1:00 M")},
                new Object[]{timeStr("1:00PM")},
                new Object[]{timeStr("AM")},
                new Object[]{timeStr("PM")},
                new Object[]{timeStr("1 AM")},
                new Object[]{timeStr("1PM")},
                new Object[]{timeStr("1: AM")},
                new Object[]{timeStr("1:0 AM")},
                new Object[]{timeStr("-1:00 AM")},
                new Object[]{timeStr("1:-10 AM")},
                new Object[]{timeStr("00:00 AM")},
                new Object[]{timeStr("00:00 PM")},
                new Object[]{timeStr("13:00 PM")},
                new Object[]{timeStr("10:60 PM")},
                new Object[]{timeStr("1:00 am")},
                new Object[]{timeStr("1:00 pm")}
        };
    }

    private Object[] validParametersFor_addMinutes() {
        return new Object[]{
                new Object[]{timeStr("11:59 AM"), minutesToAdd(1), expectedTimeStr("12:00 PM")},
                new Object[]{timeStr("11:59 PM"), minutesToAdd(1), expectedTimeStr("12:00 AM")},
                new Object[]{timeStr("12:00 AM"), minutesToAdd(-1), expectedTimeStr("11:59 PM")},
                new Object[]{timeStr("12:00 PM"), minutesToAdd(-1), expectedTimeStr("11:59 AM")},
                new Object[]{timeStr("12:00 AM"), minutesToAdd(1), expectedTimeStr("12:01 AM")},
                new Object[]{timeStr("12:00 PM"), minutesToAdd(1), expectedTimeStr("12:01 PM")},
                new Object[]{timeStr("12:01 AM"), minutesToAdd(-1), expectedTimeStr("12:00 AM")},
                new Object[]{timeStr("12:01 PM"), minutesToAdd(-1), expectedTimeStr("12:00 PM")},

                new Object[]{timeStr("12:00 AM"), minutesToAdd(60), expectedTimeStr("1:00 AM")},
                new Object[]{timeStr("12:00 PM"), minutesToAdd(60), expectedTimeStr("1:00 PM")},

                new Object[]{timeStr("9:13 AM"), minutesToAdd(200), expectedTimeStr("12:33 PM")},
                new Object[]{timeStr("9:13 AM"), minutesToAdd(-200), expectedTimeStr("5:53 AM")},
                new Object[]{timeStr("9:00 AM"), minutesToAdd(1440), expectedTimeStr("9:00 AM")},
                new Object[]{timeStr("9:00 AM"), minutesToAdd(-1440), expectedTimeStr("9:00 AM")},
                new Object[]{timeStr("9:00 AM"), minutesToAdd(720), expectedTimeStr("9:00 PM")},
                new Object[]{timeStr("9:00 AM"), minutesToAdd(-720), expectedTimeStr("9:00 PM")},
                new Object[]{timeStr("9:00 AM"), minutesToAdd(0), expectedTimeStr("9:00 AM")}
        };
    }

    // Used in order to provide context to each parameter
    private static String timeStr(String timeStr) {
        return timeStr;
    }

    // Used in order to provide context to each parameter
    private static int minutesToAdd(int minutesToAdd) {
        return minutesToAdd;
    }

    // Used in order to provide context to each parameter
    private static String expectedTimeStr(String expectedTimeStr) {
        return expectedTimeStr;
    }
}