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
	
	private int velPelotaX=5;
	private int velPelotaY=5;
	private int ladoPelota=20;

	private Pelota pelota = new Pelota(anchoVentana/2-ladoPelota/2, altoVentana/2-ladoPelota/2, velPelotaX, velPelotaY, ladoPelota);
	private Paleta raquetaIzquierda = new Paleta(altoVentana/2-altoRaqueta/2, altoRaqueta, anchoRaqueta, velRaqueta);
	private Paleta raquetaDerecha = new Paleta(altoVentana/2-altoRaqueta/2, altoRaqueta, anchoRaqueta, velRaqueta);
	
	private int key=0;
	private long tiempoDeRun;
	private long tiempoDeSleep=20;
	private boolean run = true;
	private int tiempoDeEspera = 1000;
	
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
        
        while (true) {
        	if (!run)
				try {
					Thread.sleep(500);
					run=true;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	
            while(run){
            	calcularColisiones();
            	desplazamientoPelota();
            	desplazamientoRaquetaIzquierda();
            	desplazamientoRaquetaDerecha();
            	reiniciarJuego();
            	pintar();
            	sleep();
            }
        }

    }
    
    private void calcularColisiones(){
    	//Colision paredes (superior e inferior) con la bola
    	//Aqui la posicion minima que le ponemos a y es 25 por el marco de la ventana
    	if (pelota.y >= altoVentana-ladoPelota || pelota.y <=25){
    		pelota.veloY = -pelota.veloY;
    	}
    	
    	//Colision de la bola con las paletas
    	if(pelota.x >= 0 && pelota.x<=anchoRaqueta && pelota.y >= raquetaIzquierda.y && pelota.y <= raquetaIzquierda.y +altoRaqueta)
    		pelota.veloX = -pelota.veloX;
    	//ponemos el 20 por posible marco
    	if (pelota.x >= anchoVentana-raquetaDerecha.ancho-20 && pelota.x <=anchoVentana && pelota.y >= raquetaDerecha.y && pelota.y <= raquetaDerecha.y +altoRaqueta)
    		pelota.veloX = -pelota.veloX;	
    }
       
    private void desplazamientoRaquetaIzquierda() {
    	
    	//Aqui la posicion minima que le ponemos a y es 25 por el marco de la ventana    	
    	if(raquetaIzquierda.y>25 && key==KeyEvent.VK_UP) 
    		raquetaIzquierda.y -= raquetaIzquierda.velPaleta;
    	if(raquetaIzquierda.y <altoVentana-altoRaqueta && key==KeyEvent.VK_DOWN) 
    		raquetaIzquierda.y += raquetaIzquierda.velPaleta;
    	
    }
    
    private void desplazamientoRaquetaDerecha(){ //mirar
    	if(pelota.y>=raquetaDerecha.alto/2+pelota.ladoPelota/2 && pelota.y <= altoVentana-raquetaDerecha.alto/2-pelota.ladoPelota/2)
    		raquetaDerecha.y = pelota.y + ladoPelota/2 - altoRaqueta/2;
    }
    
    private void desplazamientoPelota(){
    	pelota.x += pelota.veloX;
    	pelota.y += pelota.veloY;
    	
    }
    
    private void reiniciarJuego(){
    	if (pelota.x<0 || pelota.x > anchoVentana){
    		pelota.x = anchoVentana/2 - ladoPelota/2;
    		pelota.y = altoVentana/2 - ladoPelota/2;
    		raquetaIzquierda.y = altoVentana/2 - altoRaqueta/2;
    		raquetaDerecha.y = altoVentana/2 - altoRaqueta/2;
    		run=false;
    	}
    }
    
    private void pintar(){
        BufferStrategy bf = this.getBufferStrategy();
        Graphics g = null;
       
        try {
            g = bf.getDrawGraphics();
                
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, anchoVentana, altoVentana);
            
            //pintamos la pelota
            g.setColor(Color.WHITE);
            g.fillOval(pelota.x, pelota.y, pelota.ladoPelota, pelota.ladoPelota);

            //pintamos las paletas
            g.setColor(Color.WHITE);
            g.fillRect(0, raquetaIzquierda.y, raquetaIzquierda.ancho, raquetaIzquierda.alto);
            g.fillRect(anchoVentana-raquetaDerecha.ancho, raquetaDerecha.y, raquetaDerecha.ancho, raquetaDerecha.alto);
            
        } finally {
            g.dispose();
        }
        bf.show();
             
        Toolkit.getDefaultToolkit().sync();
    }
    
    private void sleep(){
    	tiempoDeRun = ( System.currentTimeMillis() + tiempoDeSleep );
        while(System.currentTimeMillis() < tiempoDeRun) {
        
        }
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
