/**
 * @author Zhanghao Wen
 */
package edu.iastate.cs572.proj2;

import java.util.ArrayList;
import java.util.List;

public class Test_PairClause {
	public static void main(String[] args) {
		List<Clause> allClauselist = new ArrayList<Clause>();
		Clause c1 = new Clause();
		c1.add(new Literal('W'));
		c1.add(new Literal('O'));
		c1.get(1).flipNegative();
		c1.add(new Literal('R'));
		c1.get(2).flipNegative();

		Clause c2 = new Clause();
		c2.add(new Literal('P'));
		c2.add(new Literal('R'));
		c2.add(new Literal('W'));
		c2.get(2).flipNegative();

		Clause c3 = new Clause();
		c3.add(new Literal('W'));
		c3.get(0).flipNegative();

		Clause c4 = new Clause();
		c4.add(new Literal('O'));

		Clause c5 = new Clause();
		c5.add(new Literal('W'));

		Clause c6 = new Clause();
		c6.add(new Literal('P'));
		c6.get(0).flipNegative();

		allClauselist.add(c1);
		allClauselist.add(c2);
		allClauselist.add(c3);
		allClauselist.add(c4);
		allClauselist.add(c5);
		allClauselist.add(c6);

		System.out.println(allClauselist.toString());

		for (int i = 0; i < allClauselist.size(); i++) { // for each pair of clauses Ci Cj in clauses do:
			for (int j = i + 1; j < allClauselist.size(); j++) {
				System.out.println(Main.PLResolvetest(allClauselist.get(i), allClauselist.get(j)));
			}
		}
	}
}
