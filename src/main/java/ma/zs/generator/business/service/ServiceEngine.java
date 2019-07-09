package ma.zs.generator.business.service;

import java.io.IOException;
import ma.zs.generator.business.pojo.Pojo;

public interface ServiceEngine {

    public void generate() throws IOException;

    public String generateFile(Pojo pojo);

    public String generateImport(Pojo pojo);

    public String generateClassHeader(Pojo pojo);

    public String generateSave(Pojo pojo);

    public String generateSaveWithItems(Pojo pojo);

    public String generateRemove(Pojo pojo);

    public String generateRemoveById(Pojo pojo);

    public String generateRemoveByReference(Pojo pojo);

    public String generateFindById(Pojo pojo);

    public String generateFindByReference(Pojo pojo);

    public String generateFindAll(Pojo pojo);

    public String generateFindByCriteria(Pojo pojo);

    public String generateClone(Pojo pojo);

    public String generateCloneList(Pojo pojo);

    public String constructClassName(Pojo pojo);

    public String constructFileName(Pojo pojo);
}
