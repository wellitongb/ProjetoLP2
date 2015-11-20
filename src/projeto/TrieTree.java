package projeto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wellitongb
 */
public class TrieTree {
    private final TrieNode root;
    
    /**
     * Construtor para 
     */
    public TrieTree() {
        this.root = new TrieNode('-', null);
    }
    
    /**
     *
     * @param word
     */
    public void insertWord(String word){
        TrieNode aux= this.root;
        char[] keys = new char[word.length()];
        word.getChars(0, word.length(), keys, 0);
        for(int i = 0; i < keys.length; i++){
            aux.addNodePt(keys[i]);
            TrieNode aux2 = aux.getNodePt(keys[i]);
            aux = aux2;
        }
        aux.addNodePt('*');
    }
    
    /**
     *
     * @param word
     */
    public void removeWord(String word){
        int cont = 0;
        TrieNode aux = this.root;
        char[] keys = new char[word.length()];
        word.getChars(0, word.length(), keys, 0);
        for(; cont < keys.length; cont++){
            if(aux.containNodePt(keys[cont])){
                TrieNode aux2 = aux.getNodePt(keys[cont]);
                aux = aux2;
            }
            else
                return;
        }
        if(aux.getChildren().size() > 1){
            aux.removeNodePt('*');
        }
        else{
            while(aux.getChildren().size() < 2){
                TrieNode aux2 = aux.getPatern();
                aux = aux2;
                cont--;
            }
            aux.removeNodePt(keys[cont]);
        }
    }
    
    /**
     *
     * @param word
     * @return
     */
    public boolean searchWord(String word){
        TrieNode aux= this.root;
        char[] keys = new char[word.length()];
        word.getChars(0, word.length(), keys, 0);
        for(int i = 0; i < keys.length; i++){
            TrieNode aux2 = aux.getNodePt(keys[i]);
            if(aux2 == null)
                return false;
            aux = aux2;
        }
        return aux.containNodePt('*');
    }
    
    /**
     *
     * @param preffix
     * @return
     */
    public int getWordCountFromPreffix(String preffix){
        TrieNode aux= this.root;
        char[] keys = new char[preffix.length()];
        preffix.getChars(0, preffix.length(), keys, 0);
        for(int i = 0; i < keys.length - 1; i++){
            TrieNode aux2 = aux.getNodePt(keys[i]);
            if(aux2 == null)
                return 0;
            aux = aux2;
        }
        return aux.getNumberOfWords();
    }
    
    /**
     *
     * @param preffix
     * @return
     */
    public List getWordsFromPreffix(String preffix){
        TrieNode aux = this.root;
        char[] keys = new char[preffix.length()];
        preffix.getChars(0, preffix.length(), keys, 0);
        for(int i = 0; i < keys.length; i++){
            TrieNode aux2 = aux.getNodePt(keys[i]);
            if(aux2 == null)
                return null;
            aux = aux2;
        }
        return aux.getWords(preffix);
    }
    
    /**
     *
     */
    public void printTrie(){
        int count;
        ArrayList<String> list;
        for(TrieNode child : this.root.getChildren()){
            if(!child.getChildren().isEmpty()){
                list = child.toPrintWord("");
                for (String list1 : list) {
                    if(list1.contains("*"))
                        count = 2;
                    else
                        count = 0;
                    for(; count < list1.length(); count++){
                        System.out.print("-");
                    }
                    System.out.println(" " + list1);
                }
            }
        }
    }
}
