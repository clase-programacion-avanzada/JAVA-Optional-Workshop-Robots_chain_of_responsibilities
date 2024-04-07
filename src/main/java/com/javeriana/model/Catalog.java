package com.javeriana.model;

import java.util.ArrayList;
import java.util.List;

public class Catalog {

    private List<Robot> robotsCatalog;

    public Catalog() {
        this.robotsCatalog = new ArrayList<>();
    }

    public boolean addRobot(String code, double maxWeight) {
        Robot robot = new Robot(code, maxWeight);
        if (searchRobotByCode(code) != null) {
            return false;
        }
        return this.robotsCatalog.add(robot);
    }

    public List<Robot> getRobotsCatalog() {
        return robotsCatalog;
    }

    public Robot searchRobotByCode(String code) {
        for (Robot robot : this.robotsCatalog) {
            if (robot.getCode().equals(code)) {
                return robot;
            }
        }
        return null;
    }

    public boolean addComponentToRobot(String code, int id, String name, double weight) {
        Robot robot = this.searchRobotByCode(code);
        if (robot == null) {
            return false;
        }
        return robot.addComponent(id, name, weight);
    }

    public boolean removeRobot(String code) {
        Robot robot = this.searchRobotByCode(code);
        if (robot == null) {
            return false;
        }
        return this.robotsCatalog.remove(robot);
    }

    public List<String> getComponentsNamesUsedInAllRobots() {
        List<String> componentsNames = new ArrayList<>();
        for (Robot robot : this.robotsCatalog) {
            for (Component component : robot.getComponents()) {
                if (!componentsNames.contains(component.getName())) {
                    componentsNames.add(component.getName());
                }
            }
        }
        return componentsNames;
    }




}
