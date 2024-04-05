package com.javeriana.model;

import java.util.ArrayList;
import java.util.List;

public class Robot {

    private String code;
    private double maxWeight;
    private List<Component> components = new ArrayList<Component>();

    // No-arg constructor

    public Robot() {
    }

    public Robot(String code, double maxWeight) {
        this.code = code;
        this.maxWeight = maxWeight;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public int getComponentsWeight() {
        int totalWeight = 0;
        for (Component component : this.components) {
            totalWeight += component.getWeight();
        }
        return totalWeight;
    }

    public boolean addComponent(int id, String name, double weight) {
        Component component = new Component(id, name, weight);
        boolean canAdd = this.maxWeight > component.getWeight() +  this.getComponentsWeight();
        if (canAdd) {
           return this.components.add(component);
        }

        return false;
    }

    public List<String> getComponentsNames() {
        List<String> componentsNames = new ArrayList<String>();
        for (Component component : this.components) {
            componentsNames.add(component.getName());
        }
        return componentsNames;
    }



}
