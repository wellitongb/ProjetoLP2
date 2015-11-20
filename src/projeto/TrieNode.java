package projeto;

import java.util.ArrayList;

/**
 *
 * @author wellitongb
 */
public class TrieNode {
    private final char character;
    private TrieNode patern;
    private final ArrayList<TrieNode> children;
    
    /**
     *
     * @param character
     * @param patern
     */
    public TrieNode(char character, TrieNode patern) {
        this.character = character;
        this.patern = patern;
        children = new ArrayList<>();
    }

    /**
     *
     * @return
     */
    public char getCharacter() {
        return character;
    }

    /**
     *
     * @return
     */
    public ArrayList<TrieNode> getChildren() {
        return children;
    }

    /**
     *
     * @return
     */
    public TrieNode getPatern() {
        return patern;
    }
     
    /**
     *
     * @param character
     * @return
     */
    public TrieNode getNodePt(char character){
        TrieNode aux = null;
        for (TrieNode pont1 : children) {
            if(pont1.getCharacter() == character){
               aux = pont1;
               break;
            }
        }
        return aux;
    }
    
    /**
     *
     * @param character
     * @return 
     */
    public boolean addNodePt(char character){
        int index = 0;
        for (TrieNode child : this.children) {
            if(child.getCharacter() == character){
               return false;
            }
        }
        TrieNode node = new TrieNode(character, this);
        for (TrieNode child : this.children) {
            if(character < child.getCharacter()){
                index = this.children.indexOf(child);
                this.children.add(index, node);
                return true;
            }
        }
        this.children.add(node);
        return true;
    }
    
    /**
     *
     * @param character
     * @return
     */
    public boolean removeNodePt(char character){
        for (TrieNode pont1 : children){
            if(pont1.getCharacter() == character){
               return children.remove(pont1);
            }
        }
        return false;
    }
    
    /**
     *
     * @param character
     * @return
     */
    public boolean containNodePt(char character){
        for (TrieNode pont1 : children){
            if(pont1.getCharacter() == character){
               return children.contains(pont1);
            }
        }
        return false;
    }
    
    /**
     *
     * @return
     */
    public int getNumberOfWords(){
        int count = 0;
        for (TrieNode child : this.children) {
            if(child.getCharacter() == '*')
                return 1;
            else
                count = count + child.getNumberOfWords();
        }
        return count;
    }
    
    /**
     *
     * @param preffix
     * @return
     */
    public ArrayList getWords(String preffix){
        Character aux = this.character;
        ArrayList<String> list = new ArrayList<>();
        for (TrieNode child : this.children) {
            if(child.getCharacter() == '*'){
               // String aux2 = preffix.concat(aux.toString());
                list.add(preffix);
            }
            else{
                Character childCharacter = child.getCharacter();
                String aux2 = preffix.concat(childCharacter.toString());
                ArrayList<String> aux3 = (child.getWords(aux2));
                if(aux3 != null)
                    list.addAll(aux3);
            }
        }
        if(list.isEmpty())
            return null;
        else
            return list;
    }
    
    public ArrayList<String> toPrintWord(String preffix){
        Character aux = this.character;
        ArrayList<String> list = new ArrayList<>();
        String aux2;
        ArrayList<String> aux3;        
        for (TrieNode child : this.children) {
            if(child.character == '*'){
                aux2 = preffix.concat(aux.toString() + " *");
                list.add(aux2);
                aux3 = null;
            }
            else{
                aux2 = preffix.concat(aux.toString());
                if(!list.contains(aux2 + " *") && !list.contains(aux2))
                    list.add(aux2);
                aux3 = (child.toPrintWord(aux2));
            }
            if(aux3 != null)
                list.addAll(aux3);
        }
        if(list.isEmpty())
            return null;
        else
            return list;
    }
}
