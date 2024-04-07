package com.javeriana.model;

import java.util.ArrayList;
import java.util.List;

public class Robot {

    private String code;
    private double maxWeight;

    private List<Component> components;

    public Robot(String code, double maxWeight) {
        this.code = code;
        this.maxWeight = maxWeight;
        this.components = new ArrayList<>();
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

    public List<Component> getComponents() {
        return components;
    }

    public boolean addComponent(int id, String name, double weight) {
        Component component = new Component(id, name, weight);

        if (this.getComponentsWeight() + component.getWeight() > this.maxWeight) {
            return false;
        }

        return this.components.add(component);
    }

    public double getComponentsWeight() {
        double totalWeight = 0;
        for (Component component : this.components) {
            totalWeight += component.getWeight();
        }
        return totalWeight;
    }

    public List<String> getComponentsNames() {
        List<String> names = new ArrayList<>();
        for (Component component : this.components) {
            names.add(component.getName());
        }
        return names;
    }




}
