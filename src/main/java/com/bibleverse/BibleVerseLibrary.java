
package com.bibleverse;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;

public class BibleVerseLibrary {

	// Used to generate random numbers and to read the JSON files
	private final Random random = new Random();
	private final Gson gson = new Gson();
	
	// These classes are used to store the information from the JSON files
	private static class Book {
		String book;
		List<Chapter> chapters;
	}
	
	private static class Chapter {
		int chapter;
		List<Verse> verses;
	}
	
	private static class Verse {
		int verse;
		String text;
	}
	
	// Loads the requested Bible book from the JSON file
	private Book loadBook(String bookName) {
		
		// Creates the filepath based on the book name
		String filepath = "/bible/" + bookName + ".json";
		try (InputStream stream = getClass().getResourceAsStream(filepath)) {
			
			// Checks if the book exists
			if (stream == null) {
				
			    throw new IllegalArgumentException("Bible book not found: " + bookName);
			    
			}
			
			// Converts the JSON file into a Book object using Gson
			Book book = gson.fromJson(new InputStreamReader(stream, StandardCharsets.UTF_8), Book.class);

			return book;
			
		} catch (IllegalArgumentException e) {
			throw e;
		    
		} catch (Exception e) {
			
			// Throws an exception if something goes wrong while loading the book
		    throw new IllegalStateException("Error loading Bible book: " + bookName, e);
		}
		
	}
	
	// Array containing all 66 Bible books
	private static final String[] BOOKS = {
		    // Old Testament (39 books)
			"Genesis", "Exodus", "Leviticus", "Numbers", "Deuteronomy", "Joshua", "Judges", "Ruth", "1Samuel", "2Samuel",
			"1Kings", "2Kings", "1Chronicles", "2Chronicles", "Ezra", "Nehemiah", "Esther", "Job", "Psalms", "Proverbs",
		    "Ecclesiastes", "SongofSolomon", "Isaiah", "Jeremiah", "Lamentations", "Ezekiel", "Daniel", "Hosea", "Joel",
		    "Amos", "Obadiah", "Jonah", "Micah", "Nahum", "Habakkuk", "Zephaniah", "Haggai", "Zechariah", "Malachi",
		    // New Testament (27 books)
		    "Matthew", "Mark", "Luke", "John", "Acts", "Romans", "1Corinthians", "2Corinthians", "Galatians", "Ephesians",
		    "Philippians", "Colossians", "1Thessalonians", "2Thessalonians", "1Timothy", "2Timothy", "Titus", "Philemon",
		    "Hebrews", "James", "1Peter", "2Peter", "1John", "2John", "3John", "Jude", "Revelation"
		};
	
	// Returns a random verse from the specified Bible book
	public String getRandomVerse(String bookName) {
		
		// Loads the Bible book using the loadBook method
		Book book = loadBook(bookName);
		
			
			List<Chapter> chapters = book.chapters;
			
			// Selects a random chapter and makes sure it contains verses
			Chapter chapter;
			do { chapter = chapters.get(random.nextInt(chapters.size())); 
			
			} while (chapter.verses.isEmpty());
			
			// Selects a random verse from the chosen chapter
			Verse verse = chapter.verses.get(random.nextInt(chapter.verses.size()));
			
			// Returns the book name, chapter, verse number and scripture
			return book.book + " " + chapter.chapter + ":" + verse.verse + " - " + verse.text;
	} 
	
	// Retrieves a specific verse using the book name, chapter and verse number
	public String getVerse(String bookName, int chapter, int verse) {
		
			
			Book book = loadBook(bookName);

			List<Chapter> chapters = book.chapters;
			
			// Checks if the requested chapter is valid
			if (chapter < 1 || chapter > chapters.size()) {
				
				throw new IllegalArgumentException("Invalid Chapter: " + chapter);
			}
			
			// Gets the chapter using its index and subtracts 1 cause java indexs start at 0 and the bible doesnt have verse 0
			Chapter selectedChapter = chapters.get(chapter - 1);
			
			// Checks if the requested verse exists in the chapter
			if (verse < 1 || verse > selectedChapter.verses.size()) {

				throw new IllegalArgumentException("Invalid Verse: " + verse);
			}
			
			// Gets the requested verse from the selected chapter
			Verse selectedVerse = selectedChapter.verses.get(verse - 1);

			return bookName + " " + chapter + ":" + verse + " " + "- " + selectedVerse.text + " ";
			
		
	}
	
	// Returns a random verse from any book in the Bible
	public String getRandomVerse() {

		// Generates a random index based on the number of books
		int randomIndex = random.nextInt(BOOKS.length);
		
		// Gets the book name at the randomly selected index
		String selectedBook = BOOKS[randomIndex];
		
		return getRandomVerse(selectedBook);
		
	}
	
	// Returns all the verses from a specific chapter
	public String getChapter(String bookName, int chapter) {
		
		Book book = loadBook(bookName);
			
			List<Chapter> chapters = book.chapters;
			
			// Checks if the requested chapter exists
			if (chapter < 1 || chapter > chapters.size()) {
				
				throw new IllegalArgumentException("Invalid Chapter: " + chapter);
			}
			
			Chapter selectedChapter = chapters.get(chapter - 1);

			
			StringBuilder result = new StringBuilder();
			
			// Adds the book name and chapter number at the beginning
			result.append(bookName).append("   ").append(chapter).append("\n\n\n");
			
			// Loops through every verse in the selected chapter
			for (Verse currentVerse : selectedChapter.verses) {
				
				
				result.append(currentVerse.verse).append(" - ").append(currentVerse.text).append("\n");
			} 
			
			return result.toString();
		
	}
	
	// Searches the entire Bible for verses containing a specific word or phrase
	public List<String> searchVerses(String keyword) {
		
		// Makes sure the keyword is not null or empty
		if (keyword == null || keyword.isBlank()) {
			throw new IllegalArgumentException("Search keyword cannot be null or empty");
		}
		
		// ArrayList is used since we don't know how many results will be found
		List<String> results = new ArrayList<>();

		String searchKeyword = keyword.toLowerCase(java.util.Locale.ROOT);
		
		// Loops through all books
		for (String bookName : BOOKS) {
			
			Book book = loadBook(bookName);

				// Loops through every chapter in the current book
				for (Chapter currentChapter : book.chapters) {
					
					// Loops through every verse in the current chapter
					for (Verse currentVerse : currentChapter.verses) {
						
						// Checks if the scripture contains the keyword
						if (currentVerse.text.toLowerCase(java.util.Locale.ROOT).contains(searchKeyword)) {
							
							// Adds the matching verse and its reference to the results list
							results.add(bookName + " " + currentChapter.chapter + ":" + currentVerse.verse + " " + "- " + currentVerse.text + " ");
						}
					}
				}
				
			
			} 
		
		// Returns all matching verses, or an empty list if none were found
		return results;
		
	}

}
