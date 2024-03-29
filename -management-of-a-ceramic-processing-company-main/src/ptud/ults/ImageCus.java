/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptud.ults;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

/**
 *
 * @author TomTom
 */
public class ImageCus {
    public static ImageIcon getScaledImageIcon(String imagePath, int width, int height) {
        BufferedImage img = null;
        try {
            InputStream is = ImageCus.class.getResourceAsStream(imagePath);
            if (is == null) {
                throw new IllegalArgumentException("Cannot find resource: " + imagePath);
            }
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
            return null;  // Handle the exception appropriately
        }

        Image dimg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(dimg);
    }

    public static ImageIcon getScaledImageIconByte(byte[] imageData, int width, int height) {
        BufferedImage img = null;
        try {
            InputStream is = new ByteArrayInputStream(imageData);
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
            return null;  // Handle the exception appropriately
        }

        Image dimg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(dimg);
    }
}
