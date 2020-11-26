/**
 * @author Zhanghao Wen
 */

package edu.iastate.cs572.proj2;

public class Node {

	char value;
	String cnfString; // for test purpose
	ConjunctiveNormalForm cnf;
	Clause clause;
	Literal literal;
	Node left, right;
	String leftString = "";
	String rightString = "";

	Node(char item) {
		value = item;
		left = right = null;
		cnfString = String.valueOf(value);
		clause = new Clause();
		cnf = new ConjunctiveNormalForm();

		// set up literal object
		if (!isOperator(item)) {
			literal = new Literal(item);
		}
	}

	public void setCNF() {
		if (!isOperator(value)) { // Letter only literal
			clause.add(literal);
			cnf.add(clause);
		} else if (this.value == '~') { // literal. only has right child
			cnf = testNegationFunction(this.right.cnf);// work for any #clause
//			cnf = negationFunctionLastVersion(this.right.cnf); // only work if #clause <=2 
		} else if (this.value == '|') {
			cnf = orFunction(this.right.cnf, this.left.cnf);
		} else if (this.value == '&') {
			cnf = andFunction(this.right.cnf, this.left.cnf);
		} else if (this.value == '=') {
			cnf = implyFunction(this.left.cnf, this.right.cnf);
		} else if (this.value == '<') {
			cnf = biImplyFunction(this.left.cnf, this.right.cnf);
		}
	}

	boolean isOperator(char c) {

		if (c == '&' || c == '|' || c == '=' || c == '<' || c == '~') {
			return true;
		}
		return false;
	}

	public void testsetCNF() {

		if (this.left != null) {
			leftString = this.left.cnfString;
		}
		if (this.right != null) {
			rightString = this.right.cnfString;
		}

		cnfString = cnfString + leftString + rightString;
	}

	public ConjunctiveNormalForm biImplyFunction(ConjunctiveNormalForm left, ConjunctiveNormalForm right) {
		ConjunctiveNormalForm result = new ConjunctiveNormalForm();
		// (cnf1=>cnf2)&(cnf2=>cnf1)
		ConjunctiveNormalForm LHS = new ConjunctiveNormalForm();
		ConjunctiveNormalForm RHS = new ConjunctiveNormalForm();

		ConjunctiveNormalForm lCopy = new ConjunctiveNormalForm();
		ConjunctiveNormalForm rCopy = new ConjunctiveNormalForm();

		ConjunctiveNormalForm lCopy1 = new ConjunctiveNormalForm();
		ConjunctiveNormalForm rCopy1 = new ConjunctiveNormalForm();

		// very deeeep COPY because negation may changed literal value
		for (int i = 0; i < left.size(); i++) { // each clause
			Clause clause = new Clause();
			for (int j = 0; j < left.get(i).size(); j++) { // each literal
				Literal literal = new Literal(left.get(i).get(j).literal);
				clause.add(literal);
			}
			lCopy.add(clause);
		}
		// copy of right cnf
		for (int i = 0; i < right.size(); i++) {
			Clause clause = new Clause();
			for (int j = 0; j < right.get(i).size(); j++) { // each literal
				Literal literal = new Literal(right.get(i).get(j).literal);
				clause.add(literal);
			}
			rCopy.add(clause);
		}
		// second copy of left cnf
		for (int i = 0; i < left.size(); i++) { // each clause
			Clause clause = new Clause();
			for (int j = 0; j < left.get(i).size(); j++) { // each literal
				Literal literal = new Literal(left.get(i).get(j).literal);
				clause.add(literal);
			}
			lCopy1.add(clause);
		}
		// second copy of right cnf
		for (int i = 0; i < right.size(); i++) {
			Clause clause = new Clause();
			for (int j = 0; j < right.get(i).size(); j++) { // each literal
				Literal literal = new Literal(right.get(i).get(j).literal);
				clause.add(literal);
			}
			rCopy1.add(clause);
		}

		LHS = implyFunction(lCopy, rCopy);
		RHS = implyFunction(rCopy1, lCopy1);

		result = andFunction(LHS, RHS);
		return result;
	}

	public ConjunctiveNormalForm implyFunction(ConjunctiveNormalForm left, ConjunctiveNormalForm right) {
		ConjunctiveNormalForm result = new ConjunctiveNormalForm();
		ConjunctiveNormalForm afterNegation = new ConjunctiveNormalForm();
		// Apply negation rule
		afterNegation = testNegationFunction(left);
		// Apply or rule
		result = orFunction(afterNegation, right);

		return result;
	}

	public ConjunctiveNormalForm orFunction(ConjunctiveNormalForm a, ConjunctiveNormalForm b) {
		ConjunctiveNormalForm result = new ConjunctiveNormalForm();
		for (int i = 0; i < a.size(); i++) {
			for (int j = 0; j < b.size(); j++) {
				Clause clause = new Clause();
				clause.add(a.get(i));
				clause.add(b.get(j));
				result.add(clause); // need test
			}
		}

		return result;
	}

	public ConjunctiveNormalForm andFunction(ConjunctiveNormalForm a, ConjunctiveNormalForm b) {
		ConjunctiveNormalForm result = new ConjunctiveNormalForm();
		result = a;
		for (int i = 0; i < b.size(); i++) {
			result.add(b.get(i));
		}
		return result;
	}

	public ConjunctiveNormalForm testNegationFunction(ConjunctiveNormalForm a) {
		ConjunctiveNormalForm result = new ConjunctiveNormalForm();

		// very deep copy
		ConjunctiveNormalForm copyCNF = new ConjunctiveNormalForm();
		for (int i = 0; i < a.size(); i++) { // each clause
			Clause clause = new Clause();
			for (int j = 0; j < a.get(i).size(); j++) { // each literal
				Literal literal = new Literal(a.get(i).get(j).literal);
				literal.setSign(a.get(i).get(j).isNegative);
				clause.add(literal);
			}
			copyCNF.add(clause);
		}

		// recursively call
		if (copyCNF.size() > 2) {
			ConjunctiveNormalForm processor = new ConjunctiveNormalForm();

			processor.add(copyCNF.get(0));
			result = testNegationFunction(processor);

			for (int i = 1; i < copyCNF.size(); i++) {
				ConjunctiveNormalForm processor1 = new ConjunctiveNormalForm();
				ConjunctiveNormalForm result2 = new ConjunctiveNormalForm();
				processor1.add(copyCNF.get(i));
				result2 = testNegationFunction(processor1);

				result = orFunction(result, result2);
			}

		} else {

			// negate all literals
			for (int i = 0; i < copyCNF.size(); i++) { // each clause
				for (int j = 0; j < copyCNF.get(i).size(); j++) { // each literal
					copyCNF.get(i).get(j).flipNegative();
				}
			}
			// suppose only 2 clause in original list
			if (copyCNF.size() == 2) {
				Clause c1 = copyCNF.get(0); // first clause
				Clause c2 = copyCNF.get(1); // second clause
				for (int x = 0; x < c1.size(); x++) {
					for (int y = 0; y < c2.size(); y++) {
						Literal l1 = c1.get(x);
						Literal l2 = c2.get(y);
						Clause clause = new Clause();
						clause.add(l1);
						clause.add(l2);
						result.add(clause);

					}
				}
			} else if (copyCNF.size() == 1) { // only 1 clause in cnf, simply take each literal inside that clause and
												// turn it
				// into clause with individual interal
				Clause c1 = copyCNF.get(0); //
				for (int x = 0; x < c1.size(); x++) {
					Literal l1 = c1.get(x);
					Clause clause = new Clause();
					clause.add(l1);
					result.add(clause);
				}
			}

		}

		return result;
	}

	public ConjunctiveNormalForm negationFunctionLastVersion(ConjunctiveNormalForm a) {
		ConjunctiveNormalForm result = new ConjunctiveNormalForm();
		System.out.println("feed in clause is " + a);
		// very deep copy
		ConjunctiveNormalForm copyCNF = new ConjunctiveNormalForm();
		for (int i = 0; i < a.size(); i++) { // each clause
			Clause clause = new Clause();
			for (int j = 0; j < a.get(i).size(); j++) { // each literal
				Literal literal = new Literal(a.get(i).get(j).literal);
				literal.setSign(a.get(i).get(j).isNegative);
				clause.add(literal);
			}
			copyCNF.add(clause);
		}

		System.out.println("after deep copy, copyCNF is " + copyCNF);

		// negate all literals in all clause

		for (int i = 0; i < copyCNF.size(); i++) { // each clause
			for (int j = 0; j < copyCNF.get(i).size(); j++) { // each literal
				System.out.println("before flip " + copyCNF.get(i).get(j));
				copyCNF.get(i).get(j).flipNegative();
				System.out.println("after flip " + copyCNF.get(i).get(j));
			}
		}

		// suppose only 2 clause in original list
		// PLACE to IMORVE
		if (copyCNF.size() == 2) {
			Clause c1 = copyCNF.get(0);
			Clause c2 = copyCNF.get(1);

			for (int x = 0; x < c1.size(); x++) {
				for (int y = 0; y < c2.size(); y++) {
					Literal l1 = c1.get(x);
					Literal l2 = c2.get(y);
					Clause clause = new Clause();
					clause.add(l1);
					clause.add(l2);
					result.add(clause);

				}
			}
		} else if (copyCNF.size() == 1) { // only 1 clause in cnf, simply take each literal inside that clause and
											// return
											// it
			// into clause with individual interal
			Clause c1 = copyCNF.get(0); //
			for (int x = 0; x < c1.size(); x++) {
				Literal l1 = c1.get(x);
				Clause clause = new Clause();
				clause.add(l1);
				result.add(clause);
			}
		}

		return result;
	}

	public String toString() {

		return cnfString.toString();
	}

}
