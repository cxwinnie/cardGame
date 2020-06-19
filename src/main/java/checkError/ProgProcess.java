package checkError;

import checkError.domian.ProgKeyWord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XiandaXu on 2020/6/11.
 * 并发检测处理C语言源代码
 */
public class ProgProcess {

    public static List<ProgKeyWord> listProgKeyWord = new ArrayList();

    private static StringBuffer prog = new StringBuffer();

    /**
     * this method is to read the standard input
     */

    static Map<String, Integer> token = new HashMap<String, Integer>() {
        {
            put("auto", 1);
            put("break", 2);
            put("case", 3);
            put("char", 4);
            put("const", 5);
            put("continue", 6);
            put("default", 7);
            put("do", 8);
            put("double", 9);
            put("else", 10);
            put("enum", 11);
            put("extern", 12);
            put("float", 13);
            put("for", 14);
            put("goto", 15);
            put("if", 16);
            put("int", 17);
            put("long", 18);
            put("register", 19);
            put("return", 20);
            put("short", 21);
            put("signed", 22);
            put("sizeof", 23);
            put("static", 24);
            put("struct", 25);
            put("switch", 26);
            put("typedef", 27);
            put("union", 28);
            put("unsigned", 29);
            put("void", 30);
            put("volatile", 31);
            put("while", 32);
            put("-", 33);
            put("--", 34);
            put("-=", 35);
            put("->", 36);
            put("!", 37);
            put("!=", 38);
            put("%", 39);
            put("%=", 40);
            put("&", 41);
            put("&&", 42);
            put("&=", 43);
            put("(", 44);
            put(")", 45);
            put("*", 46);
            put("*=", 47);
            put(",", 48);
            put(".", 49);
            put("/", 50);
            put("/=", 51);
            put(":", 52);
            put(";", 53);
            put("?", 54);
            put("[", 55);
            put("]", 56);
            put("^", 57);
            put("^=", 58);
            put("{", 59);
            put("|", 60);
            put("||", 61);
            put("|=", 62);
            put("}", 63);
            put("~", 64);
            put("+", 65);
            put("++", 66);
            put("+=", 67);
            put("<", 68);
            put("<<", 69);
            put("<<=", 70);
            put("<=", 71);
            put("=", 72);
            put("==", 73);
            put(">", 74);
            put(">=", 75);
            put(">>", 76);
            put(">>=", 77);
            put("\"", 78);
            put("comment", 79);
            put("constant", 80);
            put("identifier", 81);
            put("literal_string", 82);
            put("#include", 83);
            put("#define", 84);
        }
    };

    static Integer pos = -1;
    static Character ch;

    // Get next char. It will increase pos by one!!
    static boolean GetNextChar(StringBuffer s) {
        if (pos == s.length() - 1) {
            return false;
        }

        ch = s.charAt(++pos);
        return true;
    }

    static boolean IsAlpha(Character ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == '#';
    }

    static boolean IsNum(Character ch) {
        return ch >= '0' && ch <= '9';
    }

    static void GoBack() {
        --pos;
    }

    /**
     * you should add some code in this method to achieve this lab
     */
    private static void analysis(String filePath) {
        prog = ReadCFile.readFile(filePath);
        Integer lineNum = 1;
        Integer outputIndex = 1;
        while (GetNextChar(prog)) {
            Integer state = 0, syn = -1;
            String _token = "";
            while (state >= 0) {
                switch (state) {
                    case 0:
                        if (ch == '+') state = 1;
                        else if (ch == '-') state = 2;
                        else if (ch == '*') state = 3;
                        else if (ch == '/') state = 4;
                        else if (ch == '%') state = 5;
                        else if (ch == '&') state = 6;
                        else if (ch == '|') state = 7;
                        else if (ch == '^') state = 8;
                        else if (ch == '~') state = 9;
                        else if (ch == '>') state = 10;
                        else if (ch == '<') state = 11;
                        else if (ch == '=') state = 12;
                        else if (ch == '!') state = 13;
                        else if (ch == ';') state = 14;
                        else if (ch == '[') state = 15;
                        else if (ch == ']') state = 16;
                        else if (ch == '(') state = 17;
                        else if (ch == ')') state = 18;
                        else if (ch == '{') state = 19;
                        else if (ch == '}') state = 20;
                        else if (ch == '"') state = 21;
                        else if (ch == ',') state = 22;
                        else if (ch == '?') state = 23;
                        else if (ch == ':') state = 24;
                        else if (ch == '.') state = 25;
                        else if (IsAlpha(ch) || ch == '_') state = 26;
                        else if (IsNum(ch)) state = 27;
                        else if (ch == ' ' || ch == '\t' || ch == '\n') {
                            if(ch == '\n')
                                lineNum++;
                            state = -1; // Skip
                        }
                        else state = -2; // Something wrong
                        break;
                    case 1:
                        // '+'
                        _token += ch;
                        if (GetNextChar(prog)) {
                            if (IsNum(ch)) {
                                state = 27;
                                break;
                            } else if (ch == '+') {
                                // "++"
                                state = 48;
                                break;
                            } else {
                                GoBack();
                            }
                        }
                        state = -1;
                        syn = token.get("+");
                        break;
                    case 2:
                        // '-'
                        _token += ch;
                        if (GetNextChar(prog)) {
                            if (IsNum(ch)) {
                                state = 27;
                                break;
                            } else if (ch == '-') {
                                // "--"
                                state = 49;
                                break;
                            } else {
                                GoBack();
                            }
                        }
                        state = -1;
                        syn = token.get("-");
                        break;
                    case 3:
                        // '*'
                        _token += ch;
                        state = -1;
                        syn = token.get("*");
                        break;
                    case 4:
                        // '/'
                        _token += ch;
                        if (GetNextChar(prog)) {
                            if (ch == '/') {
                                // one-line comment
                                state = 28;
                                break;
                            } else if (ch == '*') {
                                // multi-lines comment
                                state = 29;
                                break;
                            } else if (ch == '=') {
                                // "/="
                                state = 30;
                                break;
                            } else {
                                GoBack();
                            }
                        }
                        state = -1;
                        syn = token.get("/");
                        break;
                    case 5:
                        // '%'
                        _token += ch;
                        if (GetNextChar(prog)) {
                            if (ch == '=') {
                                // "%="
                                state = 31;
                                break;
                            } else {
                                GoBack();
                            }
                        }
                        state = -1;
                        syn = token.get("%");
                        break;
                    case 6:
                        // '&'
                        _token += ch;
                        if (GetNextChar(prog)) {
                            if (ch == '=') {
                                // "&="
                                state = 32;
                                break;
                            } else if (ch == '&') {
                                // "&&"
                                state = 33;
                                break;
                            } else {
                                GoBack();
                            }
                        }
                        state = -1;
                        syn = token.get("&");
                        break;
                    case 7:
                        // '|'
                        _token += ch;
                        if (GetNextChar(prog)) {
                            if (ch == '=') {
                                // "|="
                                state = 34;
                                break;
                            } else if (ch == '|') {
                                // "||"
                                state = 35;
                                break;
                            } else {
                                GoBack();
                            }
                        }
                        state = -1;
                        syn = token.get("|");
                        break;
                    case 8:
                        // '^'
                        _token += ch;
                        if (GetNextChar(prog)) {
                            if (ch == '=') {
                                // "^="
                                state = 36;
                                break;
                            } else {
                                GoBack();
                            }
                        }
                        state = -1;
                        syn = token.get("^");
                        break;
                    case 9:
                        // '~'
                        _token += ch;
                        state = -1;
                        syn = token.get("~");
                        break;
                    case 10:
                        // '>'
                        _token += ch;
                        if (GetNextChar(prog)) {
                            if (ch == '=') {
                                // ">="
                                state = 37;
                                break;
                            } else if (ch == '>') {
                                // ">>"
                                state = 38;
                                break;
                            } else {
                                GoBack();
                            }
                        }
                        state = -1;
                        syn = token.get(">");
                        break;
                    case 11:
                        // '<'
                        _token += ch;
                        if (GetNextChar(prog)) {
                            if (ch == '=') {
                                // "<="
                                state = 40;
                                break;
                            } else if (ch == '>') {
                                // "<<"
                                state = 41;
                                break;
                            } else {
                                GoBack();
                            }
                        }
                        state = -1;
                        syn = token.get("<");
                        break;
                    case 12:
                        // '='
                        _token += ch;
                        if (GetNextChar(prog)) {
                            if (ch == '=') {
                                // "=="
                                state = 43;
                                break;
                            } else {
                                GoBack();
                            }
                        }
                        state = -1;
                        syn = token.get("=");
                        break;
                    case 13:
                        // '!'
                        _token += ch;
                        if (GetNextChar(prog)) {
                            if (ch == '=') {
                                // "!="
                                state = 44;
                                break;
                            } else {
                                GoBack();
                            }
                        }
                        state = -1;
                        syn = token.get("!");
                        break;
                    case 14:
                        // ";"
                        _token += ch;
                        state = -1;
                        syn = token.get(";");
                        break;
                    case 15:
                        // "["
                        _token += ch;
                        state = -1;
                        syn = token.get("[");
                        break;
                    case 16:
                        // "]"
                        _token += ch;
                        state = -1;
                        syn = token.get("]");
                        break;
                    case 17:
                        // "("
                        _token += ch;
                        state = -1;
                        syn = token.get("(");
                        break;
                    case 18:
                        // ")"
                        _token += ch;
                        state = -1;
                        syn = token.get(")");
                        break;
                    case 19:
                        // "{"
                        _token += ch;
                        state = -1;
                        syn = token.get("{");
                        break;
                    case 20:
                        // "{"
                        _token += ch;
                        state = -1;
                        syn = token.get("}");
                        break;
                    case 21:
                        // "\"..."
                        _token += ch;
                        if (GetNextChar(prog)) {
                            if (ch == '"' && !_token.endsWith("\\")) {
                                // closed string
                                state = 45;
                                break;
                            } else {
                                // unclosed string, keep reading.
                                state = 21;
                                break;
                            }
                        }
                        // unclosed string. ERROR!
                        state = -2;
                        break;
                    case 22:
                        // ","
                        _token += ch;
                        state = -1;
                        syn = token.get(",");
                        break;
                    case 23:
                        // "?"
                        _token += ch;
                        state = -1;
                        syn = token.get("?");
                        break;
                    case 24:
                        // ":"
                        _token += ch;
                        state = -1;
                        syn = token.get(":");
                        break;
                    case 25:
                        // "."
                        _token += ch;
                        if (GetNextChar(prog)) {
                            if (IsNum(ch)) {
                                // double a = .247;
                                state = 27;
                                break;
                            } else {
                                GoBack();
                            }
                        }
                        state = -1;
                        syn = token.get(".");
                        break;
                    case 26:
                        // identifier
                        _token += ch;
                        if (GetNextChar(prog)) {
                            if (IsNum(ch) || IsAlpha(ch) || ch == '_') {
                                state = 26;
                                break;
                            } else {
                                GoBack();
                            }
                        }
                        state = -1;
                        if (token.containsKey(_token)) {
                            syn = token.get(_token);
                        } else {
                            syn = token.get("identifier");
                        }
                        break;
                    case 27:
                        // constant
                        // Because we assume there is no error, so some thing like "2.5.1" is impossible.
                        _token += ch;
                        if (GetNextChar(prog)) {
                            if (IsNum(ch) || ch == '.') {
                                state = 27;
                                break;
                            } else {
                                GoBack();
                            }
                        }
                        state = -1;
                        syn = token.get("constant");
                        break;
                    case 28:
                        // one-line comment
                        _token += ch;
                        if (GetNextChar(prog)) {
                            if (ch != '\n') {
                                state = 28;
                                break;
                            } else {
                                state = -1;
                                syn = token.get("comment");
                                lineNum++;
                                break;
                            }
                        }
                        state = -1;
                        syn = token.get("comment");
                        break;
                    case 29:
                        // multi-line comment
                        _token += ch;
                        if (GetNextChar(prog)) {
                            if (ch != '*') {
                                if(ch == '\n'){
                                    lineNum++;
                                }
                                state = 29;
                                break;
                            } else {
                                // try to mathch multi-line comment's close mark
                                state = 46;
                                break;
                            }
                        }
                        // unclosed multi-line comment. ERROR!
                        state = -2;
                        break;
                    case 30:
                        // "/="
                        _token += ch;
                        state = -1;
                        syn = token.get("/=");
                        break;
                    case 31:
                        // "%="
                        _token += ch;
                        state = -1;
                        syn = token.get("%=");
                        break;
                    case 32:
                        // "&="
                        _token += ch;
                        state = -1;
                        syn = token.get("&=");
                        break;
                    case 33:
                        // "&&"
                        _token += ch;
                        state = -1;
                        syn = token.get("&&");
                        break;
                    case 34:
                        // "|="
                        _token += ch;
                        state = -1;
                        syn = token.get("|=");
                        break;
                    case 35:
                        // "||"
                        _token += ch;
                        state = -1;
                        syn = token.get("||");
                        break;
                    case 36:
                        // "^="
                        _token += ch;
                        state = -1;
                        syn = token.get("^=");
                        break;
                    case 37:
                        // ">="
                        _token += ch;
                        state = -1;
                        syn = token.get(">=");
                        break;
                    case 38:
                        // ">>"
                        _token += ch;
                        if (GetNextChar(prog)) {
                            if (ch == '=') {
                                // ">>="
                                state = 39;
                                break;
                            } else {
                                GoBack();
                            }
                        }
                        state = -1;
                        syn = token.get(">>");
                        break;
                    case 39:
                        // ">>="
                        _token += ch;
                        state = -1;
                        syn = token.get(">>=");
                        break;
                    case 40:
                        // "<="
                        _token += ch;
                        state = -1;
                        syn = token.get("<=");
                        break;
                    case 41:
                        // "<<"
                        _token += ch;
                        if (GetNextChar(prog)) {
                            if (ch == '=') {
                                // "<<="
                                state = 42;
                                break;
                            } else {
                                GoBack();
                            }
                        }
                        state = -1;
                        syn = token.get("<<");
                        break;
                    case 42:
                        // "<<="
                        _token += ch;
                        state = -1;
                        syn = token.get("<<=");
                        break;
                    case 43:
                        // "=="
                        _token += ch;
                        state = -1;
                        syn = token.get("==");
                        break;
                    case 44:
                        // "!="
                        _token += ch;
                        state = -1;
                        syn = token.get("!=");
                        break;
                    case 45:
                        // "\"...\""
                        _token += ch;
                        state = -1;
                        syn = token.get("literal_string");
                        break;
                    case 46:
                        // multi-line comment: /*...*
                        _token += ch;
                        if (GetNextChar(prog)) {
                            if (ch == '/') {
                                // closed multi-line comment
                                state = 47;
                                break;
                            } else {
                                // just a normal character '*' in multi-line comment
                                // /*..*...
                                if(ch == '\n'){
                                    lineNum++;
                                }
                                state = 29;
                                break;
                            }
                        }
                        // unclosed multi-line comment. ERROR!
                        state = -2;
                        break;
                    case 47:
                        // multi-line comment: /*...*/
                        _token += ch;
                        state = -1;
                        syn = token.get("comment");
                        break;
                    case 48:
                        // "++"
                        _token += ch;
                        state = -1;
                        syn = token.get("++");
                        break;
                    case 49:
                        // "--"
                        _token += ch;
                        state = -1;
                        syn = token.get("--");
                        break;
                }
            }
            if (syn != -1) {

                switch (syn) {
                    case 82:
                        // Special process for string.
                        addKeyWordToMap("\"", 78, lineNum);
                        addKeyWordToMap(_token.substring(1, _token.length() - 1), 81, lineNum);
                        addKeyWordToMap("\"", 78, lineNum);
                        break;
                    default:
                        addKeyWordToMap(_token, syn, lineNum);
                        break;
                }
            }
        }
    }

    /**
     * add the keyword map to list
     *
     * @param keyWord
     * @param tokenIndex
     */
    public static void addKeyWordToMap(String keyWord, Integer tokenIndex, Integer lineNum) {
        ProgKeyWord progKeyWord = new ProgKeyWord(keyWord, tokenIndex, lineNum);
        listProgKeyWord.add(progKeyWord);
    }

    public static void process(String filePath) {
        analysis(filePath);
        for (ProgKeyWord progKeyWord : listProgKeyWord) {
            System.out.println(progKeyWord.getKey() + ":" + progKeyWord.getIndex()+":" + progKeyWord.getLineNum());
        }
    }
}
