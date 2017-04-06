/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webimagecrawler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.border.TitledBorder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



/**
 *
 * @author anony
 */
public class WebImageCrawler extends javax.swing.JFrame {

    public String dir = "";
    public String text = "";
    
    public void downloadImg(String Url,String imgName, String imgType) throws MalformedURLException, IOException, InterruptedException {
        URL url = new URL(Url);
        InputStream in = new BufferedInputStream(url.openStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n = 0;
        while (-1!=(n=in.read(buf)))
        {
           out.write(buf, 0, n);       
        }
        out.close();
        in.close();
        byte[] response = out.toByteArray();
        FileOutputStream fos = new FileOutputStream( dir + "//" + imgName + "." + imgType);
        fos.write(response);
        fos.close();
    }
    
    private boolean isMatch(String s, String pattern) {
        try {
            Pattern patt = Pattern.compile(pattern);
            Matcher matcher = patt.matcher(s);
            return matcher.matches();
        } catch (RuntimeException e) {
            return false;
        }       
    }
    public void GetLink(String url) throws IOException, InterruptedException {  
        Document doc = Jsoup.connect(url).timeout(0).get();
        Elements media = doc.select("img[src]");
                  
        JFrame f = new JFrame("Download Status");
        JProgressBar progressBar = new JProgressBar();
        f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
        Container content = f.getContentPane();
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setBackground(Color.blue);
        TitledBorder border = BorderFactory.createTitledBorder("Downloading...");
        progressBar.setBorder(border);
        content.add(progressBar, BorderLayout.NORTH);
        f.setSize(400, 100);
        f.setLocationRelativeTo(null); 
        f.setVisible(true);
        
        if (url.contains(jComboBox1.getSelectedItem().toString()) == true || url.contains("http://hentailxersreading10.blogspot.com/")) {
            switch (jComboBox1.getSelectedIndex()) {
                case 0: {
                    int size = media.size();
                    System.out.println(size);
                    for (Element src : media) {   
                        if (src.attr("abs:src").contains("http://cdn.truyentranh.net/") != true) {
                            if (src.attr("abs:src").contains(".jpg") || src.attr("abs:src").contains(".png")) {
                                int percent = (int) Math.ceil((double) 100 / size);
                                String Url = src.attr("abs:src");
                                String name = Url.substring(Url.lastIndexOf("/")+1,Url.lastIndexOf("."));
                                String type = Url.substring(Url.lastIndexOf(".")+1);
                                downloadImg(Url,name,type);
                                text += "Save " + name + "." + type + " success!\n";
                                jTextArea1.setText(text);
                                jTextArea1.update(jTextArea1.getGraphics());
                                jTextArea1.setCaretPosition(jTextArea1.getText().length() - 1);
                                progressBar.setValue(progressBar.getValue() + percent);
                                f.update(f.getGraphics());
                            }
                            else size--;
                        }
                        else size--;
                    }
                    progressBar.setValue(100);
                    Thread.sleep(100);
                    f.dispose();
                    JOptionPane.showMessageDialog(null, "Download Complete!");
                    break;
                }
                case 1: {
                    for (Element src : media) {   
                        if (src.attr("abs:src").contains("chapter-error.png") != true && src.attr("abs:src").contains("chapter-fix.png") != true) {
                            if (src.attr("abs:src").contains(".jpg") || src.attr("abs:src").contains(".png")) {
                                String Url = src.attr("abs:src");
                                String name = Url.substring(Url.lastIndexOf("/")+1,Url.lastIndexOf("."));
                                String type = Url.substring(Url.lastIndexOf(".")+1);
                                downloadImg(Url,name,type);
                            }
                        }
                    }
                    break;
                }
                case 2: {
                    for (Element src : media) {   
                            if (src.attr("abs:src").contains(".jpg") || src.attr("abs:src").contains(".png")) {
                                String Url = src.attr("abs:src");
                                String name = Url.substring(Url.lastIndexOf("/")+1,Url.lastIndexOf("."));
                                String type = Url.substring(Url.lastIndexOf(".")+1);
                                downloadImg(Url,name,type);
                            }
                    }
                    break;
                }
                case 3: {
                    int size = media.size();
                    for (Element src : media) {   
                        if (src.attr("abs:src").contains("https://resources.blogblog.com/") != true) {
                            if (src.attr("abs:src").contains(".jpg") || src.attr("abs:src").contains(".png")) {
                                int percent = 100/size;
                                String Url = src.attr("abs:src");
                                String name = Url.substring(Url.lastIndexOf("/")+1,Url.lastIndexOf("."));
                                String type = Url.substring(Url.lastIndexOf(".")+1);
                                downloadImg(Url,name,type);
                                text += "Save " + name + "." + type + " success!\n";
                                jTextArea1.setText(text);
                                jTextArea1.update(jTextArea1.getGraphics());
                                jTextArea1.setCaretPosition(jTextArea1.getText().length() - 1);
                                progressBar.setValue(progressBar.getValue() + percent);
                                f.update(f.getGraphics());
                            }
                            else size--;
                        }
                        else size--;
                    }
                    progressBar.setValue(100);
                    Thread.sleep(100);
                    f.dispose();
                    JOptionPane.showMessageDialog(null, "Download Complete!");
                    break;
                }
                default: {
                    JOptionPane.showMessageDialog(null, "Host and URL is mismatch. Please choose Host again!");
                    break;
                }
            }    
        }
    }
    
    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }
    
    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
    
    /**
     * Creates new form WebImageCrawler
     */
    public WebImageCrawler() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jLabel1 = new javax.swing.JLabel();
        url = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        downloadButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Web Image Crawler");

        url.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                urlCaretUpdate(evt);
            }
        });

        jLabel2.setText("Link");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        downloadButton.setText("Download");
        downloadButton.setEnabled(false);
        downloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadButtonActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel3.setText("Made by Nhữ Đình Toàn (https://github.com/toannhu)");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "http://truyentranh.net/", "http://blogtruyen.com/", "http://manga24h.me/", "http://hentailxers.site/" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel4.setText("Host");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jComboBox1, 0, 460, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(url)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(downloadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 223, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(187, 187, 187)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(url, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(downloadButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel3)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void urlCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_urlCaretUpdate
        // TODO add your handling code here:
        if (url.getText().length() >= 1) {
            downloadButton.setEnabled(true);
        } else {
            downloadButton.setEnabled(false);
        }
    }//GEN-LAST:event_urlCaretUpdate

    private void downloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadButtonActionPerformed
        String regex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";        
        if(isMatch(url.getText(),regex) == true) {
            try {
                jFileChooser1.setCurrentDirectory(new File(System.getProperty("user.dir")));
                jFileChooser1.setDialogTitle("choosertitle");
                jFileChooser1.setFileSelectionMode(jFileChooser1.DIRECTORIES_ONLY);
                jFileChooser1.setAcceptAllFileFilterUsed(false);
                jFileChooser1.showSaveDialog(null);
                dir = jFileChooser1.getSelectedFile().getAbsolutePath();
                text += dir +"\n";
                jTextArea1.setText(dir);
                GetLink(url.getText());
            } catch (IOException ex) {
                Logger.getLogger(WebImageCrawler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(WebImageCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else JOptionPane.showMessageDialog(null, "Wrong URL! Please enter again!");
    }//GEN-LAST:event_downloadButtonActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(WebImageCrawler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WebImageCrawler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WebImageCrawler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WebImageCrawler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WebImageCrawler().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton downloadButton;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField url;
    // End of variables declaration//GEN-END:variables
}
