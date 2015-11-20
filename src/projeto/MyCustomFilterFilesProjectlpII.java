/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto;

import java.io.File;

class MyCustomFilterFilesProjectlpII extends javax.swing.filechooser.FileFilter {
        
        @Override
        public boolean accept(File file) {
            // Allow only directories, or files with ".projectlpII" extension
            return file.isDirectory() || file.getAbsolutePath().endsWith(".projectlpII");
        }
        
        @Override
        public String getDescription() {
            // This description will be displayed in the dialog,
            // hard-coded = ugly, should be done via I18N
            return "Dados projectlpII (*.projectlpII)";
        }
    }
