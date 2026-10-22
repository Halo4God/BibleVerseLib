# BibleVerseLib

A lightweight Java library for retrieving and searching Bible verses from the **King James Version (KJV)**. The 66 books are stored as JSON resources inside the JAR, so verse retrieval and searching work **offline**—no API key or internet connection is needed at runtime.

## Features

- Retrieve a random verse from any Bible book or from a chosen book.
- Retrieve a specific verse by book, chapter, and verse number.
- Retrieve all verses in a chapter.
- Search all 66 books for a word or phrase, ignoring capitalization.
- Validate requested chapters, verses, and search keywords.

## Requirements

- **Java 21** or later
- **Gson 2.13.2** for JSON parsing
- **Maven** to build from source (recommended)

JUnit 5 is used for the project's automated tests and is only needed when running those tests.

## Install

### Option A: Build and install from source (recommended for Maven users)

Clone this repository and, in the project directory, run:

```bash
mvn clean install
```

This runs the tests, builds the JAR, and installs the library (with its Maven dependency information) into your local Maven repository.

In the **other** Maven project's `pom.xml`, add:

```xml
<dependency>
    <groupId>com.bibleverse</groupId>
    <artifactId>bible-verse-lib</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

Maven will resolve Gson transitively from the library's POM.

### Option B: Download the JAR

Download `bible-verse-lib-0.0.1-SNAPSHOT.jar` from [GitHub Releases](https://github.com/Halo4God/BibleVerseLib/releases) once a release asset is available, and add it to your project's classpath or build configuration.

**Important:** This is a regular (not shaded) JAR. If using the JAR directly, you must also make **Gson 2.13.2** available to the consuming application. Downloading the JAR alone does not include Gson.

## Usage

Import the library in your Java application:

```java
import com.bibleverse.BibleVerseLibrary;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        BibleVerseLibrary bible = new BibleVerseLibrary();

        System.out.println(bible.getRandomVerse());
        System.out.println(bible.getRandomVerse("John"));
        System.out.println(bible.getVerse("John", 3, 16));
        System.out.println(bible.getChapter("Psalms", 23));

        List<String> matches = bible.searchVerses("faith");
        for (String match : matches) {
            System.out.println(match);
        }
    }
}
```

### Public methods

| Method | Returns | Description |
| --- | --- | --- |
| `getRandomVerse()` | `String` | Random verse from a randomly chosen Bible book |
| `getRandomVerse(String bookName)` | `String` | Random verse from the specified book |
| `getVerse(String bookName, int chapter, int verse)` | `String` | Text and reference for a specified verse |
| `getChapter(String bookName, int chapter)` | `String` | All verses in a chapter, formatted on separate lines |
| `searchVerses(String keyword)` | `List<String>` | Every matching verse and its reference, ignoring case |

Book names should match the JSON filenames, for example `"Genesis"`, `"John"`, or `"1Samuel"`.

### Errors and search behavior

- Invalid book names, chapter numbers, verse numbers, and blank/null search terms cause an `IllegalArgumentException`.
- Errors while reading Bible data may cause an `IllegalStateException`.
- A valid search term with no matches returns an **empty list**, not an exception.
- Search results are substring matches (for example, searching for `"faith"` also matches longer words containing that sequence).
- Random selection currently chooses a book, then a chapter, then a verse; **not every verse has equal probability**.

## Build and test

Run the JUnit tests:

```bash
mvn test
```

Create the JAR (and rerun the tests):

```bash
mvn clean package
```

The compiled library will be at `target/bible-verse-lib-0.0.1-SNAPSHOT.jar`. The Bible JSON resources are packaged inside it; test source files are not.

## Project structure

```text
src/
  main/
    java/com/bibleverse/BibleVerseLibrary.java
    resources/bible/                 # KJV book JSON files
  test/
    java/com/bibleverse/BibleVerseLibraryTest.java
pom.xml
```

## Bible text and attribution

Bible text: **King James Version (KJV)**.

JSON data sourced from [aruljohn/Bible-kjv](https://github.com/aruljohn/Bible-kjv). The source dataset's [LICENSE](src/main/resources/bible/LICENSE) and [README](src/main/resources/bible/README.md) are retained with the resources. Review applicable copyright and licensing requirements before redistributing the Bible text in your jurisdiction.

## Project status

Current version: **0.0.1-SNAPSHOT** (initial development release). Created as an educational Java library project.
