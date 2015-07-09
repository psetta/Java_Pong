import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
 
 
 
public class Pong extends JFrame implements KeyListener {
  
	private int anchoVentana=600;
	private int altoVentana=400;
	
	private int anchoRaqueta=15;
	private int altoRaqueta=60;
	private int velRaqueta=5;
	
	private int velPelotaX=10;
	private int velPelotaY=10;
	private int ladoPelota=20;

	private Pelota pelota = new Pelota(anchoVentana/2-ladoPelota/2, altoVentana/2-ladoPelota/2, velPelotaX, velPelotaY, ladoPelota);
	private Paleta raquetaIzquierda = new Paleta(altoVentana/2-altoRaqueta/2, altoRaqueta, anchoRaqueta, velRaqueta);
	private Paleta raquetaDerecha = new Paleta(altoVentana/2-altoRaqueta/2, altoRaqueta, anchoRaqueta, velRaqueta);
	
	private int key=0;
	private long tiempoDeRun=0;
	private long tiempoDeSleep=10;
	
	
	
	
    public static void main(String[] args) {
        new Pong();
    }
   
    public Pong() {
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(anchoVentana, altoVentana);
        this.setResizable(false);
        this.setLocation(100, 100);
        this.setVisible(true);
       
        this.createBufferStrategy(2);
        this.addKeyListener(this);
        
        
        while(true){
        	calcularColisiones();
        	desplazamientoPelota();
        	desplazamientoRaquetaIzquierda();
        	desplazamientoRaquetaDerecha();
        }
        
    }
    
    private void calcularColisiones(){
    	//Colision paredes (superior e inferior) con la bola
    	if (pelota.y >= altoVentana-ladoPelota || pelota.y <=0){
    		pelota.veloY = -pelota.veloY;
    	}
    	
    	//Colision de la bola con las paletas
    	if(pelota.x >= 0 && pelota.x<=anchoRaqueta && pelota.y >= raquetaIzquierda.y && pelota.y <= raquetaIzquierda.y +altoRaqueta)
    		pelota.veloX = -pelota.veloX;
    	
    	if (pelota.x >= anchoVentana-raquetaDerecha.ancho && pelota.x <=anchoVentana && pelota.y >= raquetaDerecha.y && pelota.y <= raquetaDerecha.y +altoRaqueta)
    		pelota.veloX = -pelota.veloX;	
    }
       
    private void desplazamientoRaquetaIzquierda() {
    	
    	if(raquetaIzquierda.y>0 && key==KeyEvent.VK_UP) 
    		raquetaIzquierda.y -= raquetaIzquierda.velPaleta;
    	if(raquetaIzquierda.y <altoVentana-altoRaqueta && key==KeyEvent.VK_DOWN) 
    		raquetaIzquierda.y += raquetaIzquierda.velPaleta;
    	
    }
    
    private void desplazamientoRaquetaDerecha(){
    	//continuar
    	raquetaDerecha.y = pelota.y + ladoPelota/2 - altoRaqueta/2;
    }
    
    private void desplazamientoPelota(){
    	pelota.x += pelota.veloX;
    	pelota.y += pelota.veloY;
    }
    
    @Override
    public void keyPressed(KeyEvent e){
        key=e.getKeyCode();
    }
    
    @Override
    public void keyReleased(KeyEvent e){
        key=0;
    }
    
    @Override
    public void keyTyped(KeyEvent e){
        
    }
}
