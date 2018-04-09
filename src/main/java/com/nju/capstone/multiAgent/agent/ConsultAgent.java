package com.nju.capstone.multiAgent.agent;

import com.nju.capstone.multiAgent.agentHelper.DFUtil;
import com.nju.capstone.multiAgent.behavior.listener.ConsultListener;
import com.nju.capstone.multiAgent.ontology.BidOntology;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.*;
import jade.core.Agent;

/**
 * Created by h77 on 2017/5/5.
 * 简单的协商Agent
 */
public class ConsultAgent extends Agent {

    private Codec codec = new SLCodec();
    private Ontology ontology = BidOntology.getInstance();

    protected void setup() {
        getContentManager().registerLanguage(codec);
        getContentManager().registerOntology(ontology);
        DFUtil.registerService(this,"consult");
        System.out.println("创建 ConsultAgent");
        addBehaviour(new ConsultListener(this));
    }
}