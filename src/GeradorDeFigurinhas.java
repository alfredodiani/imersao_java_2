import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
//import java.io.FileInputStream;
//import java.net.URL;

import javax.imageio.ImageIO;

public class GeradorDeFigurinhas {
    
    public void cria(InputStream inputStream,String frase) throws Exception{
        
        //leitura da imagem
        //InputStream inputStream = new FileInputStream(new File("entrada/filme.jpg"));
        //InputStream inputStream = new URL("https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies_9.jpg").openStream();
        //BufferedImage imagemOriginal = ImageIO.read(new URL("https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies_9.jpg"));
        BufferedImage imagemOriginal = ImageIO.read(inputStream);

        //cria nova imagem em memória com transparencia e tamanho novo
        int largura = imagemOriginal.getWidth(); 
        int altura = imagemOriginal.getHeight();
        int novaAltura = altura + 200;
        BufferedImage novaImagem= new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

        //copiar a imagem original para nova imagem (em memória)
        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(imagemOriginal, 0, 0, null);

        //configurar a fonte
        var fonte = new Font("Font.SANS_SERIF", Font.BOLD, 80);
        graphics.setFont(fonte);
        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle2D retangulo = fontMetrics.getStringBounds(frase, graphics);
        int larguraTexto = (int) retangulo.getWidth();
        int posicaoTextoX = (largura - larguraTexto) / 2 ;
       
        //escrever uma frase na imagem
        graphics.drawString(frase, posicaoTextoX, novaAltura -100);

        //criando contorno
        FontRenderContext fontRenderContext = graphics.getFontRenderContext();
        TextLayout textLayout = new TextLayout(frase, fonte , fontRenderContext);
        
        Shape outline = textLayout.getOutline(null);
        AffineTransform transform = graphics.getTransform();
        transform.translate(posicaoTextoX, novaAltura -100);
        graphics.setTransform(transform);

        Stroke outlineStroke = new BasicStroke(largura * 0.008f);
        graphics.setStroke(outlineStroke);
        graphics.setColor(Color.BLACK);
        graphics.draw(outline);
        graphics.setColor(Color.YELLOW);
        graphics.fill(outline);

        //escrever nova imagem em um arquivo
        long millis = System.currentTimeMillis();
        ImageIO.write(novaImagem,"png",new File("saida/imagem_" + millis + ".png"));

    }
}
