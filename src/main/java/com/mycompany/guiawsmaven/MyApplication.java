/*
 * https://www.youtube.com/watch?v=q50bFzRF4yo&t=254s
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.guiawsmaven;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author Andressa
 */
@ApplicationPath("rest")
public class MyApplication extends ResourceConfig {
    
    public MyApplication() {
        packages("controller");
    }   
}
