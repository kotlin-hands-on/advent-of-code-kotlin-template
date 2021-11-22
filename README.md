# Advent of Code Kotlin Template

[Advent of Code][aoc] – an annual event in December since 2015.
Every year since then, with the first day of December, a programming puzzles contest is published every day for twenty-four days.
A set of Christmas-oriented challenges provide any input you have to use to answer using the language of your choice.
We offer you a template prepared to use with [Kotlin][kotlin] language within this repository.

## Workflow
**Advent of Code Kotlin Template** is a particular type of GitHub repository that lets you speed up the setup phase and start writing your AoC solutions immediately.

The general idea is straightforward – to create a new project based on this template, you need to log in to your GitHub account and use the **Use this template** green button.
And remember – **do not fork it!**

After creating a new project based on this template in your account, a dedicated GitHub Actions workflow will start and clean up the code from redundant files.
It will also personalize code to use your username and project name in namespaces and Gradle properties.
How cool is that?

Right after the [@actions-user][actions-user] actor pushes the second commit to your repository, you're ready to clone it within the IntelliJ IDEA.

From now, everything's in your hands!
Join the [Advent of Code][aoc] contest, solve the Day O1 as soon as it is published.

For the following days, copy the `Day01.kt` solution file, its `Day01Test.kt` tests, and name it with an incremented day.

> Remember to join the Kotlin contest!
> 
> To do that, edit your project's _About_ section with ⚙️ icon and add the `aoc-2021-in-kotlin` topic to your project.
> 
> **We will find your repository and count you in our giveaway.** 

## Content

After you create a new project based on the current template repository using the **Use this template** button, a bare minimal scaffold will appear in your GitHub account with the following structure:

```
.
├── README.md                           README file
├── build.gradle.kts                    Gradle configuration created with Kotlin DSL
├── gradle
│   └── wrapper                         Gradle Wrapper
├── gradle.properties                   Gradle configuration properties
├── gradlew                             *nix Gradle Wrapper script
├── gradlew.bat                         Windows Gradle Wrapper script
├── settings.gradle.kts                 Gradle project settings
└── src
    ├── main
    │   ├── kotlin
    │   │   └── com.github.you.project
    │   │       ├── Day.kt              Base class for Day* implementations
    │   │       ├── Day01.kt            An empty implementation for the first AoC day
    │   │       └── utils
    │   │           ├── Resources.kt    Utility class for loading input txt files
    │   │           └── utils.kt        A set of utility methods shared across your classes
    │   └── resources
    │       └── day01.txt               An empty file for the Day 01 input data
    └── test
        └── kotlin
            └── com.github.you.project
                ├── DayTest.kt          Base test class
                └── Day01Test.kt        Class to test your implementation against test data 
```

After the first puzzle appears, go to the `Day01.kt` and for each `part1` and `part2` classes, provide an algorithm implementation using the provided `input` data loaded from the `day01.txt` file.
This input data is common for both parts, and you can find it on the bottom of each day on the [Advent of Code][aoc] page.

To read the input data as a list of strings, you can go with the `String.ints()` utility method provided in the [`utils.kt`][file:utils] file, like:

```kotlin
class Day01 : Day(1) {

    override fun part1(input: String): Int {
        return input.ints().sum()
    }

    // ...
}
```

This file also contains the `String.md5()` method for generating MD5 has out of the given string and expects more helper functions for the sake of the [KISS principle][kiss].

To check if everything works as expected during the development, you can use the test data and answers within each day's story and provide them for your `DayTest` test implementation.
You may want to run such a test case by clicking the _Test All Days_ Run/Debug Configuration provided in the top-right toolbar or check the test result for each day separately.

To go with the next day, place the `day02.txt` file into the `resources` with relevant input data, create `Day02.kt` file with the class implementation:

```kotlin
class Day02 : Day(2) { 
    // ...
}
```

Then just provide tests for the second day in a similar manner:

```kotlin
class Day02Test : DayTest() {
    
    override val day = Day02()

    @Test
    override fun `Part 1`() {
        assertEquals(0, day.part1("test_input")) // check against test input
        assertEquals(0, day.part1())             // check solution against input data
    }

    // ...
}
```

## Getting help

If you stuck with Kotlin-specific questions or anything related to this template, check out the following resources:

- [Kotlin docs][docs]
- [Kotlin Slack][slack]
- Template [issue tracker][issues]


[actions-user]: https://github.com/actions-user
[aoc]: https://adventofcode.com
[docs]: https://kotlinlang.org/docs/home.html
[issues]: https://github.com/kotlin-hands-on/advent-of-code-kotlin-template/issues
[kiss]: https://en.wikipedia.org/wiki/KISS_principle
[kotlin]: https://kotlinlang.org
[slack]: https://surveys.jetbrains.com/s3/kotlin-slack-sign-up
[file:kotlin]: .github/readme/kotlin.svg
[file:utils]: src/main/kotlin/com/github/kotlinhandson/aoc/utils/utils.kt
