/* The following code was generated by JFlex 1.7.0-1 tweaked for IntelliJ platform */

package pascal.taie.intellij.tir.syntax;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.TokenType;
import pascal.taie.intellij.tir.syntax.TirTypes;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.7.0-1
 * from the specification file <tt>/Users/xlor/Repos/intellij-tai-e/src/main/kotlin/pascal/taie/intellij/tir/syntax/Tir.flex</tt>
 */
class TirLexer implements FlexLexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;
  public static final int WAITING_VALUE = 2;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1, 1
  };

  /** 
   * Translates characters to character classes
   * Chosen bits are [9, 6, 6]
   * Total runtime size is 3872 bytes
   */
  public static int ZZ_CMAP(int ch) {
    return ZZ_CMAP_A[(ZZ_CMAP_Y[(ZZ_CMAP_Z[ch>>12]<<6)|((ch>>6)&0x3f)]<<6)|(ch&0x3f)];
  }

  /* The ZZ_CMAP_Z table has 272 entries */
  static final char ZZ_CMAP_Z[] = zzUnpackCMap(
    "\1\0\1\1\1\2\7\3\1\4\4\3\1\5\1\6\1\7\4\3\1\10\6\3\1\11\1\12\361\3");

  /* The ZZ_CMAP_Y table has 704 entries */
  static final char ZZ_CMAP_Y[] = zzUnpackCMap(
    "\1\0\1\1\1\2\26\3\1\4\1\3\1\5\3\3\1\6\5\3\1\7\1\3\1\7\1\3\1\7\1\3\1\7\1\3"+
    "\1\7\1\3\1\7\1\3\1\7\1\3\1\7\1\3\1\7\1\3\1\7\1\3\1\10\1\3\1\10\1\4\4\3\1\6"+
    "\1\10\34\3\1\4\1\10\4\3\1\11\1\3\1\10\2\3\1\12\2\3\1\10\1\5\2\3\1\12\16\3"+
    "\1\13\227\3\1\4\12\3\1\10\1\6\2\3\1\14\1\3\1\10\5\3\1\5\114\3\1\10\25\3\1"+
    "\4\56\3\1\7\1\3\1\5\1\15\2\3\1\10\3\3\1\5\5\3\1\10\1\3\1\10\5\3\1\10\1\3\1"+
    "\6\1\5\6\3\1\4\15\3\1\10\67\3\1\4\3\3\1\10\61\3\1\16\105\3\1\10\32\3");

  /* The ZZ_CMAP_A table has 960 entries */
  static final char ZZ_CMAP_A[] = zzUnpackCMap(
    "\11\0\1\4\1\2\1\1\1\5\1\3\22\0\1\4\1\55\1\51\1\0\1\11\1\7\1\60\1\0\1\61\1"+
    "\62\1\53\1\45\1\42\1\41\1\47\1\6\12\10\1\43\1\44\1\56\1\54\1\57\1\0\1\67\5"+
    "\11\1\50\5\11\1\70\16\11\1\63\1\52\1\64\1\60\1\11\1\0\1\15\1\17\1\23\1\31"+
    "\1\27\1\12\1\36\1\40\1\13\1\11\1\32\1\16\1\34\1\14\1\30\1\24\1\11\1\22\1\20"+
    "\1\21\1\25\1\26\1\37\1\35\1\33\1\11\1\65\1\60\1\66\1\60\6\0\1\1\232\0\12\46"+
    "\106\0\12\46\6\0\12\46\134\0\12\46\40\0\12\46\54\0\12\46\60\0\12\46\6\0\12"+
    "\46\116\0\2\1\46\0\12\46\26\0\12\46\74\0\12\46\16\0\62\46");

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\2\0\1\1\1\2\1\3\1\4\1\5\15\6\1\7"+
    "\1\10\1\11\1\12\1\13\1\14\1\1\1\15\1\16"+
    "\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26"+
    "\1\27\1\30\2\31\1\3\1\4\14\1\1\32\2\33"+
    "\2\0\1\6\1\34\6\6\1\35\14\6\1\0\1\36"+
    "\1\0\1\37\2\22\2\0\1\34\6\0\1\35\14\0"+
    "\1\40\6\6\1\41\1\42\3\6\1\43\13\6\7\0"+
    "\1\41\1\42\3\0\1\43\13\0\16\6\1\37\4\6"+
    "\1\44\23\0\1\44\1\45\4\6\1\0\4\6\1\46"+
    "\1\6\1\47\1\50\4\6\2\0\1\45\10\0\1\46"+
    "\1\0\1\47\1\50\4\0\4\6\1\0\3\6\1\51"+
    "\4\6\1\52\10\0\1\51\4\0\7\6\1\0\4\6"+
    "\1\53\13\0\1\53\10\6\1\0\1\54\1\6\10\0"+
    "\1\54\1\0\1\6\1\55\6\6\1\56\1\0\1\55"+
    "\6\0\1\57\5\6\1\60\1\57\5\0\1\60\3\6"+
    "\3\0\1\6\1\61\1\6\1\0\1\61\1\0\1\6"+
    "\1\0\1\6\1\0";

  private static int [] zzUnpackAction() {
    int [] result = new int[354];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\71\0\162\0\253\0\344\0\u011d\0\u0156\0\u018f"+
    "\0\u01c8\0\u0201\0\u023a\0\u0273\0\u02ac\0\u02e5\0\u031e\0\u0357"+
    "\0\u0390\0\u03c9\0\u0402\0\u043b\0\u0474\0\162\0\162\0\162"+
    "\0\u0474\0\162\0\u04ad\0\162\0\u04e6\0\u04e6\0\u051f\0\u0558"+
    "\0\162\0\162\0\162\0\u0591\0\162\0\162\0\162\0\253"+
    "\0\u05ca\0\162\0\162\0\u0603\0\u063c\0\u0675\0\u06ae\0\u06e7"+
    "\0\u0720\0\u0759\0\u0792\0\u07cb\0\u0804\0\u083d\0\u0876\0\u08af"+
    "\0\u08e8\0\u0921\0\u095a\0\u0993\0\u09cc\0\u018f\0\u0a05\0\u0a3e"+
    "\0\u0a77\0\u0ab0\0\u0ae9\0\u0b22\0\u018f\0\u0b5b\0\u0b94\0\u0bcd"+
    "\0\u0c06\0\u0c3f\0\u0c78\0\u0cb1\0\u0cea\0\u0d23\0\u0d5c\0\u0d95"+
    "\0\u0dce\0\u04ad\0\162\0\u0e07\0\162\0\u0e40\0\u0e79\0\u0eb2"+
    "\0\u0eeb\0\162\0\u0f24\0\u0f5d\0\u0f96\0\u0fcf\0\u1008\0\u1041"+
    "\0\162\0\u107a\0\u10b3\0\u10ec\0\u1125\0\u115e\0\u1197\0\u11d0"+
    "\0\u1209\0\u1242\0\u127b\0\u12b4\0\u12ed\0\162\0\u1326\0\u135f"+
    "\0\u1398\0\u13d1\0\u140a\0\u1443\0\u147c\0\u018f\0\u14b5\0\u14ee"+
    "\0\u1527\0\u018f\0\u1560\0\u1599\0\u15d2\0\u160b\0\u1644\0\u167d"+
    "\0\u16b6\0\u16ef\0\u1728\0\u1761\0\u179a\0\u17d3\0\u180c\0\u1845"+
    "\0\u187e\0\u18b7\0\u18f0\0\u1929\0\u1962\0\162\0\u199b\0\u19d4"+
    "\0\u1a0d\0\162\0\u1a46\0\u1a7f\0\u1ab8\0\u1af1\0\u1b2a\0\u1b63"+
    "\0\u1b9c\0\u1bd5\0\u1c0e\0\u1c47\0\u1c80\0\u1cb9\0\u1cf2\0\u1d2b"+
    "\0\u1d64\0\u1d9d\0\u1dd6\0\u1e0f\0\u1e48\0\u1e81\0\u1eba\0\u1ef3"+
    "\0\u1f2c\0\u1f65\0\u1f9e\0\u018f\0\u1fd7\0\u2010\0\u2049\0\u2082"+
    "\0\u018f\0\u20bb\0\u20f4\0\u212d\0\u2166\0\u219f\0\u21d8\0\u2211"+
    "\0\u224a\0\u2283\0\u22bc\0\u22f5\0\u232e\0\u2367\0\u23a0\0\u23d9"+
    "\0\u2412\0\u244b\0\u2484\0\u24bd\0\162\0\u018f\0\u24f6\0\u252f"+
    "\0\u2568\0\u25a1\0\u25da\0\u2613\0\u264c\0\u2685\0\u26be\0\u018f"+
    "\0\u26f7\0\u018f\0\u018f\0\u2730\0\u2769\0\u27a2\0\u27db\0\u2814"+
    "\0\u284d\0\162\0\u2886\0\u28bf\0\u28f8\0\u2931\0\u296a\0\u29a3"+
    "\0\u29dc\0\u2a15\0\162\0\u2a4e\0\162\0\162\0\u2a87\0\u2ac0"+
    "\0\u2af9\0\u2b32\0\u2b6b\0\u2ba4\0\u2bdd\0\u2c16\0\u2c4f\0\u2c88"+
    "\0\u2cc1\0\u2cfa\0\u018f\0\u2d33\0\u2d6c\0\u2da5\0\u2dde\0\162"+
    "\0\u2e17\0\u2e50\0\u2e89\0\u2ec2\0\u2efb\0\u2f34\0\u2f6d\0\u2fa6"+
    "\0\162\0\u2fdf\0\u3018\0\u3051\0\u308a\0\u30c3\0\u30fc\0\u3135"+
    "\0\u316e\0\u31a7\0\u31e0\0\u3219\0\u3252\0\u328b\0\u32c4\0\u32fd"+
    "\0\u3336\0\u018f\0\u336f\0\u33a8\0\u33e1\0\u341a\0\u3453\0\u348c"+
    "\0\u34c5\0\u34fe\0\u3537\0\u3570\0\u35a9\0\162\0\u35e2\0\u361b"+
    "\0\u3654\0\u368d\0\u36c6\0\u36ff\0\u3738\0\u3771\0\u37aa\0\u018f"+
    "\0\u37e3\0\u381c\0\u3855\0\u388e\0\u38c7\0\u3900\0\u3939\0\u3972"+
    "\0\u39ab\0\162\0\u39e4\0\u3a1d\0\u018f\0\u3a56\0\u3a8f\0\u3ac8"+
    "\0\u3b01\0\u3b3a\0\u3b73\0\162\0\u3bac\0\162\0\u3be5\0\u3c1e"+
    "\0\u3c57\0\u3c90\0\u3cc9\0\u3d02\0\u018f\0\u3d3b\0\u3d74\0\u3dad"+
    "\0\u3de6\0\u3e1f\0\u018f\0\162\0\u3e58\0\u3e91\0\u3eca\0\u3f03"+
    "\0\u3f3c\0\162\0\u3f75\0\u3fae\0\u3fe7\0\u4020\0\u4059\0\u4092"+
    "\0\u40cb\0\u018f\0\u4104\0\u413d\0\162\0\u4176\0\u41af\0\u41e8"+
    "\0\u4221\0\u425a";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[354];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\3\5\4\1\5\1\6\1\7\1\10\1\11\1\12"+
    "\1\13\1\14\2\10\1\15\1\16\1\17\1\20\1\21"+
    "\1\10\1\22\1\23\6\10\1\24\2\10\1\25\1\26"+
    "\1\27\1\30\1\31\1\7\1\32\1\10\1\33\1\3"+
    "\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43"+
    "\1\44\1\45\1\46\1\47\1\3\1\10\1\3\1\4"+
    "\1\50\1\4\1\51\1\50\1\52\1\53\1\7\1\3"+
    "\1\54\1\55\1\56\1\57\2\3\1\60\1\61\1\62"+
    "\1\63\1\64\1\3\1\65\1\66\6\3\1\67\2\3"+
    "\1\25\1\26\1\27\1\30\1\31\1\7\1\32\1\3"+
    "\1\33\1\3\1\34\1\35\1\36\1\37\1\40\1\41"+
    "\1\42\1\43\1\44\1\45\1\46\1\47\2\3\72\0"+
    "\5\4\71\0\1\70\71\0\1\71\1\10\30\72\6\0"+
    "\1\73\1\72\17\0\1\72\10\0\1\7\35\0\1\7"+
    "\1\74\31\0\31\10\6\0\1\73\1\10\17\0\1\10"+
    "\10\0\3\10\1\75\25\10\6\0\1\73\1\10\17\0"+
    "\1\10\10\0\2\10\1\76\1\10\1\77\17\10\1\100"+
    "\4\10\6\0\1\73\1\10\17\0\1\10\10\0\15\10"+
    "\1\101\1\10\1\102\1\103\10\10\6\0\1\73\1\10"+
    "\17\0\1\10\10\0\7\10\1\104\1\10\1\105\17\10"+
    "\6\0\1\73\1\10\17\0\1\10\10\0\11\10\1\106"+
    "\17\10\6\0\1\73\1\10\17\0\1\10\10\0\12\10"+
    "\1\107\15\10\1\110\6\0\1\73\1\10\17\0\1\10"+
    "\10\0\17\10\1\111\11\10\6\0\1\73\1\10\17\0"+
    "\1\10\10\0\5\10\1\112\1\113\15\10\1\114\4\10"+
    "\6\0\1\73\1\10\17\0\1\10\10\0\12\10\1\115"+
    "\2\10\1\116\13\10\6\0\1\73\1\10\17\0\1\10"+
    "\10\0\20\10\1\117\10\10\6\0\1\73\1\10\17\0"+
    "\1\10\10\0\25\10\1\120\3\10\6\0\1\73\1\10"+
    "\17\0\1\10\10\0\20\10\1\121\10\10\6\0\1\73"+
    "\1\10\17\0\1\10\10\0\1\7\35\0\1\7\22\0"+
    "\51\122\1\123\1\124\16\122\54\0\1\125\70\0\1\125"+
    "\1\0\1\126\66\0\1\125\2\0\1\127\21\0\1\130"+
    "\35\0\1\130\23\0\1\4\1\51\1\4\2\51\76\0"+
    "\1\131\67\0\1\132\1\0\1\133\17\0\1\134\61\0"+
    "\1\135\1\0\1\136\1\137\57\0\1\140\1\0\1\141"+
    "\70\0\1\142\71\0\1\143\15\0\1\144\57\0\1\145"+
    "\56\0\1\146\1\147\15\0\1\150\56\0\1\151\2\0"+
    "\1\152\73\0\1\153\75\0\1\154\63\0\1\155\40\0"+
    "\2\70\2\0\65\70\10\0\31\71\7\0\1\71\17\0"+
    "\1\71\10\0\31\72\6\0\1\73\1\72\17\0\1\72"+
    "\7\0\1\10\1\0\30\10\7\0\1\10\17\0\1\10"+
    "\10\0\1\74\35\0\1\74\1\0\1\156\30\0\4\10"+
    "\1\157\24\10\6\0\1\73\1\10\17\0\1\10\10\0"+
    "\10\10\1\160\1\161\4\10\1\162\12\10\6\0\1\73"+
    "\1\10\17\0\1\10\10\0\14\10\1\163\14\10\6\0"+
    "\1\73\1\10\17\0\1\10\10\0\6\10\1\164\22\10"+
    "\6\0\1\73\1\10\17\0\1\10\10\0\27\10\1\165"+
    "\1\10\6\0\1\73\1\10\17\0\1\10\10\0\14\10"+
    "\1\166\14\10\6\0\1\73\1\10\17\0\1\10\10\0"+
    "\10\10\1\167\20\10\6\0\1\73\1\10\17\0\1\10"+
    "\10\0\5\10\1\170\23\10\6\0\1\73\1\10\17\0"+
    "\1\10\10\0\5\10\1\171\15\10\1\172\5\10\6\0"+
    "\1\73\1\10\17\0\1\10\10\0\12\10\1\173\16\10"+
    "\6\0\1\73\1\10\17\0\1\10\10\0\11\10\1\174"+
    "\17\10\6\0\1\73\1\10\17\0\1\10\10\0\11\10"+
    "\1\175\17\10\6\0\1\73\1\10\17\0\1\10\10\0"+
    "\5\10\1\176\23\10\6\0\1\73\1\10\17\0\1\10"+
    "\10\0\14\10\1\177\14\10\6\0\1\73\1\10\17\0"+
    "\1\10\10\0\3\10\1\200\14\10\1\201\10\10\6\0"+
    "\1\73\1\10\17\0\1\10\10\0\7\10\1\202\21\10"+
    "\6\0\1\73\1\10\17\0\1\10\10\0\6\10\1\203"+
    "\22\10\6\0\1\73\1\10\17\0\1\10\10\0\11\10"+
    "\1\204\17\10\6\0\1\73\1\10\17\0\1\10\10\0"+
    "\11\10\1\205\17\10\6\0\1\73\1\10\17\0\1\10"+
    "\1\122\3\0\1\122\1\0\63\122\56\0\1\41\71\0"+
    "\1\41\21\0\1\130\35\0\1\130\20\0\1\206\15\0"+
    "\1\207\74\0\1\210\1\211\4\0\1\212\66\0\1\213"+
    "\62\0\1\214\111\0\1\215\55\0\1\216\64\0\1\217"+
    "\65\0\1\220\70\0\1\221\15\0\1\222\57\0\1\223"+
    "\67\0\1\224\70\0\1\225\64\0\1\226\77\0\1\227"+
    "\57\0\1\230\14\0\1\231\57\0\1\232\67\0\1\233"+
    "\73\0\1\234\70\0\1\235\57\0\5\10\1\236\23\10"+
    "\6\0\1\73\1\10\17\0\1\10\10\0\11\10\1\237"+
    "\17\10\6\0\1\73\1\10\17\0\1\10\10\0\17\10"+
    "\1\240\11\10\6\0\1\73\1\10\17\0\1\10\10\0"+
    "\20\10\1\241\10\10\6\0\1\73\1\10\17\0\1\10"+
    "\10\0\6\10\1\242\22\10\6\0\1\73\1\10\17\0"+
    "\1\10\10\0\6\10\1\243\22\10\6\0\1\73\1\10"+
    "\17\0\1\10\10\0\5\10\1\244\23\10\6\0\1\73"+
    "\1\10\17\0\1\10\10\0\11\10\1\245\17\10\6\0"+
    "\1\73\1\10\17\0\1\10\10\0\11\10\1\246\17\10"+
    "\6\0\1\73\1\10\17\0\1\10\10\0\4\10\1\247"+
    "\24\10\6\0\1\73\1\10\17\0\1\10\10\0\20\10"+
    "\1\250\10\10\6\0\1\73\1\10\17\0\1\10\10\0"+
    "\15\10\1\251\13\10\6\0\1\73\1\10\17\0\1\10"+
    "\10\0\13\10\1\252\15\10\6\0\1\73\1\10\17\0"+
    "\1\10\10\0\10\10\1\253\20\10\6\0\1\73\1\10"+
    "\17\0\1\10\10\0\26\10\1\254\2\10\6\0\1\73"+
    "\1\10\17\0\1\10\10\0\16\10\1\255\12\10\6\0"+
    "\1\73\1\10\17\0\1\10\10\0\11\10\1\256\17\10"+
    "\6\0\1\73\1\10\17\0\1\10\10\0\6\10\1\246"+
    "\22\10\6\0\1\73\1\10\17\0\1\10\10\0\5\10"+
    "\1\257\23\10\6\0\1\73\1\10\17\0\1\10\10\0"+
    "\17\10\1\260\11\10\6\0\1\73\1\10\17\0\1\10"+
    "\10\0\20\10\1\261\10\10\6\0\1\73\1\10\17\0"+
    "\1\10\70\0\1\262\15\0\1\263\74\0\1\264\76\0"+
    "\1\265\71\0\1\266\56\0\1\267\70\0\1\270\67\0"+
    "\1\271\74\0\1\272\70\0\1\273\63\0\1\274\104\0"+
    "\1\275\65\0\1\276\66\0\1\277\65\0\1\300\106\0"+
    "\1\125\60\0\1\301\63\0\1\302\65\0\1\273\67\0"+
    "\1\303\102\0\1\304\71\0\1\305\50\0\6\10\1\306"+
    "\22\10\6\0\1\73\1\10\17\0\1\10\10\0\5\10"+
    "\1\307\23\10\6\0\1\73\1\10\17\0\1\10\10\0"+
    "\12\10\1\310\16\10\6\0\1\73\1\10\17\0\1\10"+
    "\10\0\22\10\1\311\6\10\6\0\1\73\1\10\17\0"+
    "\1\10\10\0\17\10\1\312\11\10\6\0\1\73\1\10"+
    "\17\0\1\10\10\0\31\10\1\313\5\0\1\73\1\10"+
    "\17\0\1\10\10\0\12\10\1\314\16\10\6\0\1\73"+
    "\1\10\17\0\1\10\10\0\12\10\1\315\16\10\6\0"+
    "\1\73\1\10\17\0\1\10\10\0\3\10\1\316\25\10"+
    "\6\0\1\73\1\10\17\0\1\10\10\0\10\10\1\317"+
    "\20\10\6\0\1\73\1\10\17\0\1\10\10\0\27\10"+
    "\1\320\1\10\6\0\1\73\1\10\17\0\1\10\10\0"+
    "\12\10\1\321\16\10\6\0\1\73\1\10\17\0\1\10"+
    "\10\0\30\10\1\322\6\0\1\73\1\10\17\0\1\10"+
    "\10\0\10\10\1\323\20\10\6\0\1\73\1\10\17\0"+
    "\1\10\10\0\5\10\1\324\23\10\6\0\1\73\1\10"+
    "\17\0\1\10\10\0\17\10\1\325\11\10\6\0\1\73"+
    "\1\10\17\0\1\10\10\0\11\10\1\326\17\10\6\0"+
    "\1\73\1\10\17\0\1\10\10\0\4\10\1\327\24\10"+
    "\6\0\1\73\1\10\17\0\1\10\10\0\1\330\30\0"+
    "\1\331\4\0\1\330\40\0\1\332\67\0\1\333\75\0"+
    "\1\334\100\0\1\335\65\0\1\336\102\0\1\313\51\0"+
    "\1\337\70\0\1\340\61\0\1\341\75\0\1\342\107\0"+
    "\1\343\53\0\1\344\106\0\1\345\50\0\1\346\65\0"+
    "\1\347\102\0\1\350\62\0\1\351\63\0\1\352\64\0"+
    "\4\10\1\353\24\10\6\0\1\73\1\10\17\0\1\10"+
    "\10\0\2\10\1\354\26\10\6\0\1\73\1\10\17\0"+
    "\1\10\10\0\17\10\1\355\11\10\6\0\1\73\1\10"+
    "\17\0\1\10\10\0\24\10\1\356\4\10\6\0\1\73"+
    "\1\10\17\0\1\10\21\0\1\357\57\0\12\10\1\360"+
    "\16\10\6\0\1\73\1\10\17\0\1\10\10\0\5\10"+
    "\1\361\23\10\6\0\1\73\1\10\17\0\1\10\10\0"+
    "\13\10\1\306\15\10\6\0\1\73\1\10\17\0\1\10"+
    "\10\0\3\10\1\362\25\10\6\0\1\73\1\10\17\0"+
    "\1\10\10\0\4\10\1\363\24\10\6\0\1\73\1\10"+
    "\17\0\1\10\10\0\11\10\1\364\17\10\6\0\1\73"+
    "\1\10\17\0\1\10\10\0\13\10\1\365\15\10\6\0"+
    "\1\73\1\10\17\0\1\10\10\0\3\10\1\366\25\10"+
    "\6\0\1\73\1\10\17\0\1\10\10\0\21\10\1\367"+
    "\7\10\6\0\1\73\1\10\17\0\1\10\10\0\1\330"+
    "\30\0\1\331\4\0\1\330\15\0\1\370\14\0\1\371"+
    "\35\0\1\371\36\0\1\372\66\0\1\373\105\0\1\374"+
    "\75\0\1\375\56\0\1\376\63\0\1\377\76\0\1\332"+
    "\60\0\1\u0100\71\0\1\u0101\75\0\1\u0102\72\0\1\u0103"+
    "\60\0\1\u0104\106\0\1\u0105\47\0\13\10\1\u0106\15\10"+
    "\6\0\1\73\1\10\17\0\1\10\10\0\5\10\1\u0107"+
    "\23\10\6\0\1\73\1\10\17\0\1\10\10\0\3\10"+
    "\1\u0108\4\10\1\u0109\5\10\1\u010a\2\10\1\u010b\7\10"+
    "\6\0\1\73\1\10\17\0\1\10\10\0\17\10\1\u010c"+
    "\11\10\6\0\1\73\1\10\17\0\1\10\33\0\1\u010d"+
    "\45\0\5\10\1\u010e\23\10\6\0\1\73\1\10\17\0"+
    "\1\10\10\0\13\10\1\u010f\15\10\6\0\1\73\1\10"+
    "\17\0\1\10\10\0\17\10\1\u0110\11\10\6\0\1\73"+
    "\1\10\17\0\1\10\10\0\17\10\1\306\11\10\6\0"+
    "\1\73\1\10\17\0\1\10\10\0\11\10\1\u0111\17\10"+
    "\6\0\1\73\1\10\17\0\1\10\10\0\6\10\1\364"+
    "\22\10\6\0\1\73\1\10\17\0\1\10\10\0\10\10"+
    "\1\u0112\20\10\6\0\1\73\1\10\17\0\1\10\10\0"+
    "\1\371\35\0\1\371\15\0\1\370\27\0\1\u0113\62\0"+
    "\1\u0114\66\0\1\u0115\4\0\1\u0116\5\0\1\u0117\2\0"+
    "\1\u0118\66\0\1\u0119\56\0\1\u011a\76\0\1\u011b\74\0"+
    "\1\u011c\70\0\1\332\62\0\1\u011d\65\0\1\u0102\72\0"+
    "\1\u011e\60\0\17\10\1\u011f\11\10\6\0\1\73\1\10"+
    "\17\0\1\10\10\0\13\10\1\u0120\15\10\6\0\1\73"+
    "\1\10\17\0\1\10\10\0\4\10\1\u0121\24\10\6\0"+
    "\1\73\1\10\17\0\1\10\10\0\11\10\1\u0122\2\10"+
    "\1\u0123\14\10\6\0\1\73\1\10\17\0\1\10\10\0"+
    "\3\10\1\u0124\25\10\6\0\1\73\1\10\17\0\1\10"+
    "\10\0\23\10\1\u0125\5\10\6\0\1\73\1\10\17\0"+
    "\1\10\10\0\4\10\1\u0126\24\10\6\0\1\73\1\10"+
    "\17\0\1\10\24\0\1\u0127\54\0\23\10\1\u0128\5\10"+
    "\6\0\1\73\1\10\17\0\1\10\10\0\11\10\1\306"+
    "\17\10\6\0\1\73\1\10\17\0\1\10\10\0\4\10"+
    "\1\u010f\24\10\6\0\1\73\1\10\17\0\1\10\10\0"+
    "\17\10\1\u0129\11\10\6\0\1\73\1\10\17\0\1\10"+
    "\27\0\1\u012a\64\0\1\u012b\61\0\1\u012c\75\0\1\u012d"+
    "\2\0\1\u012e\57\0\1\u012f\110\0\1\u0130\51\0\1\u0131"+
    "\107\0\1\u0132\56\0\1\332\63\0\1\u011b\103\0\1\u0133"+
    "\51\0\20\10\1\u0134\10\10\6\0\1\73\1\10\17\0"+
    "\1\10\10\0\17\10\1\u0135\11\10\6\0\1\73\1\10"+
    "\17\0\1\10\10\0\11\10\1\u0136\17\10\6\0\1\73"+
    "\1\10\17\0\1\10\10\0\5\10\1\u0137\23\10\6\0"+
    "\1\73\1\10\17\0\1\10\10\0\17\10\1\u0138\11\10"+
    "\6\0\1\73\1\10\17\0\1\10\10\0\12\10\1\u0139"+
    "\16\10\6\0\1\73\1\10\17\0\1\10\10\0\4\10"+
    "\1\u013a\24\10\6\0\1\73\1\10\17\0\1\10\10\0"+
    "\11\10\1\u013b\17\10\6\0\1\73\1\10\17\0\1\10"+
    "\27\0\1\u013c\51\0\21\10\1\306\7\10\6\0\1\73"+
    "\1\10\17\0\1\10\30\0\1\u013d\67\0\1\u013e\62\0"+
    "\1\u013f\64\0\1\u0140\102\0\1\u0141\63\0\1\u0142\62\0"+
    "\1\u0143\75\0\1\u0144\100\0\1\332\47\0\2\10\1\u0145"+
    "\26\10\6\0\1\73\1\10\17\0\1\10\10\0\17\10"+
    "\1\u0146\11\10\6\0\1\73\1\10\17\0\1\10\10\0"+
    "\11\10\1\u0147\17\10\6\0\1\73\1\10\17\0\1\10"+
    "\10\0\13\10\1\u0148\15\10\6\0\1\73\1\10\17\0"+
    "\1\10\10\0\11\10\1\u0149\17\10\6\0\1\73\1\10"+
    "\17\0\1\10\10\0\5\10\1\u014a\23\10\6\0\1\73"+
    "\1\10\17\0\1\10\10\0\10\10\1\u014b\20\10\6\0"+
    "\1\73\1\10\17\0\1\10\12\0\1\u014c\105\0\1\u014d"+
    "\62\0\1\u014e\72\0\1\u014f\66\0\1\u0150\64\0\1\u0151"+
    "\73\0\1\u0152\60\0\12\10\1\u0153\16\10\6\0\1\73"+
    "\1\10\17\0\1\10\10\0\3\10\1\u0154\25\10\6\0"+
    "\1\73\1\10\17\0\1\10\10\0\3\10\1\u0155\25\10"+
    "\6\0\1\73\1\10\17\0\1\10\10\0\15\10\1\u0155"+
    "\13\10\6\0\1\73\1\10\17\0\1\10\10\0\24\10"+
    "\1\u0147\4\10\6\0\1\73\1\10\17\0\1\10\22\0"+
    "\1\u0156\61\0\1\u0157\70\0\1\u0158\102\0\1\u0158\77\0"+
    "\1\u014e\44\0\2\10\1\u0159\26\10\6\0\1\73\1\10"+
    "\17\0\1\10\10\0\13\10\1\u015a\15\10\6\0\1\73"+
    "\1\10\17\0\1\10\10\0\5\10\1\u015b\23\10\6\0"+
    "\1\73\1\10\17\0\1\10\12\0\1\u015c\101\0\1\u015d"+
    "\62\0\1\u015e\63\0\5\10\1\u015f\23\10\6\0\1\73"+
    "\1\10\17\0\1\10\10\0\6\10\1\u015a\22\10\6\0"+
    "\1\73\1\10\17\0\1\10\15\0\1\u0160\71\0\1\u015d"+
    "\62\0\13\10\1\u0161\15\10\6\0\1\73\1\10\17\0"+
    "\1\10\23\0\1\u0162\55\0\17\10\1\u015a\11\10\6\0"+
    "\1\73\1\10\17\0\1\10\27\0\1\u015d\41\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[17043];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String[] ZZ_ERROR_MSG = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\2\0\1\11\22\1\3\11\1\1\1\11\1\1\1\11"+
    "\4\1\3\11\1\1\3\11\2\1\2\11\17\1\2\0"+
    "\25\1\1\0\1\11\1\0\1\11\2\1\2\0\1\11"+
    "\6\0\1\11\14\0\1\11\27\1\7\0\1\1\1\11"+
    "\3\0\1\11\13\0\24\1\23\0\1\11\5\1\1\0"+
    "\14\1\2\0\1\11\10\0\1\11\1\0\2\11\4\0"+
    "\4\1\1\0\10\1\1\11\10\0\1\11\4\0\7\1"+
    "\1\0\5\1\13\0\1\11\10\1\1\0\2\1\10\0"+
    "\1\11\1\0\10\1\1\11\1\0\1\11\6\0\7\1"+
    "\1\11\5\0\1\11\3\1\3\0\3\1\1\0\1\11"+
    "\1\0\1\1\1\0\1\1\1\0";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[354];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private CharSequence zzBuffer = "";

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /**
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  TirLexer(java.io.Reader in) {
    this.zzReader = in;
  }


  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    int size = 0;
    for (int i = 0, length = packed.length(); i < length; i += 2) {
      size += packed.charAt(i);
    }
    char[] map = new char[size];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < packed.length()) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }

  public final int getTokenStart() {
    return zzStartRead;
  }

  public final int getTokenEnd() {
    return getTokenStart() + yylength();
  }

  public void reset(CharSequence buffer, int start, int end, int initialState) {
    zzBuffer = buffer;
    zzCurrentPos = zzMarkedPos = zzStartRead = start;
    zzAtEOF  = false;
    zzAtBOL = true;
    zzEndRead = end;
    yybegin(initialState);
  }

  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   *
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {
    return true;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final CharSequence yytext() {
    return zzBuffer.subSequence(zzStartRead, zzMarkedPos);
  }


  /**
   * Returns the character at position <tt>pos</tt> from the
   * matched text.
   *
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch.
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer.charAt(zzStartRead+pos);
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of
   * yypushback(int) and a match-all fallback rule) this method
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() {
    if (!zzEOFDone) {
      zzEOFDone = true;
    
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public IElementType advance() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    CharSequence zzBufferL = zzBuffer;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL/*, zzEndReadL*/);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL/*, zzEndReadL*/);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + ZZ_CMAP(zzInput) ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
        zzDoEOF();
        return null;
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1: 
            { return TokenType.BAD_CHARACTER;
            } 
            // fall through
          case 50: break;
          case 2: 
            { yybegin(YYINITIAL); return TokenType.WHITE_SPACE;
            } 
            // fall through
          case 51: break;
          case 3: 
            { yybegin(YYINITIAL); return TirTypes.DIV_OP;
            } 
            // fall through
          case 52: break;
          case 4: 
            { yybegin(YYINITIAL); return TirTypes.MOD_OP;
            } 
            // fall through
          case 53: break;
          case 5: 
            { yybegin(YYINITIAL); return TirTypes.INTEGER;
            } 
            // fall through
          case 54: break;
          case 6: 
            { yybegin(YYINITIAL); return TirTypes.IDENTIFIER;
            } 
            // fall through
          case 55: break;
          case 7: 
            { yybegin(YYINITIAL); return TirTypes.SUB_OP;
            } 
            // fall through
          case 56: break;
          case 8: 
            { yybegin(YYINITIAL); return TirTypes.COMMA;
            } 
            // fall through
          case 57: break;
          case 9: 
            { yybegin(YYINITIAL); return TirTypes.COLON;
            } 
            // fall through
          case 58: break;
          case 10: 
            { yybegin(YYINITIAL); return TirTypes.SEMICOLON;
            } 
            // fall through
          case 59: break;
          case 11: 
            { yybegin(YYINITIAL); return TirTypes.ADD_OP;
            } 
            // fall through
          case 60: break;
          case 12: 
            { yybegin(YYINITIAL); return TirTypes.DOT;
            } 
            // fall through
          case 61: break;
          case 13: 
            { yybegin(YYINITIAL); return TirTypes.MUL_OP;
            } 
            // fall through
          case 62: break;
          case 14: 
            { yybegin(YYINITIAL); return TirTypes.EQUAL;
            } 
            // fall through
          case 63: break;
          case 15: 
            { yybegin(YYINITIAL); return TirTypes.NOT_OP;
            } 
            // fall through
          case 64: break;
          case 16: 
            { yybegin(YYINITIAL); return TirTypes.LANGLE;
            } 
            // fall through
          case 65: break;
          case 17: 
            { yybegin(YYINITIAL); return TirTypes.RANGLE;
            } 
            // fall through
          case 66: break;
          case 18: 
            { yybegin(YYINITIAL); return TirTypes.BIT_OP;
            } 
            // fall through
          case 67: break;
          case 19: 
            { yybegin(YYINITIAL); return TirTypes.LPAREN;
            } 
            // fall through
          case 68: break;
          case 20: 
            { yybegin(YYINITIAL); return TirTypes.RPAREN;
            } 
            // fall through
          case 69: break;
          case 21: 
            { yybegin(YYINITIAL); return TirTypes.LBRACKET;
            } 
            // fall through
          case 70: break;
          case 22: 
            { yybegin(YYINITIAL); return TirTypes.RBRACKET;
            } 
            // fall through
          case 71: break;
          case 23: 
            { yybegin(YYINITIAL); return TirTypes.LBRACE;
            } 
            // fall through
          case 72: break;
          case 24: 
            { yybegin(YYINITIAL); return TirTypes.RBRACE;
            } 
            // fall through
          case 73: break;
          case 25: 
            { yybegin(WAITING_VALUE); return TokenType.WHITE_SPACE;
            } 
            // fall through
          case 74: break;
          case 26: 
            { yybegin(YYINITIAL); return TirTypes.COMMENT;
            } 
            // fall through
          case 75: break;
          case 27: 
            { yybegin(YYINITIAL); return TirTypes.CONSTANT_IDENTIFIER;
            } 
            // fall through
          case 76: break;
          case 28: 
            { yybegin(YYINITIAL); return TirTypes.IF;
            } 
            // fall through
          case 77: break;
          case 29: 
            { yybegin(YYINITIAL); return TirTypes.AT;
            } 
            // fall through
          case 78: break;
          case 30: 
            { yybegin(YYINITIAL); return TirTypes.STRING_LITERAL;
            } 
            // fall through
          case 79: break;
          case 31: 
            { yybegin(YYINITIAL); return TirTypes.CMP_OP;
            } 
            // fall through
          case 80: break;
          case 32: 
            { yybegin(YYINITIAL); return TirTypes.FLOAT;
            } 
            // fall through
          case 81: break;
          case 33: 
            { yybegin(YYINITIAL); return TirTypes.NEW;
            } 
            // fall through
          case 82: break;
          case 34: 
            { yybegin(YYINITIAL); return TirTypes.NOP;
            } 
            // fall through
          case 83: break;
          case 35: 
            { yybegin(YYINITIAL); return TirTypes.TRY;
            } 
            // fall through
          case 84: break;
          case 36: 
            { yybegin(YYINITIAL); return TirTypes.GOTO;
            } 
            // fall through
          case 85: break;
          case 37: 
            { yybegin(YYINITIAL); return TirTypes.MODIFIER;
            } 
            // fall through
          case 86: break;
          case 38: 
            { yybegin(YYINITIAL); return TirTypes.THROW;
            } 
            // fall through
          case 87: break;
          case 39: 
            { yybegin(YYINITIAL); return TirTypes.CATCH;
            } 
            // fall through
          case 88: break;
          case 40: 
            { yybegin(YYINITIAL); return TirTypes.CLASS;
            } 
            // fall through
          case 89: break;
          case 41: 
            { yybegin(YYINITIAL); return TirTypes.RETURN;
            } 
            // fall through
          case 90: break;
          case 42: 
            { yybegin(YYINITIAL); return TirTypes.LINE_NUMBER;
            } 
            // fall through
          case 91: break;
          case 43: 
            { yybegin(YYINITIAL); return TirTypes.EXTENDS;
            } 
            // fall through
          case 92: break;
          case 44: 
            { yybegin(YYINITIAL); return TirTypes.NEW_ARRAY;
            } 
            // fall through
          case 93: break;
          case 45: 
            { yybegin(YYINITIAL); return TirTypes.INTERFACE;
            } 
            // fall through
          case 94: break;
          case 46: 
            { yybegin(YYINITIAL); return TirTypes.NULL_TYPE;
            } 
            // fall through
          case 95: break;
          case 47: 
            { yybegin(YYINITIAL); return TirTypes.INSTANCEOF;
            } 
            // fall through
          case 96: break;
          case 48: 
            { yybegin(YYINITIAL); return TirTypes.IMPLEMENTS;
            } 
            // fall through
          case 97: break;
          case 49: 
            { yybegin(YYINITIAL); return TirTypes.INVOKE_KEY;
            } 
            // fall through
          case 98: break;
          default:
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
