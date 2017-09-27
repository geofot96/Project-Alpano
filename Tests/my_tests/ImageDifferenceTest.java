package my_tests;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Put description here
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */
public class ImageDifferenceTest {
    public static void main(String[] args){
        int[][] imgIn1 = read("images/niesen-profile_threaded.png");
        int[][] imgIn2 = read("images/niesen-profile_unthreaded.png");
        int[][] imgOut = imgIn1;
        try{
            for (int i = 0; i < imgIn1.length; i++) {
                for (int j = 0; j < imgIn1[0].length; j++) {
                    if (imgIn1[i][j] !=imgIn2[i][j]){
                        System.err.println("vhdj");
                    }
                }
            }}
        catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Different image sizes");
        }

        write("images/niesenDiff.png",imgOut);
        show(imgOut, "difference");
    }



    public static int[][] read(String path) {
        try {
            BufferedImage image = ImageIO.read(new File(path));
            return fromBufferedImage(image);
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Path: " + path);
            System.exit(1);
            return null;
        }
    }

    public static boolean write(String path, int[][] array) {

        // Convert array to Java image
        BufferedImage image = toBufferedImage(array);

        // Get desired file format
        int index = path.lastIndexOf('.');
        if (index < 0)
            return false;
        String extension = path.substring(index + 1);

        // Export image
        try {
            return ImageIO.write(image, extension, new File(path));
        } catch (IOException e) {
            return false;
        }

    }

    public static int[][] fromBufferedImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] array = new int[height][width];
        for (int row = 0; row < height; ++row) {
            for (int col = 0; col < width; ++col) {
                array[row][col] = image.getRGB(col, row) & 0xffffff;
            }
        }
        return array;
    }

    public static BufferedImage toBufferedImage(int[][] array) {
        int width = array[0].length;
        int height = array.length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int row = 0; row < height; ++row) {
            for (int col = 0; col < width; ++col) {
                image.setRGB(col, row, array[row][col] | 0xff000000);
            }
        }
        return image;
    }

    public static void show(int[][] array, String title) {

        // Convert array to Java image
        final BufferedImage image = toBufferedImage(array);

        // Create a panel to render this image
        @SuppressWarnings("serial") JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, Math.max(getWidth(), 100), Math.max(getHeight(), 100), null, null);
            }
        };

        // Create a frame to hold this panel
        final JFrame frame = new JFrame(title);
        frame.add(panel);
        frame.getContentPane().setPreferredSize(new Dimension(Math.max(image.getWidth(), 300), Math.max(image.getHeight(), 300)));
        frame.pack();

        // Register closing event
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.setVisible(false);
                synchronized (frame) {
                    frame.notifyAll();
                }
            }
        });

        // Show this frame
        frame.setVisible(true);

        // Wait for close operation
        try {
            synchronized (frame) {
                while (frame.isVisible())
                    frame.wait();
            }
        } catch (InterruptedException e) {
            // Empty on purpose
        }
        frame.dispose();
    }
}
