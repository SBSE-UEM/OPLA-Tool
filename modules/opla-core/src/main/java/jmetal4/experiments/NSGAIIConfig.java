package jmetal4.experiments;

import jmetal4.core.experiments.ExperimentCommomConfigs;

public class NSGAIIConfig extends ExperimentCommomConfigs {

    private int populationSize;

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        super.validateGreaterOrEqualOne("populationSize", populationSize);
        this.populationSize = populationSize;
    }


}