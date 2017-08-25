/** see ../../../../../../../LICENSE for release rights */
package ws.nzen.codec.binary;

import static org.junit.Assert.*;

import org.junit.Test;

/** 
 */
public class ValidatesBase65536
{

	/**
	 * Test method for {@link ws.nzen.codec.binary.Base65536#encode(byte[])}.
	 */
	@Test
	public void testEncode()
	{
		String sourceHello = "hello world";
		String canonHello = "驨ꍬ啯𒁷ꍲᕤ";
		assertEquals("string bytes should match canon", canonHello,
				Base65536.encode( sourceHello.getBytes() ) );
	}

}




















