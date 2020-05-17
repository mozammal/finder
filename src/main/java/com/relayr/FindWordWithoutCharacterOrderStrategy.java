package com.relayr;

import java.util.*;

// Concrete Strategy
public class FindWordWithoutCharacterOrderStrategy implements FindWordStrategy {
  private static final String[] EMPTY_STRING_ARRAY = new String[0];
  Map<String, List<String>> inMemoryCache;

  public FindWordWithoutCharacterOrderStrategy(String[] dictionary) {
    inMemoryCache = new HashMap<>();
    populateCache(dictionary);
  }

/**
 * this method caches string array by sorting every string so that similar words
 * are placed in the same bucket
 * @param dictionary array of string
 * @version 1.0
 * @author Mozammal Hossain
 */
  private void populateCache(String[] dictionary) {
    if (dictionary == null || dictionary.length == 0)
      return;

    for (String word : dictionary) {
      char[] wordConvertedToCharArray = word.toCharArray();
      Arrays.sort(wordConvertedToCharArray);
      String sortedWord = new String(wordConvertedToCharArray);
      if (!inMemoryCache.containsKey(sortedWord)) {
          inMemoryCache.put(sortedWord, new ArrayList<>());
      }
      inMemoryCache.get(sortedWord).add(word);
    }
  }

  /**
   * this method returns array of string that matched
   * @param word string to find in the array of string
   * @return array if string that matched with the words in the array of string
   * @version 1.0
   * @author Mozammal Hossain
   */
  @Override
  public String[] find(String word) {
    if (word == null || word.length() ==0)
      return EMPTY_STRING_ARRAY;

    char[] wordConvertedToCharArray = word.toCharArray();
    Arrays.sort(wordConvertedToCharArray);
    String sortedWord = new String(wordConvertedToCharArray);
    Optional<List<String>> answers =
        Optional.ofNullable(
                inMemoryCache.get(sortedWord));

    if (answers.isPresent()) {
      return answers.get().toArray(new String[0]);
    }
    return new String[0];
  }
}
