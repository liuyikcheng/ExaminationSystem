/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package querylist;

/**
 *
 * @author Krissy
 */
public class Collector {
    Integer collector_id;
    Integer paper_id;
    String staffId;
    String bundleId;
    
    public Collector(){}
    
    public Collector(   Integer collector_id,
                        Integer paper_id,
                        String staffId,
                        String bundleId
                        ){
        this.collector_id = collector_id;
        this.paper_id = paper_id;
        this.staffId = staffId;
        this.bundleId = bundleId;
    }
    
    public Integer getPaper(){
        return this.paper_id;
    }
    
    public String getStaffId(){
        return this.staffId;
    }
}
