package jtvprog;

/**
 * XML configuration handle class;
 * @author Stanislav Nepochatov
 */
public class config {
    
    config() {
        
    }
    
    /**
     * Array of tv channels
     */
    private String[] tvOrder;
    
    /**
     * Get list of channels as string array
     * @return array with channels by order
     */
    public String[] getOrder() {
        return this.tvOrder;
    }
    
    /**
     * Set new index to specified item
     * @param oldIndex
     * @param newIndex 
     */
    public void setIndex(int oldIndex, int newIndex) {
        
    }
    
}
