package projeto;

import br.ufrn.imd.lp2.imagesegmentation.ImageInformation;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
public class Project {

    public static void main(String[] args) {
        
            //-------TesteDataClass-------------//
        /*try {
            Dado arquivo_1 = new Dado();
            
            //ImageInformation seg = arquivo_1.getSeg();
            
            /*
            Image aux = seg.getOriginalImage().getScaledInstance(340, 510, 0);
            BufferedImage imagem = new BufferedImage(aux.getWidth(null), aux.getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = imagem.createGraphics();
            g.drawImage(aux, 0, 0, aux.getWidth(null), aux.getHeight(null), null);
            new File("C:\\Users\\wellitongb\\Desktop\\Teste\\Images\\").mkdirs();
            ImageIO.write(imagem, "JPG", new File("C:\\Users\\wellitongb\\Desktop\\Teste\\Images\\teste01.jpg")); 
            */
            //String category = (String) arquivo_1.getModel().firstElement();
            //ArrayList<Integer> FieldListSelect = arquivo_1.getListImagesTaged().get(category);
          /*  File caminho = new File(".");
            JFileChooser aux = new JFileChooser();
            int returnVal = aux.showOpenDialog(null);
            ImageEdit alteração;  
        try {
            if (returnVal == JFileChooser.APPROVE_OPTION){
                alteração = new ImageEdit(caminho.getCanonicalPath(), "model", aux.getSelectedFile().getAbsolutePath());
                Data arquivo_2 = new Data(caminho.getCanonicalPath(), "blusa", false);
                arquivo_2.add(alteração.saveWidth(600), "model", true);
            }
        } catch (IOException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }
          */
            
        //} catch (IOException | ClassNotFoundException ex) {
         //   Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        //}
        
        //-------FinalTesteDataClass-------//
        // Cria uma janela gráfica
        InterfaceGrafica janela = new InterfaceGrafica();
        //Abre a janela e roda toda a aplicação
        janela.run(janela);
        
    }
    
}
