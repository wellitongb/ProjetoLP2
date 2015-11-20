/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto;

import br.ufrn.imd.lp2.imagesegmentation.ImageInformation;
import br.ufrn.imd.lp2.imagesegmentation.ImageSegmentation;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ListModel;
import net.coobird.thumbnailator.Thumbnails;

/**
 *
 * @author wellitongb
 */
public class Function {
    public static final int JLABEL = 0;
    public static final int JBUTTON = 1;
    public static final int JTOGGLEBUTTON = 2;
    public static final int JCHECKBOX = 3;
    public static final int JRADIOBUTTON = 4;
    //public static final int BUTTONGROUP = 5;
    public static final int JCOMBOBOX = 6;
    public static final int JLIST = 7;
    public static final int JTEXTFIELD = 8;
    public static final int JTEXTAREA = 9;
    public static final int JSCROLLBAR = 10;
    public static final int JSLIDER = 11;
    public static final int JPROGRESSBAR = 12;
    public static final int JFORMATTEDTEXTFIELD = 13;
    public static final int JPASSWORDPASSFIELD = 14;
    public static final int JSPINNER = 15;
    public static final int JSEPARATOR = 16;
    public static final int JTEXTPANE = 17;
    public static final int JEDITORPANE = 18;
    public static final int JTREE = 19;
    public static final int JTABLE = 20;
    
    
    /**
     *
     * @param paternFrame
     * @param childFrame
     * @param paternPanel
     * @param childPanel
     * @param label
     * @param objects
     * @param componentA
     * @param componentB
     * @param componentC
     * @throws IOException
     */
    public static final void repaintPanelWithJlabels(JFrame[] paternFrame,JFrame[] childFrame,JPanel[] paternPanel, JPanel[] childPanel,JLabel[] label,ArrayList<Data>[] objects, JRadioButton[] components) throws IOException{
        int cont = 1;
        int contlayout = 1;
        MyJLabel auxMyJLabel =  null;
        childPanel[0].removeAll();
        childPanel[0].repaint();
        childPanel[0].revalidate();
        for(Data elements : objects[0]) {
            if(cont%3 == 0){
                contlayout++;
            }                       
            BufferedImage imageTumbnails = Thumbnails.of(elements.getPathDirectory() + "Images\\" + elements.getNameFile() + ".jpg").height(150).outputFormat("jpg").asBufferedImage();
            
            if(components[0].isSelected()){
                auxMyJLabel = new MyJLabel(new ImageIcon(elements.getSeg().getOriginalImage()), new ImageIcon(imageTumbnails), label[0], paternFrame[0], childFrame[0]);
            }
            else{
                if(components[1].isSelected()){
                    auxMyJLabel = new MyJLabel(new ImageIcon(elements.getSeg().getRegionMarkedImage()), new ImageIcon(imageTumbnails), label[0], paternFrame[0], childFrame[0]);
                }
                else{
                    auxMyJLabel = new MyJLabel(new ImageIcon(elements.getImageAcinzentada()), new ImageIcon(imageTumbnails), label[0], paternFrame[0], childFrame[0]);
                }
            }
            if(auxMyJLabel.getIcon().getIconWidth() > 100){  
                auxMyJLabel.setIcon(new ImageIcon(Thumbnails.of(imageTumbnails).width(100).outputFormat("jpg").asBufferedImage()));
            }
            childPanel[0].add(auxMyJLabel);
            cont++;
        }
        GridLayout auxGridLayout = (GridLayout) childPanel[0].getLayout();
        auxGridLayout.setRows(contlayout);
        auxGridLayout.setVgap(10);
        childPanel[0].setLayout(auxGridLayout);
        childPanel[0].repaint();
        childPanel[0].revalidate();
        changePanelCardLayout(paternPanel, childPanel);
    }

    /**
     *
     * @param painel
     * @param comp
     */
    public static final void changePanelCardLayout(JPanel painel[], Component[] comp){
        painel[0].removeAll();
        painel[0].repaint();
        painel[0].revalidate();
        painel[0].add(comp[0]);
        painel[0].repaint();
        painel[0].revalidate();
    }
    
    /**
     * Método responsável por tornar as regiões da imagem seguimentada em diferentes tons de cinza.
     *@param imagePixels Matriz de pixels da imagem Original
     *@param regionMap Matriz de regiões ao qual cada pixel pertece
     * @param regionCont Número total de regiões segmentadas
     * @return 
     */
    public static final int[] regionColor(int[] imagePixels, int[] regionMap, int regionCont){
        int imageGrayedPixels [] = new int [imagePixels.length];
        int [] grayRegion = new int[regionCont];
        for(int i = 0; i < regionCont; i++){
            grayRegion[i] = 0;
        }
        grayRegion[0] = 0xFF000000;
        int media = (int) (255 / regionCont);
        int aux = media;
        for(int i = 1; i < regionCont; i++){
            //atribui o valor média (de cinza) a cada região
            int gray = (0xFF000000) | ((media << 16) & 0x00FF0000) | ((media << 8) & 0x0000FF00) | (media & 0x000000FF);
            grayRegion[i] =  gray;
            media = media + aux;
        }
        
        // seta todos os pixels de cada região para sua escala cinza
        for(int i = 0; i < imageGrayedPixels.length; i++){
          imageGrayedPixels[i] = grayRegion[regionMap[i]];
        }
        return imageGrayedPixels;
    }
    
    /**
     *
     * @param model
     * @param type
     * @return
     */
    public static final ListModel getAllElements(ListModel model, boolean type) {
        ArrayList<String> elements = new ArrayList<>();
        DefaultListModel aux1 = new DefaultListModel();
        DefaultComboBoxModel aux2 = new DefaultComboBoxModel();
        
        for(int i = 0; i < model.getSize(); i++){
            if(!elements.contains((String) model.getElementAt(i)))
                elements.add((String) model.getElementAt(i));
        }
        elements.sort(String::compareToIgnoreCase);
        
        for(String element : elements) {
            aux1.addElement(element);
            aux2.addElement(element);
        }
        if(type)
            return aux1;
        else
            return aux2;
    }
    
    /**
     * Método utilizado para escurecer regiões não selecionadas da imagem segmentada ou da imagem do mapa de rótulos.
     * @param imageMarked parâmetro responsável por fornecer o vetor de pixels da imagem segmentada ou da imagem do mapa de rótulos.
     * @param segmentationImage
     * @param regionSelected
     * @param label
     */
    public static final void modificationImage(int[] imageMarked, ImageInformation segmentationImage, ArrayList<Integer> regionSelected, JLabel label){
        int[] imageMap = segmentationImage.getSegmentedImageMap();        
        int[] imageModificated = new int[imageMarked.length];
        for(int i = 0; i < imageMarked.length; i++){
            if(-1 == regionSelected.indexOf(imageMap[i])){
                Color cor = new Color(imageMarked[i]);
                int red = (cor.getRed() / 2);
                int blue = (cor.getBlue() / 2);
                int gree = (cor.getGreen() / 2);
                Color cor2 = new Color(red, gree, blue);
                imageModificated[i] = cor2.getRGB();
            }
            else{
                imageModificated[i] = imageMarked[i]; 
            }
        }
        BufferedImage image = asBufferedImage(imageModificated, segmentationImage.getWidth(), segmentationImage.getHeight());
        label.setIcon(new ImageIcon(image));
    }
    
    /**
     *
     * @param evt
     * @param paternFrame
     * @param childFrame
     * @param paternPanel
     * @param childPanel
     * @param label
     * @param objects
     * @param components
     * @throws IOException
     */
    public static final void jRadioButtonActionPerformed(java.awt.event.ActionEvent evt,
    JFrame[] paternFrame,JFrame[] childFrame,JPanel[] paternPanel, JPanel[] childPanel,JLabel[] label,ArrayList<Data>[] objects, JRadioButton[] components) throws IOException{
        if(childPanel[0].getComponents().length != 0){
            repaintPanelWithJlabels(paternFrame, childFrame, paternPanel, childPanel, label, objects, components);
        }
    }
    
    /**
     *
     * @param pathDirectory
     * @param objectsData
     * @param jTextField
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static final void initObjectsData(String[] pathDirectory, ArrayList<Data>[] objectsData, JTextField[] jTextField) throws IOException, ClassNotFoundException{
        Category file = new Category(pathDirectory[0], jTextField[0].getText(), true);
        ArrayList<String> pathFilesData = file.getPathsData();
        ArrayList<ObjectInputStream> filesData = new ArrayList<>();
        for(int i = 0; i < pathFilesData.size(); i++){
            filesData.add(new ObjectInputStream(new FileInputStream(new File(pathFilesData.get(i))))); 
        }
        for(ObjectInputStream elements : filesData) {
            String pathDirectoryTemp = elements.readUTF();
            String nameFileTemp = elements.readUTF();
            Integer colorRadiusTemp = elements.readInt();
            Integer minSizeTemp = elements.readInt();
            Double blurLevelTemp = elements.readDouble();
            ImageInformation segTemp = (ImageInformation) elements.readObject();
            DefaultListModel modelTemp = (DefaultListModel) elements.readObject();
            HashMap<String, ArrayList<Integer>> mapTemp = (HashMap<String, ArrayList<Integer>>) elements.readObject();
            objectsData[0].add(new Data(pathDirectoryTemp, nameFileTemp,colorRadiusTemp,minSizeTemp,blurLevelTemp,segTemp, modelTemp, mapTemp));
        }
    }
    
    /**
     * Atribui um objeto, com valores internos, da classe ImageInformation à variavel privada "seg"
     * Cria ou atribui um objeto, com valores internos, da classe Data à variavel privada "arquivo";
     * @param file
     * @param model
     * @param listImagesTaged
     * @param regionSelected
     * @param pathDirectory
     * @param nameImage
     * @param blurLevel
     * @param colorRadius
     * @param minSize
     * @return 
     */
    public static Object[] changeImageSegmentation(Data[] file,DefaultListModel[] model,HashMap<String, ArrayList<Integer>>[] listImagesTaged, ArrayList<Integer>[] regionSelected, String[] pathDirectory, String[] nameImage, Double[] blurLevel, Integer[] colorRadius, Integer[] minSize){
       ImageInformation segmentationImage = ImageSegmentation.performSegmentation(pathDirectory[0] + "Images\\"+ nameImage[0] + ".jpg", blurLevel[0], colorRadius[0], minSize[0]);
       if(file[0] == null){
           file[0] = new Data(pathDirectory[0], nameImage[0], colorRadius[0], minSize[0], blurLevel[0], segmentationImage, model[0], listImagesTaged[0]);
       }
       else{
           file[0].set(nameImage[0], blurLevel[0], colorRadius[0], minSize[0], segmentationImage, model[0], listImagesTaged[0]);
       }
        model[0] = new DefaultListModel();
        listImagesTaged[0] = new HashMap<>();
        regionSelected[0] = new ArrayList<>();
       return new Object[]{segmentationImage, model[0],listImagesTaged[0], regionSelected[0], file[0]};
    }
    
    /**
     *
     * @param ImagePixels
     * @param width
     * @param height
     * @return
     */
    public static final BufferedImage asBufferedImage(int[] ImagePixels, int width, int height){
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, width, height, ImagePixels, 0, width);
        return image;
    }
}
