package info.bowkett.katas.bloom;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by jbowkett on 05/08/2014.
 */
public class BloomFilter {

  private static final int DEFAULT_SIZE_M = 100_000_000;
  private static final int DEFAULT_SIZE_K = 5;
  //sha is max 64 bytes, need 4 byte chunks for each hash
  public static final int MAX_SIZE_K = 64 / 4;

  private final boolean [] bloom;
  private final int kSize;

  /**
   * @param mSize - the size of m, or the number of bits
   * @param kSize - the number of hashing functions to apply
   */
  public BloomFilter(int mSize, int kSize) {
    if(kSize > MAX_SIZE_K) {
      throw new IllegalArgumentException("Max value for K is "+MAX_SIZE_K);
    }
    this.kSize = kSize;
    bloom = new boolean[mSize];
  }

  public BloomFilter(){
    this(DEFAULT_SIZE_M, DEFAULT_SIZE_K);
  }

  public void add(String input) {
    for (int i = 0; i < kSize; i++) {
      bloom[computeNthHashFor(input, i)] = true;
    }
  }

  public boolean contains(String input) {
    //bloom[hash_1 of a] &&
    //bloom[hash_2 of a] &&
    //bloom[hash_..k of a]
    boolean present = true;
    for (int i = 0; i < kSize; i++) {
      present &= bloom[computeNthHashFor(input, i)];
    }
    return present;
  }

  public void addAll(Stream<String> allDictionary) {
    allDictionary.forEach(s -> add(s));
  }

  public int computeNthHashFor(String input, int i) {
    try {
      final MessageDigest digestCreator = MessageDigest.getInstance("SHA-512");
      // Always yields 64 bytes in length
      final byte[] digest = digestCreator.digest(input.getBytes());

      //get the correct 4 bytes out to create an integer 0 => 0-3, 1 => 4-7
      final byte [] region = getNthRegion(digest, i);
      final int index = Math.abs(toInt(region));
      //modulo the hash with the number of M
      final int indexInBounds = index % bloom.length;
      return indexInBounds;
    }
    catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return -1;
    }
  }

  protected static int toInt(byte[] bytes) {
    return ByteBuffer.wrap(bytes).getInt();
  }

//  public <T> T[] getNthRegion(T[] world, int i) {
  public byte[] getNthRegion(byte[] world, int i) {
    final int startIndex = i * 4;
    final int endIndex = startIndex + 4;
    return Arrays.copyOfRange(world, startIndex, endIndex);
  }
}
