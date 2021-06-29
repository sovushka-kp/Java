package world.ucode;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@MultipartConfig
public class Pixelate extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part file = req.getPart("file");
        BufferedImage image = ImageIO.read(file.getInputStream());
        String format = req.getHeader("format");
        int range = Integer.parseInt(req.getHeader("range"));

        pixelate(image, range);
        ImageIO.write(image, format, resp.getOutputStream());
    }

    private void pixelate(BufferedImage imageToPixelate, int pixelSize) {
        for (int y = 0; y < imageToPixelate.getHeight(); y += pixelSize) {
            for (int x = 0; x < imageToPixelate.getWidth(); x += pixelSize) {
                BufferedImage croppedImage = getCroppedImage(imageToPixelate, x, y, pixelSize, pixelSize);
                Color dominantColor = getDominantColor(croppedImage);
                for (int yd = y; (yd < y + pixelSize) && (yd < imageToPixelate.getHeight()); yd++) {
                    for (int xd = x; (xd < x + pixelSize) && (xd < imageToPixelate.getWidth()); xd++) {
                        imageToPixelate.setRGB(xd, yd, dominantColor.getRGB());
                    }
                }
            }
        }
    }

    private BufferedImage getCroppedImage(BufferedImage image, int startx, int starty, int width, int height) {
        if (startx < 0) startx = 0;
        if (starty < 0) starty = 0;
        if (startx > image.getWidth()) startx = image.getWidth();
        if (starty > image.getHeight()) starty = image.getHeight();
        if (startx + width > image.getWidth()) width = image.getWidth() - startx;
        if (starty + height > image.getHeight()) height = image.getHeight() - starty;
        return image.getSubimage(startx, starty, width, height);
    }

    private Color getDominantColor(BufferedImage image) {
        Map<Integer, Integer> colorCounter = new HashMap<>(100);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int currentRGB = image.getRGB(x, y);
                int count = colorCounter.getOrDefault(currentRGB, 0);
                colorCounter.put(currentRGB, count + 1);
            }
        }
        return getDominantColor(colorCounter);
    }

    private Color getDominantColor(Map<Integer, Integer> colorCounter) {
        int dominantRGB = colorCounter.entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get()
                .getKey();
        return new Color(dominantRGB);
    }
}

