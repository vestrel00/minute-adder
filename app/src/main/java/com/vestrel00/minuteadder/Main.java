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

/**
 * Runs some sample inputs for {@link MinuteAdder#addMinutes(String, int)}.
 * Run this via ./gradlew run
 * <p>
 * For a complete set of test cases and input/output verification,
 * run the tests instead: ./gradlew test
 */
public class Main {

    public static void main(String[] args) {
        MinuteAdder minuteAdder = new MinuteAdder();

        System.out.println();
        System.out.println("Running MinuteAdder.addMinutes(timeStr, minutesToAdd)...");
        System.out.println("1:00 AM - 60 minutes = " + minuteAdder.addMinutes("1:00 AM", -60));
        System.out.println("1:00 AM - 61 minutes = " + minuteAdder.addMinutes("1:00 AM", -61));
        System.out.println("11:00 PM + 59 minutes = " + minuteAdder.addMinutes("11:00 PM", 59));
        System.out.println("11:00 PM + 60 minutes = " + minuteAdder.addMinutes("11:00 PM", 60));
        System.out.println("9:13 AM + 200 minutes = " + minuteAdder.addMinutes("9:13 AM", 200));
        System.out.println("9:13 AM - 200 minutes = " + minuteAdder.addMinutes("9:13 AM", -200));
        System.out.println("9:00 AM + 1440 minutes = " + minuteAdder.addMinutes("9:00 AM", 1440));
        System.out.println("9:00 AM - 1440 minutes = " + minuteAdder.addMinutes("9:00 AM", 1440));
        System.out.println("9:00 AM + 720 minutes = " + minuteAdder.addMinutes("9:00 AM", 720));
        System.out.println("9:00 AM - 720 minutes = " + minuteAdder.addMinutes("9:00 AM", 720));
        System.out.println("9:00 AM + 0 minutes = " + minuteAdder.addMinutes("9:00 AM", 0));
    }
}