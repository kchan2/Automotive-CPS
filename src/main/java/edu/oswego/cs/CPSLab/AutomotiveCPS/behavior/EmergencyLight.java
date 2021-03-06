/*
 * Class for Emergency Vehicle Light
 */
package edu.oswego.cs.CPSLab.AutomotiveCPS.behavior;

import edu.oswego.cs.CPSLab.AutomotiveCPS.*;
import de.adesso.anki.messages.LightsPatternMessage;
import de.adesso.anki.messages.LightsPatternMessage.LightConfig;

/**
 *
 * @author notebook
 */
public class EmergencyLight extends Behavior {

    private LightConfig configOn1;
    private LightConfig configOn2;
    private LightConfig configOff1;
    private LightConfig configOff2;
    private LightsPatternMessage lpmOn;
    private LightsPatternMessage lpmOff;
    private boolean lightOn;

    public EmergencyLight(CPSCar car) {
        super(car);

        configOn1 = new LightConfig(LightsPatternMessage.LightChannel.ENGINE_RED, LightsPatternMessage.LightEffect.STROBE, 0, 0, 0);
        //configOn2 = new LightConfig(LightsPatternMessage.LightChannel.ENGINE_BLUE, LightsPatternMessage.LightEffect.STROBE, 0, 0, 0);
        lpmOn = new LightsPatternMessage();
        lpmOn.add(configOn1);
        //lpmOn.add(configOn2);

        configOff1 = new LightConfig(LightsPatternMessage.LightChannel.ENGINE_RED, LightsPatternMessage.LightEffect.STEADY, 0, 0, 0);
        //configOff2 = new LightConfig(LightsPatternMessage.LightChannel.ENGINE_BLUE, LightsPatternMessage.LightEffect.STEADY, 0, 0, 0);
        lpmOff = new LightsPatternMessage();
        lpmOff.add(configOff1);
        //lpmOff.add(configOff2);

        lightOn = false;
    }

    public void run() {
        if (lightOn) {
            car.sendMessage(lpmOff);
            lightOn = false;
        } else {
            car.sendMessage(lpmOn);
            lightOn = true;
        }
    }
    
    public void turnOn(){
        if (!lightOn){
            car.sendMessage(lpmOn);
            lightOn = true;
        }
    }
    public void turnOff(){
        if (lightOn) {
            car.sendMessage(lpmOff);
            lightOn = false;
        } 
    }

}
