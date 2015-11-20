/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto;

//import br.ufrn.imd.lp2.imagesegmentation.ImageInformation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author wellitongb
 */
public class Category {
    private int imagesQuant;
    private final String pathDirectory, type;
    private final ArrayList<String> imagesName,pathsImages;
    /**
     *
     * @param pathDirectory
     * @param type
     * @param hasCreated
     */
    public Category(String pathDirectory, String type, boolean hasCreated) {
        if (pathDirectory == null) {
        throw new NullPointerException("Argument pathDirectory is null");
        }
        if (type == null) {
        throw new NullPointerException("Argument type is null");
        }
        
        this.imagesQuant = 0;
        this.pathDirectory = pathDirectory;
        this.type = type;
        this.pathsImages = new ArrayList<>();
        this.imagesName = new ArrayList<>();
        if(hasCreated)
            this.openFile();
    }
    
    public ArrayList<String> getPathsData(){
        ArrayList<String> aux = new ArrayList<>();
        for(String imagesNameElement : this.imagesName) {
          aux.add(this.pathDirectory + "Data\\" + imagesNameElement + ".projectlpII");
        }
        return aux;
    }

    /**
     *
     * @return
     */
    public int getImagesQuant() {
        return this.imagesQuant;
    }

    /**
     *
     * @return
     */
    public String getPathDirectory() {
        return this.pathDirectory;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return this.type;
    }
        
    /**
     *
     * @return
     */
    public ArrayList<String> getImagesName() {
        return this.imagesName;
    }
    
    /**
     *
     * @param pathImage
     * @param imageName
     * @param haveToSave
     */
    public void add(String pathImage,String imageName, boolean haveToSave){
      if(addPathDirectoryImageOriginal(pathImage) && addImageName(imageName))
            this.imagesQuant++;
        if(haveToSave)
            saveFile();
    }
    
    /**
     *
     * @param pathImage
     */
    private boolean addPathDirectoryImageOriginal(String pathImage){
        if(!this.pathsImages.contains(pathImage)){
            this.pathsImages.add(pathImage);
            return true;
        }
        return false;
    }
    
    /**
     *
     * @param imageName
     */
    private boolean addImageName(String imageName){
        if(!this.imagesName.contains(imageName)){
            this.imagesName.add(imageName);
            return true;
        }
        return false;
    }
    
    /**
     *
     * @param imageName
     * @param haveToSave
     * @return
     */
    public boolean remove(String imageName, boolean haveToSave){
        if(this.imagesName.contains(imageName)){
            int i = this.imagesName.indexOf(imageName);
            String pathImage = this.pathsImages.get(i);
            String name = this.imagesName.get(i);
            if((i >= 0) && this.pathsImages.remove(pathImage) && this.imagesName.remove(name)){
                this.imagesQuant--;
                if(haveToSave){
                    saveFile();
                }
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }
    
    /**
     *
     */
    private void openFile(){
        try{
            File directory = new File(this.pathDirectory);
            directory.mkdirs();
            File arquivo = new File(pathDirectory.concat("Categories\\" + this.type + ".category"));
            if(arquivo.exists()){
                ObjectInputStream read;
                read = new ObjectInputStream(new FileInputStream(arquivo));
                this.imagesQuant = read.readInt();
                for(int i = 0; i < this.imagesQuant; i++){
                    this.pathsImages.add(read.readUTF());
                }
                for(int i = 0; i < this.imagesQuant; i++){
                    this.imagesName.add(read.readUTF());
                }
                read.close();
                //JOptionPane.showMessageDialog(null, "Leitura dos Dados feita com sucesso!");
            }
            //else
                //JOptionPane.showMessageDialog(null, "file not exist");
        }
        catch (IOException ex){
            JOptionPane.showMessageDialog(null, "Leitura dos Dados de Category resultou em falha, repita a operação!!!");
            //adicionar comandos aqui
            Logger.getLogger(InterfaceGrafica.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    /**
     *
     */
    public void saveFile(){
        new File(this.pathDirectory.concat("\\Categories\\")).mkdirs();
        File arquivo = new File(pathDirectory.concat("\\Categories\\" + this.type + ".category"));
        try {
            ObjectOutputStream write;
            write = new ObjectOutputStream(new FileOutputStream(arquivo));
            write.writeInt(this.imagesQuant);
            for(int i = 0; i < this.imagesQuant; i++){
                String pathDirectoryImage = this.imagesName.get(i);
                write.writeUTF(pathDirectoryImage);
            }
            for(int i = 0; i < this.imagesQuant; i++){
                String name = this.imagesName.get(i);
                write.writeUTF(name);
            }
            write.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Falha ao Salvar Dado Category!");
            Logger.getLogger(InterfaceGrafica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
