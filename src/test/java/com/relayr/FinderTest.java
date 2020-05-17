package com.relayr;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class FinderTest {

  @Test
  public void testShouldReturnOneWordForSmallDataSet() {
    String[] dictionary = new String[5];
    dictionary[0] = "asd";
    dictionary[1] = "asdd";
    dictionary[2] = "fre";
    dictionary[3] = "glk";
    dictionary[4] = "lkm";
    Finder finder = new Finder(dictionary);
    String[] results = finder.find("sad");
    Assert.assertArrayEquals(new String[] {"asd"}, results);
  }

  @Test
  public void testShouldReturnEmptyResult() {
    String[] dictioanry = new String[5];
    dictioanry[0] = "asd";
    dictioanry[1] = "asdd";
    dictioanry[2] = "fre";
    dictioanry[3] = "glk";
    dictioanry[4] = "lkm";
    Finder finder = new Finder(dictioanry);
    String[] results = finder.find("sada");
    Assert.assertArrayEquals(new String[0], results);
  }

  @Test
  public void testShouldReturnMoreThanOneWordForLargeDataSet() throws IOException {

    List<String> dictionary = fetchWordsFromFile();
    Finder finder = new Finder(dictionary.toArray(new String[0]));
    String[] expected = {"erste", "ester", "reset", "reste", "steer", "teres", "terse", "trees"};
    String[] actual = finder.find("trees");
    Arrays.sort(expected);
    Arrays.sort(actual);
    Assert.assertArrayEquals(expected, actual);
  }

  @Test
  public void testShouldReturnQuicklyFor69903WordsDatSet() throws IOException {

    List<String> dictionary = fetchWordsFromFile();
    long statTime = System.nanoTime();
    Finder finder = new Finder(dictionary.toArray(new String[0]));
    String[] expected = {"erste", "ester", "reset", "reste", "steer", "teres", "terse", "trees"};
    String[] actual = finder.find("trees");
    long endTime = System.nanoTime();
    long elapsedTime = endTime - statTime;
    System.out.printf(
        "Total elapsed second for 69903 word data set : %d %n",
        TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS));
  }

  @Test
  public void testShouldReturnResultWithin3SecFor69903WordsAnd1000000Iterations()
      throws IOException {

    List<String> dictionary = fetchWordsFromFile();
    String word;
    long statTime = System.nanoTime();
    Finder finder = new Finder(dictionary.toArray(new String[0]));
    Random random = new Random();
    for (int i = 0; i < 1000000; i++) {
      word = dictionary.get(random.nextInt(dictionary.size()));
      String[] actual = finder.find(word);
    }
    long endTime = System.nanoTime();
    long elapsedTime = endTime - statTime;
    System.out.printf(
        "Total elapsed second for 69903 word data set and 1000000 iteration: %d %n",
        TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS));
  }

  private List<String> fetchWordsFromFile() throws IOException {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    InputStream in = classLoader.getResourceAsStream("word.txt");
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
    String word;
    List<String> dictionary = new ArrayList<>();
    while ((word = bufferedReader.readLine()) != null) {
      dictionary.add(word);
    }
    return dictionary;
  }
}
