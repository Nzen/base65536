/** see ../../../../../../../LICENSE for release rights */
package ws.nzen.codec.binary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/** 
 */
public class Base65536
{
	protected static final int LAST_BYTE_AS_IND = -1;
	protected static final int LAST_BYTE_AS_PT = 5376;
	protected static final int MAX_BYTE = 255; // aka (1 << 8) -1
	protected static final int MAP_SIZE = 257;
	protected static Map<Integer, Integer> pointsToEncode
			= new HashMap<>( MAP_SIZE); // this could have been an array
	protected static Map<Integer, Integer> decodeFromPoints
			= new HashMap<>( MAP_SIZE );


	public static String encode( byte[] input )
	{
		ensureEncodeMapReady();
		StringBuilder result = new StringBuilder( input.length / 2 );
		short front;
		int back;
		for ( int ind = 0; ind < input.length; ind += 2 )
		{
			if ( ind == input.length -2 || ind == input.length -3 )
			{
				System.out.println( "--debug" );
			}
			front = (short)input[ ind ];
			if ( ind +1 >= input.length )
			{
				back = LAST_BYTE_AS_IND;
			}
			else
			{
				back = input[ ind +1 ];
			}
			System.out.print( "f"+ front +",b"+ back );
			Integer halfEncodedCodePoint = pointsToEncode.get( back );
			System.out.print( "  cp"+ (front + halfEncodedCodePoint) );
			char[] fromCodePoint = Character.toChars( front + halfEncodedCodePoint );
			// 4TESTS extracted for debugging
			for ( char nn : fromCodePoint )
			{
				System.out.print( "  => "+ nn );
			}
			System.out.println();
			result.append( fromCodePoint );
		}
		return result.toString();
	}


	public static String encode( byte[] input, int wrapAt )
	{
		StringBuilder output = new StringBuilder( encode( input ) );
		int lim = output.length();
		for ( int ind = wrapAt; ind < lim -1; ind += wrapAt )
		{
			output.insert( ind, '\n' );
			lim += 1; // to handle the increased size
		}
		return output.toString();
	}


	/** decode, assumes strict mode */
	public static byte[] decode( String inB65536 )
	{
		final boolean strict = true;
		return decode( inB65536, strict );
	}


	/**  */
	public static byte[] decode( String inB65536, boolean strict )
			throws RuntimeException
	{
		if ( inB65536 == null )
		{
			return null;
		}
		else if ( inB65536.isEmpty() )
		{
			return new byte[ 0 ];
		}
		ensureDecoderReady();
		int[] pointsAsData = inB65536.codePoints().toArray();
		ArrayList<Byte> output = new ArrayList<>( pointsAsData.length *2 );
		int cpInd = 0, cpLim = pointsAsData.length;
		for ( int asPoint : pointsAsData )
		{
			if ( strict && ( /*Character.isJavaIdentifierPart( asPoint ) // too broad
					||*/ Character.isSpaceChar( asPoint ) ) )
			{
				// basically "if not in our safe alphabet" 
				throw new RuntimeException( "codePoint "+ asPoint
						+" is not a valid base65536 char" );
			}
			int front = asPoint & MAX_BYTE;
			int decodeKey = asPoint - front;
			Integer back = decodeFromPoints.get( decodeKey );
			if ( back == null )
			{
				throw new RuntimeException( "codePoint "+ asPoint
						+" is not a valid base65536 char" );
			}
			else if ( back == LAST_BYTE_AS_PT && cpInd +1 < cpLim )
			{
				throw new RuntimeException( "codePoint "+ asPoint
						+" has LAST_BYTE, but isn't at end" );
			}
			else
			{
				output.add( (byte)front );
				output.add( (byte)(back.intValue() ) );
			}
			cpInd++;
		}
		byte[] actualOutput = new byte[ output.size() ]; // IMPROVE figure out the real size
		int aoInd = 0;
		for ( Byte next : output )
		{
			actualOutput[ aoInd ] = next;
			aoInd++;
		}
		return actualOutput;
	}


	protected static void ensureEncodeMapReady()
	{
		if ( pointsToEncode == null )
		{
			pointsToEncode = new HashMap<>();
		}
		if ( pointsToEncode.isEmpty() )
		{
			pointsToEncode.put( LAST_BYTE_AS_IND, LAST_BYTE_AS_PT );
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


	protected static void ensureDecoderReady()
	{
		if ( decodeFromPoints == null )
		{
			decodeFromPoints = new HashMap<>( MAP_SIZE );
		}
		if ( decodeFromPoints.isEmpty() )
		{
			decodeFromPoints.put( LAST_BYTE_AS_PT, LAST_BYTE_AS_IND );
			decodeFromPoints.put(  13_312,   0 );
			decodeFromPoints.put(  13_568,   1 );
			decodeFromPoints.put(  13_824,   2 );
			decodeFromPoints.put(  14_080,   3 );
			decodeFromPoints.put(  14_336,   4 );
			decodeFromPoints.put(  14_592,   5 );
			decodeFromPoints.put(  14_848,   6 );
			decodeFromPoints.put(  15_104,   7 );
			decodeFromPoints.put(  15_360,   8 );
			decodeFromPoints.put(  15_616,   9 );

			decodeFromPoints.put(  15_872,  10 );
			decodeFromPoints.put(  16_128,  11 );
			decodeFromPoints.put(  16_384,  12 );
			decodeFromPoints.put(  16_640,  13 );
			decodeFromPoints.put(  16_896,  14 );
			decodeFromPoints.put(  17_152,  15 );
			decodeFromPoints.put(  17_408,  16 );
			decodeFromPoints.put(  17_664,  17 );
			decodeFromPoints.put(  17_920,  18 );
			decodeFromPoints.put(  18_176,  19 );

			decodeFromPoints.put(  18_432,  20 );
			decodeFromPoints.put(  18_688,  21 );
			decodeFromPoints.put(  18_944,  22 );
			decodeFromPoints.put(  19_200,  23 );
			decodeFromPoints.put(  19_456,  24 );
			decodeFromPoints.put(  19_968,  25 );
			decodeFromPoints.put(  20_224,  26 );
			decodeFromPoints.put(  20_480,  27 );
			decodeFromPoints.put(  20_736,  28 );
			decodeFromPoints.put(  20_992,  29 );

			decodeFromPoints.put(  21_248,  30 );
			decodeFromPoints.put(  21_504,  31 );
			decodeFromPoints.put(  21_760,  32 );
			decodeFromPoints.put(  22_016,  33 );
			decodeFromPoints.put(  22_272,  34 );
			decodeFromPoints.put(  22_528,  35 );
			decodeFromPoints.put(  22_784,  36 );
			decodeFromPoints.put(  23_040,  37 );
			decodeFromPoints.put(  23_296,  38 );
			decodeFromPoints.put(  23_552,  39 );

			decodeFromPoints.put(  23_808,  40 );
			decodeFromPoints.put(  24_064,  41 );
			decodeFromPoints.put(  24_320,  42 );
			decodeFromPoints.put(  24_576,  43 );
			decodeFromPoints.put(  24_832,  44 );
			decodeFromPoints.put(  25_088,  45 );
			decodeFromPoints.put(  25_344,  46 );
			decodeFromPoints.put(  25_600,  47 );
			decodeFromPoints.put(  25_856,  48 );
			decodeFromPoints.put(  26_112,  49 );

			decodeFromPoints.put(  26_368,  50 );
			decodeFromPoints.put(  26_624,  51 );
			decodeFromPoints.put(  26_880,  52 );
			decodeFromPoints.put(  27_136,  53 );
			decodeFromPoints.put(  27_392,  54 );
			decodeFromPoints.put(  27_648,  55 );
			decodeFromPoints.put(  27_904,  56 );
			decodeFromPoints.put(  28_160,  57 );
			decodeFromPoints.put(  28_416,  58 );
			decodeFromPoints.put(  28_672,  59 );

			decodeFromPoints.put(  28_928,  60 );
			decodeFromPoints.put(  29_184,  61 );
			decodeFromPoints.put(  29_440,  62 );
			decodeFromPoints.put(  29_696,  63 );
			decodeFromPoints.put(  29_952,  64 );
			decodeFromPoints.put(  30_208,  65 );
			decodeFromPoints.put(  30_464,  66 );
			decodeFromPoints.put(  30_720,  67 );
			decodeFromPoints.put(  30_976,  68 );
			decodeFromPoints.put(  31_232,  69 );

			decodeFromPoints.put(  31_488,  70 );
			decodeFromPoints.put(  31_744,  71 );
			decodeFromPoints.put(  32_000,  72 );
			decodeFromPoints.put(  32_256,  73 );
			decodeFromPoints.put(  32_512,  74 );
			decodeFromPoints.put(  32_768,  75 );
			decodeFromPoints.put(  33_024,  76 );
			decodeFromPoints.put(  33_280,  77 );
			decodeFromPoints.put(  33_536,  78 );
			decodeFromPoints.put(  33_792,  79 );

			decodeFromPoints.put(  34_048,  80 );
			decodeFromPoints.put(  34_304,  81 );
			decodeFromPoints.put(  34_560,  82 );
			decodeFromPoints.put(  34_816,  83 );
			decodeFromPoints.put(  35_072,  84 );
			decodeFromPoints.put(  35_328,  85 );
			decodeFromPoints.put(  35_584,  86 );
			decodeFromPoints.put(  35_840,  87 );
			decodeFromPoints.put(  36_096,  88 );
			decodeFromPoints.put(  36_352,  89 );

			decodeFromPoints.put(  36_608,  90 );
			decodeFromPoints.put(  36_864,  91 );
			decodeFromPoints.put(  37_120,  92 );
			decodeFromPoints.put(  37_376,  93 );
			decodeFromPoints.put(  37_632,  94 );
			decodeFromPoints.put(  37_888,  95 );
			decodeFromPoints.put(  38_144,  96 );
			decodeFromPoints.put(  38_400,  97 );
			decodeFromPoints.put(  38_656,  98 );
			decodeFromPoints.put(  38_912,  99 );

			decodeFromPoints.put(  39_168, 100 );
			decodeFromPoints.put(  39_424, 101 );
			decodeFromPoints.put(  39_680, 102 );
			decodeFromPoints.put(  39_936, 103 );
			decodeFromPoints.put(  40_192, 104 );
			decodeFromPoints.put(  40_448, 105 );
			decodeFromPoints.put(  41_216, 106 );
			decodeFromPoints.put(  41_472, 107 );
			decodeFromPoints.put(  41_728, 108 );
			decodeFromPoints.put(  42_240, 109 );

			decodeFromPoints.put(  67_072, 110 );
			decodeFromPoints.put(  73_728, 111 );
			decodeFromPoints.put(  73_984, 112 );
			decodeFromPoints.put(  74_240, 113 );
			decodeFromPoints.put(  77_824, 114 );
			decodeFromPoints.put(  78_080, 115 );
			decodeFromPoints.put(  78_336, 116 );
			decodeFromPoints.put(  78_592, 117 );
			decodeFromPoints.put(  82_944, 118 );
			decodeFromPoints.put(  83_200, 119 );

			decodeFromPoints.put(  92_160, 120 );
			decodeFromPoints.put(  92_416, 121 );
			decodeFromPoints.put( 131_072, 122 );
			decodeFromPoints.put( 131_328, 123 );
			decodeFromPoints.put( 131_584, 124 );
			decodeFromPoints.put( 131_840, 125 );
			decodeFromPoints.put( 132_096, 126 );
			decodeFromPoints.put( 132_352, 127 );
			decodeFromPoints.put( 132_608, 128 );
			decodeFromPoints.put( 132_864, 129 );

			decodeFromPoints.put( 133_120, 130 );
			decodeFromPoints.put( 133_376, 131 );
			decodeFromPoints.put( 133_632, 132 );
			decodeFromPoints.put( 133_888, 133 );
			decodeFromPoints.put( 134_144, 134 );
			decodeFromPoints.put( 134_400, 135 );
			decodeFromPoints.put( 134_656, 136 );
			decodeFromPoints.put( 134_912, 137 );
			decodeFromPoints.put( 135_168, 138 );
			decodeFromPoints.put( 135_424, 139 );

			decodeFromPoints.put( 135_680, 140 );
			decodeFromPoints.put( 135_936, 141 );
			decodeFromPoints.put( 136_192, 142 );
			decodeFromPoints.put( 136_448, 143 );
			decodeFromPoints.put( 136_704, 144 );
			decodeFromPoints.put( 136_960, 145 );
			decodeFromPoints.put( 137_216, 146 );
			decodeFromPoints.put( 137_472, 147 );
			decodeFromPoints.put( 137_728, 148 );
			decodeFromPoints.put( 137_984, 149 );

			decodeFromPoints.put( 138_240, 150 );
			decodeFromPoints.put( 138_496, 151 );
			decodeFromPoints.put( 138_752, 152 );
			decodeFromPoints.put( 139_008, 153 );
			decodeFromPoints.put( 139_264, 154 );
			decodeFromPoints.put( 139_520, 155 );
			decodeFromPoints.put( 139_776, 156 );
			decodeFromPoints.put( 140_032, 157 );
			decodeFromPoints.put( 140_288, 158 );
			decodeFromPoints.put( 140_544, 159 );

			decodeFromPoints.put( 140_800, 160 );
			decodeFromPoints.put( 141_056, 161 );
			decodeFromPoints.put( 141_312, 162 );
			decodeFromPoints.put( 141_568, 163 );
			decodeFromPoints.put( 141_824, 164 );
			decodeFromPoints.put( 142_080, 165 );
			decodeFromPoints.put( 142_336, 166 );
			decodeFromPoints.put( 142_592, 167 );
			decodeFromPoints.put( 142_848, 168 );
			decodeFromPoints.put( 143_104, 169 );

			decodeFromPoints.put( 143_360, 170 );
			decodeFromPoints.put( 143_616, 171 );
			decodeFromPoints.put( 143_872, 172 );
			decodeFromPoints.put( 144_128, 173 );
			decodeFromPoints.put( 144_384, 174 );
			decodeFromPoints.put( 144_640, 175 );
			decodeFromPoints.put( 144_896, 176 );
			decodeFromPoints.put( 145_152, 177 );
			decodeFromPoints.put( 145_408, 178 );
			decodeFromPoints.put( 145_664, 179 );

			decodeFromPoints.put( 145_920, 180 );
			decodeFromPoints.put( 146_176, 181 );
			decodeFromPoints.put( 146_432, 182 );
			decodeFromPoints.put( 146_688, 183 );
			decodeFromPoints.put( 146_944, 184 );
			decodeFromPoints.put( 147_200, 185 );
			decodeFromPoints.put( 147_456, 186 );
			decodeFromPoints.put( 147_712, 187 );
			decodeFromPoints.put( 147_968, 188 );
			decodeFromPoints.put( 148_224, 189 );

			decodeFromPoints.put( 148_480, 190 );
			decodeFromPoints.put( 148_736, 191 );
			decodeFromPoints.put( 148_992, 192 );
			decodeFromPoints.put( 149_248, 193 );
			decodeFromPoints.put( 149_504, 194 );
			decodeFromPoints.put( 149_760, 195 );
			decodeFromPoints.put( 150_016, 196 );
			decodeFromPoints.put( 150_272, 197 );
			decodeFromPoints.put( 150_528, 198 );
			decodeFromPoints.put( 150_784, 199 );

			decodeFromPoints.put( 151_040, 200 );
			decodeFromPoints.put( 151_296, 201 );
			decodeFromPoints.put( 151_552, 202 );
			decodeFromPoints.put( 151_808, 203 );
			decodeFromPoints.put( 152_064, 204 );
			decodeFromPoints.put( 152_320, 205 );
			decodeFromPoints.put( 152_576, 206 );
			decodeFromPoints.put( 152_832, 207 );
			decodeFromPoints.put( 153_088, 208 );
			decodeFromPoints.put( 153_344, 209 );

			decodeFromPoints.put( 153_600, 210 );
			decodeFromPoints.put( 153_856, 211 );
			decodeFromPoints.put( 154_112, 212 );
			decodeFromPoints.put( 154_368, 213 );
			decodeFromPoints.put( 154_624, 214 );
			decodeFromPoints.put( 154_880, 215 );
			decodeFromPoints.put( 155_136, 216 );
			decodeFromPoints.put( 155_392, 217 );
			decodeFromPoints.put( 155_648, 218 );
			decodeFromPoints.put( 155_904, 219 );

			decodeFromPoints.put( 156_160, 220 );
			decodeFromPoints.put( 156_416, 221 );
			decodeFromPoints.put( 156_672, 222 );
			decodeFromPoints.put( 156_928, 223 );
			decodeFromPoints.put( 157_184, 224 );
			decodeFromPoints.put( 157_440, 225 );
			decodeFromPoints.put( 157_696, 226 );
			decodeFromPoints.put( 157_952, 227 );
			decodeFromPoints.put( 158_208, 228 );
			decodeFromPoints.put( 158_464, 229 );

			decodeFromPoints.put( 158_720, 230 );
			decodeFromPoints.put( 158_976, 231 );
			decodeFromPoints.put( 159_232, 232 );
			decodeFromPoints.put( 159_488, 233 );
			decodeFromPoints.put( 159_744, 234 );
			decodeFromPoints.put( 160_000, 235 );
			decodeFromPoints.put( 160_256, 236 );
			decodeFromPoints.put( 160_512, 237 );
			decodeFromPoints.put( 160_768, 238 );
			decodeFromPoints.put( 161_024, 239 );

			decodeFromPoints.put( 161_280, 240 );
			decodeFromPoints.put( 161_536, 241 );
			decodeFromPoints.put( 161_792, 242 );
			decodeFromPoints.put( 162_048, 243 );
			decodeFromPoints.put( 162_304, 244 );
			decodeFromPoints.put( 162_560, 245 );
			decodeFromPoints.put( 162_816, 246 );
			decodeFromPoints.put( 163_072, 247 );
			decodeFromPoints.put( 163_328, 248 );
			decodeFromPoints.put( 163_584, 249 );

			decodeFromPoints.put( 163_840, 250 );
			decodeFromPoints.put( 164_096, 251 );
			decodeFromPoints.put( 164_352, 252 );
			decodeFromPoints.put( 164_608, 253 );
			decodeFromPoints.put( 164_864, 254 );
			decodeFromPoints.put( 165_120, 255 );
		}
	}


}




















