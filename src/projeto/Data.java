package projeto;

import br.ufrn.imd.lp2.imagesegmentation.ImageInformation;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * classe utilizada para tratar manipulações de arquivos "*.projetolpII"
 * @author wellitongb
 */
public final class Data implements Serializable{
    private final JFileChooser windows = new JFileChooser();
    private String pathDirectory,nameFile;
    private Double blurLevel;
    private Integer colorRadius, minSize;
    private BufferedImage imageAcinzentada;
    private ImageInformation seg;
    private DefaultListModel model;
    private HashMap<String, ArrayList<Integer>> listImagesTaged;
    public ArrayList<Category> aux;

    /**
     * O método-construtor da classe Dado, ao qual ele seta os valores das variaveis privadas 
     * @param pathDirectory Diretório principal onde a imagem está
     * @param nameFile Nome da imagem original
     * @param blurLevel Parâmetro responsável pelo valor da variável blurLevel 
     * @param colorRadius Parâmetro responsável pelo valor da variável colorRadius
     * @param minSize Parâmetro responsável pelo valor da variável minSize
     * @param seg Parâmetro responsável pelo valor da variável seg
     * @param model Parâmetro responsável pelo valor da variável model
     * @param listImagesTaged Parâmetro responsável pelo valor da variável listImagesTaged
     */
    public Data(String pathDirectory,String nameFile, Integer colorRadius, Integer minSize, Double blurLevel, ImageInformation seg,DefaultListModel model,HashMap<String, ArrayList<Integer>> listImagesTaged) {
        this.pathDirectory = pathDirectory;
        this.nameFile = nameFile;
        this.blurLevel = blurLevel;
        this.colorRadius = colorRadius;
        this.minSize = minSize;
        this.seg = seg;
        this.model = model;
        this.listImagesTaged = listImagesTaged;
        this.aux = new ArrayList<>();
        createdImageGrayed();
    }
    
    /**
     * O método-construtor da classe Dado simplificado. Responsável unicamente pela abertura de arquivo pela primeira vez
     * @throws IOException Exceção usada pelo método abrir. Essa exceção captura erros de leitura escrita
     * @throws ClassNotFoundException Exceção usada pelo método abrir. Essa exceção caputraa erros de ausência de classes.
     */
    public Data() throws IOException, ClassNotFoundException{
        this.aux = new ArrayList<>();
        this.Abrir();
    }
    
    /**
     *
     * @return
     */
    public String getNameFile(){
        return this.nameFile;
    }
    
    /**
     * Método get da variável model 
     * @return um objeto da classe DefaultListModel
     */
    public DefaultListModel getModel() {
        return model;
    }

    /**
     * Método get da variável listImagesTaged
     * @return um objeto da classe Hashmap, que recebe como modelo de chave uma String e como modelo um ArrayList de inteiros
     */
    public HashMap<String, ArrayList<Integer>> getListImagesTaged() {
        return listImagesTaged;
    }
    
    /**
     * Método get da variável pathDirectory
     * @return um objeto da classe String
     */
    public String getPathDirectory() {
        return pathDirectory;
    }

    /**
     * Método get da variável blurLevel 
     * @return um objeto da classe Double
     */
    public Double getBlurLevel() {
        return blurLevel;
    }

    /**
     * Método get da variável colorRadius
     * @return um objeto da classe Interger 
     */
    public Integer getColorRadius() {
        return colorRadius;
    }

    /**
     * Método get da variável minSize
     * @return um objeto da classe Interger
     */
    public Integer getMinSize() {
        return minSize;
    }

    /**
     * Método get da variável imageAcinzentada
     * @return um objeto da classe BufferedImage
     */
    public BufferedImage getImageAcinzentada() {
        return imageAcinzentada;
    }

    /**
     * Método get da variável seg
     * @return um objeto da classe ImageInformation 
     */
    public ImageInformation getSeg() {
        return seg;
    }

    /**
     * Método responsável por atribuir os valores dos parâmetros as suas respectivas variáveis
     * @param nameFile Parâmetro atribuido a variável NameFile
     * @param blurLevel Parâmetro atribuido a variável blurLevel
     * @param colorRadius Parâmetro atribuido a variável colorRadius
     * @param minSize Parâmetro atribuido a variável minSize
     * @param seg Parâmetro atribuido a variável seg
     * @param model Parâmetro atribuido a variável model
     * @param listImagesTaged Parâmetro atribuido a variável listImagesTaged
     */
    public void set(String nameFile, Double blurLevel, Integer colorRadius, Integer minSize, ImageInformation seg,DefaultListModel model,HashMap<String, ArrayList<Integer>> listImagesTaged) {
        this.nameFile = nameFile;
        this.blurLevel = blurLevel;
        this.colorRadius = colorRadius;
        this.minSize = minSize;
        this.seg = seg;
        this.model = model;
        this.aux.clear();
        for(int i = 0; i < this.model.getSize(); i++){
            String aux2 = (String) this.model.getElementAt(i);
            this.aux.add(i , new Category(this.pathDirectory,aux2,true));
            this.aux.get(i).remove(this.nameFile, false);
        }
        this.listImagesTaged = listImagesTaged;
        createdImageGrayed();
    }

    /**
     * Método responsável pela criação da imagem acinzentada  
     */
    private void createdImageGrayed(){
        this.imageAcinzentada = seg.getOriginalImage();
        int gray[] = corRegiao(seg.getOriginalPixels(), seg.getSegmentedImageMap(), seg.getTotalRegions());
        this.imageAcinzentada.setRGB(0, 0, this.imageAcinzentada.getWidth(), this.imageAcinzentada.getHeight(), gray, 0, this.imageAcinzentada.getWidth());
    }

    /**
     * Método responsável por tornar as regiões da imagem seguimentada em diferentes tons de cinza.
     *@param imagem Matriz de pixels da imagem Original
     *@param mapaRegiões Matriz de regiões ao qual cada pixel pertece
     * @param nRegiões Número total de regiões segmentadas
     */
    private int[] corRegiao(int[] imagem, int[] mapaRegiões, int nRegiões){
        int imagemGray [] = new int [imagem.length];
        int [] grayRegion = new int[nRegiões];
        for(int i = 0; i < nRegiões; i++){
            grayRegion[i] = 0;
        }
        grayRegion[0] = 0xFF000000;
        int media = (int) (255 / nRegiões);
        int aux = media;
        for(int i = 1; i < nRegiões; i++){
            //atribui o valor média (de cinza) a cada região
            int gray = (0xFF000000) | ((media << 16) & 0x00FF0000) | ((media << 8) & 0x0000FF00) | (media & 0x000000FF);
            grayRegion[i] =  gray;
            media = media + aux;
        }
        
        // seta todos os pixels de cada região para sua escala cinza
        for(int i = 0; i < imagemGray.length; i++){
          imagemGray[i] = grayRegion[mapaRegiões[i]];
        }
        return imagemGray;
    }

    /**
     * Método responsável por acessar o arquivo no disco, que usuario selecionar. Após isso, ele lê o arquivo e atribui os valores lidos no arquivo a cada variável respectiva
     * @throws IOException Cláusula exceção para o processo leitura e escrita
     * @throws ClassNotFoundException Cláusula de exceção para o acesso de um método encontrado em uma variável global
     */
    public void Abrir() throws IOException, ClassNotFoundException  {
        windows.setDialogTitle("Abrindo Arquivo!");
        windows.setFileFilter(new MyCustomFilterFilesProjectlpII());
        File directory = new File(".");
        File directoryData = new File(directory.getAbsolutePath().concat("\\Data\\"));
        directoryData.mkdir();
        windows.setCurrentDirectory(directoryData);
        int returnVal = windows.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File arquivo = windows.getSelectedFile();
            ObjectInputStream read = new ObjectInputStream(new FileInputStream(arquivo));
            try {
               this.pathDirectory = read.readUTF();
               this.nameFile = read.readUTF();
               this.colorRadius = read.readInt();
               this.minSize = read.readInt();
               this.blurLevel = read.readDouble();
               this.seg = (ImageInformation) read.readObject();
               this.model = (DefaultListModel) read.readObject();
               this.listImagesTaged = (HashMap<String, ArrayList<Integer>>) read.readObject();
               createdImageGrayed();
               this.aux.clear();
                for(int i = 0; i < model.getSize(); i++){
                    String aux2 = (String) this.model.getElementAt(i);
                    this.aux.add(i , new Category(this.pathDirectory,aux2,true));
                    this.aux.get(i).remove(this.nameFile, false);
                }
               read.close();
               //JOptionPane.showMessageDialog(null, "Data Aberto com Sucesso!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Falha ao Abrir Dado!");
                Logger.getLogger(InterfaceGrafica.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Método responsável por salvar um arquivo após as modificações atribuidas pelo usuário 
     * @throws IOException Cláusula exceção para o processo leitura e escrita
     */
    public void Salve() throws IOException{
        new File(this.pathDirectory.concat("Data\\")).mkdirs();
        File arquivo = new File(pathDirectory.concat("Data\\" + this.nameFile + ".projectlpII"));
        for(int i = 0; i < this.model.size(); i++){
            this.aux.get(i).add(this.pathDirectory + "Images\\" + this.nameFile + ".jpg", this.nameFile, true);
        }
        ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream(arquivo));
        try {
           write.writeUTF(this.pathDirectory);
           write.writeUTF(this.nameFile);
           write.writeInt(this.colorRadius);
           write.writeInt(this.minSize);
           write.writeDouble(this.blurLevel);
           write.writeObject(this.seg);
           write.writeObject(this.model);
           write.writeObject(this.listImagesTaged);
           write.close();
           JOptionPane.showMessageDialog(null, "Dados Salvo com Sucesso!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Falha ao Salvar Dado!");
            Logger.getLogger(InterfaceGrafica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
