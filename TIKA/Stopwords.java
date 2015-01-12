import java.util.HashSet;

public class Stopwords {
	HashSet s = new HashSet();

	public Stopwords() {
		s.add("a");
		s.add("able");
		s.add("about");
		s.add("above");
		s.add("according");
		s.add("accordingly");
		s.add("across");
		s.add("actually");
		s.add("after");
		s.add("afterwards");
		s.add("again");
		s.add("against");
		s.add("all");
		s.add("allow");
		s.add("allows");
		s.add("almost");
		s.add("alone");
		s.add("along");
		s.add("already");
		s.add("also");
		s.add("although");
		s.add("always");
		s.add("am");
		s.add("among");
		s.add("amongst");
		s.add("an");
		s.add("and");
		s.add("another");
		s.add("any");
		s.add("anybody");
		s.add("anyhow");
		s.add("anyone");
		s.add("anything");
		s.add("anyway");
		s.add("anyways");
		s.add("anywhere");
		s.add("apart");
		s.add("appear");
		s.add("appreciate");
		s.add("appropriate");
		s.add("are");
		s.add("around");
		s.add("as");
		s.add("aside");
		s.add("ask");
		s.add("asking");
		s.add("associated");
		s.add("at");
		s.add("available");
		s.add("away");
		s.add("awfully");
		s.add("b");
		s.add("be");
		s.add("became");
		s.add("because");
		s.add("become");
		s.add("becomes");
		s.add("becoming");
		s.add("been");
		s.add("before");
		s.add("beforehand");
		s.add("behind");
		s.add("being");
		s.add("believe");
		s.add("below");
		s.add("beside");
		s.add("besides");
		s.add("best");
		s.add("better");
		s.add("between");
		s.add("beyond");
		s.add("both");
		s.add("brief");
		s.add("but");
		s.add("by");
		s.add("c");
		s.add("came");
		s.add("can");
		s.add("cannot");
		s.add("cant");
		s.add("cause");
		s.add("causes");
		s.add("certain");
		s.add("certainly");
		s.add("changes");
		s.add("clearly");
		s.add("co");
		s.add("com");
		s.add("come");
		s.add("comes");
		s.add("concerning");
		s.add("consequently");
		s.add("consider");
		s.add("considering");
		s.add("contain");
		s.add("containing");
		s.add("contains");
		s.add("corresponding");
		s.add("could");
		s.add("course");
		s.add("currently");
		s.add("d");
		s.add("definitely");
		s.add("described");
		s.add("despite");
		s.add("did");
		s.add("different");
		s.add("do");
		s.add("does");
		s.add("doing");
		s.add("done");
		s.add("down");
		s.add("downwards");
		s.add("during");
		s.add("e");
		s.add("each");
		s.add("edu");
		s.add("eg");
		s.add("eight");
		s.add("either");
		s.add("else");
		s.add("elsewhere");
		s.add("enough");
		s.add("entirely");
		s.add("especially");
		s.add("et");
		s.add("etc");
		s.add("even");
		s.add("ever");
		s.add("every");
		s.add("everybody");
		s.add("everyone");
		s.add("everything");
		s.add("everywhere");
		s.add("ex");
		s.add("exactly");
		s.add("example");
		s.add("except");
		s.add("f");
		s.add("far");
		s.add("few");
		s.add("fifth");
		s.add("first");
		s.add("five");
		s.add("followed");
		s.add("following");
		s.add("follows");
		s.add("for");
		s.add("former");
		s.add("formerly");
		s.add("forth");
		s.add("four");
		s.add("from");
		s.add("further");
		s.add("furthermore");
		s.add("g");
		s.add("get");
		s.add("gets");
		s.add("getting");
		s.add("given");
		s.add("gives");
		s.add("go");
		s.add("goes");
		s.add("going");
		s.add("gone");
		s.add("got");
		s.add("gotten");
		s.add("greetings");
		s.add("h");
		s.add("had");
		s.add("happens");
		s.add("hardly");
		s.add("has");
		s.add("have");
		s.add("having");
		s.add("he");
		s.add("hello");
		s.add("help");
		s.add("hence");
		s.add("her");
		s.add("here");
		s.add("hereafter");
		s.add("hereby");
		s.add("herein");
		s.add("hereupon");
		s.add("hers");
		s.add("herself");
		s.add("hi");
		s.add("him");
		s.add("himself");
		s.add("his");
		s.add("hither");
		s.add("hopefully");
		s.add("how");
		s.add("howbeit");
		s.add("however");
		s.add("i");
		s.add("ie");
		s.add("if");
		s.add("ignored");
		s.add("immediate");
		s.add("in");
		s.add("inasmuch");
		s.add("inc");
		s.add("indeed");
		s.add("indicate");
		s.add("indicated");
		s.add("indicates");
		s.add("inner");
		s.add("insofar");
		s.add("instead");
		s.add("into");
		s.add("inward");
		s.add("is");
		s.add("it");
		s.add("its");
		s.add("itself");
		s.add("j");
		s.add("just");
		s.add("k");
		s.add("keep");
		s.add("keeps");
		s.add("kept");
		s.add("know");
		s.add("knows");
		s.add("known");
		s.add("l");
		s.add("last");
		s.add("lately");
		s.add("later");
		s.add("latter");
		s.add("latterly");
		s.add("least");
		s.add("less");
		s.add("lest");
		s.add("let");
		s.add("like");
		s.add("liked");
		s.add("likely");
		s.add("little");
		s.add("ll"); // s.added to avoid words like
		// you'll,I'll etc.
		s.add("look");
		s.add("looking");
		s.add("looks");
		s.add("ltd");
		s.add("m");
		s.add("mainly");
		s.add("many");
		s.add("may");
		s.add("maybe");
		s.add("me");
		s.add("mean");
		s.add("meanwhile");
		s.add("merely");
		s.add("might");
		s.add("more");
		s.add("moreover");
		s.add("most");
		s.add("mostly");
		s.add("much");
		s.add("must");
		s.add("my");
		s.add("myself");
		s.add("n");
		s.add("name");
		s.add("namely");
		s.add("nd");
		s.add("near");
		s.add("nearly");
		s.add("necessary");
		s.add("need");
		s.add("needs");
		s.add("neither");
		s.add("never");
		s.add("nevertheless");
		s.add("new");
		s.add("next");
		s.add("nine");
		s.add("no");
		s.add("nobody");
		s.add("non");
		s.add("none");
		s.add("noone");
		s.add("nor");
		s.add("normally");
		s.add("not");
		s.add("nothing");
		s.add("novel");
		s.add("now");
		s.add("nowhere");
		s.add("o");
		s.add("obviously");
		s.add("of");
		s.add("off");
		s.add("often");
		s.add("oh");
		s.add("ok");
		s.add("okay");
		s.add("old");
		s.add("on");
		s.add("once");
		s.add("one");
		s.add("ones");
		s.add("only");
		s.add("onto");
		s.add("or");
		s.add("other");
		s.add("others");
		s.add("otherwise");
		s.add("ought");
		s.add("our");
		s.add("ours");
		s.add("ourselves");
		s.add("out");
		s.add("outside");
		s.add("over");
		s.add("overall");
		s.add("own");
		s.add("p");
		s.add("particular");
		s.add("particularly");
		s.add("per");
		s.add("perhaps");
		s.add("placed");
		s.add("please");
		s.add("plus");
		s.add("possible");
		s.add("presumably");
		s.add("probably");
		s.add("provides");
		s.add("q");
		s.add("que");
		s.add("quite");
		s.add("qv");
		s.add("r");
		s.add("rather");
		s.add("rd");
		s.add("re");
		s.add("really");
		s.add("reasonably");
		s.add("regarding");
		s.add("regardless");
		s.add("regards");
		s.add("relatively");
		s.add("respectively");
		s.add("right");
		s.add("s");
		s.add("said");
		s.add("same");
		s.add("saw");
		s.add("say");
		s.add("saying");
		s.add("says");
		s.add("second");
		s.add("secondly");
		s.add("see");
		s.add("seeing");
		s.add("seem");
		s.add("seemed");
		s.add("seeming");
		s.add("seems");
		s.add("seen");
		s.add("self");
		s.add("selves");
		s.add("sensible");
		s.add("sent");
		s.add("serious");
		s.add("seriously");
		s.add("seven");
		s.add("several");
		s.add("shall");
		s.add("she");
		s.add("should");
		s.add("since");
		s.add("six");
		s.add("so");
		s.add("some");
		s.add("somebody");
		s.add("somehow");
		s.add("someone");
		s.add("something");
		s.add("sometime");
		s.add("sometimes");
		s.add("somewhat");
		s.add("somewhere");
		s.add("soon");
		s.add("sorry");
		s.add("specified");
		s.add("specify");
		s.add("specifying");
		s.add("still");
		s.add("sub");
		s.add("such");
		s.add("sup");
		s.add("sure");
		s.add("t");
		s.add("take");
		s.add("taken");
		s.add("tell");
		s.add("tends");
		s.add("th");
		s.add("than");
		s.add("thank");
		s.add("thanks");
		s.add("thanx");
		s.add("that");
		s.add("thats");
		s.add("the");
		s.add("their");
		s.add("theirs");
		s.add("them");
		s.add("themselves");
		s.add("then");
		s.add("thence");
		s.add("there");
		s.add("thereafter");
		s.add("thereby");
		s.add("therefore");
		s.add("therein");
		s.add("theres");
		s.add("thereupon");
		s.add("these");
		s.add("they");
		s.add("think");
		s.add("third");
		s.add("this");
		s.add("thorough");
		s.add("thoroughly");
		s.add("those");
		s.add("though");
		s.add("three");
		s.add("through");
		s.add("throughout");
		s.add("thru");
		s.add("thus");
		s.add("to");
		s.add("together");
		s.add("too");
		s.add("took");
		s.add("toward");
		s.add("towards");
		s.add("tried");
		s.add("tries");
		s.add("truly");
		s.add("try");
		s.add("trying");
		s.add("twice");
		s.add("two");
		s.add("u");
		s.add("un");
		s.add("under");
		s.add("unfortunately");
		s.add("unless");
		s.add("unlikely");
		s.add("until");
		s.add("unto");
		s.add("up");
		s.add("upon");
		s.add("us");
		s.add("use");
		s.add("used");
		s.add("useful");
		s.add("uses");
		s.add("using");
		s.add("usually");
		s.add("uucp");
		s.add("v");
		s.add("value");
		s.add("various");
		s.add("ve"); // s.added to avoid words like
		// I've,you've etc.
		s.add("very");
		s.add("via");
		s.add("viz");
		s.add("vs");
		s.add("w");
		s.add("want");
		s.add("wants");
		s.add("was");
		s.add("way");
		s.add("we");
		s.add("welcome");
		s.add("well");
		s.add("went");
		s.add("were");
		s.add("what");
		s.add("whatever");
		s.add("when");
		s.add("whence");
		s.add("whenever");
		s.add("where");
		s.add("whereafter");
		s.add("whereas");
		s.add("whereby");
		s.add("wherein");
		s.add("whereupon");
		s.add("wherever");
		s.add("whether");
		s.add("which");
		s.add("while");
		s.add("whither");
		s.add("who");
		s.add("whoever");
		s.add("whole");
		s.add("whom");
		s.add("whose");
		s.add("why");
		s.add("will");
		s.add("willing");
		s.add("wish");
		s.add("with");
		s.add("within");
		s.add("without");
		s.add("wonder");
		s.add("would");
		s.add("would");
		s.add("x");
		s.add("y");
		s.add("yes");
		s.add("yet");
		s.add("you");
		s.add("your");
		s.add("yours");
		s.add("yourself");
		s.add("yourselves");
		s.add("z");
		s.add("zero");
	}

	public String removeStopwords(String content) {
		content.replaceAll(",|!|\\.|\"|\\?|\'|\\@|\\#|:|-|;|_", "");
		content.replaceAll("\n", " "); 
		String[] content_dd = content.split(" ");
		String c=null;
		int k = 0;
		for (int j = 0; j < content_dd.length; j++) {
			if(content_dd[j].length()!=0)
			{
				content_dd[j].replaceAll(" ", "");
				content_dd[j].replaceAll("\n", ""); 
				if (s.contains(content_dd[j].toLowerCase())) {
				content_dd[j]="";
				}
			}
		}
		for(int j = 0; j < content_dd.length; j++){
			if(content_dd[j].length()!=0)
			{
				c=c+content_dd[j]+" ";
			}
		}
		return c;
	}

}
