import java.util.Vector;
import java.util.Iterator;

/*
* This class creates the properties for LTNode which are used for the
* Lexicon Trie. Each node contains a character and a boolean of whether
* that node is the last letter of a word.
*
* @author Jeremy Mednik
* @author Kyle Wiblishauser
*/
public class LTNode implements Iterable<LTNode> {
  //Member variables
  private char letter;
  private boolean isWord;
  private Vector<LTNode> children;

  /*
  * A constructor that initializes the member variables.
  *
  * @param char character, boolean lastLetter
  */
  public LTNode(char character, boolean lastLetter) {
    letter = character;
    isWord = lastLetter;
    children = new Vector();
  }

  /*
  * Returns the letter of LTNode.
  *
  * @return char letter
  */
  public char getLetter() {
    return letter;
  }

  /*
  * Returns the boolean of whether the node is the last letter of a word.
  *
  * @return boolean isWord
  */
  public boolean getIsWord() {
    return isWord;
  }

  /*
  * Sets whether the node is the last letter of a word.
  *
  * @param boolean value
  */
  public void setisWord(boolean value) {
    isWord = value;
  }

  /*
  * Creates an iterator to traverse through all the nodes in the vector.
  *
  * @return Iterator childrenIterator
  */
  public Iterator<LTNode> iterator() {
    Iterator<LTNode> childrenIterator = children.iterator();
    return childrenIterator;
  }

  /*
  * Adds a LTNode containing a letter to the Lexicon Trie.
  *
  * @param char newChar
  * @return LTNode newNode
  */
  public LTNode addChild(char newChar) {
    //Creates a new LTNode with a specific character.
    LTNode newNode = new LTNode(newChar, isWord);
    Iterator<LTNode> iter = iterator();
    //Checks to make sure that the character is not already in the Trie.
    while(iter.hasNext()) {
      if(iter.next().getLetter() == newChar) {
        return null;
      }
    }
    //Makes sure that the LTNode is in the correct spot alphabettically.
    for (int i = 0; i < children.size(); i++) {
      if (newChar < children.get(i).getLetter()) {
        //Adds the LTNode to the children vector in the given spot.
        children.add(i, newNode);
        //Returns the node.
        return newNode;
      }
    }
    //Adds the newNode to the children if alphabetically last.
    children.add(newNode);
    //Returns the node.
    return newNode;
  }

  /*
  * Gets the child node that contains the character specified. If it doesnt exist,
  * it returns null.
  *
  * @param char newChar
  * @return LTNode searchNode or null
  */
  public LTNode getChild(char newChar) {
    //Creates a new node.
    LTNode searchNode;
    Iterator<LTNode> iter = iterator();
    //Iterator that checks every child until it finds the one containing newChar.
    while(iter.hasNext()) {
      searchNode = iter.next();
      //Returns the node containing newChar.
      if(searchNode.getLetter() == newChar) {
        return searchNode;
      }
    }
    //If all of the children do not contain the character, it returns null.
    return null;
  }

  /*
  * Removes the node containing the specific character.
  *
  * @param char newChar
  */
  public void removeChild(char newChar) {
    for (int i = 0; i < children.size(); i++) {
      //If an LTNode in the children vector contains the character, it removes it.
      if (newChar == children.get(i).letter) {
        children.remove(i);
      }
    }
  }
}
