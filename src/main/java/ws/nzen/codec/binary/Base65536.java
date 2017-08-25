/** see ../../../../../../../LICENSE for release rights */
package ws.nzen.codec.binary;

import java.util.HashMap;
import java.util.Map;

/** 
 */
public class Base65536
{
	protected static final int NO_BYTE = -1; 
	protected static Map<Integer, Integer> pointsToEncode
			= new HashMap<>(); // FIX these are shorts in the example


	public static String encode( byte[] input )
	{
		ensureEncodeMapReady();
		StringBuilder result = new StringBuilder( input.length / 2 );
		int front, back; // example has these as shorts
		for ( int ind = 0; ind < input.length; ind += 2 )
		{
			front = (int)input[ ind ];
			if ( ind +1 > input.length )
			{
				back = pointsToEncode.get( NO_BYTE );
			}
			else
			{
				back = (int)input[ ind ];
			}
			char[] fromCodePoint = Character.toChars( front + back );
			// 4TESTS extracted for debugging; only expecting one char
			result.append( fromCodePoint[ 0 ] );
		}
		return result.toString();
	}


	protected static void ensureEncodeMapReady()
	{
		if ( pointsToEncode == null )
		{
			pointsToEncode = new HashMap<>();
		}
		if ( pointsToEncode.isEmpty() )
		{
			pointsToEncode.put( NO_BYTE, -1 );
			pointsToEncode.put(   0,  13_312 );
			pointsToEncode.put(   1,  13_568 );
			pointsToEncode.put(   2,  13_824 );
			pointsToEncode.put(   3,  14_080 );
			pointsToEncode.put(   4,  14_336 );
			pointsToEncode.put(   5,  14_592 );
			pointsToEncode.put(   6,  14_848 );
			pointsToEncode.put(   7,  15_104 );
			pointsToEncode.put(   8,  15_360 );
			pointsToEncode.put(   9,  15_616 );

			pointsToEncode.put(  10,  15_872 );
			pointsToEncode.put(  11,  16_128 );
			pointsToEncode.put(  12,  16_384 );
			pointsToEncode.put(  13,  16_640 );
			pointsToEncode.put(  14,  16_896 );
			pointsToEncode.put(  15,  17_152 );
			pointsToEncode.put(  16,  17_408 );
			pointsToEncode.put(  17,  17_664 );
			pointsToEncode.put(  18,  17_920 );
			pointsToEncode.put(  19,  18_176 );

			pointsToEncode.put(  20,  18_432 );
			pointsToEncode.put(  21,  18_688 );
			pointsToEncode.put(  22,  18_944 );
			pointsToEncode.put(  23,  19_200 );
			pointsToEncode.put(  24,  19_456 );
			pointsToEncode.put(  25,  19_968 );
			pointsToEncode.put(  26,  20_224 );
			pointsToEncode.put(  27,  20_480 );
			pointsToEncode.put(  28,  20_736 );
			pointsToEncode.put(  29,  20_992 );

			pointsToEncode.put(  30,  21_248 );
			pointsToEncode.put(  31,  21_504 );
			pointsToEncode.put(  32,  21_760 );
			pointsToEncode.put(  33,  22_016 );
			pointsToEncode.put(  34,  22_272 );
			pointsToEncode.put(  35,  22_528 );
			pointsToEncode.put(  36,  22_784 );
			pointsToEncode.put(  37,  23_040 );
			pointsToEncode.put(  38,  23_296 );
			pointsToEncode.put(  39,  23_552 );

			pointsToEncode.put(  40,  23_808 );
			pointsToEncode.put(  41,  24_064 );
			pointsToEncode.put(  42,  24_320 );
			pointsToEncode.put(  43,  24_576 );
			pointsToEncode.put(  44,  24_832 );
			pointsToEncode.put(  45,  25_088 );
			pointsToEncode.put(  46,  25_344 );
			pointsToEncode.put(  47,  25_600 );
			pointsToEncode.put(  48,  25_856 );
			pointsToEncode.put(  49,  26_112 );

			pointsToEncode.put(  50,  26_368 );
			pointsToEncode.put(  51,  26_624 );
			pointsToEncode.put(  52,  26_880 );
			pointsToEncode.put(  53,  27_136 );
			pointsToEncode.put(  54,  27_392 );
			pointsToEncode.put(  55,  27_648 );
			pointsToEncode.put(  56,  27_904 );
			pointsToEncode.put(  57,  28_160 );
			pointsToEncode.put(  58,  28_416 );
			pointsToEncode.put(  59,  28_672 );

			pointsToEncode.put(  60,  28_928 );
			pointsToEncode.put(  61,  29_184 );
			pointsToEncode.put(  62,  29_440 );
			pointsToEncode.put(  63,  29_696 );
			pointsToEncode.put(  64,  29_952 );
			pointsToEncode.put(  65,  30_208 );
			pointsToEncode.put(  66,  30_464 );
			pointsToEncode.put(  67,  30_720 );
			pointsToEncode.put(  68,  30_976 );
			pointsToEncode.put(  69,  31_232 );

			pointsToEncode.put(  70,  31_488 );
			pointsToEncode.put(  71,  31_744 );
			pointsToEncode.put(  72,  32_000 );
			pointsToEncode.put(  73,  32_256 );
			pointsToEncode.put(  74,  32_512 );
			pointsToEncode.put(  75,  32_768 );
			pointsToEncode.put(  76,  33_024 );
			pointsToEncode.put(  77,  33_280 );
			pointsToEncode.put(  78,  33_536 );
			pointsToEncode.put(  79,  33_792 );

			pointsToEncode.put(  80,  34_048 );
			pointsToEncode.put(  81,  34_304 );
			pointsToEncode.put(  82,  34_560 );
			pointsToEncode.put(  83,  34_816 );
			pointsToEncode.put(  84,  35_072 );
			pointsToEncode.put(  85,  35_328 );
			pointsToEncode.put(  86,  35_584 );
			pointsToEncode.put(  87,  35_840 );
			pointsToEncode.put(  88,  36_096 );
			pointsToEncode.put(  89,  36_352 );

			pointsToEncode.put(  90,  36_608 );
			pointsToEncode.put(  91,  36_864 );
			pointsToEncode.put(  92,  37_120 );
			pointsToEncode.put(  93,  37_376 );
			pointsToEncode.put(  94,  37_632 );
			pointsToEncode.put(  95,  37_888 );
			pointsToEncode.put(  96,  38_144 );
			pointsToEncode.put(  97,  38_400 );
			pointsToEncode.put(  98,  38_656 );
			pointsToEncode.put(  99,  38_912 );

			pointsToEncode.put( 100,  39_168 );
			pointsToEncode.put( 101,  39_424 );
			pointsToEncode.put( 102,  39_680 );
			pointsToEncode.put( 103,  39_936 );
			pointsToEncode.put( 104,  40_192 );
			pointsToEncode.put( 105,  40_448 );
			pointsToEncode.put( 106,  41_216 );
			pointsToEncode.put( 107,  41_472 );
			pointsToEncode.put( 108,  41_728 );
			pointsToEncode.put( 109,  42_240 );

			pointsToEncode.put( 110,  67_072 );
			pointsToEncode.put( 111,  73_728 );
			pointsToEncode.put( 112,  73_984 );
			pointsToEncode.put( 113,  74_240 );
			pointsToEncode.put( 114,  77_824 );
			pointsToEncode.put( 115,  78_080 );
			pointsToEncode.put( 116,  78_336 );
			pointsToEncode.put( 117,  78_592 );
			pointsToEncode.put( 118,  82_944 );
			pointsToEncode.put( 119,  83_200 );

			pointsToEncode.put( 120,  92_160 );
			pointsToEncode.put( 121,  92_416 );
			pointsToEncode.put( 122, 131_072 );
			pointsToEncode.put( 123, 131_328 );
			pointsToEncode.put( 124, 131_584 );
			pointsToEncode.put( 125, 131_840 );
			pointsToEncode.put( 126, 132_096 );
			pointsToEncode.put( 127, 132_352 );
			pointsToEncode.put( 128, 132_608 );
			pointsToEncode.put( 129, 132_864 );

			pointsToEncode.put( 130, 133_120 );
			pointsToEncode.put( 131, 133_376 );
			pointsToEncode.put( 132, 133_632 );
			pointsToEncode.put( 133, 133_888 );
			pointsToEncode.put( 134, 134_144 );
			pointsToEncode.put( 135, 134_400 );
			pointsToEncode.put( 136, 134_656 );
			pointsToEncode.put( 137, 134_912 );
			pointsToEncode.put( 138, 135_168 );
			pointsToEncode.put( 139, 135_424 );

			pointsToEncode.put( 140, 135_680 );
			pointsToEncode.put( 141, 135_936 );
			pointsToEncode.put( 142, 136_192 );
			pointsToEncode.put( 143, 136_448 );
			pointsToEncode.put( 144, 136_704 );
			pointsToEncode.put( 145, 136_960 );
			pointsToEncode.put( 146, 137_216 );
			pointsToEncode.put( 147, 137_472 );
			pointsToEncode.put( 148, 137_728 );
			pointsToEncode.put( 149, 137_984 );

			pointsToEncode.put( 150, 138_240 );
			pointsToEncode.put( 151, 138_496 );
			pointsToEncode.put( 152, 138_752 );
			pointsToEncode.put( 153, 139_008 );
			pointsToEncode.put( 154, 139_264 );
			pointsToEncode.put( 155, 139_520 );
			pointsToEncode.put( 156, 139_776 );
			pointsToEncode.put( 157, 140_032 );
			pointsToEncode.put( 158, 140_288 );
			pointsToEncode.put( 159, 140_544 );

			pointsToEncode.put( 160, 140_800 );
			pointsToEncode.put( 161, 141_056 );
			pointsToEncode.put( 162, 141_312 );
			pointsToEncode.put( 163, 141_568 );
			pointsToEncode.put( 164, 141_824 );
			pointsToEncode.put( 165, 142_080 );
			pointsToEncode.put( 166, 142_336 );
			pointsToEncode.put( 167, 142_592 );
			pointsToEncode.put( 168, 142_848 );
			pointsToEncode.put( 169, 143_104 );

			pointsToEncode.put( 170, 143_360 );
			pointsToEncode.put( 171, 143_616 );
			pointsToEncode.put( 172, 143_872 );
			pointsToEncode.put( 173, 144_128 );
			pointsToEncode.put( 174, 144_384 );
			pointsToEncode.put( 175, 144_640 );
			pointsToEncode.put( 176, 144_896 );
			pointsToEncode.put( 177, 145_152 );
			pointsToEncode.put( 178, 145_408 );
			pointsToEncode.put( 179, 145_664 );

			pointsToEncode.put( 180, 145_920 );
			pointsToEncode.put( 181, 146_176 );
			pointsToEncode.put( 182, 146_432 );
			pointsToEncode.put( 183, 146_688 );
			pointsToEncode.put( 184, 146_944 );
			pointsToEncode.put( 185, 147_200 );
			pointsToEncode.put( 186, 147_456 );
			pointsToEncode.put( 187, 147_712 );
			pointsToEncode.put( 188, 147_968 );
			pointsToEncode.put( 189, 148_224 );

			pointsToEncode.put( 190, 148_480 );
			pointsToEncode.put( 191, 148_736 );
			pointsToEncode.put( 192, 148_992 );
			pointsToEncode.put( 193, 149_248 );
			pointsToEncode.put( 194, 149_504 );
			pointsToEncode.put( 195, 149_760 );
			pointsToEncode.put( 196, 150_016 );
			pointsToEncode.put( 197, 150_272 );
			pointsToEncode.put( 198, 150_528 );
			pointsToEncode.put( 199, 150_784 );

			pointsToEncode.put( 200, 151_040 );
			pointsToEncode.put( 201, 151_296 );
			pointsToEncode.put( 202, 151_552 );
			pointsToEncode.put( 203, 151_808 );
			pointsToEncode.put( 204, 152_064 );
			pointsToEncode.put( 205, 152_320 );
			pointsToEncode.put( 206, 152_576 );
			pointsToEncode.put( 207, 152_832 );
			pointsToEncode.put( 208, 153_088 );
			pointsToEncode.put( 209, 153_344 );

			pointsToEncode.put( 210, 153_600 );
			pointsToEncode.put( 211, 153_856 );
			pointsToEncode.put( 212, 154_112 );
			pointsToEncode.put( 213, 154_368 );
			pointsToEncode.put( 214, 154_624 );
			pointsToEncode.put( 215, 154_880 );
			pointsToEncode.put( 216, 155_136 );
			pointsToEncode.put( 217, 155_392 );
			pointsToEncode.put( 218, 155_648 );
			pointsToEncode.put( 219, 155_904 );

			pointsToEncode.put( 220, 156_160 );
			pointsToEncode.put( 221, 156_416 );
			pointsToEncode.put( 222, 156_672 );
			pointsToEncode.put( 223, 156_928 );
			pointsToEncode.put( 224, 157_184 );
			pointsToEncode.put( 225, 157_440 );
			pointsToEncode.put( 226, 157_696 );
			pointsToEncode.put( 227, 157_952 );
			pointsToEncode.put( 228, 158_208 );
			pointsToEncode.put( 229, 158_464 );

			pointsToEncode.put( 230, 158_720 );
			pointsToEncode.put( 231, 158_976 );
			pointsToEncode.put( 232, 159_232 );
			pointsToEncode.put( 233, 159_488 );
			pointsToEncode.put( 234, 159_744 );
			pointsToEncode.put( 235, 160_000 );
			pointsToEncode.put( 236, 160_256 );
			pointsToEncode.put( 237, 160_512 );
			pointsToEncode.put( 238, 160_768 );
			pointsToEncode.put( 239, 161_024 );

			pointsToEncode.put( 240, 161_280 );
			pointsToEncode.put( 241, 161_536 );
			pointsToEncode.put( 242, 161_792 );
			pointsToEncode.put( 243, 162_048 );
			pointsToEncode.put( 244, 162_304 );
			pointsToEncode.put( 245, 162_560 );
			pointsToEncode.put( 246, 162_816 );
			pointsToEncode.put( 247, 163_072 );
			pointsToEncode.put( 248, 163_328 );
			pointsToEncode.put( 249, 163_584 );

			pointsToEncode.put( 250, 163_840 );
			pointsToEncode.put( 251, 164_096 );
			pointsToEncode.put( 252, 164_352 );
			pointsToEncode.put( 253, 164_608 );
			pointsToEncode.put( 254, 164_864 );
			pointsToEncode.put( 255, 165_120 );
		}
	}

}




















