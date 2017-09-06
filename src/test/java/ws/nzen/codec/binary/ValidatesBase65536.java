/** see ../../../../../../../LICENSE for release rights */
package ws.nzen.codec.binary;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

/** 
 */
public class ValidatesBase65536
{
	public static void main( String[] args )
	{
		// new ValidatesBase65536().testEncode();
		// new ValidatesBase65536().showBytesOf( "鵨ꍬ???饤" );
		new ValidatesBase65536().testStuff();
	}


	private void testStuff()
	{
		String sourceHello = "hello world";
		//showCodePointsOf( sourceHello );
		//showCodePointsOf( Base65536.encode( sourceHello.getBytes() ) );
		String canonHello = "驨ꍬ啯𒁷ꍲᕤ";
		//showCodePointsOf( canonHello );
		// String notHello = "鵨ꍬ???饤";
		// showCodePointsOf( notHello );
		String doneTwice = new String(Base65536.decode( Base65536.encode( sourceHello.getBytes() ) ));
		showBytesOf( doneTwice );
		showBytesOf( sourceHello );
		System.out.println( "ch is "+ doneTwice );
	}

	/**
	 * Test method for {@link ws.nzen.codec.binary.Base65536#encode(byte[])}.
	 */
	@Test
	public void testEncode()
	{
		String sourceHello = "hello world";
		String canonHello = "驨ꍬ啯𒁷ꍲᕤ";
		/*
		code points
		驨ꍬ啯𒁷ꍲᕤ
		 -23 -87 -88 -22 -115 -84 -27 -107 -81 -16 -110 -127 -73 -22 -115 -78 -31 -107 -92
		鵨ꍬ???饤 (current answer)
		  -23 -75 -88 -22 -115 -84 63 63 63 -23 -91 -92
		*/
		assertEquals("string bytes should match canon", canonHello,
				Base65536.encode( sourceHello.getBytes() ) );
	}


	private void showCodePointsOf( String whatever )
	{
		System.out.print( whatever +" <cp> " );
		Iterator<Integer> cpI = whatever.codePoints().iterator();
		while ( cpI.hasNext() )
		{
			System.out.print( " "+ cpI.next() );
		}
		System.out.println();
	}


	// deprecated
	private void showBytesOf( String whatever )
	{
		System.out.print( whatever +" <bytes> " );
		for ( byte cPoint : whatever.getBytes() )
		{
			System.out.print( " "+ cPoint );
		}
		System.out.println();
	}

}




















