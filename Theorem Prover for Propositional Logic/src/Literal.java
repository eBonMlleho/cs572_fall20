/**
 * @author Zhanghao Wen
 */

package edu.iastate.cs572.proj2;

public class Literal {
	char literal;
	boolean isNegative;

	public Literal(char item) {
		literal = item;
		isNegative = false;
	}

	public void flipNegative() {
		isNegative = !isNegative;
	}

	public void setSign(boolean b) {
		isNegative = b;
	}

	public String toString() {
		if (isNegative) {
			return "~" + literal;
		}
		return String.valueOf(literal);
	}

	// compare same literal, does not consider ~ sign
	public boolean equals(Literal literal) {
		int compareOneTwo = Character.compare(this.literal, literal.literal);
		if (compareOneTwo == 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean equalsButDifferentSign(Literal literal) {
		int compareOneTwo = Character.compare(this.literal, literal.literal);
		if (compareOneTwo == 0 && this.isNegative != literal.isNegative) {
			return true;
		} else {
			return false;
		}
	}

}
