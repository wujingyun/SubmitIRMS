/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

/**
 *
 * @author wangxiahao
 */
public class TenancyMixData {
    private String category;
    private Integer number;

    public TenancyMixData() {
    }

    public TenancyMixData(String category, Integer number) {
        this.category = category;
        this.number = number;
    }
    
    

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the number
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(Integer number) {
        this.number = number;
    }
}
