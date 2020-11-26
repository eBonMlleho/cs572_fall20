/**
 * @author Zhanghao Wen
 */
package edu.iastate.cs572.proj2;

public class Test_Resolvent {
	public static void main(String[] args) {

		Clause c1 = new Clause();
		c1.add(new Literal('P'));
		c1.add(new Literal('O'));
//		c1.get(1).flipNegative();
//		c1.add(new Literal('R'));
//		c1.get(2).flipNegative();
//		c1.add(new Literal('X'));

		Clause c2 = new Clause();
		c2.add(new Literal('P'));
		c2.get(0).flipNegative();
		c2.add(new Literal('R'));
//		c2.add(new Literal('W'));
//		c2.get(2).flipNegative();
//		c2.add(new Literal('X'));

		System.out.println(PLResolvetest(c1, c2));

	}

	public static Clause PLResolvetest(Clause clauseA, Clause clauseB) {
		Clause resultClause = new Clause();

		// deep copy of clause A
		Clause aClause = new Clause();
		for (int i = 0; i < clauseA.size(); i++) {
			aClause.add(clauseA.get(i));
		}

		// deep copy of clause B
		Clause bClause = new Clause();
		for (int i = 0; i < clauseB.size(); i++) {
			bClause.add(clauseB.get(i));
		}

		// apply resolvent

		// if clauseA and clauseB have same literal with opposite sign
		boolean haveOppsoteLiteral = false;

		// Every time you remove an item, you are changing the index of the one in front
		// of it (so when you delete list[1], list[2] becomes list[1], hence the skip.
		for (int i = aClause.size() - 1; i >= 0; i--) {
			for (int j = bClause.size() - 1; j >= 0; j--) {
				if (aClause.get(i).equalsButDifferentSign(bClause.get(j))) {

					aClause.ll.remove(i);
					bClause.ll.remove(j);
					haveOppsoteLiteral = true;
					break; // otherwise aClause index out of bound
				}
			}
		}

		// copy remaining literal and return
		if (haveOppsoteLiteral) {
			for (int i = 0; i < aClause.size(); i++) {
				resultClause.add(aClause.get(i));
			}

			for (int i = 0; i < bClause.size(); i++) {
				resultClause.add(bClause.get(i));
			}
			if (resultClause.size() != 0) {
				return resultClause;
			} else { // if size == 0 , return empty set
				return new Clause();
			}

		} else {
			return null;
		}

	}
}
