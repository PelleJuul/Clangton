open CommandLine;
open TextIO;

(* readAll : string -> string
 * læser alle linjer i en textfil og retunerer dem som en streng *)
fun readAll filename =
    let val fd =  TextIO.openIn filename
    in  TextIO.inputAll fd before TextIO.closeIn fd
    end;

local
  (* splitAt' : char * string * int * string list -> string list
   * opdeler stringen s med hensyn til karakteren c og retunerer
   * vha. listen a *)
  fun splitAt' (c, s, n, a) = if n >= size s then rev (s::a) else
                              if String.sub(s, n) = c then
                                splitAt'(c, String.extract(s, n+1, NONE), 0,
                                         String.extract(s, 0, SOME n)::a)
                              else
                                splitAt'(c, s, n+1, a);
in
  (* splitAt : char * string -> string list
   * kalder splitAt' med korrekte parametre *)
  fun splitAt (c, s) = splitAt'(c, s, 0, []);
end;

val header = "#include<stdio.h>\n int main() {\n"

fun getIf (pre, ope) = "if (" ^ pre ^ "){" ^ ope ^ ";}\n"
fun getEIf (pre, ope) = "else if (" ^ pre ^ "){" ^ ope ^ ";}\n"
fun getInt (n, v) = "int " ^ n ^ " = " ^ v ^ ";\n"

fun cleanString #" " = ""
  | cleanString #"\n" = ""
  | cleanString #"\t" = ""
  | cleanString c = Char.toString c

fun compile fin fout =
let
  val str = readAll fin
  val tail = readAll "main"
  val ostream = TextIO.openOut fout

  val str = String.translate cleanString str
  val lstr = splitAt(#"/", str);

  val size = List.nth(lstr, 0)
  val pos = List.nth(lstr, 1)
  val fin = List.nth(lstr, 2)
  val plane = List.nth(lstr, 3)
  val del1 = List.nth(lstr, 4)
  val del2 = List.nth(lstr, 5)
  val tele1 = List.nth(lstr, 6)
  val tele2 = List.nth(lstr, 7)
  val ind = List.nth(lstr, 8)
  val out = List.nth(lstr, 9)
  val code = header ^
             getInt("size", size) ^
             getInt("pos", pos) ^
             getInt("fin", fin) ^
             getInt("plane[]", "{" ^ plane ^ "}") ^
             getInt("dir", "0") ^
             getInt("del1[]", "{" ^ del1 ^ "}") ^
             getInt("del2[]", "{" ^ del2 ^ "}") ^
             getInt("tele1[]", "{" ^ tele1 ^ "}") ^
             getInt("tele2[]", "{" ^ tele2 ^ "}") ^
             getInt("ind[]", "{" ^ ind ^ "}") ^
             getInt("out[]", "{" ^ out ^ "}") ^
             tail
in
  TextIO.output(ostream, code); TextIO.closeOut(ostream)
end;

val program = compile (List.nth(arguments(), 0)) (List.nth(arguments(), 1))
