package com.michelDevs;

import com.powsybl.iidm.network.*;

public class GameEngine {


    public Network getNetwork() {

        Network network = Network.create("Def","MyGameNetwork");

        //PRODUCTION

        Substation s1 = network.newSubstation()
                .setId("Poste_soure")
                .setCountry(Country.FR)
                .add();

        VoltageLevel vl1 = s1.newVoltageLevel()
                .setId("VL_source_400")
                .setNominalV(400.0)
                .setTopologyKind(TopologyKind.BUS_BREAKER)
                .add();

        vl1.getBusBreakerView().newBus().setId("Bus1").add();

        vl1.newGenerator()
                .setId("Gen_Nucleaire")
                .setMinP(0.0)
                .setMaxP(1000.0)
                .setVoltageRegulatorOn(true)
                .setTargetP(100.0)
                .setTargetV(400.0)
                .setBus("Bus1")
                .add();


        //CONSOMMATION

        Substation s2 = network.newSubstation()
                .setId("Poste_Ville")
                .setCountry(Country.FR)
                .add();

        VoltageLevel vl2 = s2.newVoltageLevel()
                .setId("VL_Ville_400")
                .setNominalV(400.0)
                .setTopologyKind(TopologyKind.BUS_BREAKER)
                .add();

        vl2.getBusBreakerView().newBus().setId("Bus2").add();
        vl2.newLoad()
                .setId("Conso_paris")
                .setP0(100.0)
                .setQ0(0.0)
                .setBus("Bus2")
                .add();


        //LIGNES

        network.newLine()
                .setId("Ligne_HT")
                .setR(1.0).setX(10.0).setG1(0.0).setG2(0.0).setB1(0.0).setB2(0.0)
                .setVoltageLevel1(vl1.getId()).setBus1("Bus1")
                .setVoltageLevel2(vl2.getId()).setBus2("Bus2")
                .add();

        return network;
    }
}
