package com.javeriana.model;

import java.util.ArrayList;
import java.util.List;

public class Catalog {

   private List<Robot> robotsCatalog = new ArrayList<Robot>();

   public Robot searchRobotByCode(String code) {
      for (Robot robot : robotsCatalog) {
         if (robot.getCode().equals(code)) {
            return robot;
         }
      }
      return null;
   }

   public boolean addRobot(String code, double maxWeight) {

      if(searchRobotByCode(code) != null) {
         return false;
      }

      Robot robot = new Robot(code, maxWeight);
      return robotsCatalog.add(robot);

   }

    public boolean addComponentToRobot(String code, int id, String name, double weight) {
        Robot robot = searchRobotByCode(code);
        if(robot == null) {
            return false;
        }

        return robot.addComponent(id, name, weight);
    }

    public boolean removeRobot(String code) {
        Robot robot = searchRobotByCode(code);
        if(robot == null) {
            return false;
        }

        return robotsCatalog.remove(robot);
    }

    public List<String> getComponentsNamesUsedInAllRobots() {
        List<String> componentsNames = new ArrayList<String>();
        for (Robot robot : robotsCatalog) {
            for (String componentName : robot.getComponentsNames()) {
                if(!componentsNames.contains(componentName)) {
                    componentsNames.add(componentName);
                }
            }
        }
        return componentsNames;
    }

    public List<Robot> getRobotsCatalog() {
        return robotsCatalog;
    }

}
