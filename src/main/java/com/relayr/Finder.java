package com.relayr;

// Main class
public class Finder {
  private FindWordStrategy findWordStrategyAlgorithm;

  public Finder(String[] dictionary) {
    findWordStrategyAlgorithm = new FindWordWithoutCharacterOrderStrategy(dictionary);
  }

  public String[] find(String word) {
    return findWordStrategyAlgorithm.find(word);
  }
}
