package com.aau.p3.service;

import com.aau.p3.controller.MainController;

/* Interface shared amongst the subclasses that will need to hold a reference back to the MainController
*  allows for two-way communication and thus linking the controllers to the MainControllers */
public interface ControlledScreen {
    void setMainController(MainController mainController);
}
