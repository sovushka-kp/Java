package world.ucode;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@MultipartConfig
public class Corrupted extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Part file = req.getPart("file");
            BufferedImage image = ImageIO.read(file.getInputStream());

            if (checkCorrupt(image))
                resp.setStatus(200);
        } catch(Exception exception) {
                resp.setStatus(406);
        }
    }

    private boolean checkCorrupt(BufferedImage img) {
        BufferedImage image = getBlankImage(img.getHeight(), img.getWidth(), img.getType());
        return compareImage(img, image);
    }

    private BufferedImage getBlankImage(int height, int width, int type) {
        return new BufferedImage(width, height, type);
    }

    private boolean compareImage(BufferedImage imgA, BufferedImage imgB) {
        for (int y = 0; y < imgA.getHeight(); y++) {
            for(int x = 0; x < imgA.getWidth(); x++) {
                if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }
}