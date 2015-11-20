package projeto;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.coobird.thumbnailator.Thumbnails;

/**
 *
 * @author wellitongb
 */
public class ImageEdit {
    private String pathDirectory, nameImageOriginal,pathImageOriginal;

    /**
     *
     * @param pathDirectory
     * @param nameImageOriginal
     * @param pathImageOriginal
     */
    public ImageEdit(String pathDirectory, String nameImageOriginal, String pathImageOriginal) {
        if (pathDirectory == null) {
        throw new NullPointerException("Argument pathDirectory is null");
        }
        if (nameImageOriginal == null) {
        throw new NullPointerException("Argument NameImageOriginal is null");
        }
        if (pathImageOriginal == null) {
        throw new NullPointerException("Argument pathImageOriginal is null");
        }
        this.pathDirectory = pathDirectory;
        this.nameImageOriginal = nameImageOriginal;
        this.pathImageOriginal = pathImageOriginal;
    }
    
    /**
     *
     * @param width
     * @param height
     * @param type
     * @return 
     */
    public BufferedImage save(int width,int height, boolean type){
        new File(this.pathDirectory.concat("Images\\")).mkdirs();
        File imageEdit = new File(this.pathDirectory + "Images\\" + this.nameImageOriginal);
        try {
            if(type){
                if(width == -1){
                    Thumbnails.of(this.pathImageOriginal).height(height).outputFormat("jpg").toFile(this.pathDirectory + "Images\\" + this.nameImageOriginal);
                    return Thumbnails.of(this.pathImageOriginal).height(height).outputFormat("jpg").asBufferedImage();
                }
                else{
                    Thumbnails.of(this.pathImageOriginal).width(width).outputFormat("jpg").toFile(this.pathDirectory + "Images\\" + this.nameImageOriginal);
                    return Thumbnails.of(this.pathImageOriginal).width(width).outputFormat("jpg").asBufferedImage();
                }
            }
            else{
                Thumbnails.of(this.pathImageOriginal).size(width, height).outputFormat("jpg").toFile(this.pathDirectory + "Images\\" + this.nameImageOriginal);
                return Thumbnails.of(this.pathImageOriginal).size(width, height).outputFormat("jpg").asBufferedImage();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Escrita da Imagem editada resultou em falha, repita a operação!!!");
            //adicionar comandos aqui
            Logger.getLogger(ImageEdit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     *
     * @param value
     * @return
     */
    public BufferedImage saveHeight(int value){
        return save(-1,value, true);
    }
    
    /**
     *
     * @param value
     * @return
     */
    public BufferedImage saveWidth(int value){
        return save(value,-1, true);
    }
    
    /**
     *
     * @param pathDirectory
     * @param nameImageOriginal
     * @param pathImageOriginal
     */
    public void set(String pathDirectory, String nameImageOriginal, String pathImageOriginal){
        if (pathDirectory == null) {
        throw new NullPointerException("Argument pathDirectory is null");
        }
        if (nameImageOriginal == null) {
        throw new NullPointerException("Argument NameImageOriginal is null");
        }
        if (pathImageOriginal == null) {
        throw new NullPointerException("Argument pathImageOriginal is null");
        }
        this.pathDirectory = pathDirectory;
        this.nameImageOriginal = nameImageOriginal;
        this.pathImageOriginal = pathImageOriginal;
    }
}
