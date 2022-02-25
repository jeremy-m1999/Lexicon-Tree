import java.util.Vector;
import java.util.Iterator;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

/**
 * This class creates a Lexicon Trie which stores certain words and allows the
 * user to add words, see if it contains a prefix/word, remove a word, and
 * determine how many words are currently in the tree.
 *
 * @author Jeremy Mednik
 * @author Kyle Wiblishauser
 */
public class LexiconTrie implements Iterable<String>{
  private LTNode root; //Root of the LexiconTrie
  private int numWords; //Number of words in the LexiconTrie
  private int numNodes; //Number of nodes in the LexiconTrie

  /**
   * Creates a new LexiconTrie with one blank node as the root
   */
  public LexiconTrie() {
    root = new LTNode(' ', false);
  }

  /**
   * Get the number of words stored with the nodes of the LexiconTrie
   *
   * @return the number of words in the LexiconTrie
   */
  public int getNumWords() {
    return numWords;
  }

  /**
   * Get the number of nodes stored in the LexiconTrie
   *
   * @return the number of nodes in the LexiconTrie
   */
  public int getNumNodes() {
    return numNodes;
  }

  /**
   * Add each letter of a word as a node as a child of the previous node starting from the root.
   * If the node already exists, do not add the node but continue from it. Mark isWord of the node
   * of the last letter as true so the nodes are recognized as the word in the LexiconTrie
   *
   * @param word Word to added to the LexiconTrue
   */
  public void add(String word) {
    //Make all of the letters of the word lowercase
    word = word.toLowerCase();
    //LTNode to keep track of node the function is on while moving away from root
    LTNode current = root;
    //Go through each letter of the word
    for(int i = 0; i != word.length(); i++) {
      /*Add to letter to LexiconTrie if it is not already in it. Compare return
      * to null to see if a node was added or if a node for the letter was already
      * in the LexiconTrie. Add one to the number of nodes if a new node was added.
      */
      if(current.addChild(word.charAt(i)) != null) {
        numNodes += 1;
      }
      //Set current to the node for the letter
      current = current.getChild(word.charAt(i));
    }
    current.setisWord(true); //Set isWord for the node of the last letter of the word to true
    numWords += 1; //Increase the number of words by one
  }

  /**
   * Add each word on each line of a file to the LexiconTrie
   *
   * @param fileName Name of file with words to added to the LexiconTrie
   */
  public void addFromFile(String fileName) {
    //Get file from name in parameter of function. Throw an exception if the file is not found
    try(Scanner scanner = new Scanner(new File(fileName))) {
      //Get the word on each line of the file and add it to the LexiconTrie.
      while(scanner.hasNextLine()) {
        String word = scanner.nextLine();
        add(word);
      }
    }catch(IOException e){
      e.printStackTrace();
    }
  }

  /**
   * Check if a string exists as a series of nodes in the LexiconTrie. Return the node of the
   * last letter or null if it does not exist
   */
  private LTNode find(String letters) {
    //Start from the root
    LTNode cur = root;
    //Check if there are a series of nodes that make up the letters of the string
    for (int i = 0; i < letters.length(); i ++) {
      cur = cur.getChild(letters.charAt(i));
      //Return null once a node for a letter does not exist
      if (cur == null) {
        return null;
      }
    }
    return cur; //Return the last letter
  }

  /**
   * Check if a word exists as a series of nodes in the LexiconTrie.
   *
   * @param word word that is being checked to see if it is in the LexiconTrie
   *
   * @return whether the word is stored in the LexiconTrie
   */
  public boolean contains(String word) {
   //Use the find function to determine if the string of the word exists in the LexiconTrie
	if (find(word) != null) {
      return find(word).getIsWord(); //Check if the string for the word is a word in the LexiconTrie
    }
    return false;
  }

  /**
   * Check if a prefix/string exists as a series of nodes in the LexiconTrie. It does not
   * matter if it makes a word or not.
   *
   * @param word word that is being checked to see if it is in the LexiconTrie
   *
   * @return whether the prefix is stored in the LexiconTrie
   */
  public boolean containsPrefix(String prefix) {
    //Use the find function to determine is the prefix/string exists in the LexiconTrie
    return find(prefix) != null;
  }

  /**
   * Remove a word from the LexiconTrie by setting isWord for the node of the final letter to false
   *
   * @param word word that is being removed from the LexiconTrie
   */
  public void remove(String word) {
    //Start from the root
    LTNode cur = root;
    //Go through each node of each letter of the word until the node of the last letter is reached
    for (int i = 0; i < word.length(); i ++) {
      cur = cur.getChild(word.charAt(i));
    }
    cur.setisWord(false); //Set isWord for the node of the last letter to false
    numWords--; //Decrease the number of words by one
  }

  /**
   * Returns an iterator over the words in the lexicon in the alphabetical order
   *
   * @return an iterator over the words in the lexicon in the alphabetical order
   */
  public Iterator<String> iterator() {
    Vector<String> v = new Vector<String>();
    gatherWords(v, root, "");

    return v.iterator();
  }

  /**
   * A private helper method that adds the current amount of words to a vector.
   *
   * @param Vector<String> v, LTNode cur, String prefix.
   */
  private void gatherWords(Vector<String> v, LTNode cur, String prefix) {
    prefix += cur.getLetter();
    if (cur.getIsWord())
      v.add(prefix);

    //Recurse to all children of cur
    Iterator<LTNode> iter = cur.iterator();
    while(iter.hasNext())
      gatherWords(v, iter.next(), prefix);
  }
}
