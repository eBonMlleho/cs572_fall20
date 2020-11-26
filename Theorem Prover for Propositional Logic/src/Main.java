/**
 * @author Zhanghao Wen
 */

package edu.iastate.cs572.proj2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
	static LinkedList<ConjunctiveNormalForm> cnfList = new LinkedList<ConjunctiveNormalForm>();
	static LinkedList<Literal> goaLiteralList = new LinkedList<Literal>();

	public static void main(String[] args) throws FileNotFoundException {
		// read from KB tile
		File myObj = new File("KB1.txt");
		Scanner myReader = new Scanner(myObj);
		Boolean isGoalBoolean = false;

		// read from input txt file

		while (myReader.hasNextLine()) {

			String data = myReader.nextLine();
			if (!data.trim().isEmpty()) {
				if (data.equals("Knowledge Base:")) {
					// do nothing
				} else if (data.equals("Prove the following sentences by refutation:")) { // this is goal
					isGoalBoolean = true;
				} else {

					addKBtoCnf(isGoalBoolean, data);
				}

			}

		}
		myReader.close();

		// iterate all goal literals
		for (int i = 0; i < goaLiteralList.size(); i++) {
			boolean result = PLResolutiontest(cnfList, goaLiteralList.get(i));
			if (result) {
				goaLiteralList.get(i).flipNegative(); // flip back to original value
				System.out.println("The KB entails " + goaLiteralList.get(i));
				System.out.println();
			} else {
				goaLiteralList.get(i).flipNegative(); // flip back to original value
				System.out.println("The KB does not entail " + goaLiteralList.get(i));
				System.out.println();
			}

		}

	}

	public static Boolean PLResolutiontest(LinkedList<ConjunctiveNormalForm> KB, Literal literal) {
		// add all clause from all CNFs to clause list
		List<Clause> allClauselist = new ArrayList<Clause>();
		for (int i = 0; i < KB.size(); i++) {
			for (int j = 0; j < KB.get(i).size(); j++) {
				allClauselist.add(KB.get(i).get(j));
			}
		}
		literal.flipNegative();// ~a
		// add this ~a into all ClausesList
		Clause toolClause = new Clause();
		toolClause.add(literal);
		allClauselist.add(toolClause);

		System.out.println("All clause set incldues flipped goal literal is: " + allClauselist);

		List<Clause> newClauseList = new ArrayList<Clause>();

		while (true) {
			newClauseList.clear();
			for (int i = 0; i < allClauselist.size(); i++) { // for each pair of clauses Ci Cj in clauses do:
				for (int j = i + 1; j < allClauselist.size(); j++) {
					Clause resolvents = PLResolvetest(allClauselist.get(i), allClauselist.get(j));
//					System.out.print("compare 1st clause: " + allClauselist.get(i));
//					System.out.print("  with 2: " + allClauselist.get(j) + "  we get resolvent: " + resolvents + "  ");

					if (resolvents != null) {
						if (resolvents.size() == 0) { // if empty clause
//							System.out.println(allClauselist.get(i));
//							System.out.println(allClauselist.get(j));
//							System.out.println("------------------");
							System.out.println("Empty clause");

							return true;
						}
						// need to check whether new already have resolvent
						if (resolvents.size() != 0 && !myContains(newClauseList, resolvents)) {
//							System.out.println(allClauselist.get(i));
//							System.out.println(allClauselist.get(j));
//							System.out.println("------------------");
//							System.out.println(resolvents);
							newClauseList.add(resolvents);

//							System.out.println("new clause list is " + newClauseList);
						}
					} else {
						continue;
					}

				}
			}

			if (myContains(allClauselist, newClauseList)) {
				System.out.println("No new clauses are added.");

				return false;
			}

			// add new to all clauses list
			for (int i = 0; i < newClauseList.size(); i++) {
				if (!myContains(allClauselist, newClauseList.get(i))) {
					allClauselist.add(newClauseList.get(i));
				}

			}
//			System.out.println("all clauses list after adding new list is :" + allClauselist.toString());

		}

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

	/**
	 * if clause in list clauseBig have all the clause that clauseSmall list have,
	 * then return true
	 * 
	 * @param clauseBig
	 * @param clauseSmall
	 * @return
	 */
	public static boolean myContains(List<Clause> clauseBig, List<Clause> clauseSmall) {

		if (clauseSmall.size() > clauseBig.size()) {
			return false;
		}
		boolean result = true;
		outerloop: for (int j = 0; j < clauseSmall.size(); j++) {
			for (int i = 0; i < clauseBig.size(); i++) {
				if (clauseBig.get(i).equals(clauseSmall.get(j))) { // a in A has b
					result = true;
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

	/**
	 * return true if clauseBig list have this clause
	 * 
	 * @param clauseBig
	 * @param clause
	 * @return
	 */
	private static boolean myContains(List<Clause> clauseBig, Clause clause) {

		for (int i = 0; i < clauseBig.size(); i++) {
			if (clauseBig.get(i).equals(clause)) {
				return true;
			}
		}

		return false;
	}

	public static void addKBtoCnf(Boolean isGoal, String data) {
		String infix = data;
		infix = infix.replaceAll(" ", "");

		String postfix = Infix2Postfix.infixToPostfix(infix);
		ExpressionTree et = new ExpressionTree();
		char[] charArray = postfix.toCharArray();
		Node root = et.constructTree(charArray);
		et.Postorder(root);

		// process goal or not
		if (!isGoal) {
			cnfList.add(root.cnf); // save all cnfs in a list
//			System.out.println("KB all Root CNFs are " + root.cnf);

		} else {
			// goal is single literal, we simply add this literal to goalLiteralList
			goaLiteralList.add(root.cnf.get(0).get(0));
		}

	}

}
