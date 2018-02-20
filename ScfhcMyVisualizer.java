/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioviz;

import static java.lang.Integer.min;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author aksha
 */
public class ScfhcMyVisualizer implements Visualizer {
    
    private final String name = "Scfhc My Visualizer";
    
    private Integer numBands;
    private AnchorPane vizPane;
    
    private String vizPaneOriginalStyle = "";
    
    private final Double bandHeightPercentage = 1.3;
    private final Double minRectHeight = 10.0;
    
    private Double width = 0.0;
    private Double height = 0.0;
    
    private Double bandWidth = 0.0;
    private Double bandHeight = 0.0;
    private Double halfBandHeight = 0.0;
    
    private final Double startHue = 260.0;
    
    private Rectangle[] rectangles;
    
    public ScfhcMyVisualizer(){
        
    }
    
    @Override
    public String getName(){
        return name;
    }
    
    @Override
    public void start(Integer numBands, AnchorPane vizPane){
        end();
        
        vizPaneOriginalStyle = vizPane.getStyle();
        
        this.numBands = numBands;
        this.vizPane = vizPane;
        
        height = vizPane.getHeight();
        width = vizPane.getWidth();
        
        bandWidth = width / numBands;
        bandHeight = height * bandHeightPercentage;
        halfBandHeight = bandHeight / 2;
        
        rectangles = new Rectangle[numBands];
        
        Rectangle clip = new Rectangle(width, height);
        clip.setLayoutX(0);
        clip.setLayoutY(0);
        vizPane.setClip(clip);
        
        for(int i = 0; i < numBands; i++){
            Rectangle rectangle = new Rectangle();
            //rectangle.setArcWidth(bandWidth / 2 + bandWidth * i);
            //rectangle.setArcHeight(bandHeight / 2);
            rectangle.setX(10);
            rectangle.setY(-50);
            rectangle.setLayoutX(bandWidth / 2 + bandWidth * i);
            rectangle.setLayoutY(bandHeight / 2);
            rectangle.setWidth(bandWidth / 2);
            rectangle.setHeight(minRectHeight);
            rectangle.setFill(Color.hsb(startHue, 0.5, 1.0, 1.0));
            vizPane.getChildren().add(rectangle);
            rectangles[i] = rectangle;
            
            
        }
    }
    
    @Override
    public void end(){
        if(rectangles != null){
            for(Rectangle rectangle: rectangles){
                vizPane.getChildren().remove(rectangle);
            }
            
            rectangles = null;   
            vizPane.setClip(null);
            vizPane.setStyle(vizPaneOriginalStyle);
        }
        
    }
    
    @Override
    public void update(double timestamp, double duration, float[] magnitudes, float[] phases) {
        if(rectangles == null){
            return;
        }
        
        Integer num = min(rectangles.length, magnitudes.length);
        
        for(int i = 0; i < num; i++){
            rectangles[i].setHeight(((60.0 + magnitudes[i])/60.0) * halfBandHeight + minRectHeight);
            rectangles[i].setFill(Color.hsb(startHue - (magnitudes[i] * 16.0), 1.0, 1.0, 1.0));
        }
        
        Double hue = ((60.0 + magnitudes[0])/60.0) * 360;
        vizPane.setStyle("-fx-background-color: hsb(" + hue + ", 50%, 100%)");
        
        
    }
    
            
    
    
}
