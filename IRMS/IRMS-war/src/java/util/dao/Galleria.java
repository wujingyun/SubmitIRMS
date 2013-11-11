/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

/**
 *
 * @author WU JINGYUN
 */
public class Galleria {
    private String image;
    private String description;

    public Galleria() {
    }

    public Galleria(String image, String description) {
        this.image = image;
        this.description = description;
    }
    
    

   
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

   
    public String getDescription() {
        return description;
    }

    
    public void setDescription(String description) {
        this.description = description;
    }
}
