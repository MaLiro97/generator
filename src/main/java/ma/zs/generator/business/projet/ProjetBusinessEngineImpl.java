/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.projet;

import java.io.IOException;
import ma.zs.generator.business.bean.BeanEngine;
import ma.zs.generator.business.converter.ConverterEngine;
import ma.zs.generator.business.dao.DaoEngine;
import ma.zs.generator.business.rest.RestEngine;
import ma.zs.generator.business.service.ServiceEngine;
import ma.zs.generator.business.service.impl.ServiceImplEngine;
import ma.zs.generator.business.utilEngine.UtilEngine;
import ma.zs.generator.business.vo.VoEngine;
import ma.zs.generator.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Anas
 */
@Component
public class ProjetBusinessEngineImpl implements ProjetBusinessEngine {

    @Autowired
    private ServiceEngine serviceEngine;
    @Autowired
    private ServiceImplEngine serviceImplEngine;
    @Autowired
    private RestEngine restEngine;
    @Autowired
    private ConverterEngine converterEngine;
    @Autowired
    private UtilEngine utilEngine;
    @Autowired
    private DaoEngine daoEngine;
    @Autowired
    private BeanEngine beanEngine;
    @Autowired
    private VoEngine voEngine;

    @Override
    public void generateProjectDir() throws IOException {
        FileUtil.createDirectory(ProjetBusinessConfig.getProjectPathWithPackagePath());
    }

    /**
     *
     * @throws IOException
     * @throws Exception
     */
    @Override
    public void generateProject() throws IOException, Exception {
        generateProjectDir();
        beanEngine.generateBeans();
        daoEngine.generete();
        serviceEngine.generate();
        serviceImplEngine.generate();
        restEngine.generateRest();
        converterEngine.generateConverter();
        utilEngine.generate();
        voEngine.generateVo();
        //add dao engine
        //add vo engine

    }

    public RestEngine getRestEngine() {
        return restEngine;
    }

    public void setRestEngine(RestEngine restEngine) {
        this.restEngine = restEngine;
    }

 

}
