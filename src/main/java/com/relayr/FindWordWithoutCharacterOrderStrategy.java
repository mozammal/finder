package com.relayr;

import java.util.*;

// concrete Strategy
public class FindWordWithoutCharacterOrderStrategy implements FindWordStrategy {
  Map<String, List<String>> inMemoryCache;

  public FindWordWithoutCharacterOrderStrategy(String[] dictionary) {
    inMemoryCache = new HashMap<>();
    populateCache(dictionary);
  }

  private void populateCache(String[] dictionary) {
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

  @Override
  public String[] find(String word) {
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
