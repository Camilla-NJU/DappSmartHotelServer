package com.nju.capstone.multiAgent.agent;

import DO.tenant;
import com.nju.capstone.multiAgent.agentHelper.ValueCal;
import com.nju.capstone.multiAgent.behavior.listener.TenantApiListener;
import com.nju.capstone.multiAgent.behavior.listener.TenantListener;
import com.nju.capstone.multiAgent.behavior.logical.TenantBackOrderResult;
import com.nju.capstone.multiAgent.ontology.BidOntology;
import com.nju.capstone.multiAgent.ontology.Order;
import com.nju.capstone.util.CondVar;
import com.nju.capstone.vo.BidInfo;
import com.nju.capstone.vo.Consult;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by h77 on 2017/5/5.
 * 简单的房客Agent
 */
public class TenantAgent extends Agent {


    private Codec codec = new SLCodec();
    private Ontology ontology = BidOntology.getInstance();
    private tenant owner = null;
    //TenantAgent 生命周期
    private boolean isDone = false;
    private Map<Integer,Order> tenantTOorder;
    private LinkedBlockingQueue<List<BidInfo>> queues;
    private Map<Integer,List<Consult>> landlordTOconsult;

    protected void setup() {
        getContentManager().registerLanguage(codec);
        getContentManager().registerOntology(ontology);
        setEnabledO2ACommunication(true,10);
        Object[] args = getArguments();
        tenantTOorder = new HashMap<Integer, Order>();
        landlordTOconsult = new HashMap<Integer, List<Consult>>();
        if (args.length > 0) {
            CondVar latch = (CondVar) args[0];
            owner = (tenant) args[1];
            queues = (LinkedBlockingQueue<List<BidInfo>>)args[2];
            latch.signal();
        }
        System.out.println("创建 TenantAgent");
        ValueCal cal = new ValueCal();
//        cal.TrainrandomForest(this.getOwner().getId());
        addBehaviour(new TenantListener(this,cal));
        addBehaviour(new TenantApiListener(this));
        addBehaviour(new TenantBackOrderResult(null,null));
    }

//    public boolean done(){
//        //结束生命周期
//        return isDone;
//    }
    public tenant getOwner(){
        return owner;
    }
    public void setOrder(Integer id,Order order){ tenantTOorder.put(id,order);}
    public Order getOrder(Integer id){return tenantTOorder.get(id);}
    public void takeDown(){
        System.out.println("TenantAgent 被销毁");
        setEnabledO2ACommunication(false,0);
    }
    public void putResult(List<BidInfo> bid){
        try {
            this.queues.put(bid);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public List<Consult> getConsult(int landlordId){
        return landlordTOconsult.get(landlordId);
    }
    public void setConsult(int landlordId,List<Consult> consults){
        this.landlordTOconsult.put(landlordId,consults);
    }

}
