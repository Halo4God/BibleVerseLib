package com.bibleverse;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class BibleVerseLibraryTest {
	
	@Test
	void testGetVerse() {
		
		BibleVerseLibrary bible = new BibleVerseLibrary();
		
		// Retrieves John 3:16 from the Bible
		String result = bible.getVerse("John", 3, 16);
		
		// Checks if the returned verse contains the expected scripture and assertTrue passes if the text matches
		assertTrue(result.contains("For God so loved the world"));
	}
	
	@Test
	void testInvalidChapter() {
		
		BibleVerseLibrary bible = new BibleVerseLibrary();
		
		// Tests for an Invalid Chapter, John is a valid book, Chapter 99 does not exist
		assertThrows(IllegalArgumentException.class, () -> bible.getVerse("John", 99, 16));
	}
	
	@Test
	void testInvalidVerse() {
		
		BibleVerseLibrary bible = new BibleVerseLibrary();
		
		// Tests for an Invalid Verse, John is a valid book, Chapter 3 exists but verse 999 does not
		assertThrows(IllegalArgumentException.class, () -> bible.getVerse("John", 3, 999));
	}
	
	@Test
	void testInvalidKeyword() {
		
		BibleVerseLibrary bible = new BibleVerseLibrary();
		
		// Tests to see if null values can pass
		assertThrows(IllegalArgumentException.class, () -> bible.searchVerses(null));
		
		// Tests to see if empty string can pass
		assertThrows(IllegalArgumentException.class, () -> bible.searchVerses(""));
	}
	
	@Test
	void testSearchWithResults() {
		
		BibleVerseLibrary bible = new BibleVerseLibrary();
		
		// Searches for all the mentions of the word faith in the bible
		List<String> result = bible.searchVerses("faith");
		
		// Checks that the result list is not empty, meaning matches were found
		assertFalse(result.isEmpty());
	}
	
	@Test
	void testSearchWithoutResults() {
		
		BibleVerseLibrary bible = new BibleVerseLibrary();
		
		// Searches the Bible for a string that should not exist
		List<String> result = bible.searchVerses("123456789");
		
		// Checks that the results list is empty, meaning no matches were found
		assertTrue(result.isEmpty());
	}
}
