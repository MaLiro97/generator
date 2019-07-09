/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.zs.generator.business.projet;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author A O
 */
@RestController
@RequestMapping("/generator/project")
public class ProjetBusinessRest {

    @Autowired
    private ProjetBusinessEngine projetBusinessEngine;

    @PostMapping("/")
    public void generateProject() throws IOException, Exception {
        projetBusinessEngine.generateProject();
    }

    public ProjetBusinessEngine getProjetBusinessEngine() {
        return projetBusinessEngine;
    }

    public void setProjetBusinessEngine(ProjetBusinessEngine projetBusinessEngine) {
        this.projetBusinessEngine = projetBusinessEngine;
    }

}
