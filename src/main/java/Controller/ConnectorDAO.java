/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import GUI.Parameter;
import de.adesso.anki.AnkiConnector;
import de.adesso.anki.Vehicle;
import edu.oswego.cs.CPSLab.AutomotiveCPS.CPSCar;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author HN
 */
public class ConnectorDAO {
    private final AnkiConnector ankiConnector;
    private List<VehicleDAO> vehicles;
    private VehicleDAO selectedVehicle;
    private HashMap<String, VehicleDAO> existedVehicles;
    
    public ConnectorDAO(String ip, int port) throws IOException{
        this.ankiConnector = new AnkiConnector(ip,port);
        this.existedVehicles = new HashMap<>();
        this.vehicles = new ArrayList<>();
    }
    
    public AnkiConnector getAnkiConnector(){
        return ankiConnector;
    }

    public List<VehicleDAO> getVehicles() {
        return this.vehicles;
    }

    public void setVehicles(List<VehicleDAO> vehicles) {
        this.vehicles = vehicles;
    }

    public VehicleDAO getSelectedVehicle() {
        return selectedVehicle;
    }

    public void setSelectedVehicle(VehicleDAO selectedVehicle) {
        this.selectedVehicle = selectedVehicle;
    }
    
    public void addVehicle(Vehicle v){
        try{
            System.out.println("   " + v);
            System.out.println("      ID: " + v.getAdvertisement().getIdentifier());
            System.out.println("      Model: " + v.getAdvertisement().getModel());
            System.out.println("      Model ID: " + v.getAdvertisement().getModelId());
            System.out.println("      Product ID: " + v.getAdvertisement().getProductId());
            System.out.println("      Address: " + v.getAddress());
            System.out.println("      Color: " + v.getColor());
            System.out.println("      charging? " + v.getAdvertisement().isCharging());
                
            VehicleDAO vehicle = new VehicleDAO();
            vehicle.setCpsCar(new CPSCar(v));
            vehicle.setImg("GUI/img/Vehicle/"+v.getAdvertisement().getModel()+".png");
            this.vehicles.add(vehicle);
            this.existedVehicles.put(""+v.getAdvertisement().getIdentifier(),vehicle);
        }
        catch(Exception e){
            e.printStackTrace();
        }      
    }
    public void removeVehicle(String key){
        try{
            if(this.existedVehicles.containsKey(key)){
                VehicleDAO vehicle = this.existedVehicles.get(key);
                vehicles.remove(vehicle);
                this.existedVehicles.remove(key);               
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateVehicles(){     
        try{
            List<Vehicle> lst = ankiConnector.findVehicles();           
            for (Vehicle v : lst) {
                String key = ""+v.getAdvertisement().getIdentifier();
                System.out.print("Get car: "+v.getAdvertisement().getModel());
                if(!this.existedVehicles.containsKey(key)){
                    addVehicle(v);
                }                  
            }
        }     
        catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public List<String> getLightBehavior(){
        List<String> lights = new ArrayList();
        lights.add(Parameter.BEHAVIOR_BRAKE_LIGHT);
        lights.add(Parameter.BEHAVIOR_EMERGENCY_LIGHT);
        lights.add(Parameter.BEHAVIOR_FOUR_WAY_HAZARD_LIGHT);   
        return lights;
    }
    
    public List<String> getMovementBehavior(){
        List<String> lights = new ArrayList();
        lights.add(Parameter.BEHAVIOR_CHANGE_LANE);
        lights.add(Parameter.BEHAVIOR_EMERGENCY_STOP);
        lights.add(Parameter.BEHAVIOR_PULL_OVER);   
        return lights;
    }

    /**
     * Perform BEHAVIORS
     */  
    public void performBehavior(String behavior) {
        switch(behavior){
            case Parameter.BEHAVIOR_BRAKE_LIGHT:
                turnOnBrakeLight();
                break;
                
            case Parameter.BEHAVIOR_EMERGENCY_LIGHT:
                turnOnEmergencyLight();
                break;
                
            case Parameter.BEHAVIOR_FOUR_WAY_HAZARD_LIGHT:
                turnOnFourWayHazardLight();
                break;
        }
    }
    
    public void stopBehavior(String behavior){
        switch(behavior){
            case Parameter.BEHAVIOR_BRAKE_LIGHT:
                turnOffBrakeLight();
                break;
                
            case Parameter.BEHAVIOR_EMERGENCY_LIGHT:
                turnOffEmergencyLight();
                break;
                
            case Parameter.BEHAVIOR_FOUR_WAY_HAZARD_LIGHT:
                turnOffFourWayHazardLight();
                break;
        }
    }
    
    public void turnOnBrakeLight(){
        if (selectedVehicle==null)
            return;
        selectedVehicle.turnOnBrakeLight();
    }
    
    public void turnOffBrakeLight(){
        if (selectedVehicle==null)
            return;
        selectedVehicle.turnOffBrakeLight();
    }
    
    public void turnOnEmergencyLight(){
        if (selectedVehicle==null)
            return;
        selectedVehicle.turnOnEmergencyLight();
    }
    
    public void turnOffEmergencyLight(){
        if (selectedVehicle==null)
            return;
        selectedVehicle.turnOffEmergencyLight();
    }
    
    public void turnOnFourWayHazardLight(){
        if (selectedVehicle==null)
            return;
        selectedVehicle.turnOnFourWayHazardLight();
    }
    
    public void turnOffFourWayHazardLight(){
        if (selectedVehicle==null)
            return;
        selectedVehicle.turnOffFourWayHazardLight();       
    }
    
}