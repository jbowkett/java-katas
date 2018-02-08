package info.bowkett.katas.bloom;

import java.nio.ByteBuffer;

/**
 * Created by jbowkett on 18/08/2014.
 */
public class CharHashTest {

    public static void main(String[] args) {

//    String s1 = new String("String");
//
//    String s2 = new String("String");
//
//    System.out.println(s1.hashCode() +"  "+s1.hashCode());
//    System.out.println(s1.toCharArray().hashCode() +"  "+s1.toCharArray().hashCode());

       final Double maxValue = Double.MAX_VALUE;
       System.out.println("maxValue = " + maxValue);
       final Byte b = maxValue.byteValue();
       System.out.println("b = " + b);
       final double v = b.doubleValue();
       System.out.println("v = " + v);
       final long toUnsignedLong = Byte.toUnsignedLong(b);
       System.out.println("toUnsignedLong = " + toUnsignedLong);
       byte[] bytes = toByteArray(maxValue);
       System.out.println("toDouble(bytes) = " + toDouble(bytes));

       final int maxInt = Integer.MAX_VALUE;
       System.out.println("maxInt = " + maxInt);
       final byte[] intBytes = toByteArray(maxInt);
       System.out.println("toByteArray(maxInt) = " + intBytes);
       System.out.println("toInt(intBytes) = " + toInt(intBytes));

       System.out.println("Short.MAX_VALUE = " + Short.MAX_VALUE);

       final int result = 8 % 10;
       System.out.println("result = " + result);

   }


   public static byte[] toByteArray(double value) {
       byte[] bytes = new byte[8];
       ByteBuffer.wrap(bytes).putDouble(value);
       return bytes;
   }

   public static double toDouble(byte[] bytes) {
       return ByteBuffer.wrap(bytes).getDouble();
   }

   public static byte[] toByteArray(int value) {
      byte[] bytes = new byte[4];
      ByteBuffer.wrap(bytes).putInt(value);
      return bytes;
   }

   public static double toInt(byte[] bytes){
       return ByteBuffer.wrap(bytes).getInt();
   }
}
