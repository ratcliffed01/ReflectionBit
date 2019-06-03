//to compile - C:\JobTests\com.mssint.concept>javac -cp ../ IntTest.java
//to execute - C:\JobTests\com.mssint.concept>java -cp ../ com.mssint.concept.IntTest

//package com.mssint.concept;
package MSS;
import java.io.*;

public class IntTest {
	public static class Int {
		//store is where to store the bit patterns for all instances,
		//starting at bit offset for bitlen bits.
		private static byte [] store = new byte[8];
		private static int bitOffset;
		private short offset;
		private byte bitlen;
		
		Int(int bitLength) {
			offset = (short) bitOffset;
			bitlen = (byte) bitLength;
			bitOffset += bitlen;
		}
		
		public Int set(int value) {
			//Fill this method in. We do not want to see single bit at a time operations; you
			//will need use masks, etc.

			if (offset == 0){
				for (int k = 0; k < 8; k++)
					store[k] = 0;
			}
			double x = 2.0;
			double y = Double.parseDouble(Byte.toString(bitlen));
			int j = (int)Math.pow(x,y) - 1;
			
			//check value is less than bitlen allows, if greater then set to bit len
			if (value > j) value = j;

			//System.out.println("val="+value+" offset="+offset+" j="+j+" bl="+bitlen);
			//create a string of all the bits from the store
			String bstr = "";
			int i = 7;
			int pos = 0;
			String zero = "00000000000000000000000000000000000000000000000000000000000";

			//concatenate byte binary string to get all set bits
			while (i > -1){
				//cast byte array to int, making allowances for 8th bit which needs to be set for int.
				pos = (int)store[i];
				if (pos == -128) pos = 128;	//only if bit 8 is set should - be used, -10000000 mens 128
				if (pos < 0)  pos = pos*-1 + 128;

				//System.out.println("pos="+pos+" stor="+Integer.toBinaryString(pos)+" i="+i);

				bstr += zero.substring(Integer.toBinaryString(pos).length(),8) + 
						Integer.toBinaryString(pos);
				i--;
			}
			//System.out.println("b4bstr="+bstr+" bl="+bstr.length());
			//ensure length matches offset
			if (bstr.length() > offset) bstr = bstr.substring(bstr.length() - offset);
		
			// add the new bit string to the store string
			if (offset == 0)
				bstr = zero.substring(bstr.length(),bitlen) + Integer.toBinaryString(value);
			else
				bstr = Integer.toBinaryString(value) + zero.substring(bstr.length(),offset)+bstr;

			//System.out.println("bstr="+bstr+" bl="+bstr.length());

			//reload store array from new string
			String minusStr = "";
			pos = bstr.length() - 8;
			i = 0;
			while (pos > -1){
				minusStr = bstr.substring(pos,pos+8);
				//if 1st bit is set, out of range for byte, make 1st char -
				if (minusStr.charAt(0) == '1') minusStr = "-" + minusStr.substring(1);
				if (minusStr.equals("-0000000")) minusStr = "-10000000"; 	//this occurs only for 128

				//set byte array from string which may contain a -
				store[i] = Byte.valueOf(minusStr,2);
				pos -= 8;
				i++;

				//if offset not on byte boundary get last remains and set store then finish loop
				if (pos < 0 && pos > -8){
					minusStr = bstr.substring(0,pos+8);
					if (minusStr.charAt(0) == '1' && minusStr.length() == 8) 
						minusStr = "-" + minusStr.substring(1);
					if (minusStr.equals("-0000000")) minusStr = "-10000000"; 	//this occurs only for 128
					store[i] = Byte.valueOf(minusStr,2);
					break;
				}
			}
			return this;
		}
		
		public int get() {
			//start at 7 and loop backwards through stored array.
			String bstr = "";
			int i = 7;
			int pos = 0;
			while (i > -1){
				pos = (int)store[i];
				if (pos == -128) pos = 128;	//only if bit 8 is set should - be used, -10000000 means 128
				if (pos < 0)  pos = pos*-1 + 128;

				bstr += "00000000".substring(Integer.toBinaryString(pos).length()) + 
						Integer.toBinaryString(pos);
				i--;
			}
			int val = 0;
			if (bstr.length() > 0){
				bstr = bstr.substring(bstr.length() - 64);		//8 bytes array
				val = Integer.valueOf(bstr.substring(bstr.length() - (offset + bitlen), bstr.length() - offset),2);
			}
			return val;
		}
	}
	
	public void check(int expected, Int val) {
		if(expected != val.get()) {
			throw new IllegalStateException("Expected="+expected+" actual="+val.get());
		}
	}
	
	public void checkException(int expected, Int val) {
		if(expected == val.get()) {
			throw new IllegalStateException("Not Expected="+expected+" actual="+val.get());
		}
	}
	
	public void run() {
		Int val1 = new Int(11); //range 0 to 2047 
		Int val2 = new Int(3); //range 0 to 7
		Int val3 = new Int(18); //range 0 to 262143
		Int val4 = new Int(5); //range 0 to 31
		Int val5 = new Int(20); //range 0 to 1048575
		Int val6 = new Int(7); //range 0 to 127
		
		val1.set(231);
		val2.set(5);
		val3.set(123456);
		val4.set(27);
		val5.set(1032648);
		val6.set(103);
		check(231,val1);
		check(5,val2);
		check(123456,val3);
		check(27,val4);
		check(1032648,val5);
		check(103,val6);
		
		//Test legal values:
		for(int i=0; i<1048575; i++) {
			val1.set(i);
			val2.set(i);
			val3.set(i);
			val4.set(i);
			val5.set(i);
			val6.set(i);
			if(i < 2048) check(i, val1);
			if(i < 8) check(i, val2);
			if(i < 262144) check(i, val3);
			if(i < 32) check(i, val4);
			if(i < 1048576) check(i, val5);
			if(i < 128) check(i, val6);
		}
		//Test out of bounds values
		checkException(2048, val1.set(2048));
		checkException(8, val2.set(8));
		checkException(262144, val3.set(262144));
		checkException(32, val4.set(32));
		checkException(1048576, val5.set(1048576));
		checkException(128, val6.set(128));
		System.out.println("All tests completed");
	}
	
	public static void main(String[] args) {
		long time = System.currentTimeMillis();

		IntTest demo = new IntTest();
		demo.run();
		time = System.currentTimeMillis() - time;
		System.out.println("Execution time=" + time + "ms");
	}

}
