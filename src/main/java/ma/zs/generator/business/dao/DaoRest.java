/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.dao;

import ma.zs.generator.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Mohcine
 */
@RestController()
@RequestMapping({"/generator/rest/dao"})
public class DaoRest {

    @Autowired
    private DaoEngineImpl daoEngineImpl;

    @PostMapping("/")
    public void generate() throws Exception {

//        FileUtil.createDirectory(daoEngineImpl.getPath());

        daoEngineImpl.generete();
    }

    public DaoEngineImpl getDaoEngineImpl() {
        return daoEngineImpl;
    }

    public void setDaoEngineImpl(DaoEngineImpl daoEngineImpl) {
        this.daoEngineImpl = daoEngineImpl;
    }

}
