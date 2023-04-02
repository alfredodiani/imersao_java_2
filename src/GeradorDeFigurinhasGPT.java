import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class GeradorDeFigurinhasGPT {
    
    public void cria(InputStream inputStream,String frase) throws Exception{
        
        //leitura da imagem
        BufferedImage imagemOriginal = ImageIO.read(inputStream);

        //cria nova imagem em memória com transparencia e tamanho novo
        int largura = imagemOriginal.getWidth(); 
        int altura = imagemOriginal.getHeight();
        int novaAltura = altura + 400;
        BufferedImage novaImagem= new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

        //copiar a imagem original para nova imagem (em memória)
        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(imagemOriginal, 0, 0, null);

        //configurar a fonte
        var fonte = new Font(Font.SANS_SERIF, Font.BOLD, 200);
        graphics.setFont(fonte);
        graphics.setColor(Color.YELLOW);

        //posição do texto
        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle2D retangulo = fontMetrics.getStringBounds(frase, graphics);
        int larguraTexto = (int) retangulo.getWidth();
        int posicaoTextoX = (largura - larguraTexto) / 2 ;
     
        // Create a new Shape object from the text, using the font and Graphics2D
        GlyphVector gv = fonte.createGlyphVector(graphics.getFontRenderContext(), frase);
        Shape textShape = gv.getOutline(posicaoTextoX, novaAltura - 100);

        // Set the stroke to create an outline border around the text
        Color strokeColor = Color.BLACK;
        Stroke stroke = new BasicStroke(15.0f);
        graphics.setStroke(stroke);

        // Draw the text shape with the stroke color
        graphics.setPaint(strokeColor);
        graphics.draw(textShape);

        // Draw the text with the font color
        graphics.setPaint(Color.YELLOW);
        graphics.fill(textShape);

        /*
        * in this updated code, we create a Shape object from the text using createGlyphVector and getOutline methods. 
        * Then we set the stroke to create an outline border around the text and draw the textShape with the stroke color 
        * using graphics.draw. Finally, we draw the textShape with the font color using graphics.fill. This way, 
        * the text is drawn only once, with the correct colors and the desired outline border.
         */

        //escrever nova imagem em um arquivo
        long millis = System.currentTimeMillis();
        ImageIO.write(novaImagem,"png",new File("saida/imagem_" + millis + ".png"));

    }
}
