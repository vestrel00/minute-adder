# MinuteAdder

This project solves the following problem in Java.

> Without using any built-in date or time functions, write a function or method that accepts two
mandatory arguments: the first argument is a 12-hour time string with the format "[H]H:MM {AM|PM}",
and the second argument is a (signed) integer. The second argument is the number of minutes to add to
the time of day represented by the first argument. The return value should be a string of the same
format as the first argument. For example, AddMinutes("9:13 AM", 200) would return "12:33 PM".

The solution is located at *app/src/main/java/com/vestrel00/minuteadder/MinuteAdder.java*, which
is fully documented and unit tested. 

The test class is located at *app/src/test/java/com/vestrel00/minuteadder/MinuteAdderTest.java*.

You may run the unit tests by entering the following command in the command line:

```
./gradlew test
```

You may then view the test results in your browser by opening the html index file located in
app/build/reports/tests/index.html. Assuming you are using a Mac OSX, you can do this by entering
the following command in the command line:

```
open app/build/reports/tests/test/index.html 
```

A main class and main method is also included in this project so that you may run this like an
application and see some sample inputs and outputs in the command line. However, it is recommended 
that you take a look and run the tests instead for a full suite of input/output combinations.

The main class is located at *app/src/main/java/com/vestrel00/minuteadder/MinuteAdder.java*.

You may run the main method by entering the following command in the command line:

```
./gradlew run
```

## Notes

1. Java 7 or above is required to run this project.
2. IntelliJ IDEA CE 2016.2 was used as the IDE for development for this project.
3. Plenty of Javadocs and comments were left in code that explains a lot of the code, including styles and conventions.