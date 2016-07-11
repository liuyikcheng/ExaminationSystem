/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qrgen;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author Krissy
 */
class Screen extends JPanel {
    
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String myWeb = "";
        int width = 300;
        int height = 300;
         
        BufferedImage bufferedImage = null;
    
    public String localIp() throws Exception {
      InetAddress addr = InetAddress.getLocalHost();
      System.out.println("Local HostAddress:"+addr.getHostAddress());
      String hostname = addr.getHostName();
      System.out.println("Local host name: "+hostname);
      ServerSocket s = new ServerSocket(0);
      System.out.println("listening on port: " + s.getLocalPort());
      return addr.getHostAddress()+":"+s.getLocalPort();
   }
         
    public Screen(){
        repaint();
            try {
                this.myWeb = "$CHIEF:"+localIp()+":$";
            } catch (Exception ex) {
                Logger.getLogger(Screen.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public void paint (Graphics graphics){
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.AZTEC_LAYERS, 10);
        hints.put("Version", "10");
            try {

                BitMatrix byteMatrix = qrCodeWriter.encode(myWeb, BarcodeFormat.QR_CODE, width, height,hints);

                for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            } catch (WriterException ex) {
                Logger.getLogger(Screen.class.getName()).log(Level.SEVERE, null, ex);
            }

//        g.drawRect(100,100,50,50);
//        g.fillRect(1100,100,50,50);
    }
}
