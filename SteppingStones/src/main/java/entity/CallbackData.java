/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entity.Tutor;

/**
 *
 * @author huixintang
 */
public class CallbackData implements Callback {//class that implements the method to callback defined in the interface
    public static Object obj;
    
    @Override
    public void callback(Object obj) {
        if (obj.getClass() == Tutor.class){
            this.obj = obj;
        }
    }
}
