/**
 * @author Zhanghao Wen
 */
package edu.iastate.cs572.proj2;

import java.util.ArrayList;
import java.util.List;

public class Test_Contains {

	public static void main(String[] args) {
		List<Clause> newClauseList = new ArrayList<Clause>();
		List<Clause> ClauseList = new ArrayList<Clause>();
		Clause c1 = new Clause();
		c1.add(new Literal('O'));
		c1.add(new Literal('R'));

		Clause c2 = new Clause();
		c2.add(new Literal('R'));
		c2.get(0).flipNegative();

		Clause c3 = new Clause();
		c3.add(new Literal('O'));
		c3.get(0).flipNegative();

		Clause c6 = new Clause();
		c6.add(new Literal('R'));

		Clause c7 = new Clause();
		c7.add(new Literal('O'));
		ClauseList.add(c1);
		ClauseList.add(c2);
		ClauseList.add(c3);
//		ClauseList.add(c6);
//		ClauseList.add(c7);

		Clause c4 = new Clause();
		c4.add(new Literal('O'));

		Clause c5 = new Clause();
		c5.add(new Literal('R'));

		newClauseList.add(c4);
		newClauseList.add(c5);
		System.out.println(ClauseList);
		System.out.println(newClauseList);
		System.out.println(contains(ClauseList, newClauseList));

	}

	public static boolean contains(List<Clause> clauseBig, List<Clause> clauseSmall) {

		if (clauseSmall.size() > clauseBig.size()) {
			return false;
		}

		boolean result = true;

		outerloop: for (int j = 0; j < clauseSmall.size(); j++) {

			for (int i = 0; i < clauseBig.size(); i++) {
//				System.out.println("??");
				if (clauseBig.get(i).equals(clauseSmall.get(j))) { // a in A has b
					result = true;
//					System.out.println("equaled is literal:" + clauseBig.get(i));
//					System.out.println(result);
					continue outerloop;
				} else {
					result = false;
				}
			}

			if (result == false) { // if after all a no a equals b, then return false
				return false;
			}

		}

		return result;
	}

}
